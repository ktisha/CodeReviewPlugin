package utils;


import com.google.common.collect.Lists;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import content.Review;
import content.outcome.CommitToReview;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;
import ui.ReviewBalloonBuilder;
import ui.forms.ReviewForm;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * User: ktisha
 */
public class ReviewManager implements DumbAware {
  private static final Logger LOG = Logger.getInstance(ReviewManager.class.getName());
  private static ReviewManager ourInstance;
  private final Project myProject;

  private ReviewManager(Project project) {
    myProject = project;
  }

  public static ReviewManager getInstance(Project project) {
    if (ourInstance == null) {
      ourInstance = new ReviewManager(project);
    }
    return ourInstance;
  }

  public void addReview(final Editor editor, int startOffset, int endOffset, String path) {
    final Review review = new Review(startOffset, endOffset, path);
    final ReviewForm reviewForm = new ReviewForm(review, editor.getProject());
    final ReviewBalloonBuilder reviewBalloonBuilder = new ReviewBalloonBuilder();
    reviewBalloonBuilder.showBalloon(review, editor, reviewForm, ReviewBundle.message("review.addReview"));
    reviewForm.requestFocus();
  }

  public void saveReview(Review review) {
    if (!StringUtil.isEmptyOrSpaces(review.getText()))
      ReviewService.getInstance(myProject).addReview(review);
  }

  public List<Review> getReviews() {
    return ReviewService.getInstance(myProject).getReviews();
  }

  public List<CommitToReview> getCommitsToReview() {
    final List<CommitToReview> reviews = Lists.newArrayList();

    try {
      URL url = new URL("http://127.0.0.1:8000/to_review?author="+System.getProperty("user.name"));
      final InputStream inputStream = url.openStream();
      XMLReader xr;
      Reader reader = new InputStreamReader(inputStream);
      try {
        xr = XMLReaderFactory.createXMLReader();
        ToReviewParser parser = new ToReviewParser(reviews);
        xr.setContentHandler(parser);
        xr.parse(new InputSource(reader));
      }
      catch (SAXException e) {
        LOG.warn(e.getMessage());
      }
      finally {
        reader.close();
      }
    }
    catch (MalformedURLException e) {
      LOG.warn(e.getMessage());
    }
    catch (IOException e) {
      LOG.warn(e.getMessage());
    }
    return reviews;
    //return ReviewService.getInstance(myProject).getCommitsToReview();
  }

  public List<String> getAuthors() {
    final List<String> authors = Lists.newArrayList();

    try {
      URL url = new URL("http://127.0.0.1:8000/all_authors");
      InputStream inputStream = url.openStream();
      Reader reader = new InputStreamReader(inputStream);
      XMLReader xr;
      try {
        xr = XMLReaderFactory.createXMLReader();
        AuthorsParser parser = new AuthorsParser(authors);
        xr.setContentHandler(parser);
        xr.parse(new InputSource(reader));
      }
      catch (SAXException e) {
        LOG.warn(e.getMessage());
      }
      finally {
        reader.close();
      }
    }
    catch (MalformedURLException e) {
      LOG.warn(e.getMessage());
    }
    catch (IOException e) {
      LOG.warn(e.getMessage());
    }
    return authors;
    //return ReviewService.getInstance(myProject).getAuthors();
  }

  private class ToReviewParser extends DefaultHandler {
    private final List<CommitToReview> myReviews;
    private CharArrayWriter myContent = new CharArrayWriter();

    public ToReviewParser(List<CommitToReview> reviews) {
      //To change body of created methods use File | Settings | File Templates.
      myReviews = reviews;
    }

    public void startElement(String namespaceURI,
                             String localName,
                             String qName,
                             Attributes attr) throws SAXException {
      myContent.reset();
    }

    public void endElement(String namespaceURI,
                           String localName,
                           String qName) throws SAXException {
      if (localName.equals("commit")) {
        CommitToReview commitToReview = new CommitToReview(myContent.toString().trim());
        myReviews.add(commitToReview);
      }
    }

    public void characters(char[] ch, int start, int length)
      throws SAXException {
      myContent.write(ch, start, length);
    }
  }

  private class AuthorsParser extends DefaultHandler {
    private final List<String> myAuthors;
    private CharArrayWriter myContent = new CharArrayWriter();

    public AuthorsParser(List<String> authors) {
      myAuthors = authors;
    }

    public void startElement(String namespaceURI,
                             String localName,
                             String qName,
                             Attributes attr) throws SAXException {
      myContent.reset();
    }

    public void endElement(String namespaceURI,
                           String localName,
                           String qName) throws SAXException {
      if (localName.equals("user")) {
        myAuthors.add(myContent.toString());
      }
    }

    public void characters(char[] ch, int start, int length)
      throws SAXException {
      myContent.write(ch, start, length);
    }
  }
}

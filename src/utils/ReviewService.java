package utils;

import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializer;
import content.Review;
import org.jdom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ktisha
 */
@State(name = "ReviewService",
       storages = {
         @Storage(file = StoragePathMacros.PROJECT_FILE),
         @Storage(file = StoragePathMacros.PROJECT_CONFIG_DIR + "/review.xml", scheme = StorageScheme.DIRECTORY_BASED)
       }
)
public class ReviewService implements PersistentStateComponent<Element> {
  private MyState myState = new MyState();

  public static class MyState {
    public List<Review> REVIEWS = new ArrayList<Review>();
    public List<String> COMMITS_TO_REVIEW = new ArrayList<String>();
    public List<String> COMMIT_AUTHORS = new ArrayList<String>();

    public MyState() {
    }
  }

  public ReviewService() {
  }

  @Override
  public Element getState() {
    return XmlSerializer.serialize(myState);
  }

  @Override
  public void loadState(Element state) {
    final MyState service = XmlSerializer.deserialize(state, MyState.class);
    if (service != null) {
      myState.REVIEWS.addAll(service.REVIEWS);
    }
  }

  public static ReviewService getInstance(Project project) {
    return ServiceManager.getService(project, ReviewService.class);
  }

  public void addReview(Review review) {
    myState.REVIEWS.add(review);
  }

  public List<Review> getReviews() {
    return myState.REVIEWS;
  }

  public List<String> getCommitsToReview() {
    return myState.COMMITS_TO_REVIEW;
  }

  public List<String> getAuthors() {
    return myState.COMMIT_AUTHORS;
  }
}

package actions;


import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import content.Review;
import ui.ReviewBalloonBuilder;
import ui.forms.ReviewForm;
import utils.ReviewBundle;
import utils.ReviewService;

import java.util.List;

/**
 * User: ktisha
 */
public class ReviewManager implements DumbAware {
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

  public List<String> getCommitsToReview() {
    return ReviewService.getInstance(myProject).getCommitsToReview();
  }
}

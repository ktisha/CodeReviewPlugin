package ui.toolWindow;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.openapi.project.Project;
import com.intellij.ui.SimpleTextAttributes;
import com.intellij.ui.treeStructure.SimpleNode;
import content.Review;
import org.jetbrains.annotations.NotNull;

/**
 * User: ktisha
 */
public class ReviewNode extends SimpleNode {
  private Review myReview;

  public ReviewNode(Project project, Review review) {
    super(project);
    myReview = review;
  }

  @Override
  public boolean isAlwaysLeaf() {
    return true;
  }

  @NotNull
  @Override
  public SimpleNode[] getChildren() {
    return new SimpleNode[0];
  }

  @Override
  protected void update(PresentationData presentationData) {
    presentationData.addText(myReview.getText(), SimpleTextAttributes.REGULAR_BOLD_ATTRIBUTES);
    final String filePath = myReview.getFilePath();
    int startOffset = myReview.getStartOffset();

    presentationData
      .addText(" (file : " + filePath + ", start offset " + String.valueOf(startOffset) + ")",
               SimpleTextAttributes.GRAYED_BOLD_ATTRIBUTES);
  }

  public Review getReview() {
    return myReview;
  }

  public void setReview(@NotNull final Review review) {
    myReview = review;
  }
}


package ui.toolWindow.outcome;

import utils.ReviewManager;
import com.intellij.ide.projectView.PresentationData;
import com.intellij.openapi.project.Project;
import com.intellij.ui.treeStructure.SimpleNode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ktisha
 */
public class OutcomeRootNode extends SimpleNode {
  private List<SimpleNode> myChildren = new ArrayList<SimpleNode>();

  public OutcomeRootNode(@NotNull final Project project) {
    super(project);
  }

  @NotNull
  @Override
  public SimpleNode[] getChildren() {
    if (myChildren.isEmpty()) {
      List<String> commitsToReview = ReviewManager.getInstance(myProject).getCommitsToReview();
      for (String commit : commitsToReview) {
        myChildren.add(new CommitNode(myProject, commit));
      }
    }
    return myChildren.toArray(new SimpleNode[myChildren.size()]);
  }

  @Override
  public void update(PresentationData data) {
    data.setPresentableText("To Review:");
  }
}


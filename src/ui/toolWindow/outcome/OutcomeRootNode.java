package ui.toolWindow.outcome;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.openapi.project.Project;
import com.intellij.ui.treeStructure.SimpleNode;
import content.outcome.CommitToReview;
import org.jetbrains.annotations.NotNull;
import utils.ReviewManager;

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
      List<CommitToReview> commitsToReview = ReviewManager.getInstance(myProject).getCommitsToReview();
      for (CommitToReview commit : commitsToReview) {
        myChildren.add(new CommitNode(myProject, commit.getCommitNo()));
      }
    }
    return myChildren.toArray(new SimpleNode[myChildren.size()]);
  }

  @Override
  public void update(PresentationData data) {
    data.setPresentableText("To Review:");
  }
}


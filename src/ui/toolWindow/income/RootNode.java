package ui.toolWindow.income;

import utils.ReviewManager;
import com.intellij.ide.projectView.PresentationData;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.treeStructure.SimpleNode;
import content.Review;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ktisha
 */
public class RootNode extends SimpleNode {
  private VirtualFile myFile;
  private List<SimpleNode> myChildren = new ArrayList<SimpleNode>();

  public RootNode(@NotNull final Project project, @NotNull final VirtualFile virtualFile) {
    super(project);
    myFile = virtualFile;
  }

  @NotNull
  @Override
  public SimpleNode[] getChildren() {
    if (myChildren.isEmpty()) {
      List<Review> reviews = ReviewManager.getInstance(myProject).getReviews();
      for (Review review : reviews) {
        myChildren.add(new ReviewNode(myProject, review));
      }
    }
    return myChildren.toArray(new SimpleNode[myChildren.size()]);
  }

  @Override
  public void update(PresentationData data) {
    data.setPresentableText(myFile.getName());
  }
}

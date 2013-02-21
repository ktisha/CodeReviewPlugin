package ui.toolWindow.outcome;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.openapi.project.Project;
import com.intellij.ui.SimpleTextAttributes;
import com.intellij.ui.treeStructure.SimpleNode;
import org.jetbrains.annotations.NotNull;

/**
 * User: ktisha
 */
public class CommitNode extends SimpleNode {

  private final String myCommit;

  public CommitNode(Project project, String commit) {
    super(project);
    myCommit = commit;
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
    presentationData.addText(myCommit, SimpleTextAttributes.REGULAR_ATTRIBUTES);
  }

  public String getCommit() {
    return myCommit;
  }
}


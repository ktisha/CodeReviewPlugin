package ui.toolWindow.outcome;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.FilePathImpl;
import com.intellij.openapi.vcs.changes.TextRevisionNumber;
import com.intellij.openapi.vcs.history.CurrentRevision;
import com.intellij.openapi.vcs.history.VcsFileRevision;
import com.intellij.openapi.vcs.history.VcsHistoryUtil;
import com.intellij.ui.SimpleTextAttributes;
import com.intellij.ui.treeStructure.SimpleNode;
import com.intellij.ui.treeStructure.SimpleTree;
import org.jetbrains.annotations.NotNull;

import java.awt.event.InputEvent;

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


  @Override
  public void handleDoubleClickOrEnter(SimpleTree tree, InputEvent inputEvent) {

    VcsHistoryUtil.showDifferencesInBackground(getProject(), new FilePathImpl(getProject().getProjectFile()),
                                               VcsFileRevision.NULL, new CurrentRevision(getProject().getProjectFile(),
                                                                                         new TextRevisionNumber(myCommit)), false);
  }

}


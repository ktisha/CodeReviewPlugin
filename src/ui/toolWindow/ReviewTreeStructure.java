package ui.toolWindow;

import com.intellij.openapi.project.Project;
import com.intellij.ui.treeStructure.SimpleNode;
import com.intellij.ui.treeStructure.SimpleTreeStructure;

/**
 * User: ktisha
 */

public class ReviewTreeStructure extends SimpleTreeStructure {
  private SimpleNode myRootElement;
  private Project myProject;

  public ReviewTreeStructure(Project project, SimpleNode root) {
    super();
    myProject = project;
    myRootElement = root;
  }

  public SimpleNode getRootElement() {
    return myRootElement;
  }
}

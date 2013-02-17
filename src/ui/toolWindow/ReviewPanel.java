package ui.toolWindow;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;

/**
 * User: ktisha
 * <p/>
 * Main code review panel
 */
public class ReviewPanel extends SimpleToolWindowPanel {
  private final Project myProject;

  public ReviewPanel(Project project) {
    super(false);
    myProject = project;
  }
}

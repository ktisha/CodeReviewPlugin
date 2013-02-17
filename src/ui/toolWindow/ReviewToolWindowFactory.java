package ui.toolWindow;

import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManager;

/**
 * User: ktisha
 */
public class ReviewToolWindowFactory implements ToolWindowFactory, DumbAware {

  @Override
  public void createToolWindowContent(Project project, ToolWindow toolWindow) {
    final ContentManager contentManager = toolWindow.getContentManager();
    ReviewPanel reviewPanel = new ReviewPanel(project);

    final Content incomeContent = ContentFactory.SERVICE.getInstance().createContent(reviewPanel, "Income", false);
    final Content outcomeContent = ContentFactory.SERVICE.getInstance().createContent(reviewPanel, "Outcome", false);

    contentManager.addContent(incomeContent);
    contentManager.addContent(outcomeContent);
  }
}

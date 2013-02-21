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
    ReviewPanel incomeReviewPanel = new ReviewPanel(project);
    OutcomeReviewPanel outcomeReviewPanel = new OutcomeReviewPanel(project);

    final Content incomeContent =
      ContentFactory.SERVICE.getInstance().createContent(incomeReviewPanel, "Income", false);
    final Content outcomeContent = ContentFactory.SERVICE.getInstance().createContent(outcomeReviewPanel, "Outcome", false);

    contentManager.addContent(outcomeContent);
    contentManager.addContent(incomeContent);
  }
}

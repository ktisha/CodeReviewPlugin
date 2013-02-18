package ui.toolWindow;

import com.intellij.ide.util.treeView.AbstractTreeBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.treeStructure.SimpleTree;
import com.intellij.ui.treeStructure.SimpleTreeStructure;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * User: ktisha
 * <p/>
 * Main code review panel
 */
public class ReviewPanel extends SimpleToolWindowPanel {
  private final Project myProject;
  private SimpleTree myReviewTree;

  public ReviewPanel(Project project) {
    super(false);
    myProject = project;

    SimpleTreeStructure reviewTreeStructure = createTreeStructure();
    DefaultMutableTreeNode root = new DefaultMutableTreeNode();
    final DefaultTreeModel model = new DefaultTreeModel(root);
    myReviewTree = new SimpleTree(model);
    AbstractTreeBuilder reviewTreeBuilder =
      new /*ReviewTreeBuilder*/ AbstractTreeBuilder(myReviewTree, model, reviewTreeStructure, null);
    myReviewTree.invalidate();

    JScrollPane scrollPane = ScrollPaneFactory.createScrollPane(myReviewTree);
    setContent(scrollPane);
  }

  private SimpleTreeStructure createTreeStructure() {
    final VirtualFile virtualFile = myProject.getBaseDir();
    final RootNode rootNode = new RootNode(myProject, virtualFile);
    return new ReviewTreeStructure(myProject, rootNode);
  }
}

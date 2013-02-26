package ui.toolWindow.outcome;

import com.intellij.ide.util.treeView.AbstractTreeBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.treeStructure.SimpleTree;
import com.intellij.ui.treeStructure.SimpleTreeStructure;
import ui.toolWindow.ReviewTreeStructure;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * User: ktisha
 * <p/>
 * 1. Shows commits needed to be reviewed by user
 * 2. Already reviewed
 */
public class OutcomeReviewPanel extends SimpleToolWindowPanel {
  private final Project myProject;
  private SimpleTree myReviewTree;

  public OutcomeReviewPanel(Project project) {
    super(false);
    myProject = project;

    SimpleTreeStructure reviewTreeStructure = createTreeStructure();

    DefaultMutableTreeNode root = new DefaultMutableTreeNode();
    final DefaultTreeModel model = new DefaultTreeModel(root);
    myReviewTree = new SimpleTree(model);
    AbstractTreeBuilder reviewTreeBuilder =
      new AbstractTreeBuilder(myReviewTree, model, reviewTreeStructure, null);
    myReviewTree.invalidate();

    JScrollPane scrollPane = ScrollPaneFactory.createScrollPane(myReviewTree);
    setContent(scrollPane);
  }

  private SimpleTreeStructure createTreeStructure() {
    final OutcomeRootNode rootNode = new OutcomeRootNode(myProject);
    return new ReviewTreeStructure(myProject, rootNode);
  }
}

package ui.commitDialog;

import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vcs.CheckinProjectPanel;
import com.intellij.openapi.vcs.ObjectsConvertor;
import com.intellij.openapi.vcs.changes.CommitContext;
import com.intellij.openapi.vcs.checkin.CheckinHandler;
import com.intellij.openapi.vcs.checkin.CheckinHandlerFactory;
import com.intellij.openapi.vcs.ui.RefreshableOnComponent;
import com.intellij.util.ArrayUtil;
import com.intellij.util.containers.Convertor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import utils.ReviewService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: ktisha
 */
public class ReviewCommitHandlerFactory extends CheckinHandlerFactory {

  @NotNull
  @Override
  public CheckinHandler createHandler(CheckinProjectPanel panel, CommitContext commitContext) {
    return new ReviewCommitHandler(panel);
  }

  private class ReviewCommitHandler extends CheckinHandler {
    private final CheckinProjectPanel myCheckinProjectPanel;
    private JCheckBox myToReview;
    private JComboBox myReviewer;

    public ReviewCommitHandler(CheckinProjectPanel panel) {
      myCheckinProjectPanel = panel;
    }

    @Override
    public ReturnResult beforeCheckin() {
      return super.beforeCheckin();
    }

    @Nullable
    @Override
    public RefreshableOnComponent getBeforeCheckinConfigurationPanel() {
      myToReview = new JCheckBox("Add reviewer ");
      myReviewer = new JComboBox();

      final List<String> authors = ReviewService.getInstance(myCheckinProjectPanel.getProject()).getAuthors();
      List<String> list = new ArrayList<String>(authors);
      Collections.sort(list);
      list = ObjectsConvertor.convert(list, new Convertor<String, String>() {
        @Override
        public String convert(String o) {
          return StringUtil.shortenTextWithEllipsis(o, 30, 0);
        }
      });
      myReviewer = new JComboBox(ArrayUtil.toObjectArray(list));

      final JPanel panel = new JPanel(new GridLayout(1, 2));
      panel.add(myToReview);
      panel.add(myReviewer);
      return new RefreshableOnComponent() {
        @Override
        public JComponent getComponent() {
          return panel;
        }

        @Override
        public void refresh() {
          myToReview.setText("Add reviewer ");
        }

        @Override
        public void saveState() {
        }

        @Override
        public void restoreState() {
        }
      };
    }
  }
}

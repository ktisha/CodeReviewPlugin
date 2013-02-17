package actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.DumbAware;

/**
 * User: ktisha
 * <p/>
 * Add code review comment
 */
public class AddReviewAction extends AnAction implements DumbAware {

  public void actionPerformed(AnActionEvent e) {
    final Editor editor = PlatformDataKeys.EDITOR.getData(e.getDataContext());
    if (editor == null) return;

    final Document document = editor.getDocument();

    int startOffset = editor.getSelectionModel().getSelectionStart();
    int endOffset = editor.getSelectionModel().getSelectionEnd();

    if (startOffset == endOffset) {
      int lineNumber = document.getLineNumber(startOffset);
      startOffset = document.getLineStartOffset(lineNumber);
      endOffset = document.getLineEndOffset(lineNumber);
    }
    final ReviewManager reviewManager = ReviewManager.getInstance();
    reviewManager.addReview(editor, startOffset, endOffset);
  }
}

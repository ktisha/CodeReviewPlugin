package actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import utils.ReviewManager;

/**
 * User: ktisha
 * <p/>
 * Add code review comment
 */
public class AddReviewAction extends AnAction implements DumbAware {

  public void actionPerformed(AnActionEvent e) {
    Project project = e.getData(PlatformDataKeys.PROJECT);
    if (project == null) return;

    final Editor editor = PlatformDataKeys.EDITOR.getData(e.getDataContext());
    if (editor == null) return;

    final Document document = editor.getDocument();
    final PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(document);
    if (psiFile == null) return;

    final PsiElement context = psiFile.getContext();
    final VirtualFile virtualFile =
      (context == null) ? psiFile.getVirtualFile() : context.getContainingFile().getVirtualFile();
    if (virtualFile == null) return;

    int startOffset = editor.getSelectionModel().getSelectionStart();
    int endOffset = editor.getSelectionModel().getSelectionEnd();

    if (startOffset == endOffset) {
      int lineNumber = document.getLineNumber(startOffset);
      startOffset = document.getLineStartOffset(lineNumber);
      endOffset = document.getLineEndOffset(lineNumber);
    }
    final ReviewManager reviewManager = ReviewManager.getInstance(project);
    reviewManager.addReview(editor, startOffset, endOffset, virtualFile.getPath());
  }
}

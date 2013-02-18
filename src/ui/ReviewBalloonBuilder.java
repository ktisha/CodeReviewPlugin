package ui;

import actions.ReviewManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.colors.EditorColors;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.markup.HighlighterLayer;
import com.intellij.openapi.editor.markup.HighlighterTargetArea;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.ui.popup.*;
import com.intellij.ui.awt.RelativePoint;
import content.Review;
import org.jetbrains.annotations.NotNull;
import ui.forms.ReviewForm;

import java.awt.*;

/**
 * User: ktisha
 */
public class ReviewBalloonBuilder {

  private static final TextAttributes ourReviewAttributes =
    EditorColorsManager.getInstance().getGlobalScheme().getAttributes(EditorColors.WRITE_SEARCH_RESULT_ATTRIBUTES);

  public ReviewBalloonBuilder() {
  }

  public void showBalloon(@NotNull final Review review, @NotNull final Editor editor,
                          final ReviewForm balloonContent, final String title) {

    final RangeHighlighter highlighter = editor.getMarkupModel().addRangeHighlighter(review.getStartOffset(),
                                                                                     review.getEndOffset(),
                                                                                     HighlighterLayer.SELECTION,
                                                                                     ourReviewAttributes,
                                                                                     HighlighterTargetArea.EXACT_RANGE);

    final BalloonBuilder balloonBuilder =
      JBPopupFactory.getInstance().createDialogBalloonBuilder(balloonContent, title);
    balloonBuilder.setHideOnClickOutside(true);
    balloonBuilder.setHideOnKeyOutside(true);
    Balloon balloon = balloonBuilder.createBalloon();

    balloon.addListener(new JBPopupAdapter() {
      @Override
      public void onClosed(LightweightWindowEvent event) {
        if (highlighter.isValid()) {
          highlighter.dispose();
          review.setText(balloonContent.getText());
          ReviewManager.getInstance(editor.getProject()).saveReview(review);
        }
      }
    });
    final Point targetPoint = editor.visualPositionToXY(editor.offsetToVisualPosition(review.getEndOffset()));
    balloon.show(new RelativePoint(editor.getContentComponent(), targetPoint), Balloon.Position.below);
  }
}

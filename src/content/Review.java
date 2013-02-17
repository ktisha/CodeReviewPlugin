package content;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

/**
 * User: ktisha
 * <p/>
 * Simple representation of single review
 */
public class Review {
  private Date myDate;
  private String myAuthor;
  private String myText = "";
  private int myStartOffset;
  private int myEndOffset;

  public Review(@NotNull final String text, final int startOffset, final int endOffset) {
    myStartOffset = startOffset;
    myEndOffset = endOffset;
    myAuthor = System.getProperty("user.name");
    myText = text;
    myDate = new Date();
  }

  public Review(final int startOffset, final int endOffset) {
    myStartOffset = startOffset;
    myEndOffset = endOffset;
    myAuthor = System.getProperty("user.name");
    myDate = new Date();
  }

  @NotNull
  public String getAuthor() {
    return myAuthor;
  }

  public void setAuthor(@NotNull final String author) {
    myAuthor = author;
  }

  @NotNull
  public String getText() {
    return myText;
  }

  public void setText(@NotNull final String text) {
    myText = text;
  }

  @NotNull
  public Date getDate() {
    return myDate;
  }

  public void setDate(@NotNull final Date date) {
    myDate = date;
  }

  public int getEndOffset() {
    return myEndOffset;
  }

  public void setEndOffset(int endOffset) {
    myEndOffset = endOffset;
  }

  public int getStartOffset() {
    return myStartOffset;
  }

  public void setStartOffset(int startOffset) {
    myStartOffset = startOffset;
  }
}

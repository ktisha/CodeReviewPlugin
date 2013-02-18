package utils;

import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializer;
import content.Review;
import org.jdom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ktisha
 */
@State(name = "ReviewService",
      storages = {
      @Storage( file = StoragePathMacros.PROJECT_FILE),
      @Storage( file = StoragePathMacros.PROJECT_CONFIG_DIR + "/review.xml", scheme = StorageScheme.DIRECTORY_BASED)
      }
)
public class ReviewService implements PersistentStateComponent<Element> {
  private MyState myState = new MyState();

  public class MyState {
    public List<Review> REVIEWS = new ArrayList<Review>();
    public MyState(){}
  }

  public ReviewService() {
  }

  @Override
  public Element getState() {
    return XmlSerializer.serialize(myState);
  }

  @Override
  public void loadState(Element state) {
    final MyState service = XmlSerializer.deserialize(state, MyState.class);
    if (service != null)
      myState.REVIEWS.addAll(service.REVIEWS);
  }

  public static ReviewService getInstance(Project project) {
    return ServiceManager.getService(project, ReviewService.class);
  }

  public void addReview(Review review) {
    myState.REVIEWS.add(review);
  }
}

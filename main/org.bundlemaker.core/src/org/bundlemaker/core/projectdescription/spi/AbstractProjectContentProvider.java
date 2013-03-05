package org.bundlemaker.core.projectdescription.spi;

import java.util.Collections;
import java.util.List;

import org.bundlemaker.core.internal.projectdescription.BundleMakerProjectDescription;
import org.bundlemaker.core.internal.projectdescription.ProjectContentEntry;
import org.bundlemaker.core.internal.projectdescription.gson.GsonProjectDescriptionHelper;
import org.bundlemaker.core.projectdescription.IProjectContentProblem;
import org.bundlemaker.core.projectdescription.IProjectContentProvider;
import org.bundlemaker.core.projectdescription.IProjectDescription;
import org.bundlemaker.core.projectdescription.file.FileBasedProjectContentProvider;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * <p>
 * Superclass for all implementations of IProjectContentProvider
 * </p>
 * 
 * <p>
 * <b>Implementation note:</b> Implementators should call {@link #fireProjectDescriptionChangedEvent()} after the
 * provider changed.
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractProjectContentProvider implements IProjectContentProvider {

  /** - */
  private BundleMakerProjectDescription _projectDescription;

  /** - */
  @Expose
  @SerializedName("id")
  private String                        _id;

  /**
   * {@inheritDoc}
   */
  @Override
  public final String getId() {
    return _id;
  }

  /**
   * {@inheritDoc}
   */
  public final void setId(String id) {
    _id = id;
  }

  /**
   * <b>Internal method</b> This method is not intended to be called from outside bundlemaker.core
   * 
   * @param description
   */
  public void setProjectDescription(BundleMakerProjectDescription description) {
    _projectDescription = description;

    init(description);
  }

  /**
   * <p>
   * </p>
   * 
   * @param description
   */
  protected void init(IProjectDescription description) {
    // empty implementation
    System.out.println("init");
  }

  /**
   * This method should be called after this provider has been changed.
   */
  public void fireProjectDescriptionChangedEvent() {
    if (_projectDescription != null) {
      _projectDescription.fireProjectDescriptionChangedEvent();
    }
  }

  /**
   * <p>
   * </p>
   */
  public void fireProjectDescriptionRecomputedEvent() {
    if (_projectDescription != null) {
      _projectDescription.fireProjectDescriptionRecomputedEvent();
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public List<IProjectContentProblem> getProblems() {
    return Collections.emptyList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasProblems() {
    return !getProblems().isEmpty();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected IModifiableProjectContentEntry createNewContentEntry() {
    return new ProjectContentEntry(this);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final String toJson() {
    return GsonProjectDescriptionHelper.gson().toJson(this);
  }

  /**
   * <p>
   * </p>
   * 
   * @param gsonString
   * @param type
   * @return
   */
  public static <T extends FileBasedProjectContentProvider> T fromJson(String gsonString,
      Class<T> type) {
    return GsonProjectDescriptionHelper.gson().fromJson(gsonString, type);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((_id == null) ? 0 : _id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AbstractProjectContentProvider other = (AbstractProjectContentProvider) obj;
    if (_id == null) {
      if (other._id != null)
        return false;
    } else if (!_id.equals(other._id))
      return false;
    return true;
  }
}

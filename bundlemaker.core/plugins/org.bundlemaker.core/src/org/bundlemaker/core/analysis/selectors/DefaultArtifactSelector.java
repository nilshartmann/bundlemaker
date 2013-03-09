package org.bundlemaker.core.analysis.selectors;

import java.util.Arrays;
import java.util.List;

import org.bundlemaker.core.analysis.IArtifactSelector;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.eclipse.core.runtime.Assert;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DefaultArtifactSelector implements IArtifactSelector {

  /** - */
  @Expose
  @SerializedName("artifacts")
  private List<? extends IBundleMakerArtifact> _bundleMakerArtifacts;

  /**
   * <p>
   * Creates a new instance of type {@link DefaultArtifactSelector}.
   * </p>
   * 
   * @param bundleMakerArtifacts
   */
  public DefaultArtifactSelector(List<? extends IBundleMakerArtifact> bundleMakerArtifacts) {
    Assert.isNotNull(bundleMakerArtifacts);
    for (IBundleMakerArtifact artifact : bundleMakerArtifacts) {
      Assert.isNotNull(artifact);
    }

    _bundleMakerArtifacts = bundleMakerArtifacts;
  }

  /**
   * <p>
   * Creates a new instance of type {@link DefaultArtifactSelector}.
   * </p>
   * 
   * @param bundleMakerArtifacts
   */
  public DefaultArtifactSelector(IBundleMakerArtifact... bundleMakerArtifacts) {
    Assert.isNotNull(bundleMakerArtifacts);
    for (IBundleMakerArtifact artifact : bundleMakerArtifacts) {
      Assert.isNotNull(artifact);
    }

    _bundleMakerArtifacts = Arrays.asList(bundleMakerArtifacts);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<? extends IBundleMakerArtifact> getBundleMakerArtifacts() {
    return _bundleMakerArtifacts;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((_bundleMakerArtifacts == null) ? 0 : _bundleMakerArtifacts.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    DefaultArtifactSelector other = (DefaultArtifactSelector) obj;
    if (_bundleMakerArtifacts == null) {
      if (other._bundleMakerArtifacts != null)
        return false;
    } else if (!_bundleMakerArtifacts.equals(other._bundleMakerArtifacts))
      return false;
    return true;
  }
}

package org.bundlemaker.analysis.ui.dsmview;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependency;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DsmViewModel extends AbstractDsmViewModel {

  /** - */
  private IArtifact[]     _artifacts;

  /** - */
  private IDependency[][] _dependencies;

  /**
   * <p>
   * Creates a new instance of type {@link DsmViewModel}.
   * </p>
   * 
   * @param headers
   * @param dependencies
   */
  public DsmViewModel(IArtifact[] headers, IDependency[][] dependencies) {

    Assert.isNotNull(headers);
    Assert.isNotNull(dependencies);
    Assert.isTrue(headers.length == dependencies.length);

    _artifacts = headers;
    _dependencies = dependencies;
  }

  /**
   * <p>
   * Creates a new instance of type {@link DsmViewModel}.
   * </p>
   * 
   */
  public DsmViewModel() {

    _artifacts = new IArtifact[0];
    _dependencies = new IDependency[0][0];
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected IDsmViewConfiguration createConfiguration() {
    return new DefaultDsmViewConfiguration();
  }

  @Override
  protected String[][] createValues() {

    String[][] result = new String[_artifacts.length][_artifacts.length];
    for (int i = 0; i < result.length; i++) {
      for (int j = 0; j < result.length; j++) {
        if (_dependencies[i][j] != null) {
          result[i][j] = "" + _dependencies[i][j].getWeight();
        }
      }
    }

    //
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String[] createLabels() {

    //
    String[] result = new String[_artifacts.length];

    //
    for (int i = 0; i < _artifacts.length; i++) {
      result[i] = _artifacts[i].getQualifiedName();
    }

    //
    return result;
  }

  @Override
  public String getToolTip(int x, int y) {
    return null;
  }
}

package org.bundlemaker.core.internal.analysis;

import org.bundlemaker.core.analysis.model.ArtifactType;
import org.bundlemaker.core.analysis.model.IArtifact;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class AdapterGroup2IArtifact extends AbstractArtifactContainer implements IArtifact {

  /** the name */
  private String _name;

  /**
   * <p>
   * Creates a new instance of type {@link AdapterGroup2IArtifact}.
   * </p>
   * 
   * @param modularizedSystem
   */
  public AdapterGroup2IArtifact(String name, IArtifact parent) {
    super(ArtifactType.Group, parent);

    Assert.isNotNull(name);

    _name = name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return _name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getQualifiedName() {
    return _name;
  }
}

package org.bundlemaker.core.analysis.cmd;

import java.util.ArrayList;
import java.util.List;

import org.bundlemaker.core.analysis.IResourceArtifact;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class SimpleResourceArtifactSelector implements IResourceArtifactSelector {

  /** - */
  private List<IResourceArtifact> _resourceArtifacts;

  /**
   * <p>
   * Creates a new instance of type {@link SimpleResourceArtifactSelector}.
   * </p>
   */
  public SimpleResourceArtifactSelector() {
    _resourceArtifacts = new ArrayList<IResourceArtifact>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<IResourceArtifact> getResourceArtifacts() {
    return _resourceArtifacts;
  }
}

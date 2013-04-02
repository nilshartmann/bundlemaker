package org.bundlemaker.core.internal.analysis;

import java.util.Collection;
import java.util.LinkedList;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.spi.AbstractArtifactContainer;

/**
 * <p>
 * - we have to filter out empty packages: - https://bundlemaker.jira.com/browse/BM-345
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractPackageFilteringArtifact extends AbstractArtifactContainer {

  /** - */
  private Collection<IBundleMakerArtifact> _filteredChildren;

  /** - */
  private boolean                          _initialized = false;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractPackageFilteringArtifact}.
   * </p>
   * 
   * @param name
   */
  public AbstractPackageFilteringArtifact(String name) {
    super(name);

    //
    _filteredChildren = new LinkedList<IBundleMakerArtifact>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<IBundleMakerArtifact> getChildren() {

    //
    if (((AdapterRoot2IArtifact) getRoot()).isInInvalidationCaches()) {
      return super.getChildren();
    }

    //
    if (!_initialized) {

      //
      _filteredChildren.clear();

      //
      for (IBundleMakerArtifact bundleMakerArtifact : getModifiableChildrenCollection()) {

        if (bundleMakerArtifact instanceof IPackageArtifact
            && !((IPackageArtifact) bundleMakerArtifact).containsTypesOrResources()) {
          // skip
        } else {
          _filteredChildren.add(bundleMakerArtifact);
        }
      }

      //
      _initialized = true;
    }

    //
    return _filteredChildren;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void invalidateCaches() {
    super.invalidateCaches();
    _initialized = false;
  }
}

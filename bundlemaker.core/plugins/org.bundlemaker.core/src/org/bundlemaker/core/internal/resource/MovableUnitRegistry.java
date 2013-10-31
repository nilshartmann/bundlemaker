package org.bundlemaker.core.internal.resource;

import java.util.List;

import org.bundlemaker.core.common.utils.AbstractBundleMakerExtensionRegistry;
import org.bundlemaker.core.common.utils.IBundleMakerExtensionRegistry;
import org.bundlemaker.core.spi.movableunit.IMovableUnitCreator;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MovableUnitRegistry {

  /** - */
  private IBundleMakerExtensionRegistry<IMovableUnitCreator> _movUnitRegistry;

  /**
   * <p>
   * Creates a new instance of type {@link MovableUnitRegistry}.
   * </p>
   */
  public MovableUnitRegistry() {

    //
    _movUnitRegistry = new AbstractBundleMakerExtensionRegistry<IMovableUnitCreator>(
        "org.bundlemaker.core.movableunitcreator") {

      /**
       * {@inheritDoc}
       */
      @Override
      protected IMovableUnitCreator createInstanceFromExtension(IExtension extension) throws CoreException {

        //
        IConfigurationElement actionElement = extension.getConfigurationElements()[0];

        //
        IMovableUnitCreator movableUnitCreator = (IMovableUnitCreator) actionElement.createExecutableExtension("class");

        //
        return movableUnitCreator;
      }
    };

    //
    _movUnitRegistry.initialize();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public List<IMovableUnitCreator> getCreators() {
    return _movUnitRegistry.getExtensionInstances();
  }

  /** - */
  private static MovableUnitRegistry _instance;

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public static MovableUnitRegistry instance() {

    //
    if (_instance == null) {
      _instance = new MovableUnitRegistry();
    }

    //
    return _instance;
  }
}

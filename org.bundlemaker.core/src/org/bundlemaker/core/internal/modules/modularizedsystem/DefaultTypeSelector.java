package org.bundlemaker.core.internal.modules.modularizedsystem;

import java.util.Set;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.ITypeSelector;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.projectdescription.IFileBasedContent;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DefaultTypeSelector implements ITypeSelector {

  /** - */
  public static final String             BUNDLEMAKER_INTERNAL_JDK_MODULE_IDENTIFIER = "#####BUNDLEMAKER_INTERNAL_JDK_MODULE_IDENTIFIER#####";

  /** - */
  private IBundleMakerProjectDescription _bundleMakerProjectDescription;

  /**
   * <p>
   * Creates a new instance of type {@link DefaultTypeSelector}.
   * </p>
   * 
   * @param bundleMakerProjectDescription
   */
  public DefaultTypeSelector(IBundleMakerProjectDescription bundleMakerProjectDescription) {

    Assert.isNotNull(bundleMakerProjectDescription);

    _bundleMakerProjectDescription = bundleMakerProjectDescription;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean matchesAllModules() {
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<IModuleIdentifier> getSourceModules() {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IType selectType(IResourceModule module, String fullyQualifiedName, Set<IType> types) {

    //
    int currentIndex = Integer.MAX_VALUE;
    IType currentType = null;

    for (IType iType : types) {

      // get the content identifier
      String contentID = iType.getContentId();

      if (!contentID.equals(DefaultTypeSelector.BUNDLEMAKER_INTERNAL_JDK_MODULE_IDENTIFIER)) {
        
        // get the file based content
        IFileBasedContent fileBasedContent = _bundleMakerProjectDescription.getFileBasedContent(contentID);

        // get the index
        int index = _bundleMakerProjectDescription.getFileBasedContent().indexOf(fileBasedContent);

        //
        if (index < currentIndex) {
          currentIndex = index;
          currentType = iType;
        }
      }
    }

    // return null
    return currentType;
  }
}

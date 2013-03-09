package org.bundlemaker.core.itest._framework.analysis;

import java.util.Set;

import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.ITypeSelector;
import org.bundlemaker.core.projectdescription.IProjectContentEntry;
import org.bundlemaker.core.projectdescription.IProjectDescription;
import org.bundlemaker.core.resource.IType;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class TestTypeSelector implements ITypeSelector {

  /** - */
  public static final String  BUNDLEMAKER_INTERNAL_JDK_MODULE_IDENTIFIER = "#####BUNDLEMAKER_INTERNAL_JDK_MODULE_IDENTIFIER#####";

  /** - */
  private IProjectDescription _bundleMakerProjectDescription;

  /** - */
  private boolean             _preferJdkTypes;

  /**
   * <p>
   * Creates a new instance of type {@link TestTypeSelector}.
   * </p>
   * 
   * @param bundleMakerProjectDescription
   */
  public TestTypeSelector(IProjectDescription bundleMakerProjectDescription) {

    Assert.isNotNull(bundleMakerProjectDescription);

    _bundleMakerProjectDescription = bundleMakerProjectDescription;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final boolean isPreferJdkTypes() {
    return _preferJdkTypes;
  }

  /**
   * <p>
   * </p>
   * 
   * @param preferJdkTypes
   */
  public final void setPreferJdkTypes(boolean preferJdkTypes) {
    _preferJdkTypes = preferJdkTypes;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IType selectType(IResourceModule module, String fullyQualifiedName, Set<IType> types) {

    //
    int currentIndex = Integer.MAX_VALUE;
    IType currentType = null;
    IType jdkType = null;

    for (IType iType : types) {

      //
      String identifier = iType.getProjectContentEntryId().toString();

      // get the content identifier
      if (!identifier.equals(TestTypeSelector.BUNDLEMAKER_INTERNAL_JDK_MODULE_IDENTIFIER)) {

        // get the file based content
        IProjectContentEntry fileBasedContent = _bundleMakerProjectDescription.getProjectContentEntry(identifier);

        // get the index
        int index = _bundleMakerProjectDescription.getContent().indexOf(fileBasedContent);

        //
        if (index < currentIndex) {
          currentIndex = index;
          currentType = iType;
        }
      } else {
        jdkType = iType;
      }
    }

    if (_preferJdkTypes) {
      return jdkType != null ? jdkType : currentType;
    } else {
      return currentType != null ? currentType : jdkType;
    }
  }
}
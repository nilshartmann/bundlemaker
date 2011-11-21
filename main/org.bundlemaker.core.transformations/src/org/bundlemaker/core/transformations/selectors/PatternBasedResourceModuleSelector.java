package org.bundlemaker.core.transformations.selectors;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.util.FileUtils;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class PatternBasedResourceModuleSelector extends AbstractPatternBasedSelector implements IResourceModuleSelector {

  /**
   * {@inheritDoc}
   */
  @Override
  public List<IResourceModule> getMatchingResourceModules(IModularizedSystem modularizedSystem) {

    //
    Assert.isNotNull(modularizedSystem);

    // create the result
    List<IResourceModule> result = new LinkedList<IResourceModule>();

    // iterate over all the modules
    for (IResourceModule resourceModule : modularizedSystem.getResourceModules()) {

      //
      if (matches(resourceModule)) {
        result.add(resourceModule);
      }
    }

    // return the result
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean matches(IResourceModule resourceModule) {

    // get the full path
    String modulePath = FileUtils.normalize(resourceModule.getClassification().toString() + "/"
        + resourceModule.getModuleIdentifier());

    //
    return isIncluded(modulePath);
  }
}

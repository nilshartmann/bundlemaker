package org.bundlemaker.core.transformations.selectors;

import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.util.FileUtils;

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
  public boolean matches(IResourceModule resourceModule) {

    // get the full path
    String modulePath = FileUtils.normalize(resourceModule.getClassification().toString() + "/"
        + resourceModule.getModuleIdentifier());

    //
    return isIncluded(modulePath);
  }
}

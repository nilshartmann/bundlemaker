package org.bundlemaker.core.modules.query;

import org.bundlemaker.core.modules.IModule;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModuleQueryFilters {

  /** TRUE_QUERY_FILTER */
  public static IQueryFilter<IModule> TRUE_QUERY_FILTER = new IQueryFilter<IModule>() {

                                                          @Override
                                                          public boolean matches(IModule type) {
                                                            return true;
                                                          }
                                                        };
}

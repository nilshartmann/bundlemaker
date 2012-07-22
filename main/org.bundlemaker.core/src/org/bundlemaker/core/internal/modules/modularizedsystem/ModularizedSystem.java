/*******************************************************************************
 * Copyright (c) 2011 Gerd Wuetherich (gerd@gerd-wuetherich.de).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Gerd Wuetherich (gerd@gerd-wuetherich.de) - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.internal.modules.modularizedsystem;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.projectdescription.IProjectDescription;
import org.bundlemaker.core.resource.IType;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModularizedSystem extends AbstractValidatingModularizedSystem {

  /**
   * <p>
   * Creates a new instance of type {@link ModularizedSystem}.
   * </p>
   * 
   * @param name
   */
  public ModularizedSystem(String name, IProjectDescription projectDescription) {

    //
    super(name, projectDescription);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Map<String, Set<IType>> getAmbiguousTypes() {

    // create the result
    Map<String, Set<IType>> result = new HashMap<String, Set<IType>>();

    //
    for (Entry<String, Set<IType>> entry : getTypeNameToTypeCache().entrySet()) {

      if (entry.getValue().size() > 1) {
        result.put(entry.getKey(), entry.getValue());
      }
    }

    // return the result
    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  private static class ModuleAndQueryFilterKey {

    /** - */
    private IResourceModule _resourceModule;

    // /** - */
    // private IQueryFilter<IReference> _queryFilter;

    /**
     * <p>
     * Creates a new instance of type {@link ModuleAndQueryFilterKey}.
     * </p>
     * 
     * @param resourceModule
     * @param queryFilter
     */
    public ModuleAndQueryFilterKey(IResourceModule resourceModule/* , IQueryFilter<IReference> queryFilter */) {

      Assert.isNotNull(resourceModule);
      // Assert.isNotNull(queryFilter);

      _resourceModule = resourceModule;
      // _queryFilter = queryFilter;
    }

    /**
     * <p>
     * </p>
     * 
     * @return
     */
    public IResourceModule getResourceModule() {
      return _resourceModule;
    }

    // /**
    // * <p>
    // * </p>
    // *
    // * @return
    // */
    // public IQueryFilter<IReference> getQueryFilter() {
    // return _queryFilter;
    // }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      // result = prime * result + ((_queryFilter == null) ? 0 : _queryFilter.hashCode());
      result = prime * result + ((_resourceModule == null) ? 0 : _resourceModule.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      ModuleAndQueryFilterKey other = (ModuleAndQueryFilterKey) obj;
      // if (_queryFilter == null) {
      // if (other._queryFilter != null)
      // return false;
      // } else if (!_queryFilter.equals(other._queryFilter))
      // return false;
      if (_resourceModule == null) {
        if (other._resourceModule != null)
          return false;
      } else if (!_resourceModule.equals(other._resourceModule))
        return false;
      return true;
    }
  }
}

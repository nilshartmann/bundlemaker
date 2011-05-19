package org.bundlemaker.core.modules.query;

import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.resource.IReference;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ReferenceQueryFilters {

  /** TRUE_QUERY_FILTER */
  public static IQueryFilter<IReference> TRUE_QUERY_FILTER = new IQueryFilter<IReference>() {

                                                             @Override
                                                             public boolean matches(IReference type) {
                                                               return true;
                                                             }
                                                           };

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public static ReferenceFilter createReferenceFilter(boolean excludeContainedTypes, boolean includeSourceReferences,
      boolean includeBinaryReferences, boolean includeDirectReferences, boolean includeIndirectReferences) {

    //
    ReferenceFilter referenceFilter = new ReferenceFilter();

    // set parameters
    referenceFilter.setExludeContainedTypes(excludeContainedTypes);
    referenceFilter.setIncludeSourceReferences(includeSourceReferences);
    referenceFilter.setIncludeBinaryReferences(includeBinaryReferences);
    referenceFilter.setIncludeDirectReferences(includeDirectReferences);
    referenceFilter.setIncludeIndirectReferences(includeIndirectReferences);

    // return the filter
    return referenceFilter;
  }

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  public static class ReferenceFilter implements IQueryFilter<IReference> {

    /** - */
    private IResourceModule resourceModule;

    /** - */
    private boolean         exludeContainedTypes      = true;

    /** - */
    private boolean         includeSourceReferences   = true;

    /** - */
    private boolean         includeBinaryReferences   = true;

    /** - */
    private boolean         includeIndirectReferences = true;

    /** - */
    private boolean         includeDirectReferences   = true;

    private Set<String>     _containedTypeNames;

    /**
     * <p>
     * </p>
     * 
     * @param resourceModule
     */
    public void setResourceModule(IResourceModule resourceModule) {
      this.resourceModule = resourceModule;
    }

    /**
     * <p>
     * </p>
     * 
     * @param exludeContainedTypes
     */
    public void setExludeContainedTypes(boolean exludeContainedTypes) {
      this.exludeContainedTypes = exludeContainedTypes;
    }

    /**
     * <p>
     * </p>
     * 
     * @param includeSourceReferences
     */
    public void setIncludeSourceReferences(boolean includeSourceReferences) {
      this.includeSourceReferences = includeSourceReferences;
    }

    /**
     * <p>
     * </p>
     * 
     * @param includeIndirectReferences
     */
    public void setIncludeIndirectReferences(boolean includeIndirectReferences) {
      this.includeIndirectReferences = includeIndirectReferences;
    }

    public void setIncludeBinaryReferences(boolean includeBinaryReferences) {
      this.includeBinaryReferences = includeBinaryReferences;
    }

    public void setIncludeDirectReferences(boolean includeDirectReferences) {
      this.includeDirectReferences = includeDirectReferences;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean matches(IReference reference) {

      if (!((reference.isBinaryReference() && includeBinaryReferences) || reference.isSourceReference()
          && includeSourceReferences)) {
        return false;
      }

      if (!((reference.isDirectlyReferenced() && includeDirectReferences) || reference.isIndirectlyReferenced()
          && includeIndirectReferences)) {
        return false;
      }

      if (!(reference.isBinaryReference() && includeBinaryReferences)) {
        return false;
      }

      // TODO: packages?
      if (exludeContainedTypes && containedTypeNames().contains(reference.getFullyQualifiedName())) {
        return false;
      }

      // finally return 'true'
      return true;
    }

    private Set<String> containedTypeNames() {

      if (_containedTypeNames == null) {
        _containedTypeNames = new HashSet<String>(resourceModule.getContainedTypeNames());
      }

      return _containedTypeNames;
    }
  }
}

package org.bundlemaker.core.internal.modules.query;

import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.ITypeModule;
import org.bundlemaker.core.resource.IReference;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @deprecated use AnalysisModel instead
 */
@Deprecated
public class ReferenceQueryFilters {

  /** TRUE_QUERY_FILTER */
  public static IQueryFilter<IReference>       ALL_REFERENCES_QUERY_FILTER                 = createReferenceFilter(
                                                                                               false, true, true, true,
                                                                                               true);

  /** ALL_DIRECT_EXTERNAL_REFERENCES_QUERY_FILTER */
  public static IQueryFilter<IReference>       ALL_DIRECT_EXTERNAL_REFERENCES_QUERY_FILTER = createReferenceFilter(
                                                                                               true, true, true, true,
                                                                                               false);

  public final static IQueryFilter<IReference> TRUE_QUERY_FILTER                           = new IQueryFilter<IReference>() {

                                                                                             @Override
                                                                                             public boolean matches(
                                                                                                 IReference content) {
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
    private IModule     resourceModule;

    /** - */
    private boolean     exludeContainedTypes      = true;

    /** - */
    private boolean     includeSourceReferences   = true;

    /** - */
    private boolean     includeBinaryReferences   = true;

    /** - */
    private boolean     includeIndirectReferences = true;

    /** - */
    private boolean     includeDirectReferences   = true;

    /** - */
    private Set<String> _containedTypeNames;

    /**
     * <p>
     * </p>
     * 
     * @param resourceModule
     */
    public void setResourceModule(IModule resourceModule) {
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

      if (!(((reference.isBinaryReference() && includeBinaryReferences)) || (reference.isSourceReference() && includeSourceReferences))) {

        if (!(reference.isBinaryReference() || reference.isSourceReference())) {
          System.out.println("Reference " + reference + " is neither binary- nor source reference!");
        }
        return false;
      }

      if (!((reference.isDirectlyReferenced() && includeDirectReferences) || (reference.isIndirectlyReferenced() && includeIndirectReferences))) {
        return false;
      }

      // TODO: packages?
      if (exludeContainedTypes && containedTypeNames().contains(reference.getFullyQualifiedName())) {
        return false;
      }

      // finally return 'true'
      return true;
    }

    /**
     * <p>
     * </p>
     * 
     * @return
     */
    private Set<String> containedTypeNames() {

      if (_containedTypeNames == null) {
        _containedTypeNames = new HashSet<String>(
            resourceModule.adaptAs(ITypeModule.class).getContainedTypeNames());
      }

      return _containedTypeNames;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + (exludeContainedTypes ? 1231 : 1237);
      result = prime * result + (includeBinaryReferences ? 1231 : 1237);
      result = prime * result + (includeDirectReferences ? 1231 : 1237);
      result = prime * result + (includeIndirectReferences ? 1231 : 1237);
      result = prime * result + (includeSourceReferences ? 1231 : 1237);
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
      ReferenceFilter other = (ReferenceFilter) obj;
      if (exludeContainedTypes != other.exludeContainedTypes)
        return false;
      if (includeBinaryReferences != other.includeBinaryReferences)
        return false;
      if (includeDirectReferences != other.includeDirectReferences)
        return false;
      if (includeIndirectReferences != other.includeIndirectReferences)
        return false;
      if (includeSourceReferences != other.includeSourceReferences)
        return false;
      return true;
    }

    @Override
    public String toString() {
      return "ReferenceFilter [resourceModule=" + resourceModule + ", exludeContainedTypes=" + exludeContainedTypes
          + ", includeSourceReferences=" + includeSourceReferences + ", includeBinaryReferences="
          + includeBinaryReferences + ", includeIndirectReferences=" + includeIndirectReferences
          + ", includeDirectReferences=" + includeDirectReferences + ", _containedTypeNames=" + _containedTypeNames
          + "]";
    }

  }
}

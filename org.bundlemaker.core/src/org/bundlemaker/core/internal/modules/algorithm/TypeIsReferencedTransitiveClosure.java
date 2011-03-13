package org.bundlemaker.core.internal.modules.algorithm;

import java.util.Set;

import org.bundlemaker.core.internal.modules.modularizedsystem.ModularizedSystem;
import org.bundlemaker.core.modules.query.IQueryFilter;
import org.bundlemaker.core.resource.IType;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 */
public class TypeIsReferencedTransitiveClosure extends AbstractTypeClosureQuery {

  /**
   * <p>
   * Creates a new instance of type {@link TypeIsReferencedTransitiveClosure}.
   * </p>
   * 
   * @param modularizedSystem
   */
  public TypeIsReferencedTransitiveClosure(ModularizedSystem modularizedSystem) {
    super(modularizedSystem);
  }

  /**
   * <p>
   * </p>
   * 
   * @param typeName
   */
  public void resolveType(String typeName, IQueryFilter<IType> queryFilter) {

    Assert.isNotNull(typeName);
    Assert.isNotNull(queryFilter);

    System.out.println(String.format("Resolving type '%s'.", typeName));

    Set<IType> types = getModularizedSystem().getTypeNameToReferringCache().get(typeName);

    if (types == null || types.isEmpty()) {
      System.out.println("NO TYPE REFERS TO '" + typeName + "'.");
      return;
    }

    for (IType type : types) {

      if (queryFilter.matches(type)) {

        if (!getTypesMap().containsKey(type.getFullyQualifiedName())) {
          getTypesMap().put(type.getFullyQualifiedName(), type);
          resolveType(type.getFullyQualifiedName(), queryFilter);
        }

      }
    }
  }
}

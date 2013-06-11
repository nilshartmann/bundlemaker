package org.bundlemaker.core._type;

import java.util.List;
import java.util.Set;

import org.bundlemaker.core.resource.IModule;

public interface ITypeModularizedSystem {

  Set<IType> getTypes();

  List<ITypeSelector> getTypeSelectors();

  Set<IType> getTypes(String fullyQualifiedName, IModule referencingModule);

  Set<IType> getTypes(String fullyQualifiedName);

  IType getType(String fullyQualifiedName, IModule referencingModule);
}

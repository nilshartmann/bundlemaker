package org.bundlemaker.core._type;

import java.util.List;
import java.util.Set;

import org.bundlemaker.core.common.collections.GenericCache;
import org.bundlemaker.core.internal.modules.ChangeAction;
import org.bundlemaker.core.resource.IModule;

public interface ITypeModularizedSystem {

  Set<IType> getTypes();

  List<ITypeSelector> getTypeSelectors();

  Set<IType> getTypes(String fullyQualifiedName, IModule referencingModule);

  Set<IType> getTypes(String fullyQualifiedName);

  IType getType(String fullyQualifiedName, IModule referencingModule);

  IType getType(String fullyQualifiedName);

  Set<IReference> getUnsatisfiedReferences(IModule resourceModule);

  void typeChanged(IType type, IModule module, ChangeAction action);

  IModule getAssociatedModule(IType type);

  GenericCache<String, Set<IType>> getTypeNameToReferringCache();
}

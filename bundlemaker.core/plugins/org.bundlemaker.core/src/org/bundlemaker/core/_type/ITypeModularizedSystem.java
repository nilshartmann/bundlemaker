package org.bundlemaker.core._type;

import java.util.List;
import java.util.Set;

public interface ITypeModularizedSystem {

  Set<IType> getTypes();

  List<ITypeSelector> getTypeSelectors();
}

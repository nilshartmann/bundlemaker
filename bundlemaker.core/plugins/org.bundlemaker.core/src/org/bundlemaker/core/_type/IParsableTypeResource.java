package org.bundlemaker.core._type;

import java.util.Set;

import org.bundlemaker.core._type.internal.Reference;
import org.bundlemaker.core._type.internal.Type;

public interface IParsableTypeResource extends ITypeResource {

  /**
   * <p>
   * </p>
   * 
   * @param fullyQualifiedName
   * @return
   */
  public Type getOrCreateType(String fullyQualifiedName, TypeEnum typeEnum, boolean abstractType);

  /**
   * <p>
   * </p>
   * 
   * @param type
   */
  public void setPrimaryType(IType type);

  /**
   * <p>
   * </p>
   * 
   * @param fullyQualifiedName
   * @return
   */
  public Type getType(String fullyQualifiedName);

  Set<Reference> getModifiableReferences();

  Set<Type> getModifiableContainedTypes();

  void recordReference(String fullyQualifiedName, ReferenceAttributes referenceAttributes);
}

package org.bundlemaker.core.internal.analysis.cache;

import org.bundlemaker.core.resource.IType;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class TypeKey {

  /** - */
  private String _typeName;

  /** - */
  private IType  _type;

  /**
   * <p>
   * Creates a new instance of type {@link TypeKey}.
   * </p>
   * 
   * @param typeName
   */
  public TypeKey(String typeName) {
    Assert.isNotNull(typeName);

    //
    _typeName = typeName;
  }

  /**
   * <p>
   * Creates a new instance of type {@link TypeKey}.
   * </p>
   * 
   * @param type
   */
  public TypeKey(IType type) {
    Assert.isNotNull(type);

    _type = type;
  }

  public final boolean hasType() {
    return _type != null;
  }

  public final String getTypeName() {
    return _typeName;
  }

  public final IType getType() {
    return _type;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((_type == null) ? 0 : _type.hashCode());
    result = prime * result + ((_typeName == null) ? 0 : _typeName.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    TypeKey other = (TypeKey) obj;
    if (_type == null) {
      if (other._type != null)
        return false;
    } else if (!_type.equals(other._type))
      return false;
    if (_typeName == null) {
      if (other._typeName != null)
        return false;
    } else if (!_typeName.equals(other._typeName))
      return false;
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "TypeKey [_typeName=" + _typeName + ", _type=" + _type + "]";
  }
}
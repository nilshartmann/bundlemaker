package org.bundlemaker.core.itestframework;

import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.ReferenceType;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ExpectedReference {

  /** - */
  private String        _fullyQualifiedName;

  /** - */
  private ReferenceType _referenceType;

  /** - */
  private Boolean       _extends;

  /** - */
  private Boolean       _implements;

  /** - */
  private Boolean       _classAnnotation;

  /** - */
  private Boolean       _directlyReferenced;

  /** - */
  private Boolean       _indirectlyReferenced;

  /** - */
  private Boolean       _isCompileTime;

  /** - */
  private Boolean       _isRuntimeTime;

  /**
   * <p>
   * Creates a new instance of type {@link ExpectedReference}.
   * </p>
   * 
   * @param fullyQualifiedName
   * @param referenceType
   * @param extendz
   * @param implementz
   * @param classAnnotation
   */
  public ExpectedReference(String fullyQualifiedName, ReferenceType referenceType, Boolean extendz, Boolean implementz,
      Boolean classAnnotation) {
    super();

    _fullyQualifiedName = fullyQualifiedName;
    _referenceType = referenceType;
    _extends = extendz;
    _implements = implementz;
    _classAnnotation = classAnnotation;
  }

  /**
   * <p>
   * </p>
   * 
   * @param reference
   * @return
   */
  public boolean matches(IReference reference) {

    //
    if (nullSafeEquals(_fullyQualifiedName, reference.getFullyQualifiedName())) {
      return false;
    }

    //
    if (nullSafeEquals(_referenceType, reference.getReferenceType())) {
      return false;
    }

    //
    if (nullSafeEquals(_extends, reference.isExtends())) {
      return false;
    }

    //
    if (nullSafeEquals(_implements, reference.isImplements())) {
      return false;
    }

    //
    if (nullSafeEquals(_classAnnotation, reference.isClassAnnotation())) {
      return false;
    }

    //
    return true;
  }

  /**
   * <p>
   * </p>
   * 
   * @param expected
   * @param actual
   */
  private boolean nullSafeEquals(Object expected, Object actual) {
    return expected != null && !actual.equals(expected);
  }

  @Override
  public String toString() {
    return "ExpectedReference [_fullyQualifiedName=" + _fullyQualifiedName + ", _referenceType=" + _referenceType
        + ", _extends=" + _extends + ", _implements=" + _implements + ", _classAnnotation=" + _classAnnotation
        + ", _directlyReferenced=" + _directlyReferenced + ", _indirectlyReferenced=" + _indirectlyReferenced
        + ", _isCompileTime=" + _isCompileTime + ", _isRuntimeTime=" + _isRuntimeTime + "]";
  }
}
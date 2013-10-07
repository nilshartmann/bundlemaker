package org.bundlemaker.core.jtype.internal;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.jtype.IParsableTypeResource;
import org.bundlemaker.core.jtype.IReference;
import org.bundlemaker.core.jtype.IReferenceRecorder;
import org.bundlemaker.core.jtype.IType;
import org.bundlemaker.core.jtype.ReferenceAttributes;
import org.bundlemaker.core.jtype.TypeEnum;
import org.bundlemaker.core.project.BundleMakerProjectCore;
import org.bundlemaker.core.resource.IModuleResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class TypeResource implements IParsableTypeResource, IReferenceRecorder {

  /** - */
  private Set<Reference>               _references;

  /** - */
  private Set<Type>                    _containedTypes;

  /** - */
  private IType                        _primaryType;

  /** - */
  private transient TypeCache          _resourceCache;

  /** - */
  private transient ReferenceContainer _referenceContainer;

  /** - */
  private String                       _sourceName;

  /**
   * <p>
   * Creates a new instance of type {@link TypeResource}.
   * </p>
   * 
   * @param resourceCache
   */
  public TypeResource(TypeCache resourceCache) {

    _resourceCache = resourceCache;

    _referenceContainer = new ReferenceContainer(resourceCache.getFlyWeightReferenceCache()) {
      @Override
      protected Set<Reference> createReferencesSet() {
        return references();
      }
    };
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getSourceName() {
    return _sourceName;
  }

  /**
   * <p>
   * </p>
   * 
   * @param type
   * @return
   */
  @Override
  public boolean isPrimaryType(IType type) {
    return type != null && type.equals(_primaryType);
  }

  @Override
  public boolean hasPrimaryType() {
    return _primaryType != null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IType getContainedType() throws CoreException {

    //
    if (_containedTypes == null || _containedTypes.isEmpty()) {
      return null;
    }

    //
    if (_containedTypes.size() == 1) {
      return _containedTypes.toArray(new IType[0])[0];
    }

    // throw new exception
    throw new CoreException(new Status(IStatus.ERROR, BundleMakerProjectCore.BUNDLE_ID,
        String.format("Resource '%s' contains more than one type.", this)));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<IReference> getReferences() {
    Set<? extends IReference> result = references();
    return Collections.unmodifiableSet(result);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<IType> getContainedTypes() {
    Set<? extends IType> types = containedTypes();
    return Collections.unmodifiableSet(types);
  }

  @Override
  public IType getPrimaryType() {
    return _primaryType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean containsTypes() {
    return _containedTypes != null && !_containedTypes.isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void recordReference(String fullyQualifiedName, ReferenceAttributes referenceAttributes) {

    //
    _referenceContainer.recordReference(fullyQualifiedName, referenceAttributes);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Type getOrCreateType(String fullyQualifiedName, TypeEnum typeEnum, boolean abstractType) {

    //
    Type type = _resourceCache.getOrCreateType(fullyQualifiedName, typeEnum, abstractType);

    //
    containedTypes().add(type);

    //
    return type;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Type getType(String fullyQualifiedName) {

    for (Type containedType : containedTypes()) {
      if (containedType.getFullyQualifiedName().equals(fullyQualifiedName)) {
        return containedType;
      }
    }

    return null;
  }

  @Override
  public void setPrimaryType(IType type) {
    _primaryType = type;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  private Set<Type> containedTypes() {

    if (_containedTypes == null) {
      _containedTypes = new HashSet<Type>();
    }

    return _containedTypes;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.resource.IModifiableResource#getModifiableContainedTypes ()
   */
  public Set<Type> getModifiableContainedTypes() {
    return containedTypes();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.resource.IModifiableResource#getModifiableReferences ()
   */
  public Set<Reference> getModifiableReferences() {
    return references();
  }

  /**
   * <p>
   * </p>
   * 
   * @param sourceName
   */
  public void setSourceName(String sourceName) {
    _sourceName = sourceName;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  private Set<Reference> references() {

    //
    if (_references == null) {
      _references = new HashSet<Reference>();
    }

    //
    return _references;
  }
}

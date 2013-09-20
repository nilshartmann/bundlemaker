package org.bundlemaker.core.common.utils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.RegistryFactory;
import org.eclipse.core.runtime.dynamichelpers.ExtensionTracker;
import org.eclipse.core.runtime.dynamichelpers.IExtensionChangeHandler;
import org.eclipse.core.runtime.dynamichelpers.IExtensionTracker;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractBundleMakerExtensionRegistry<T> implements IExtensionChangeHandler,
    IBundleMakerExtensionRegistry<T> {

  /** - */
  public final String      _extensionPointId;

  /** - */
  private boolean          _isInitalized = false;

  /** - */
  private ExtensionTracker _tracker;

  /** - */
  private List<T>          _extensionInstances;

  /** - */
  private List<String>     _extensionBundleNamespaces;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractBundleMakerExtensionRegistry}.
   * </p>
   * 
   * @param extensionPointId
   */
  public AbstractBundleMakerExtensionRegistry(String extensionPointId) {
    Assert.isNotNull(extensionPointId);

    //
    _extensionPointId = extensionPointId;
  }

  /**
   * @return
   */
  public List<String> getExtensionBundleNamespaces() {
    return _extensionBundleNamespaces;
  }

  /**
   * <p>
   * </p>
   */
  public void initialize() {

    //
    if (_isInitalized) {
      return;
    }

    // set initialized
    _isInitalized = true;

    //
    _extensionInstances = new LinkedList<T>();
    _extensionBundleNamespaces = new LinkedList<String>();

    // get the extension registry
    IExtensionRegistry registry = RegistryFactory.getRegistry();

    // get the extension points
    IExtensionPoint extensionPoint = registry.getExtensionPoint(_extensionPointId);

    // get the extension tracker
    _tracker = new ExtensionTracker(registry);

    //
    for (IExtension extension : extensionPoint.getExtensions()) {

      //
      _extensionBundleNamespaces.add(extension.getContributor().getName());

      //
      addExtension(_tracker, extension);
    }

    // register IExtensionChangeHandler
    _tracker.registerHandler(this, ExtensionTracker.createExtensionPointFilter(extensionPoint));
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public boolean isInitalized() {
    return _isInitalized;
  }

  /**
   * <p>
   * </p>
   */
  public void dispose() {
    _tracker.unregisterHandler(this);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public List<T> getExtensionInstances() {
    return Collections.unmodifiableList(_extensionInstances);
  }

  /**
   * {@inheritDoc}
   */
  public void addExtension(IExtensionTracker tracker, IExtension extension) {

    try {

      T parserFactory = createInstanceFromExtension(extension);

      _tracker.registerObject(extension, parserFactory, IExtensionTracker.REF_STRONG);

      // the parser factories
      _extensionInstances.add(parserFactory);

    } catch (CoreException e) {
      //
    }
  }

  @SuppressWarnings("unchecked")
  public void removeExtension(IExtension extension, Object[] objects) {

    for (Object object : objects) {
      disposeInstance(extension, (T) object);
      _extensionInstances.remove(object);
      _tracker.unregisterObject(extension, object);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param extension
   * @return
   * @throws CoreException
   */
  protected abstract T createInstanceFromExtension(IExtension extension) throws CoreException;

  /**
   * <p>
   * </p>
   * 
   * @param extension
   * @param instance
   * @throws CoreException
   */
  protected void disposeInstance(IExtension extension, T instance) {
    //
  }
}

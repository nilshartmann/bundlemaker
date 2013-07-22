/*******************************************************************************
 * Copyright (c) 2011 Gerd Wuetherich (gerd@gerd-wuetherich.de).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Gerd Wuetherich (gerd@gerd-wuetherich.de) - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.jdt.internal;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
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
public class ExtensionRegistryTracker<T> implements IExtensionChangeHandler {

  /** - */
  private ExtensionTracker              _tracker;

  /** - */
  private List<T>                       _extensionObjects;

  /** - */
  private String                        _extensionPointId;

  /** - */
  private ExtensionLifecycleListener<T> _lifecycleListener;

  /**
   * <p>
   * Creates a new instance of type {@link ExtensionRegistryTracker}.
   * </p>
   * 
   * @param extensionPointId
   */
  public ExtensionRegistryTracker(String extensionPointId) {
    this(extensionPointId, null);
  }

  /**
   * <p>
   * Creates a new instance of type {@link ExtensionRegistryTracker}.
   * </p>
   * 
   * @param extensionPointId
   * @param lifecycleListener
   */
  public ExtensionRegistryTracker(String extensionPointId, ExtensionLifecycleListener<T> lifecycleListener) {
    Assert.isNotNull(extensionPointId);

    _extensionPointId = extensionPointId;
    _lifecycleListener = lifecycleListener;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public String getExtensionPointId() {
    return _extensionPointId;
  }

  /**
   * <p>
   * </p>
   */
  public final void initialize() {

    //
    _extensionObjects = new LinkedList<T>();

    // get the extension registry
    IExtensionRegistry registry = RegistryFactory.getRegistry();

    // get the extension points
    IExtensionPoint extensionPoint = registry.getExtensionPoint(getExtensionPointId());

    // get the extension tracker
    _tracker = new ExtensionTracker(registry);

    //
    for (IExtension extension : extensionPoint.getExtensions()) {
      addExtension(_tracker, extension);
    }

    // register IExtensionChangeHandler
    _tracker.registerHandler(this, ExtensionTracker.createExtensionPointFilter(extensionPoint));
  }

  /**
   * <p>
   * </p>
   */
  public final void dispose() {
    _tracker.unregisterHandler(this);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final synchronized List<T> getExtensionObjects() {
    return Collections.unmodifiableList(_extensionObjects);
  }

  /**
   * {@inheritDoc}
   */
  public final synchronized void addExtension(IExtensionTracker tracker, IExtension extension) {

    try {

      T extensionObject = createFromExtension(extension);

      if (_lifecycleListener != null) {
        _lifecycleListener.initialize(extensionObject);
      }

      _tracker.registerObject(extension, extensionObject, IExtensionTracker.REF_STRONG);

      // the parser factories
      _extensionObjects.add(extensionObject);

    } catch (CoreException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public final synchronized void removeExtension(IExtension extension, Object[] objects) {

    for (Object object : objects) {

      T extensionObject = (T) object;

      if (_lifecycleListener != null) {
        _lifecycleListener.dispose(extensionObject);
      }

      _extensionObjects.remove(extensionObject);
      _tracker.unregisterObject(extension, extensionObject);
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
  @SuppressWarnings("unchecked")
  private T createFromExtension(IExtension extension) throws CoreException {

    //
    IConfigurationElement actionElement = extension.getConfigurationElements()[0];

    //
    return (T) actionElement.createExecutableExtension("class");
  }

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   * 
   * @param <T>
   */
  public static class ExtensionLifecycleListener<T> {

    /**
     * <p>
     * </p>
     * 
     * @param extensionObject
     */
    protected void initialize(T extensionObject) {
      //
    }

    /**
     * <p>
     * </p>
     * 
     * @param extensionObject
     */
    protected void dispose(T extensionObject) {
      //
    }
  }
}

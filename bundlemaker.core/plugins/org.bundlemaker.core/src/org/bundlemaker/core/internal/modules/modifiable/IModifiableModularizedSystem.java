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
package org.bundlemaker.core.internal.modules.modifiable;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.internal.modules.event.IModularizedSystemChangedListener;
import org.bundlemaker.core.modules.ChangeAction;
import org.bundlemaker.core.modules.IGroup;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.modules.transformation.ITransformation;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IModifiableModularizedSystem extends IModularizedSystem {

  /**
   * <p>
   * </p>
   * 
   * @param listener
   */
  void addModularizedSystemChangedListener(IModularizedSystemChangedListener listener);

  /**
   * <p>
   * </p>
   * 
   * @param listener
   */
  void removeModularizedSystemChangedListener(IModularizedSystemChangedListener listener);

  /**
   * <p>
   * Creates a new IModifiableResourceModule and adds it to this {@link IModularizedSystem}.
   * </p>
   * 
   * @param moduleIdentifier
   *          the module identifier
   * @return
   */
  IModifiableModule createResourceModule(IModuleIdentifier moduleIdentifier);

  /**
   * <p>
   * </p>
   * 
   * @param moduleIdentifier
   * @param path
   * @return
   */
  IModifiableModule createResourceModule(ModuleIdentifier moduleIdentifier, IPath path);

  /**
   * <p>
   * Adds the given {@link IModifiableResourceModule} to this {@link IModularizedSystem}.
   * </p>
   * 
   * @param identifier
   * @param resourceModule
   */
  void addModule(IModule resourceModule);

  /**
   * <p>
   * Removes the module with the given {@link IModuleIdentifier}.
   * </p>
   * 
   * @param identifier
   *          the {@link IModuleIdentifier}.
   */
  void removeModule(IModuleIdentifier identifier);

  /**
   * <p>
   * Removes the given {@link IModule} from this {@link IModularizedSystem}.
   * </p>
   * 
   * @param module
   */
  void removeModule(IModule module);

  /**
   * <p>
   * Returns the {@link IModifiableResourceModule} with the given {@link IModuleIdentifier}.
   * </p>
   * 
   * @param moduleIdentifier
   * @return
   */
  IModifiableModule getModifiableResourceModule(IModuleIdentifier moduleIdentifier);

  /**
   * <p>
   * Returns all {@link IModifiableResourceModule IModifiableResourceModules} for this {@link IModularizedSystem}.
   * </p>
   * 
   * @return
   */
  Collection<IModifiableModule> getModifiableResourceModules();

  /**
   * <p>
   * </p>
   * 
   * @param isDisabled
   */
  void disableModelModifiedNotification(boolean isDisabled);

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean isModelModifiedNotificationDisabled();

  /**
   * <p>
   * </p>
   * 
   * @param append
   * @return
   */
  IGroup getGroup(IPath append);

  /**
   * <p>
   * </p>
   * 
   * @param absolutePath
   * @return
   */
  IGroup getOrCreateGroup(IPath absolutePath);

  /**
   * <p>
   * </p>
   * 
   * @param group
   */
  void removeGroup(IGroup group);

  /**
   * <p>
   * </p>
   * 
   * @param path
   */
  void removeGroup(IPath path);

  // TODO: UNIFY with 'disableModelModifiedNotification'
  boolean isHandleModelModification();

  // TODO: UNIFY with 'disableModelModifiedNotification'
  void setHandleModelModification(boolean handleModelModification);

  /**
   * <p>
   * </p>
   * 
   * @param monitor
   * @param transformations
   */
  void applyTransformations(IProgressMonitor monitor, List<ITransformation> transformations);

  /**
   * <p>
   * </p>
   * 
   * @param monitor
   * @param transformation
   */
  void applyTransformations(IProgressMonitor monitor, ITransformation... transformation);

  void resourcesChanged(Collection<? extends IResource> resources, IModule module, ChangeAction added);

  void typeChanged(IType type, IModule module, ChangeAction added);

  IType getType(String fullyQualifiedName);

  Set<IReference> getUnsatisfiedReferences(IModule resourceModule);
}

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
package org.bundlemaker.core.internal.modules.modularizedsystem;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.bundlemaker.core.internal.JdkModuleCreator;
import org.bundlemaker.core.internal.modules.ResourceModule;
import org.bundlemaker.core.internal.modules.TypeModule;
import org.bundlemaker.core.internal.resource.Type;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.modules.modifiable.IModifiableResourceModule;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.projectdescription.IFileBasedContent;
import org.bundlemaker.core.projectdescription.IRootPath;
import org.bundlemaker.core.resource.TypeEnum;
import org.bundlemaker.core.transformation.ITransformation;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractTransformationAwareModularizedSystem extends AbstractModularizedSystem {

  /**
   * <p>
   * Creates a new instance of type {@link AbstractTransformationAwareModularizedSystem}.
   * </p>
   * 
   * @param name
   * @param projectDescription
   */
  public AbstractTransformationAwareModularizedSystem(String name, IBundleMakerProjectDescription projectDescription) {
    super(name, projectDescription);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void applyTransformations(IProgressMonitor progressMonitor) {

    SubMonitor subMonitor = SubMonitor.convert(progressMonitor);
    subMonitor.beginTask("Transforming Module '" + getName() + "'", 100);

    // step 1: clear prior results
    getModifiableResourceModulesMap().clear();
    getModifiableNonResourceModulesMap().clear();
    preApplyTransformations();

    // // step 2: set up the JRE
    try {
      TypeModule jdkModule = JdkModuleCreator.getJdkModules(this).get(0);
      setExecutionEnvironment(jdkModule);
      getModifiableNonResourceModulesMap().put(getExecutionEnvironment().getModuleIdentifier(),
          (TypeModule) getExecutionEnvironment());
    } catch (CoreException e1) {
      e1.printStackTrace();
    }

    subMonitor.worked(10);

    // step 3: create the type modules
    for (IFileBasedContent fileBasedContent : getProjectDescription().getFileBasedContent()) {
      if (!fileBasedContent.isResourceContent()) {
        IModuleIdentifier identifier = new ModuleIdentifier(fileBasedContent.getName(), fileBasedContent.getVersion());
        // TODO!!
        try {
          TypeModule typeModule = createTypeModule(fileBasedContent.getId(), identifier, new File[] { fileBasedContent
              .getBinaryRootPaths().toArray(new IRootPath[0])[0].getAsFile() });
          getModifiableNonResourceModulesMap().put(typeModule.getModuleIdentifier(), typeModule);
        } catch (CoreException ex) {
          // TODO
          ex.printStackTrace();
        }
      }
    }
    subMonitor.worked(10);

    // step 4: transform modules
    List<ITransformation> transformations = getTransformations();
    SubMonitor transformationMonitor = subMonitor.newChild(70);
    transformationMonitor.beginTask("Begin", transformations.size() * 4);

    for (ITransformation transformation : transformations) {

      // step 4.1: apply transformation
      transformation.apply((IModifiableModularizedSystem) this, transformationMonitor.newChild(1));

      // step 4.2: clean up empty modules
      for (Iterator<Entry<IModuleIdentifier, IModifiableResourceModule>> iterator = getModifiableResourceModulesMap()
          .entrySet().iterator(); iterator.hasNext();) {

        // get next module
        Entry<IModuleIdentifier, IModifiableResourceModule> module = iterator.next();

        // if the module is empty - remove it
        if (module.getValue().getResources(ContentType.BINARY).isEmpty()
            && module.getValue().getResources(ContentType.SOURCE).isEmpty()) {

          // remove the module
          iterator.remove();
        }
      }
      transformationMonitor.worked(1);
    }

    postApplyTransformations();
    // subMonitor.worked(10);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IModifiableResourceModule createResourceModule(IModuleIdentifier createModuleIdentifier) {

    // create the result
    ResourceModule result = new ResourceModule(createModuleIdentifier, this);

    // add it to the internal hash map
    getModifiableResourceModulesMap().put(result.getModuleIdentifier(), result);

    // return the result
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addModifiableResourceModule(IModifiableResourceModule resourceModule) {
    Assert.isNotNull(resourceModule);
    Assert.isTrue(!getModifiableResourceModulesMap().containsKey(resourceModule.getModuleIdentifier()));

    //
    getModifiableResourceModulesMap().put(resourceModule.getModuleIdentifier(), resourceModule);

    // notify
    resourceModuleAdded(resourceModule);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeModule(IModuleIdentifier identifier) {
    Assert.isNotNull(identifier);
    Assert.isTrue(getModifiableResourceModulesMap().containsKey(identifier));

    // remove the entry
    IModifiableResourceModule resourceModule = getModifiableResourceModulesMap().remove(identifier);

    // notify
    resourceModuleRemoved(resourceModule);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeModule(IModule module) {
    Assert.isNotNull(module);

    // remove the module
    removeModule(module.getModuleIdentifier());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<IModifiableResourceModule> getModifiableResourceModules() {

    // return an unmodifiable copy
    return Collections.unmodifiableCollection(getModifiableResourceModulesMap().values());
  }

  /**
   * <p>
   * </p>
   * 
   */
  protected void preApplyTransformations() {
    // do nothing...
  }

  /**
   * <p>
   * </p>
   * 
   */
  protected void postApplyTransformations() {
    // do nothing...
  }

  /**
   * <p>
   * </p>
   * 
   * @param resourceModule
   */
  protected void resourceModuleAdded(IModifiableResourceModule resourceModule) {
    // do nothing...
  }

  /**
   * <p>
   * </p>
   * 
   * @param resourceModule
   */
  protected void resourceModuleRemoved(IModifiableResourceModule resourceModule) {
    // do nothing...
  }

  /**
   * <p>
   * </p>
   * 
   * @param identifier
   * @param files
   * @return
   */
  private TypeModule createTypeModule(String contentId, IModuleIdentifier identifier, File... files) {

    // create the type module
    TypeModule typeModule = new TypeModule(identifier, this);

    //
    for (int i = 0; i < files.length; i++) {

      // add all the contained types
      try {

        // TODO DIRECTORIES!!
        // TODO:PARSE!!
        List<String> types = getContainedTypesFromJarFile(files[i]);

        for (String type : types) {

          // TODO: TypeEnum!!
          Type type2 = new Type(type, TypeEnum.CLASS, contentId);

          // type2.setTypeModule(typeModule);

          typeModule.getModifiableSelfResourceContainer().add(type2);
        }

      } catch (IOException e) {

        //
        e.printStackTrace();
      }
    }
    // return the module
    return typeModule;
  }

  /**
   * <p>
   * </p>
   * 
   * @param file
   * @return
   * @throws IOException
   */
  private static List<String> getContainedTypesFromJarFile(File file) throws IOException {

    // create the result list
    List<String> result = new LinkedList<String>();

    // create the jar file
    JarFile jarFile = new JarFile(file);

    // get the entries
    Enumeration<JarEntry> entries = jarFile.entries();
    while (entries.hasMoreElements()) {
      JarEntry jarEntry = (JarEntry) entries.nextElement();
      if (jarEntry.getName().endsWith(".class")) {
        result.add(jarEntry.getName().substring(0, jarEntry.getName().length() - ".class".length()).replace('/', '.'));
      }
    }

    // return the result
    return result;
  }
}

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
package org.bundlemaker.core.internal;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core._type.IParsableTypeResource;
import org.bundlemaker.core._type.ITypeModule;
import org.bundlemaker.core._type.TypeEnum;
import org.bundlemaker.core._type.internal.DefaultTypeSelector;
import org.bundlemaker.core._type.internal.Type;
import org.bundlemaker.core.common.utils.FileUtils;
import org.bundlemaker.core.common.utils.VMInstallUtils;
import org.bundlemaker.core.internal.api.resource.IResourceStandin;
import org.bundlemaker.core.internal.modules.Module;
import org.bundlemaker.core.internal.parser.ResourceCache;
import org.bundlemaker.core.internal.resource.ModuleIdentifier;
import org.bundlemaker.core.internal.resource.MovableUnit;
import org.bundlemaker.core.internal.resource.Resource;
import org.bundlemaker.core.internal.resource.ResourceStandin;
import org.bundlemaker.core.resource.IModularizedSystem;
import org.bundlemaker.core.resource.IModule;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.LibraryLocation;

public class JdkModuleCreator {

  /**
   * @param bundleMakerProject
   * @return
   * @throws CoreException
   */
  public static IModule getJdkModules(IModularizedSystem modularizedSystem) throws CoreException {

    try {

      //
      String jre = modularizedSystem.getBundleMakerProject().getProjectDescription().getJRE();

      // get the vm install (has to exist exist)
      IVMInstall vmInstall = VMInstallUtils.getIVMInstall(jre);

      if (vmInstall == null) {
        vmInstall = JavaRuntime.getDefaultVMInstall();
      }

      // create virtual modules for the vms
      return createModuleForVMInstall(vmInstall, modularizedSystem);

    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  /**
   * @param vmInstall
   * @return
   * @throws CoreException
   * @throws IOException
   */
  private static IModule createModuleForVMInstall(IVMInstall vmInstall, IModularizedSystem modularizedSystem)
      throws CoreException, IOException {

    Module jdkModule = new Module(new ModuleIdentifier(vmInstall.getName(), vmInstall.getName()),
        modularizedSystem);

    jdkModule.adaptAs(ITypeModule.class);

    // TODO
    jdkModule.setResourceModule(true);

    //
    Set<IResourceStandin> resources = new HashSet<IResourceStandin>();

    //
    for (LibraryLocation libraryLocation : JavaRuntime.getLibraryLocations(vmInstall)) {

      // get the root
      File root = libraryLocation.getSystemLibraryPath().toFile();

      // get the children
      List<String> children = FileUtils.getAllChildren(root);

      //
      ResourceCache resourceCache = new ResourceCache();

      for (String child : children) {

        //
        if (child.equalsIgnoreCase("META-INF/MANIFEST.MF") || child.endsWith("JCE_RSA.RSA")
            || child.endsWith("JCE_RSA.SF")) {
          continue;
        }

        //
        Resource resource = new Resource(DefaultTypeSelector.BUNDLEMAKER_INTERNAL_JDK_MODULE_IDENTIFIER,
            root.getAbsolutePath(), child, resourceCache);

        ResourceStandin resourceStandin = new ResourceStandin(resource);

        // TODO: Parsing!! ITYPE
        if (child.endsWith(".class")) {

          //
          String typeName = child.substring(0, child.length() - ".class".length());

          // if (packageName.indexOf('.') != -1) {
          //
          // packageName = packageName.substring(0,
          // packageName.lastIndexOf('.'));

          typeName = typeName.replace('/', '.');
          typeName = typeName.replace('\\', '.');

          Type type = resource.adaptAs(IParsableTypeResource.class).getOrCreateType(typeName, TypeEnum.CLASS, false);

          //
          if (type.isLocalOrAnonymousType()) {
            continue;
          }

          type.setBinaryResource(resource);

          Assert.isNotNull(type.getBinaryResource());
        }

        resources.add(resourceStandin);
      }
    }

    //
    for (IResourceStandin resource : resources) {
      jdkModule.addMovableUnit(new MovableUnit(null, resource));
    }

    //
    return jdkModule;
  }
}

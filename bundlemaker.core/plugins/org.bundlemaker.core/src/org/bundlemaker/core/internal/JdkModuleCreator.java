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

import org.bundlemaker.core.internal.modules.Module;
import org.bundlemaker.core.internal.modules.modularizedsystem.DefaultTypeSelector;
import org.bundlemaker.core.internal.parser.ResourceCache;
import org.bundlemaker.core.internal.projectdescription.IResourceStandin;
import org.bundlemaker.core.internal.resource.Resource;
import org.bundlemaker.core.internal.resource.ResourceStandin;
import org.bundlemaker.core.internal.resource.Type;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.projectdescription.ProjectContentType;
import org.bundlemaker.core.resource.TypeEnum;
import org.bundlemaker.core.util.FileUtils;
import org.bundlemaker.core.util.JdkCreator;
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
      IVMInstall vmInstall = JdkCreator.getIVMInstall(jre);

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

    Module virtualModule = new Module(new ModuleIdentifier(vmInstall.getName(), vmInstall.getName()),
        modularizedSystem);

    virtualModule.setResourceModule(false);

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

          Type type = resource.getOrCreateType(typeName, TypeEnum.CLASS, false);

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
    virtualModule.addAll(resources, ProjectContentType.BINARY);

    //
    return virtualModule;
  }
}

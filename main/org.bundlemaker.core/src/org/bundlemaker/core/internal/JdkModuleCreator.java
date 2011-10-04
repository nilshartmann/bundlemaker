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

import java.io.IOException;
import java.util.List;

import org.bundlemaker.core.internal.modules.TypeContainer;
import org.bundlemaker.core.internal.modules.TypeModule;
import org.bundlemaker.core.internal.modules.modularizedsystem.DefaultTypeSelector;
import org.bundlemaker.core.internal.resource.Type;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.resource.TypeEnum;
import org.bundlemaker.core.util.FileUtils;
import org.bundlemaker.core.util.JdkCreator;
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
  public static TypeModule getJdkModules(IModularizedSystem modularizedSystem) throws CoreException {

    try {

      //
      String jre = modularizedSystem.getBundleMakerProject().getProjectDescription().getJRE();

      // get the vm install (has to exist exist)
      IVMInstall vmInstall = JdkCreator.getIVMInstall(jre);

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
  private static TypeModule createModuleForVMInstall(IVMInstall vmInstall, IModularizedSystem modularizedSystem)
      throws CoreException, IOException {

    TypeModule virtualModule = new TypeModule(new ModuleIdentifier(vmInstall.getName(), vmInstall.getName()),
        modularizedSystem);

    for (LibraryLocation libraryLocation : JavaRuntime.getLibraryLocations(vmInstall)) {

      List<String> children = FileUtils.getAllChildren(libraryLocation.getSystemLibraryPath().toFile());

      for (String child : children) {

        // TODO: Parsing!! ITYPE
        if (child.endsWith(".class")) {
          String typeName = child.substring(0, child.length() - ".class".length());

          // if (packageName.indexOf('.') != -1) {
          //
          // packageName = packageName.substring(0,
          // packageName.lastIndexOf('.'));

          typeName = typeName.replace('/', '.');
          typeName = typeName.replace('\\', '.');

          // TODO
          Type type = new Type(typeName, TypeEnum.CLASS, DefaultTypeSelector.BUNDLEMAKER_INTERNAL_JDK_MODULE_IDENTIFIER);
          //
          // type.setTypeModule(virtualModule);

          ((TypeContainer) virtualModule.getModifiableSelfResourceContainer()).add(type);
          // }
        }
      }

    }

    return virtualModule;
  }
}

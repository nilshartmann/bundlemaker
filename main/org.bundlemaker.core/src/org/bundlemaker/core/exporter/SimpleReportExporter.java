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
package org.bundlemaker.core.exporter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IReferencedModulesQueryResult;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.query.ReferenceQueryFilters;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class SimpleReportExporter extends AbstractExporter {

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean canExport(IModularizedSystem modularizedSystem, IResourceModule module, IModuleExporterContext context) {
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void doExport(IProgressMonitor progressMonitor) throws CoreException {

    SubMonitor subMonitor = SubMonitor.convert(progressMonitor, 10);
    subMonitor.beginTask(null, 10);

    StringBuilder builder = new StringBuilder();
    builder.append(getCurrentModule().getModuleIdentifier().toString() + "\n");

    builder.append("\n");
    builder.append("Source-Content: \n");

    for (IResource resource : asSortedList(getCurrentModule().getResources(ContentType.SOURCE))) {
      builder.append(resource.getPath() + "\n");

      for (IReference reference : resource.getReferences()) {
        builder.append(" * " + reference.toString() + "\n");
      }

      for (IType type : resource.getContainedTypes()) {
        builder.append(" - " + type.getFullyQualifiedName() + "\n");

        for (IReference reference : type.getReferences()) {
          builder.append("   * " + reference.toString() + "\n");
        }
      }
    }
    subMonitor.worked(1);

    builder.append("\n");
    builder.append("Binary-Content: \n");
    for (IResource resource : asSortedList(getCurrentModule().getResources(ContentType.BINARY))) {
      builder.append(resource.getPath() + "\n");

      for (IReference reference : resource.getReferences()) {
        builder.append(" * " + reference.toString() + "\n");
      }

      for (IResource stickyResources : resource.getStickyResources()) {
        builder.append(" ~sticky~ " + stickyResources.getPath() + "\n");
      }

      for (IType type : resource.getContainedTypes()) {
        builder.append(" - " + type.getFullyQualifiedName() + "\n");

        for (IReference reference : type.getReferences()) {
          builder.append("   * " + reference.toString() + "\n");
        }
      }
    }
    subMonitor.worked(1);

    builder.append("\n");
    builder.append("Referenced Types: \n");
    Set<String> referencedTypes = getCurrentModule().getReferencedTypeNames(
        ReferenceQueryFilters.ALL_DIRECT_EXTERNAL_REFERENCES_QUERY_FILTER);
    for (String referencedType : asSortedList(referencedTypes)) {
      builder.append(referencedType + "\n");
    }
    subMonitor.worked(2);

    builder.append("\n");
    builder.append("Indirectly referenced Types: \n");
    Set<String> indirectlyReferencedTypes = getCurrentModule().getReferencedTypeNames(
        ReferenceQueryFilters.ALL_DIRECT_EXTERNAL_REFERENCES_QUERY_FILTER);
    for (String referencedType : asSortedList(indirectlyReferencedTypes)) {
      if (!referencedTypes.contains(referencedType)) {
        builder.append(referencedType + "\n");
      }
    }
    subMonitor.worked(2);

    builder.append("\n");
    builder.append("Referenced Modules: \n");
    IReferencedModulesQueryResult queryResult = getCurrentModularizedSystem().getReferencedModules(getCurrentModule(),
        null);

    for (IModule referencedModule : queryResult.getReferencedModules()) {
      builder.append(referencedModule.getModuleIdentifier().toString() + "\n");
    }
    subMonitor.worked(1);

    builder.append("\n");
    builder.append("Transitive referenced modules: \n");
    Set<IModule> referencedModules = getCurrentModularizedSystem().getTransitiveReferencedModules(getCurrentModule(),
        ReferenceQueryFilters.ALL_DIRECT_EXTERNAL_REFERENCES_QUERY_FILTER).getReferencedModules();

    for (IModule referencedModule : referencedModules) {
      builder.append("  " + referencedModule.getModuleIdentifier().toString() + "\n");
    }
    subMonitor.worked(1);

    // TODO
    builder.append("\n");
    builder.append("Missing Types: \n");
    for (IReference missingType : queryResult.getUnsatisfiedReferences()) {
      builder.append(missingType + "\n");
    }
    subMonitor.worked(1);

    // builder.append("\n");
    // builder.append("Types with ambigious modules: \n");
    // for (Entry<String, Set<IModule>> missingType : queryResult.getReferencedTypesWithAmbiguousModules().entrySet()) {
    //
    // builder.append(missingType.getKey() + ":\n");
    // for (IModule typeModule : missingType.getValue()) {
    // builder.append(" - " + typeModule.getModuleIdentifier().toString() + "\n");
    // }
    // }

    try {
      //
      File outFile = new File(getCurrentContext().getDestinationDirectory(), getCurrentModule().getModuleIdentifier()
          .toString() + ".txt");

      if (!outFile.getParentFile().exists()) {
        outFile.getParentFile().mkdirs();
      }

      FileWriter fileWriter = new FileWriter(outFile);
      fileWriter.write(builder.toString());
      fileWriter.flush();
      fileWriter.close();
      subMonitor.worked(2);

    } catch (IOException e) {
      // TODO
      e.printStackTrace();
      throw new CoreException(new Status(IStatus.ERROR, "", ""));
    }

  }

  /**
   * <p>
   * </p>
   * 
   * @param <T>
   * @param set
   * @return
   */
  private static <T extends Comparable<T>> List<T> asSortedList(Set<T> set) {

    //
    List<T> arrayList = new ArrayList<T>(set);

    //
    Collections.sort(arrayList);

    //
    return arrayList;
  }
}

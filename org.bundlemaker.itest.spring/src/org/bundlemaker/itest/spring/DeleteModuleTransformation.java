package org.bundlemaker.itest.spring;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.transformation.ITransformation;
import org.eclipse.core.runtime.IProgressMonitor;

public class DeleteModuleTransformation implements ITransformation {

  /** - */
  private IModuleIdentifier _moduleIdentifier;

  /**
   * <p>
   * Creates a new instance of type {@link DeleteModuleTransformation}.
   * </p>
   * 
   * @param name
   * @param version
   */
  public DeleteModuleTransformation(String name, String version) {
    this(new ModuleIdentifier(name, version));
  }

  /**
   * <p>
   * Creates a new instance of type {@link DeleteModuleTransformation}.
   * </p>
   * 
   * @param moduleIdentifier
   */
  public DeleteModuleTransformation(IModuleIdentifier moduleIdentifier) {

    //
    _moduleIdentifier = moduleIdentifier;
  }

  /**
   * {@inheritDoc}
   */
  public void apply(IModifiableModularizedSystem modularizedSystem, IProgressMonitor progressMonitor) {

    Set<IResource> sourceResources = modularizedSystem.getModifiableResourceModule(_moduleIdentifier).getResources(
        ContentType.SOURCE);

    System.out.println("Unassigned sources:");
    for (IResource resource : getSortedResources(sourceResources)) {
      System.out.println(" - " + resource.getRoot() + "\\" + resource.getPath().replace('/', '\\'));
    }

    Set<IResource> binaryResources = modularizedSystem.getModifiableResourceModule(_moduleIdentifier).getResources(
        ContentType.BINARY);

    System.out.println("Unassigned classes:");
    for (IResource resource : getSortedResources(binaryResources)) {
      System.out.println(" - " + resource.getRoot() + "\\" + resource.getPath().replace('/', '\\'));
    }

    //
    modularizedSystem.removeModule(_moduleIdentifier);
  }

  /**
   * <p>
   * </p>
   * 
   * @param sourceResources
   * @return
   */
  private List<IResource> getSortedResources(Set<IResource> sourceResources) {

    //
    List<IResource> sorted = new LinkedList<IResource>(sourceResources);

    //
    Collections.sort(sorted, new Comparator<IResource>() {
      public int compare(IResource resource1, IResource resource2) {

        //
        if (!resource1.getRoot().equals(resource2.getRoot())) {
          return resource1.getRoot().compareTo(resource2.getRoot());
        }

        //
        return resource1.getPath().compareTo(resource2.getPath());
      }
    });

    //
    return sorted;
  }
}

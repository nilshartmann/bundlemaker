package org.bundlemaker.core.analysis.algorithms.sorter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;

public class DsmSorterRegistry {
  private static DsmSorterRegistry     singleton       = null;

  private Map<String, IArtifactSorter> artifactSorters = new HashMap<String, IArtifactSorter>();

  private IArtifactSorter              defaultSorter   = null;

  public static DsmSorterRegistry get() {
    if (singleton == null) {
      singleton = new DsmSorterRegistry();
    }
    return singleton;
  }

  public static IArtifactSorter getDefaultArtifactSorter() {
    return get().defaultSorter;
  }

  public static IArtifactSorter getArtifactSorter(String sorterName) {
    return get().artifactSorters.get(sorterName);
  }

  public static String[] getArtifactSorterNames() {
    String[] artifactSorterNames = new String[get().artifactSorters.keySet().size()];
    get().artifactSorters.keySet().toArray(artifactSorterNames);
    Arrays.sort(artifactSorterNames);

    return artifactSorterNames;
  }

  public DsmSorterRegistry() {
    IConfigurationElement[] dsmSorters = Platform.getExtensionRegistry().getConfigurationElementsFor(
        "org.bundlemaker.core.dsmsorter");
    IExtension[] extensions = Platform.getExtensionRegistry().getExtensionPoint("org.bundlemaker.core.dsmsorter")
        .getExtensions();

    for (IConfigurationElement dsmSorter : dsmSorters) {
      String name = dsmSorter.getAttribute("name");
      IArtifactSorter sorterImpl;
      try {
        sorterImpl = (IArtifactSorter) dsmSorter.createExecutableExtension("class");
        artifactSorters.put(name, sorterImpl);
        if (defaultSorter == null) {
          defaultSorter = sorterImpl;
        }
      } catch (CoreException e) {
        e.printStackTrace();
      }
    }
  }
}

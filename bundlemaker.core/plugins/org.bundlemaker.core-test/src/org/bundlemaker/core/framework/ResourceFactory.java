package org.bundlemaker.core.framework;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core._type.IParsableTypeResource;
import org.bundlemaker.core._type.TypeEnum;
import org.bundlemaker.core.internal.parser.ResourceCache;
import org.bundlemaker.core.internal.projectdescription.IResourceStandin;
import org.bundlemaker.core.internal.resource.Resource;
import org.bundlemaker.core.internal.resource.ResourceStandin;
import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.util.FileUtils;
import org.eclipse.core.runtime.CoreException;

public class ResourceFactory {

  /**
   * <p>
   * </p>
   * 
   * @return
   * @throws CoreException
   */
  public static Set<IResourceStandin> getResources() throws CoreException {

    //
    File testDir = new File(System.getProperty("user.dir"), "test-data/com.example/classes");

    //
    List<String> children = FileUtils.getAllChildren(testDir);

    //
    Set<IResourceStandin> result = new HashSet<IResourceStandin>();

    //
    ResourceCache resourceCache = new ResourceCache();

    //
    for (String child : children) {

      //
      Resource resource = new Resource("01", testDir.getAbsolutePath(), child, resourceCache);
      
      //
      if (child.endsWith(".class")) {
        String typeName = child.substring(0, child.length() - ".class".length());

        typeName = typeName.replace('/', '.');
        typeName = typeName.replace('\\', '.');

        //
        resource.adaptAs(IParsableTypeResource.class).getOrCreateType(typeName, TypeEnum.CLASS, false);
      }

      //
      result.add(new ResourceStandin(resource));
    }

    //
    return result;
  }
}

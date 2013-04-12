package org.bundlemaker.core.framework;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.internal.parser.ResourceCache;
import org.bundlemaker.core.internal.resource.Resource;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.TypeEnum;
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
  public static List<IResource> getResources() throws CoreException {

    //
    File testDir = new File(System.getProperty("user.dir"), "test-data/com.example/classes");

    //
    List<String> children = FileUtils.getAllChildren(testDir);

    //
    List<IResource> result = new LinkedList<IResource>();

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
        resource.getOrCreateType(typeName, TypeEnum.CLASS, false);
      }

      //
      result.add(resource);
    }

    //
    return result;
  }
}

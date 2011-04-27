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
package org.bundlemaker.core.internal.parser;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.bundlemaker.core.parser.IDirectory;
import org.bundlemaker.core.parser.IDirectoryFragment;
import org.bundlemaker.core.projectdescription.IFileBasedContent;
import org.bundlemaker.core.projectdescription.IRootPath;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class FileContentReader {

  /**
   * <p>
   * </p>
   * 
   * @param fileBasedContent
   * @param filterNonAnalyzableSource
   * @return
   * @throws CoreException
   */
  public static List<IDirectory> getDirectories(IFileBasedContent fileBasedContent, boolean filterNonAnalyzableSource)
      throws CoreException {

    Assert.isNotNull(fileBasedContent);

    // create the (result) directory map
    Map<String, Directory> directoryMap = new HashMap<String, Directory>();

    // add all binary paths
    for (IRootPath path : fileBasedContent.getBinaryRootPaths()) {
      getAllPackages(fileBasedContent, directoryMap, path.getAsFile(), false);
    }

    if (!fileBasedContent.getSourceRootPaths().isEmpty()
        && (!filterNonAnalyzableSource || fileBasedContent.isAnalyzeSourceResources())) {
      for (IRootPath path : fileBasedContent.getSourceRootPaths()) {
        getAllPackages(fileBasedContent, directoryMap, path.getAsFile(), true);
      }
    }

    Collection<Directory> result = directoryMap.values();

    // TODO!!
    for (Iterator<Directory> iterator = result.iterator(); iterator.hasNext();) {

      Directory directory = iterator.next();

      // TODO!!
      if (!directory.isValid()) {
        iterator.remove();
      }
    }

    // return the result
    return new LinkedList<IDirectory>(result);
  }

  /**
   * <p>
   * </p>
   * 
   * @param fileContent
   * @param packages
   * @param contentRoot
   * @param isSource
   */
  private static void getAllPackages(IFileBasedContent fileContent, Map<String, Directory> packages, File contentRoot,
      boolean isSource) {

    // TODO
    if (!contentRoot.exists()) {
      throw new RuntimeException("File '" + contentRoot.getAbsolutePath() + "' does not exist!");
    }

    //
    if (contentRoot.isDirectory()) {
      getAllPackagesFromDirectory(fileContent, contentRoot, packages, isSource);
    } else {
      getAllPackagesFromJar(fileContent, contentRoot, packages, isSource);
    }
  }

  /**
   * <p>
   * Returns all the names of the packages that are contained in the specified jar file. The package list contains the
   * packages that contain classes as well as all parent packages of those.
   * </p>
   * 
   * @param jar
   * @return
   * @throws IOException
   */
  private static Map<String, Directory> getAllPackagesFromJar(IFileBasedContent fileContent, File jar,
      Map<String, Directory> directoryMap, boolean isSourceContent) {

    Assert.isNotNull(directoryMap);
    Assert.isTrue(jar != null && jar.isFile());

    // create the jarFile wrapper...
    JarFile jarFile = null;

    try {
      jarFile = new JarFile(jar);
    } catch (IOException e) {
      throw new RuntimeException(String.format("Could not create jar file from file '%s'.", jar.getAbsolutePath()));
    }

    //
    Assert.isNotNull(jarFile);

    // Iterate over entries...
    Enumeration<?> enumeration = jarFile.entries();

    while (enumeration.hasMoreElements()) {
      JarEntry jarEntry = (JarEntry) enumeration.nextElement();

      // add package for each found directory...
      String directoryName = null;
      String entryName = null;

      // if the jar entry is a directory, the directory name is the name
      // of the jar entry...
      if (jarEntry.isDirectory()) {
        directoryName = jarEntry.getName();
      }
      // otherwise the directory name has to be computed
      else {
        int splitIndex = jarEntry.getName().lastIndexOf('/');
        if (splitIndex != -1) {
          directoryName = jarEntry.getName().substring(0, splitIndex);
          entryName = jarEntry.getName().substring(splitIndex);
        }
      }

      if (directoryName != null && entryName != null) {

        // convert path to package name
        String packageName = directoryName.endsWith("/") ? directoryName.substring(0, directoryName.length() - 1)
            : directoryName;

        // create package if necessary
        if (!directoryMap.containsKey(packageName)) {
          directoryMap.put(packageName, new Directory(fileContent, new Path(packageName), null, null));
        }

        Directory directory = directoryMap.get(packageName);

        //
        JarFileBasedDirectoryFragment jarFileBasedDirectoryFragment = null;

        List<IDirectoryFragment> fragments = isSourceContent ? directory.getSourceDirectoryFragments() : directory
            .getBinaryDirectoryFragments();
        //
        for (IDirectoryFragment directoryFragment : fragments) {

          // TODO
          if (directoryFragment instanceof JarFileBasedDirectoryFragment
              && ((JarFileBasedDirectoryFragment) directoryFragment).getJarFile().equals(jarFile)) {

            jarFileBasedDirectoryFragment = ((JarFileBasedDirectoryFragment) directoryFragment);
          }
        }

        //
        if (jarFileBasedDirectoryFragment == null) {

          jarFileBasedDirectoryFragment = new JarFileBasedDirectoryFragment(new File(jarFile.getName()), jarFile);

          //
          if (isSourceContent) {
            directory.addSourceContent(jarFileBasedDirectoryFragment);
          } else {
            directory.addBinaryContent(jarFileBasedDirectoryFragment);
          }
        }

        jarFileBasedDirectoryFragment.addJarEntry(jarEntry);
      }
    }

    // return result...
    return directoryMap;
  }

  /**
   * <p>
   * </p>
   * 
   * @param content
   * @param directory
   * @param packages
   * @param isSource
   */
  private static void getAllPackagesFromDirectory(IFileBasedContent content, File directory,
      Map<String, Directory> packages, boolean isSource) {

    //
    getAllPackagesFromDirectory(content, directory, directory, packages, isSource);
  }

  /**
   * <p>
   * Returns all children of 'root'.
   * </p>
   * 
   * @param fileContent
   * @param root
   * @param directory
   * @param packages
   * @param isSource
   */
  private static void getAllPackagesFromDirectory(IFileBasedContent fileContent, File root, File directory,
      Map<String, Directory> packages, boolean isSource) {

    // get the package directories
    String packageDirectory = root.equals(directory) ? "" : directory.getAbsolutePath().substring(
        root.getAbsolutePath().length() + 1);

    // 'normalize' the package directory
    packageDirectory = packageDirectory.replace('\\', '/');

    // declare the directory content
    FolderBasedDirectoryFragment folderBasedDirectoryContent = null;

    // iterate over all file
    for (File child : directory.listFiles()) {

      // handle file
      if (child.isFile()) {

        // create IFolderBasedDirectoryContent if necessary
        if (folderBasedDirectoryContent == null) {

          // create package if necessary
          if (!packages.containsKey(packageDirectory)) {
            packages.put(packageDirectory, new Directory(fileContent, new Path(packageDirectory), null, null));
          }

          // create the Directory object
          Directory dir = packages.get(packageDirectory);
          folderBasedDirectoryContent = new FolderBasedDirectoryFragment(root);

          // add as source...
          if (isSource) {
            dir.addSourceContent(folderBasedDirectoryContent);
          }

          // ...or binary content
          else {
            dir.addBinaryContent(folderBasedDirectoryContent);
          }
        }

        // add the content
        folderBasedDirectoryContent.getContent().add(packageDirectory + "/" + child.getName());

      }

      // handle directories
      else {

        // recursive call
        getAllPackagesFromDirectory(fileContent, root, child, packages, isSource);
      }
    }
  }

}

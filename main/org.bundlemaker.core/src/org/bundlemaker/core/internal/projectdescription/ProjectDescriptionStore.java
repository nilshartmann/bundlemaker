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
package org.bundlemaker.core.internal.projectdescription;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.internal.BundleMakerProject;
import org.bundlemaker.core.model.internal.projectdescription.xml.ContentProviderFactoryType;
import org.bundlemaker.core.model.internal.projectdescription.xml.XmlProjectDescriptionType;
import org.bundlemaker.core.projectdescription.IProjectContentProvider;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;

public class ProjectDescriptionStore {

  /**
   * @param project
   * @param projectDescription
   * @throws CoreException
   */
  public static void saveProjectDescription(IProject project, BundleMakerProjectDescription projectDescription)
      throws CoreException {

    Assert.isNotNull(project);
    Assert.isNotNull(projectDescription);

    // create the xml project description
    XmlProjectDescriptionType xmlProjectDescription = new XmlProjectDescriptionType();
    xmlProjectDescription.setCurrentId(projectDescription.getCurrentId());
    xmlProjectDescription.setJre(projectDescription.getJRE());

    // add the file based content
    // TODO
    for (IProjectContentProvider contentProvider : projectDescription.getContentProviders()) {

      ContentProviderFactoryType providerFactoryType = new ContentProviderFactoryType();

      providerFactoryType.setContentProviderFactoryClass(contentProvider.getClass().getName());
      providerFactoryType.setContentProviderFactoryId(contentProvider.getId());
      providerFactoryType.setAny(contentProvider.getConfiguration());

      xmlProjectDescription.getContentProviderFactory().add(providerFactoryType);
    }

    //
    IFile iFile = project.getFile(BundleMakerCore.PROJECT_DESCRIPTION_PATH);

    ByteArrayInputStream in = new ByteArrayInputStream(XmlProjectDescriptionExporterUtils
        .marshal(xmlProjectDescription).getBytes());

    if (!iFile.exists()) {
      iFile.create(in, true, null);
    } else {
      iFile.setContents(in, true, false, null);
    }

    try {
      in.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public static BundleMakerProjectDescription loadProjectDescription(IBundleMakerProject project) throws CoreException {

    //
    IFile iFile = project.getProject().getFile(BundleMakerCore.PROJECT_DESCRIPTION_PATH);

    // refresh
    iFile.refreshLocal(IFile.DEPTH_INFINITE, null);

    // we need this here to prevent an exception while deleting the bundlemaker project
    byte[] bs;
    bs = read(iFile.getRawLocation().toFile());

    XmlProjectDescriptionType xmlProjectDescription = XmlProjectDescriptionExporterUtils
        .unmarshal(new ByteArrayInputStream(bs));

    BundleMakerProjectDescription result = new BundleMakerProjectDescription((BundleMakerProject) project);
    result.setCurrentId(xmlProjectDescription.getCurrentId());
    result.setJreInternal(xmlProjectDescription.getJre());

    //
    JaxbCompoundClassLoader jaxbCompoundClassLoader = new JaxbCompoundClassLoader();

    for (ContentProviderFactoryType type : xmlProjectDescription.getContentProviderFactory()) {

      try {

        Class<?> clazz = jaxbCompoundClassLoader.getCompoundClassLoader().loadClass(
            type.getContentProviderFactoryClass());
        IProjectContentProvider instObject = (IProjectContentProvider) clazz.newInstance();
        instObject.setId(type.getContentProviderFactoryId());
        instObject.setConfiguration(type.getAny());
        result.addContentProvider(instObject, false);

      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @param file
   * @return
   * @throws IOException
   */
  public static byte[] read(File file) {

    byte[] buffer = new byte[(int) file.length()];
    InputStream ios = null;
    try {
      ios = new FileInputStream(file);
      if (ios.read(buffer) == -1) {
        throw new IOException("EOF reached while trying to read the whole file");
      }
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally {
      try {
        if (ios != null)
          ios.close();
      } catch (IOException e) {
      }
    }

    return buffer;
  }
}

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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.internal.projectdescription.BundleMakerProjectDescription;
import org.bundlemaker.core.internal.projectdescription.ResourceContent;
import org.bundlemaker.core.internal.projectdescription.RootPath;
import org.bundlemaker.core.model.internal.projectdescription.xml.XmlFileBasedContentType;
import org.bundlemaker.core.model.internal.projectdescription.xml.XmlProjectDescriptionType;
import org.bundlemaker.core.model.internal.projectdescription.xml.XmlResourceContentType;
import org.bundlemaker.core.projectdescription.AnalyzeMode;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectContentProvider;
import org.bundlemaker.core.projectdescription.IRootPath;
import org.bundlemaker.core.projectdescription.file.FileBasedContentProvider;
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
    for (IBundleMakerProjectContentProvider contentProvider : projectDescription.getContentProviders()) {

      // TODO
      if (contentProvider instanceof FileBasedContentProvider) {

        FileBasedContentProvider fileBasedContentProvider = (FileBasedContentProvider) contentProvider;

        XmlFileBasedContentType xmlFileBasedContent = new XmlFileBasedContentType();
        xmlProjectDescription.getFileBasedContent().add(xmlFileBasedContent);

        xmlFileBasedContent.setId(contentProvider.getId());
        xmlFileBasedContent.setName(fileBasedContentProvider.getFileBasedContent().getName());
        xmlFileBasedContent.setVersion(fileBasedContentProvider.getFileBasedContent().getVersion());
        xmlFileBasedContent.setAnalyzeMode(fileBasedContentProvider.getFileBasedContent().getAnalyzeMode().toString());
        for (IRootPath path : fileBasedContentProvider.getFileBasedContent().getBinaryRootPaths()) {
          xmlFileBasedContent.getBinaryPathNames().add(path.getUnresolvedPath().toString());
        }
        XmlResourceContentType xmlResourceContent = new XmlResourceContentType();
        xmlFileBasedContent.setResourceContent(xmlResourceContent);
        for (IRootPath path : fileBasedContentProvider.getFileBasedContent().getSourceRootPaths()) {
          xmlResourceContent.getSourcePathNames().add(path.getUnresolvedPath().toString());
        }
      }
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

    //
    InputStream inputStream = iFile.getContents();
    XmlProjectDescriptionType xmlProjectDescription = XmlProjectDescriptionExporterUtils.unmarshal(inputStream);
    try {
      inputStream.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    BundleMakerProjectDescription result = new BundleMakerProjectDescription((BundleMakerProject) project);
    result.setCurrentId(xmlProjectDescription.getCurrentId());
    result.setJre(xmlProjectDescription.getJre());

    for (XmlFileBasedContentType eFileBasedContent : xmlProjectDescription.getFileBasedContent()) {

      FileBasedContentProvider fileBasedContent = new FileBasedContentProvider();
      result.addContentProvider(fileBasedContent);

      fileBasedContent.getFileBasedContent().setId(eFileBasedContent.getId());
      fileBasedContent.setName(eFileBasedContent.getName());
      fileBasedContent.setVersion(eFileBasedContent.getVersion());
      fileBasedContent.setAnalyzeMode(AnalyzeMode.valueOf(eFileBasedContent.getAnalyzeMode()));

      for (String path : eFileBasedContent.getBinaryPathNames()) {
        fileBasedContent.getModifiableBinaryPaths().add(new RootPath(path, true));
      }

      ResourceContent resourceContent = fileBasedContent.getFileBasedContent().getModifiableResourceContent();

      for (String path : eFileBasedContent.getResourceContent().getSourcePathNames()) {
        resourceContent.getModifiableSourcePaths().add(new RootPath(path, false));
      }
    }

    return result;
  }
}

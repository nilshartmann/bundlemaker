package org.bundlemaker.core.internal;

import java.io.ByteArrayInputStream;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.internal.projectdescription.BundleMakerProjectDescription;
import org.bundlemaker.core.internal.projectdescription.FileBasedContent;
import org.bundlemaker.core.internal.projectdescription.ResourceContent;
import org.bundlemaker.core.model.internal.projectdescription.xml.XmlFileBasedContentType;
import org.bundlemaker.core.model.internal.projectdescription.xml.XmlProjectDescriptionType;
import org.bundlemaker.core.model.internal.projectdescription.xml.XmlResourceContentType;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

public class ProjectDescriptionStore {

	/**
	 * @param project
	 * @param projectDescription
	 * @throws CoreException
	 */
	public static void saveProjectDescription(IProject project,
			BundleMakerProjectDescription projectDescription)
			throws CoreException {

		Assert.isNotNull(project);
		Assert.isNotNull(projectDescription);

		// create the xml project description
		XmlProjectDescriptionType xmlProjectDescription = new XmlProjectDescriptionType();
		xmlProjectDescription.setCurrentId(projectDescription.getCurrentId());
		xmlProjectDescription.setJre(projectDescription.getJRE());

		// add the file based content
		for (FileBasedContent content : projectDescription
				.getModifiableFileBasedContent()) {

			XmlFileBasedContentType xmlFileBasedContent = new XmlFileBasedContentType();
			xmlProjectDescription.getFileBasedContent()
					.add(xmlFileBasedContent);

			xmlFileBasedContent.setId(content.getId());
			xmlFileBasedContent.setName(content.getName());
			xmlFileBasedContent.setVersion(content.getVersion());

			for (IPath path : content.getBinaryPaths()) {
				xmlFileBasedContent.getBinaryPathNames().add(path.toString());
			}

			if (content.isResourceContent()) {

				XmlResourceContentType xmlResourceContent = new XmlResourceContentType();
				xmlFileBasedContent.setResourceContent(xmlResourceContent);

				xmlResourceContent.setAnalyzeSourceResources(content
						.isAnalyzeSourceResources());

				for (IPath path : content.getSourcePaths()) {
					xmlResourceContent.getSourcePathNames()
							.add(path.toString());
				}
			}

		}

		//
		IFile iFile = project.getFile(new Path(
				BundleMakerCore.BUNDLEMAKER_DIRECTORY_NAME)
				.append(BundleMakerCore.PROJECT_DESCRIPTION_NAME));

		ByteArrayInputStream in = new ByteArrayInputStream(
				XmlProjectDescriptionExporterUtils.marshal(
						xmlProjectDescription).getBytes());

		if (!iFile.exists()) {
			iFile.create(in, true, null);
		} else {
			iFile.setContents(in, true, false, null);
		}
	}

	public static BundleMakerProjectDescription loadProjectDescription(
			IBundleMakerProject project) throws CoreException {

		//
		IFile iFile = project.getProject().getFile(
				new Path(BundleMakerCore.BUNDLEMAKER_DIRECTORY_NAME)
						.append(BundleMakerCore.PROJECT_DESCRIPTION_NAME));

		// refresh
		iFile.refreshLocal(IFile.DEPTH_INFINITE, null);

		//
		XmlProjectDescriptionType xmlProjectDescription = XmlProjectDescriptionExporterUtils
				.unmarshal(iFile.getContents());

		BundleMakerProjectDescription result = new BundleMakerProjectDescription(
				project);
		result.setCurrentId(xmlProjectDescription.getCurrentId());
		result.setJre(xmlProjectDescription.getJre());

		for (XmlFileBasedContentType eFileBasedContent : xmlProjectDescription
				.getFileBasedContent()) {

			FileBasedContent fileBasedContent = new FileBasedContent();
			result.getModifiableFileBasedContent().add(fileBasedContent);

			fileBasedContent.setId(eFileBasedContent.getId());
			fileBasedContent.setName(eFileBasedContent.getName());
			fileBasedContent.setVersion(eFileBasedContent.getVersion());

			for (String path : eFileBasedContent.getBinaryPathNames()) {
				fileBasedContent.getModifiableBinaryPaths().add(new Path(path));
			}

			if (eFileBasedContent.getResourceContent() != null) {

				ResourceContent resourceContent = new ResourceContent();
				fileBasedContent.setResourceContent(resourceContent);

				boolean analyse = eFileBasedContent.getResourceContent()
						.isAnalyzeSourceResources() != null
						&& eFileBasedContent.getResourceContent()
								.isAnalyzeSourceResources();

				resourceContent.setAnalyzeSourceResources(analyse);

				for (String path : eFileBasedContent.getResourceContent()
						.getSourcePathNames()) {

					resourceContent.getModifiableSourcePaths().add(
							new Path(path));
				}
			}
		}

		return result;
	}
}

package org.bundlemaker.core.parser.jdt;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.projectdescription.IFileBasedContent;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.JavaRuntime;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class JdtProjectHelper {

	/** the bundle maker JDT project postfix */
	public static final String BUNDLEMAKER_JDT_PROJECT_POSTFIX = "$bundlemaker";

	/**
	 * <p>
	 * </p>
	 * 
	 * @param bundleMakerProject
	 * @throws CoreException
	 */
	public static void setupAssociatedJavaProject(IBundleMakerProject project)
			throws CoreException {

		System.out.println("setupAssociatedJavaProject");

		IJavaProject javaProject = getAssociatedJavaProject(project);

		IResource[] children = javaProject.getProject().members();
		for (IResource iResource : children) {
			if (iResource.isLinked()) {
				iResource.delete(true, null);
			}
		}

		List<IClasspathEntry> entries = new LinkedList<IClasspathEntry>();

		IVMInstall vmInstall = JavaRuntime.getDefaultVMInstall();
		IPath path = JavaRuntime.newJREContainerPath(vmInstall);
		IClasspathEntry classpathEntry = JavaCore.newContainerEntry(path);
		entries.add(classpathEntry);

		for (IFileBasedContent projectContent : project.getProjectDescription()
				.getFileBasedContent()) {

			if (projectContent.isResourceContent()) {

				// add binary paths
				for (IPath iClasspathEntry : projectContent.getBinaryPaths()) {
					classpathEntry = JavaCore.newLibraryEntry(
							iClasspathEntry.makeAbsolute(), null, null);
					entries.add(classpathEntry);
				}

				// add source pathes
				if (!projectContent.getResourceContent().getSourcePaths()
						.isEmpty()) {

					// get the workspace root
					IWorkspaceRoot root = project.getProject().getWorkspace()
							.getRoot();

					for (IPath iSourcepathEntry : projectContent
							.getResourceContent().getSourcePaths()) {

						if (projectContent.getResourceContent()
								.isAnalyzeSourceResources()) {

							// Workspace relative...
							if (!iSourcepathEntry.isAbsolute()
									&& root.getFolder(iSourcepathEntry)
											.exists()) {
								IPath iPath = makeCanonical(iSourcepathEntry);
								IFolder linkFolder = javaProject.getProject()
										.getFolder(iPath);
								linkFolder.createLink(
										root.getFolder(iSourcepathEntry)
												.getRawLocation(), 0, null);
								classpathEntry = JavaCore
										.newSourceEntry(linkFolder
												.getFullPath());
								// projectContent.setSourcePath(linkFolder.getFullPath());
								entries.add(classpathEntry);
							}

							// try to resolve the source folder as an absolute
							// folder
							else {
								IPath iPath = makeCanonical(iSourcepathEntry);
								IFolder linkFolder = javaProject.getProject()
										.getFolder(iPath);
								linkFolder
										.createLink(iSourcepathEntry, 0, null);

								classpathEntry = JavaCore
										.newSourceEntry(linkFolder
												.getFullPath());

								// projectContent.setSourcePath(linkFolder.getFullPath());
								entries.add(classpathEntry);
							}
						}
					}
				}
			}
		}

		javaProject.setRawClasspath(entries.toArray(new IClasspathEntry[0]),
				null);
	}

	// public static void initializeJavaProject(
	// IBundleMakerProject bundleMakerProject) throws CoreException {
	//
	// // IJavaProject javaProject =
	// // getAssociatedJavaProject(bundleMakerProject);
	// //
	// // for (IProjectContent content : bundleMakerProject.getContent()) {
	// //
	// // IFileProjectContent projectContent = (IFileProjectContent) content;
	// //
	// // if (((IFileProjectContent) content).hasSourcePaths()) {
	// //
	// // // add source pathes
	// // IPath iPath = makeCanonical(projectContent.getRawSourcePath());
	// // IFolder linkFolder = javaProject.getProject().getFolder(iPath);
	// // projectContent.setSourcePath(linkFolder.getFullPath());
	// // }
	// // }
	// }

	public static boolean hasAssociatedJavaProject(
			IBundleMakerProject bundleMakerProject) {
		return hasAssociatedJavaProject(bundleMakerProject.getProject());
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param bundleMakerProject
	 * @return
	 */
	public static boolean hasAssociatedJavaProject(IProject bundleMakerProject) {

		// the associatedProjectName
		String associatedProjectName = getAssociatedJavaProjectName(bundleMakerProject);

		//
		if (!ResourcesPlugin.getWorkspace().getRoot()
				.exists(new Path(associatedProjectName))) {
			return false;
		} else {
			return true;
		}

		// TODO

		// IProject associatedJavaProject = ResourcesPlugin.getWorkspace()
		// .getRoot().getProject(associatedProjectName);
		//
		// try {
		//
		// associatedJavaProject.open(null);
		//
		// return associatedJavaProject.exists()
		// && associatedJavaProject.hasNature(JavaCore.NATURE_ID)
		// && associatedJavaProject.findMember(".classpath") != null
		// && associatedJavaProject.findMember(".classpath").exists();
		//
		// } catch (CoreException e) {
		// return false;
		// }
	}

	public static IJavaProject getAssociatedJavaProject(
			IBundleMakerProject bundleMakerProject) {

		return getAssociatedJavaProject(bundleMakerProject.getProject());
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param bundleMakerProject
	 * @return
	 */
	public static IJavaProject getAssociatedJavaProject(
			IProject bundleMakerProject) {

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject associatedProject = root
				.getProject(getAssociatedJavaProjectName(bundleMakerProject));

		IJavaProject javaProject = JavaCore.create(associatedProject);

		try {
			javaProject.open(null);
		} catch (JavaModelException e) {
			throw new RuntimeException(e.getMessage());
		}

		return javaProject;
	}

	public static IProject getAssociatedJavaProjectAsProject(
			IBundleMakerProject bundleMakerProject) {

		return getAssociatedJavaProjectAsProject(bundleMakerProject
				.getProject());
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param bundleMakerProject
	 * @return
	 */
	public static IProject getAssociatedJavaProjectAsProject(
			IProject bundleMakerProject) {

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject associatedProject = root
				.getProject(getAssociatedJavaProjectName(bundleMakerProject));

		return associatedProject;
	}

	public static IJavaProject newAssociatedJavaProject(
			IBundleMakerProject bundleMakerProject) throws CoreException {
		return newAssociatedJavaProject(bundleMakerProject.getProject());
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param bundleMakerProject
	 * @return
	 * @throws CoreException
	 */
	public static IJavaProject newAssociatedJavaProject(
			IProject bundleMakerProject) throws CoreException {

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject associatedProject = root
				.getProject(getAssociatedJavaProjectName(bundleMakerProject));

		if (associatedProject.exists()) {
			associatedProject.delete(true, null);
		}

		associatedProject.create(null);
		associatedProject.open(null);
		associatedProject.setHidden(false);

		IProjectDescription description = associatedProject.getDescription();
		description.setNatureIds(new String[] { JavaCore.NATURE_ID });
		associatedProject.setDescription(description, null);

		return JavaCore.create(associatedProject);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public static IPath makeCanonical(IPath path) {

		String[] segments = path.segments();
		StringBuilder builder = null;
		for (String segment : segments) {
			if (builder == null) {
				builder = new StringBuilder(segment);
			} else {
				builder.append("-" + segment);
			}
		}

		return new Path(builder.toString());
	}

	// private static IJavaProject createJavaProject(String javaProjectName)
	// throws CoreException {
	//
	// IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
	// IProject associatedProject = root.getProject(javaProjectName);
	// associatedProject.create(null);
	// associatedProject.open(null);
	// associatedProject.setHidden(true);
	//
	// IProjectDescription description = associatedProject.getDescription();
	// description.setNatureIds(new String[] { JavaCore.NATURE_ID });
	// associatedProject.setDescription(description, null);
	//
	// IJavaProject javaProject = JavaCore.create(associatedProject);
	//
	// IVMInstall vmInstall = JavaRuntime.getDefaultVMInstall();
	// IPath path = JavaRuntime.newJREContainerPath(vmInstall);
	// IClasspathEntry classpathEntry = JavaCore.newContainerEntry(path);
	// javaProject.setRawClasspath(new IClasspathEntry[] { classpathEntry },
	// null);
	//
	// return javaProject;
	// }

	/**
	 * <p>
	 * </p>
	 * 
	 * @param bundleMakerProject
	 * @return
	 */
	private static String getAssociatedJavaProjectName(IProject project) {

		return project.getName() + BUNDLEMAKER_JDT_PROJECT_POSTFIX;
	}

}

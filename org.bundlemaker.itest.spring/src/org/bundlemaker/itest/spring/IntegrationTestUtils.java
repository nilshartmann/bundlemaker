package org.bundlemaker.itest.spring;

import java.io.File;
import java.io.FileFilter;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Delete;
import org.apache.tools.ant.taskdefs.Mkdir;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.transformation.EmbedModuleTransformation;
import org.bundlemaker.core.transformation.resourceset.ResourceSetBasedTransformation;
import org.eclipse.core.runtime.CoreException;

public class IntegrationTestUtils {

	public static void addModularizeSpringTransformation(
			IModularizedSystem modularizedSystem) {
		// ****************************************************************************************
		// TODO: API
		ResourceSetBasedTransformation transformation = new ResourceSetBasedTransformation();
		modularizedSystem.getTransformations().add(transformation);

		// create from identifier
		IModuleIdentifier fromIdentifier = new ModuleIdentifier("Spring",
				"2.5.6");

		transformation.addModuleDefinition("Spring-Core", "2.5.6")
				.addResourceSet(
						fromIdentifier,
						new String[] { "org/springframework/core/**",
								"org/springframework/metadata/**",
								"org/springframework/util/**",
								"org/springframework/asm/**" }, null);

		transformation.addModuleDefinition("Spring-AOP", "2.5.6")
				.addResourceSet(fromIdentifier,
						new String[] { "org/springframework/aop/**" }, null);

		transformation.addModuleDefinition("Spring-Beans", "2.5.6")
				.addResourceSet(fromIdentifier,
						new String[] { "org/springframework/beans/**" }, null);

		transformation
				.addModuleDefinition("Spring-Context", "2.5.6")
				.addResourceSet(
						fromIdentifier,
						new String[] {
								"org/springframework/context/**",
								"org/springframework/instrument/classloading/**",
								"org/springframework/instrument/ejb/**",
								"org/springframework/jmx/**",
								"org/springframework/jndi/**",
								"org/springframework/remoting/*",
								"org/springframework/remoting/rmi/**",
								"org/springframework/remoting/soap/**",
								"org/springframework/remoting/support/**",
								"org/springframework/scheduling/**",
								"org/springframework/scripting/**",
								"org/springframework/stereotype/**",
								"org/springframework/ui/**",
								"org/springframework/validation/**" },
						new String[] { "org/springframework/scheduling/quartz/**" });

		transformation.addModuleDefinition("Spring-JDBC", "2.5.6")
				.addResourceSet(fromIdentifier,
						new String[] { "org/springframework/jdbc/**" }, null);

		transformation.addModuleDefinition("Spring-JMS", "2.5.6")
				.addResourceSet(fromIdentifier,
						new String[] { "org/springframework/jms/**" }, null);

		transformation.addModuleDefinition("Spring-TX", "2.5.6")
				.addResourceSet(
						fromIdentifier,
						new String[] { "org/springframework/dao/**",
								"org/springframework/jca/**",
								"org/springframework/transaction/**" }, null);
		// ****************************************************************************************
	}

	public static void addEmbedAntTransformation(
			IModularizedSystem modularizedSystem) {
		// ****************************************************************************************
		// TODO: API
		EmbedModuleTransformation embedModuleTransformation = new EmbedModuleTransformation();
		embedModuleTransformation.setHostModuleIdentifier(new ModuleIdentifier(
				"ant", "0.0.0"));
		embedModuleTransformation.getEmbeddedModulesIdentifiers().add(
				new ModuleIdentifier("ant-trax", "0.0.0"));
		embedModuleTransformation.getEmbeddedModulesIdentifiers().add(
				new ModuleIdentifier("ant-launcher", "0.0.0"));
		embedModuleTransformation.getEmbeddedModulesIdentifiers().add(
				new ModuleIdentifier("ant-junit", "0.0.0"));

		modularizedSystem.getTransformations().add(embedModuleTransformation);
		// ****************************************************************************************
	}

	/**
	 * <p>
	 * </p>
	 */
	public static IBundleMakerProjectDescription createProjectDescription(
			IBundleMakerProjectDescription projectDescription)
			throws CoreException {

		// step 1:
		projectDescription.clear();

		// step 2: add the JRE
		projectDescription.setJre("jdk16");

		// step 3: add the source and classes
		File classesZip = new File(System.getProperty("user.dir"),
				"spring/classes.zip");
		File sourceDirectory = new File(System.getProperty("user.dir"),
				"spring/source");
		projectDescription
				.addResourceContent("Spring", "2.5.6",
						classesZip.getAbsolutePath(),
						sourceDirectory.getAbsolutePath());

		// step 4: process the class path entries
		File libsDir = new File(System.getProperty("user.dir"), "spring/libs");
		File[] jarFiles = libsDir.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return !(pathname.getName().contains(".svn") || pathname
						.getName().contains(".SVN"));
			}
		});
		for (File externalJar : jarFiles) {
			projectDescription
					.addResourceContent(externalJar.getAbsolutePath());
		}

		// return the result
		return projectDescription;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param directory
	 */
	public static void delete(File directory) {

		Delete delete = new Delete();
		delete.setProject(new Project());
		delete.setDir(directory);
		delete.setQuiet(true);
		delete.execute();

		Mkdir mkdir = new Mkdir();
		mkdir.setProject(new Project());
		mkdir.setDir(directory);
		mkdir.execute();
	}
}

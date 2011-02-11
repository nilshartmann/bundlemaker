package org.bundlemaker.core.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bundlemaker.core.internal.modules.TypeModule;
import org.bundlemaker.core.internal.resource.Type;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.resource.TypeEnum;
import org.bundlemaker.core.util.FileUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.LibraryLocation;

public class JdkModuleCreator {

	/**
	 * @param bundleMakerProject
	 * @return
	 * @throws CoreException
	 */
	public static List<TypeModule> getJdkModules() throws CoreException {

		// the vmInstalls
		List<IVMInstall> vmInstalls = new ArrayList<IVMInstall>();

		vmInstalls.add(JavaRuntime.getDefaultVMInstall());

		// create the result set
		List<TypeModule> result = new ArrayList<TypeModule>();

		try {
			// create virtual modules for the vms
			for (IVMInstall vmInstall : vmInstalls) {
				TypeModule module = createModuleForVMInstall(vmInstall);
				result.add(module);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// return the result
		return result;
	}

	/**
	 * @param vmInstall
	 * @return
	 * @throws CoreException
	 * @throws IOException
	 */
	private static TypeModule createModuleForVMInstall(IVMInstall vmInstall)
			throws CoreException, IOException {

		TypeModule virtualModule = new TypeModule(new ModuleIdentifier(
				vmInstall.getName(), vmInstall.getName()));

		for (LibraryLocation libraryLocation : JavaRuntime
				.getLibraryLocations(vmInstall)) {

			List<String> children = FileUtils.getAllChildren(libraryLocation
					.getSystemLibraryPath().toFile());

			for (String child : children) {

				// TODO: Parsing!! ITYPE
				if (child.endsWith(".class")) {
					String typeName = child.substring(0, child.length()
							- ".class".length());

					// if (packageName.indexOf('.') != -1) {
					//
					// packageName = packageName.substring(0,
					// packageName.lastIndexOf('.'));

					typeName = typeName.replace('/', '.');
					typeName = typeName.replace('\\', '.');

					// TODO
					Type type = new Type(typeName, TypeEnum.CLASS);
					type.setTypeModule(virtualModule);

					virtualModule.getSelfContainer()
							.getModifiableContainedTypesMap()
							.put(typeName, type);
					// }
				}
			}

		}

		return virtualModule;
	}
}

package org.bundlemaker.core.parser.jdt;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.resource.IResourceKey;
import org.eclipse.jdt.core.dom.CompilationUnit;

/**
 * <p>
 * Defines the common interface for JDT source parser hooks.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IJdtSourceParserHook {

	/**
	 * <p>
	 * </p>
	 * 
	 * @param bundleMakerProject
	 */
	public void parseBundleMakerProjectStart(
			IBundleMakerProject bundleMakerProject);

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public void analyzeCompilationUnit(IResourceKey resourceKey,
			CompilationUnit compilationUnit);

	/**
	 * <p>
	 * </p>
	 * 
	 * @param bundleMakerProject
	 */
	public void parseBundleMakerProjectStop(
			IBundleMakerProject bundleMakerProject);
}

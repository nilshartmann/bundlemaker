package org.bundlemaker.core.parser.jdt;

import org.bundlemaker.core.IBundleMakerProject;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.CompilationUnit;

/**
 * <p>
 * Defines the common interface for AST visitor factories.
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
	public void analyzeCompilationUnit(ICompilationUnit iCompilationUnit,
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

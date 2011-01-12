package org.bundlemaker.core;

import org.bundlemaker.core.resource.IResourceKey;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IProblem {

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	IResourceKey getResource();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	boolean isError();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	int getSourceLineNumber();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	Object getMessage();

	int getSourceEnd();

	int getSourceStart();

}

package org.bundlemaker.core.modules;

import java.util.Set;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface ITypeContainer {

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	Set<String> getContainedTypeNames();

	/**
	 * <p>
	 * </p>
	 * 
	 * @param filter
	 * @return
	 */
	Set<String> getContainedTypeNames(IQueryFilter filter);

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	Set<String> getContainedPackageNames();

	/**
	 * <p>
	 * </p>
	 * 
	 * @param filter
	 * @return
	 */
	Set<String> getContainedPackageNames(IQueryFilter filter);
}

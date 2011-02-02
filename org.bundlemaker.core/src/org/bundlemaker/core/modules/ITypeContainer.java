package org.bundlemaker.core.modules;

import java.util.Collection;
import java.util.Set;

import org.bundlemaker.core.resource.IType;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface ITypeContainer {

	/**
	 * @param fullyQualifiedName
	 * @return
	 */
	IType getType(String fullyQualifiedName);

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	Collection<IType> getAllContainedTypes();

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

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
	 * <p>
	 * Returns the {@link IType} with the specified fully qualified name or
	 * <code>null</code> if no {@link IType} with the specified name exists.
	 * </p>
	 * 
	 * @param fullyQualifiedName
	 * @return the {@link IType} with the specified fully qualified name or
	 *         <code>null</code> if no {@link IType} with the specified name
	 *         exists.
	 */
	IType getType(String fullyQualifiedName);

	/**
	 * <p>
	 * Returns a collection with all contained {@link IType ITypes}.
	 * </p>
	 * 
	 * @return a collection with all contained {@link IType ITypes}.
	 */
	Collection<IType> getAllContainedTypes();

	/**
	 * <p>
	 * Returns a {@link Set} with the names of all contained types. 
	 * </p>
	 * 
	 * @return a {@link Set} with the names of all contained types.
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

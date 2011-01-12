package org.bundlemaker.core.modules;

import java.util.Set;


/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface ITypeContainer {

	Set<String> getContainedTypes();

	Set<String> getContainedTypes(IQueryFilter filter);

	Set<String> getContainedPackages();

	Set<String> getContainedPackages(IQueryFilter filter);
}

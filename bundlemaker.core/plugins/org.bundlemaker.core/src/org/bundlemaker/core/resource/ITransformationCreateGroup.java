package org.bundlemaker.core.resource;

import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface ITransformationCreateGroup extends ITransformation {

  IPath getParentGroupPath();

  IPath getGroupPath();
}

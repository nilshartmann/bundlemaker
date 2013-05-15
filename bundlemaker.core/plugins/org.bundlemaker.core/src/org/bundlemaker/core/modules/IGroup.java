package org.bundlemaker.core.modules;

import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IGroup {

  IGroup getParent();

  IPath getPath();

}

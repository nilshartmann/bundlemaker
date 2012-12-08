package org.bundlemaker.core.modules;

import org.eclipse.core.runtime.IPath;

public interface IGroup {

  IGroup getParent();

  IPath getPath();

}

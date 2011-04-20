/*******************************************************************************
 * Copyright (c) 2011 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.internal.analysis;

import org.bundlemaker.core.modules.IModule;

/**
 *
 */
public class ModulePackageKey {

	private IModule _resourceModule;

	private String _packageName;

	/**
	 * <p>
	 * </p>
	 * 
	 * @param resourceModule
	 * @param packageName
	 */
	public ModulePackageKey(IModule resourceModule, String packageName) {

		_resourceModule = resourceModule;
		_packageName = packageName;
	}

	public IModule getModule() {
		return _resourceModule;
	}

	public String getPackageName() {
		return _packageName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((_packageName == null) ? 0 : _packageName.hashCode());
		result = prime * result
				+ ((_resourceModule == null) ? 0 : _resourceModule.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModulePackageKey other = (ModulePackageKey) obj;
		if (_packageName == null) {
			if (other._packageName != null)
				return false;
		} else if (!_packageName.equals(other._packageName))
			return false;
		if (_resourceModule == null) {
			if (other._resourceModule != null)
				return false;
		} else if (!_resourceModule.equals(other._resourceModule))
			return false;
		return true;
	}
}

package org.bundlemaker.core;

import org.eclipse.core.runtime.IAdaptable;

public interface IGenericAdaptable extends IAdaptable {

  <T> T adaptAs(Class<T> clazz);
}

package org.bundlemaker.core.common;

import org.eclipse.core.runtime.IAdaptable;

public interface IGenericAdaptable extends IAdaptable {

  <T> T adaptAs(Class<T> clazz);
}

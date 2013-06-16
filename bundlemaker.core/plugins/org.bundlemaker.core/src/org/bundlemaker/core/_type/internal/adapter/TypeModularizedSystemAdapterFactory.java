package org.bundlemaker.core._type.internal.adapter;

import org.bundlemaker.core._type.ITypeModularizedSystem;
import org.bundlemaker.core._type.internal.TypeModularizedSystem;
import org.bundlemaker.core.resource.IModularizedSystem;
import org.eclipse.core.runtime.IAdapterFactory;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class TypeModularizedSystemAdapterFactory implements IAdapterFactory {

  /**
   * {@inheritDoc}
   */
  public Object getAdapter(Object adaptableObject, Class adapterType) {

    //
    if (adapterType == ITypeModularizedSystem.class) {

      IModularizedSystem modularizedSystem = (IModularizedSystem) adaptableObject;

      if (!modularizedSystem.getUserAttributes().containsKey(ITypeModularizedSystem.class.getName())) {
        modularizedSystem.getUserAttributes().put(ITypeModularizedSystem.class.getName(),
            new TypeModularizedSystem(modularizedSystem.getBundleMakerProject()));
      }

      return modularizedSystem.getUserAttributes().get(ITypeModularizedSystem.class.getName());
    }

    //
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public Class[] getAdapterList() {
    return new Class[] { ITypeModularizedSystem.class };
  }
}
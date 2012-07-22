package org.bundlemaker.core.transformation;

import org.bundlemaker.core.analysis.IGroupAndModuleContainer;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class CreateModuleTransformation implements ITransformation {

  private IGroupAndModuleContainer groupAndModuleContainer;

  private String                   qualifiedModuleName;

  private String                   moduleVersion;

  /**
   * <p>
   * Creates a new instance of type {@link CreateModuleTransformation}.
   * </p>
   * 
   * @param groupAndModuleContainer
   * @param qualifiedModuleName
   * @param moduleVersion
   */
  public CreateModuleTransformation(IGroupAndModuleContainer groupAndModuleContainer, String qualifiedModuleName,
      String moduleVersion) {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void apply(IModifiableModularizedSystem modularizedSystem, IProgressMonitor progressMonitor) {

  }
}

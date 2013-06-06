package org.bundlemaker.core.internal.transformation;

import org.bundlemaker.core.internal.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.resource.ITransformation;
import org.eclipse.core.runtime.IProgressMonitor;

public interface IInternalTransformation extends ITransformation {

  /**
   * <p>
   * </p>
   * 
   * @param modularizedSystem
   * @param monitor
   *          the progress monitor to use for reporting progress to the user. It is the caller's responsibility to call
   *          done() on the given monitor. Accepts <code>null</code>, indicating that no progress should be reported and
   *          that the operation cannot be canceled.
   */
  public void apply(IModifiableModularizedSystem modularizedSystem, IProgressMonitor progressMonitor);
}

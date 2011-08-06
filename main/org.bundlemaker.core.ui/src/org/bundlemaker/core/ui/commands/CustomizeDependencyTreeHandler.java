package org.bundlemaker.core.ui.commands;

import org.bundlemaker.analysis.ui.handlers.AbstractBundleMakerHandler;
import org.bundlemaker.core.ui.view.navigator.CommonNavigatorUtils;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.viewers.ISelection;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class CustomizeDependencyTreeHandler extends AbstractBundleMakerHandler {

  /**
   * {@inheritDoc}
   */
  @Override
  protected void execute(ExecutionEvent event, ISelection selection) throws Exception {

    // TODO: open dialog

    // update navigator
    CommonNavigatorUtils.update("org.eclipse.ui.navigator.ProjectExplorer");
  }
}

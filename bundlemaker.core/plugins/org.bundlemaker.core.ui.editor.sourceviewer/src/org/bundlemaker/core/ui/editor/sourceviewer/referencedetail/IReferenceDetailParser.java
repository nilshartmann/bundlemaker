package org.bundlemaker.core.ui.editor.sourceviewer.referencedetail;

import java.util.List;
import java.util.Map;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.resource.IModuleResource;
import org.eclipse.jface.text.Position;

/**
 */
public interface IReferenceDetailParser {

  /**
   * @return
   */
  Map<String, List<Position>> parseReferencePositions(IModuleResource resource, IModularizedSystem modularizedSystem);
}

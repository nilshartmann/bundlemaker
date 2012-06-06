package org.bundlemaker.core.ui.referencedetails;

import java.util.List;
import java.util.Map;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.resource.IResource;
import org.eclipse.jface.text.Position;

/**
 */
public interface IReferenceDetailParser {

  /**
   * @return
   */
  Map<String, List<Position>> parseReferencePositions(IResource resource, IModularizedSystem modularizedSystem);
}

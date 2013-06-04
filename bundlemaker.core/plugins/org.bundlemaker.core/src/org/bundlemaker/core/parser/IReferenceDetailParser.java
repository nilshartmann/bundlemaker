package org.bundlemaker.core.parser;

import java.util.List;
import java.util.Map;

import org.bundlemaker.core.resource.IModularizedSystem;
import org.bundlemaker.core.resource.IModuleResource;

/**
 */
public interface IReferenceDetailParser {

  /**
   * @return
   */
  Map<String, List<IPosition>> parseReferencePositions(IModuleResource resource, IModularizedSystem modularizedSystem);

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  public static interface IPosition {

    /** The offset of the position */
    int getOffset();

    /** The length of the position */
    int getLength();
  }
}

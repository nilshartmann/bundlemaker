package org.bundlemaker.core.resource;

import java.util.List;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IAddArtifactsTransformation extends ITransformation {

  List<String> getArtifactsAdded();

  String getTarget();

}

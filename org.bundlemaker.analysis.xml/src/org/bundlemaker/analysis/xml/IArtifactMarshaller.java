package org.bundlemaker.analysis.xml;

import java.io.OutputStream;

import org.bundlemaker.analysis.model.IArtifact;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IArtifactMarshaller {

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @param outputStream
   */
  void marshall(IArtifact artifact, OutputStream outputStream);
}

package org.bundlemaker.analysis.xml;

import java.io.OutputStream;

import org.bundlemaker.analysis.model.IArtifact;

/**
 * <p>
 * Defines an interface to marshal a given {@link IArtifact} tree.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IArtifactMarshaller {

  /**
   * <p>
   * Marshals the given {@link IArtifact} tree.
   * </p>
   * 
   * @param artifact
   *          the artifact to marshal
   * @param outputStream
   *          the outputstream
   */
  void marshal(IArtifact artifact, OutputStream outputStream) throws Exception;
}

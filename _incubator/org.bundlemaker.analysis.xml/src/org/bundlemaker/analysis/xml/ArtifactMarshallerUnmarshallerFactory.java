package org.bundlemaker.analysis.xml;

import org.bundlemaker.analysis.xml.internal.ArtifactMarshaller;

/**
 * <p>
 * Factory to create {@link IArtifactMarshaller} instances.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ArtifactMarshallerUnmarshallerFactory {

  /**
   * <p>
   * Creates a new {@link IArtifactMarshaller}.
   * </p>
   * 
   * @return
   */
  public static IArtifactMarshaller createMarshaller() {

    // return the new instance
    return new ArtifactMarshaller();
  }
}

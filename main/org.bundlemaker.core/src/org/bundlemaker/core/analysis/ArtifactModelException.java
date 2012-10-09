package org.bundlemaker.core.analysis;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ArtifactModelException extends RuntimeException {

  /**
   * <p>
   * Creates a new instance of type {@link ArtifactModelException}.
   * </p>
   */
  public ArtifactModelException() {
    super();
  }

  /**
   * <p>
   * Creates a new instance of type {@link ArtifactModelException}.
   * </p>
   * 
   * @param message
   * @param cause
   */
  public ArtifactModelException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * <p>
   * Creates a new instance of type {@link ArtifactModelException}.
   * </p>
   * 
   * @param message
   */
  public ArtifactModelException(String message) {
    super(message);
  }

  /**
   * <p>
   * Creates a new instance of type {@link ArtifactModelException}.
   * </p>
   * 
   * @param cause
   */
  public ArtifactModelException(Throwable cause) {
    super(cause);
  }
}

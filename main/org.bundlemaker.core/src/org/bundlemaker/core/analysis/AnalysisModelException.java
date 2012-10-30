package org.bundlemaker.core.analysis;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class AnalysisModelException extends RuntimeException {

  /** serialVersionUID */
  private static final long serialVersionUID = -7231799199554704676L;

  /**
   * <p>
   * Creates a new instance of type {@link AnalysisModelException}.
   * </p>
   */
  public AnalysisModelException() {
    super();
  }

  /**
   * <p>
   * Creates a new instance of type {@link AnalysisModelException}.
   * </p>
   * 
   * @param message
   * @param cause
   */
  public AnalysisModelException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * <p>
   * Creates a new instance of type {@link AnalysisModelException}.
   * </p>
   * 
   * @param message
   */
  public AnalysisModelException(String message) {
    super(message);
  }

  /**
   * <p>
   * Creates a new instance of type {@link AnalysisModelException}.
   * </p>
   * 
   * @param cause
   */
  public AnalysisModelException(Throwable cause) {
    super(cause);
  }
}

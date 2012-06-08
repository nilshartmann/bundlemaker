package org.bundlemaker.core.projectdescription;

/**
 * <p>
 * Represents a problem with a specific {@link IProjectContentEntry} instance.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IProjectContentProblem {

  /**
   * <p>
   * The type of a {@link IProjectContentProblem}.
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  public static enum IProjectContentProblemType {

    /** WARNING */
    WARNING,

    /** ERROR */
    ERROR;
  }

  /**
   * <p>
   * Returns the problem type.
   * </p>
   * 
   * @return the problem type.
   */
  IProjectContentProblemType getProblemType();

  /**
   * <p>
   * Returns the message of this problem.
   * </p>
   * 
   * @return the message of this problem.
   */
  String getMessage();
}

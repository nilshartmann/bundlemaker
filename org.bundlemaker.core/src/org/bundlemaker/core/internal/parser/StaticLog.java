package org.bundlemaker.core.internal.parser;

import org.bundlemaker.core.util.StopWatch;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class StaticLog {

  /**
   * <p>
   * </p>
   * 
   * @param message
   */
  public static void log(String message) {
    System.out.println(message);
  }

  /**
   * <p>
   * </p>
   * 
   * @param message
   * @param args
   */
  public static void log(String message, Object... args) {
    System.out.println(String.format(message, args));
  }

  public static <T> T log(boolean log, String message, LoggableAction<T> loggableAction) throws CoreException {

    //
    StopWatch stopWatch = null;

    //
    if (log) {
      StaticLog.log("Starting '%s'...", message);
      stopWatch = new StopWatch();
      stopWatch.start();
    }

    T result = loggableAction.execute();

    //
    if (log) {
      StaticLog.log("Finished '%s' [%s ms]", message, stopWatch.getElapsedTime());
    }

    return result;
  }
}

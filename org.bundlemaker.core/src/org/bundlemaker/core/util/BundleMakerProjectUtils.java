package org.bundlemaker.core.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.bundlemaker.core.IProblem;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BundleMakerProjectUtils {

  /** format of the compile problem message */
  private static final String COMPILE_PROBLEM_MESSAGE = "----------\n%s. %s in %s (at line %s)\n%s\n%s\n%s\n";

  /**
   * <p>
   * Parse the project and dumps the errors.
   * </p>
   * 
   * @param bundleMakerProject
   * @throws CoreException
   */
  public static void dumpProblems(List<? extends IProblem> errors) throws CoreException {

    int errorCount = 0;

    // dump errors
    if (!errors.isEmpty()) {

      for (int i = 0; i < errors.size(); i++) {
        IProblem problem = errors.get(i);

        if (problem.isError()) {

          Object[] arguments = new Object[7];
          arguments[0] = Integer.valueOf(errorCount++);
          arguments[1] = " ERROR";
          arguments[2] = problem.getResource().getPath();
          arguments[3] = Integer.valueOf(problem.getSourceLineNumber());
          String[] problematicLine;

          problematicLine = extractProblematicLine(problem);
          arguments[4] = problematicLine[0];
          arguments[5] = problematicLine[1];
          arguments[6] = problem.getMessage();

          System.out.println(String.format(COMPILE_PROBLEM_MESSAGE, arguments));
        }
      }
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param problem
   * @return
   */
  private static String[] extractProblematicLine(IProblem problem) {

    Assert.isNotNull(problem);

    int sourceStart = problem.getSourceStart();
    int sourceEnd = problem.getSourceEnd();

    try {

      return extractProblematicLine(new ByteArrayInputStream(problem.getResource().getContent()), sourceStart,
          sourceEnd);

    } catch (Exception e) {// Catch exception if any
      return new String[] { "", "" };
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param inputStream
   * @param startPos
   * @param endPos
   * @throws IOException
   */
  private static String[] extractProblematicLine(InputStream inputStream, int startPos, int endPos) throws IOException {

    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

    StringBuilder builder = new StringBuilder();

    int position = 0;
    int lineposition = 0;
    boolean done = false;
    boolean problematicLine = false;

    //
    while (!done) {

      //
      int i = reader.read();
      if (i == -1) {
        break;
      }
      char c = (char) i;

      //
      if (c != '\n' && c != '\r') {
        builder.append(c);
      } else {
        if (problematicLine) {
          done = true;
          break;
        } else {
          builder = new StringBuilder();
          lineposition = position + 1;
        }
      }

      //
      position++;

      if (position > startPos) {
        problematicLine = true;
      }
    }

    StringBuilder underscoreLine = new StringBuilder();
    for (int i = lineposition; i < startPos; i++) {
      if (builder.toString().charAt(i - (lineposition)) == '\t') {
        underscoreLine.append('\t');
      } else {
        underscoreLine.append(' ');
      }
    }
    for (int i = startPos; i <= endPos; i++) {
      underscoreLine.append('^');
    }

    // return the result
    return new String[] { builder.toString(), underscoreLine.toString() };
  }
}

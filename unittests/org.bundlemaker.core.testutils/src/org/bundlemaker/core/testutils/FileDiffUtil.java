package org.bundlemaker.core.testutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

import junit.framework.Assert;
import name.fraser.neil.diff_match_patch;
import name.fraser.neil.diff_match_patch.Diff;

import org.bundlemaker.core.testutils.BundleMakerTestUtils;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class FileDiffUtil {

  /**
   * <p>
   * </p>
   *
   * @param expected
   * @param actual
   * @param htmlResult
   */
  public static void assertArtifactModel(File expected, File actual, File htmlResult) {
    try {
      assertArtifactModel(new FileInputStream(expected), new FileInputStream(actual), htmlResult);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      Assert.fail(e.getMessage());
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param expected
   * @param actual
   * @param htmlResult
   */
  public static void assertArtifactModel(InputStream expected, InputStream actual, File htmlResult) {

    String expectedString = null;
    String actualString = null;

      expectedString = BundleMakerTestUtils.convertStreamToString(expected);
      expectedString = expectedString.replace("\r\n", "\n");
      actualString = BundleMakerTestUtils.convertStreamToString(actual);
      actualString = actualString.replace("\r\n", "\n");

    diff_match_patch patch = new diff_match_patch();

    LinkedList<Diff> diffs = patch.diff_main(expectedString, actualString);

    if (containsDiffs(diffs)) {

      String html = diff_prettyHtml(diffs);

      try {
        FileWriter fileWriter = new FileWriter(htmlResult);
        fileWriter.write(html);
        fileWriter.flush();
        fileWriter.close();
      } catch (IOException e) {
        e.printStackTrace();
      }

      //
      Assert.fail(String.format("Unexpected result. See '%s'.", htmlResult.getName()));
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param diffs
   * @return
   */
  private static boolean containsDiffs(LinkedList<Diff> diffs) {

    //
    for (Diff aDiff : diffs) {

      switch (aDiff.operation) {
      case INSERT:
        return true;
      case DELETE:
        return true;
      }
    }

    return false;
  }

  /**
   * Convert a Diff list into a pretty HTML report.
   * 
   * @param diffs
   *          LinkedList of Diff objects.
   * @return HTML representation.
   */
  private static String diff_prettyHtml(LinkedList<Diff> diffs) {

    StringBuilder html = new StringBuilder();

    for (Diff aDiff : diffs) {

      // prepare the text for HTML
      String text = aDiff.text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>\n");

      //
      switch (aDiff.operation) {
      case INSERT:
        html.append("<span style=\"background:#e6ffe6;\">").append(text).append("</span>");
        break;
      case DELETE:
        html.append("<span style=\"background:#ffe6e6;\">").append(text).append("</span>");
        break;
      case EQUAL:
        html.append(text);
        break;
      }
    }

    StringBuilder builder = new StringBuilder();

    boolean inChange = false;

    String[] splitted = html.toString().split("<br>\n");
    for (String split : splitted) {

      if (isOpener(split)) {
        inChange = true;
      }

      if (inChange && containsMarkedText(split)) {
        builder.append(split);
        builder.append("<br>\n");
      }

      if (isCloser(split)) {
        inChange = false;
      }
    }

    return builder.toString();
  }

  private static boolean containsMarkedText(String split) {

    int indexOpener = split.lastIndexOf("<span style=\"background:");
    int indexCloser = split.lastIndexOf("</span>");

    if (indexCloser == 0 && indexOpener == -1) {
      return false;
    }

    return true;
  }

  /**
   * <p>
   * </p>
   * 
   * @param split
   * @return
   */
  private static boolean isOpener(String split) {

    int indexOpener = split.lastIndexOf("<span style=\"background:");
    int indexCloser = split.lastIndexOf("</span>");

    if (indexOpener == -1) {
      return false;
    }

    if (indexCloser == -1) {
      return true;
    }

    if (indexOpener > indexCloser) {
      return true;
    }

    return false;
  }

  private static boolean isCloser(String split) {

    int indexOpener = split.lastIndexOf("<span style=\"background:");
    int indexCloser = split.lastIndexOf("</span>");

    if (indexCloser == -1) {
      return false;
    }

    if (indexOpener == -1) {
      return true;
    }

    if (indexCloser > indexOpener) {
      return true;
    }

    return false;
  }
}

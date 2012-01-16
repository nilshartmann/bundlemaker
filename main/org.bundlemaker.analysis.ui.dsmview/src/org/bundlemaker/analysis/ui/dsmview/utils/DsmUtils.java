package org.bundlemaker.analysis.ui.dsmview.utils;

public class DsmUtils {

  public static String getLongestString(String[][] values) {

    // create the result
    String result = "";

    // iterate over all strings
    for (String[] value : values) {
      for (String string : value) {

        if (string != null) {

          if (result == null) {
            result = string;
          } else if (result.length() < string.length()) {
            result = string;
          }
        }
      }
    }

    // return the result
    return result;
  }

  /**
   * <p>
   * Helper method that returns the longest string from the string array.
   * </p>
   * 
   * @param strings
   *          the string array
   * @return the longest string from the string array.
   */
  public static String getLongestString(String[] strings) {

    // create the result
    String result = null;

    // iterate over all strings
    for (String string : strings) {
      if (result == null) {
        result = string;
      } else if (result.length() < string.length()) {
        result = string;
      }
    }

    // return the result
    return (result == null ? "" : result);
  }
}

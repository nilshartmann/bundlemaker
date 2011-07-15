package org.bundlemaker.core.parser.jdt.internal.ecj;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class NameEnvironmentUtils {

  /**
   * <p>
   * </p>
   * 
   * @param compoundTypeName
   * @return
   */
  public static String getAsString(char[][] compoundTypeName, char delimiter) {

    //
    StringBuilder builder = new StringBuilder();

    //
    for (int i = 0; i < compoundTypeName.length; i++) {

      //
      char[] cs = compoundTypeName[i];

      //
      builder.append(new String(cs));

      //
      if (i + 1 < compoundTypeName.length) {
        builder.append(delimiter);
      }
    }

    return builder.toString();
  }

  public static String getAsString(char[] typeName, char[][] packageName, char delimiter) {

    //
    StringBuilder builder = new StringBuilder();

    //
    for (int i = 0; i < packageName.length; i++) {

      //
      char[] cs = packageName[i];

      //
      builder.append(new String(cs));

      //
      builder.append(delimiter);
    }

    //
    builder.append(new String(typeName));

    //
    return builder.toString();
  }
}

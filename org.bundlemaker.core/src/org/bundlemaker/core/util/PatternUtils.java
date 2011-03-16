package org.bundlemaker.core.util;

public class PatternUtils {

  /**
   * <p>
   * </p>
   * 
   * @param pattern
   * @return
   */
  public static String convertAntStylePattern(String pattern) {

    //
    String[] parts = pattern.split("\\.");

    //
    StringBuilder stringBuilder = new StringBuilder();

    //
    for (int i = 0; i < parts.length; i++) {
      String part = parts[i];
      part = part.replace("**", ".#REPEAT#");
      part = part.replace("*", "[^\\.]#REPEAT#");
      part = part.replace("#REPEAT#", "*");

      stringBuilder.append(part);
      if (i + 1 < parts.length) {
        stringBuilder.append("\\.");
      }
    }

    //
    return stringBuilder.toString();
  }

  /**
   * <p>
   * </p>
   * 
   * @param value
   * @return
   */
  private static boolean isJavaIdentifier(String value) {

    boolean start = true;
    boolean validIdentifier = true;

    for (byte b : value.getBytes()) {
      if (start) {
        validIdentifier = validIdentifier && Character.isJavaIdentifierStart(b);
        start = false;
      } else {
        validIdentifier = validIdentifier && Character.isJavaIdentifierPart(b);
      }
    }
    return validIdentifier;
  }
  
  public static void main(String[] args) {
    String pattern = convertAntStylePattern("de.ads.*.asd.**");
    System.out.println(pattern);
    System.out.println("de.ads.asasd.asd.Gerd.pferd".matches(pattern));
  }
}

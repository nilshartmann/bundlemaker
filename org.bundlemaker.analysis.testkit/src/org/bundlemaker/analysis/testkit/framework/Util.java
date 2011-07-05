package org.bundlemaker.analysis.testkit.framework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependency;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class Util {

  /**
   * <p>
   * </p>
   * 
   * @param module
   * @return
   */
  public static String toString(IArtifact root) {

    StringBuilder builder = new StringBuilder();
    toString(root, builder, 0);
    return builder.toString();
  }

  /**
   * <p>
   * </p>
   * 
   * @param root
   * @param stringBuilder
   */
  private static void toString(IArtifact artifact, StringBuilder builder, int offset) {

    //
    for (int i = 0; i < offset; i++) {
      builder.append(" ");
    }

    //
    builder.append(artifact.getQualifiedName());
    builder.append("\n");

    //
    for (IArtifact child : artifact.getChildren()) {
      toString(child, builder, offset + 1);
    }
    builder.append("\n");

    for (IDependency dependency : artifact.getDependencies()) {
      builder.append(dumpDependency(dependency));
      builder.append("\n");
    }
  }

  /**
   * <p>
   * </p>
   */
  public static String dumpDependency(IDependency iDependency) {
    return iDependency.getFrom().getQualifiedName() + " -- " + iDependency.getDependencyKind() + " ["
        + iDependency.getWeight() + "]" + " --> " + iDependency.getTo().getQualifiedName();
  }

  /**
   * <p>
   * </p>
   * 
   * @param <T>
   * @param set
   * @return
   */
  public static <T extends Comparable<T>> List<T> asSortedList(Set<T> set) {

    //
    List<T> arrayList = new ArrayList<T>(set);

    //
    Collections.sort(arrayList);

    //
    return arrayList;
  }

  /**
   * <p>
   * </p>
   * 
   * @param is
   * @return
   * @throws IOException
   */
  public static String convertStreamToString(InputStream is) {

    try {
      if (is != null) {
        Writer writer = new StringWriter();

        char[] buffer = new char[1024];
        try {
          Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
          int n;
          while ((n = reader.read(buffer)) != -1) {
            writer.write(buffer, 0, n);
          }
        } finally {
          is.close();
        }
        return writer.toString();
      } else {
        return "";
      }
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  public static String toString(RingBuffer<Character> buffer) {

    StringBuilder builder = new StringBuilder();
    while (!buffer.isEmpty()) {
      builder.append(buffer.dequeue());
    }
    return builder.toString();
  }

  /**
   * <p>
   * </p>
   * 
   * @param string1
   * @param string2
   * @return
   */
  public static boolean equalsIgnoreWhitespace(String expected, String actual) {

    expected = expected.replaceAll("\r\n", "\n");
    actual = actual.replaceAll("\r\n", "\n");
    char[] arr1 = expected.toCharArray();
    char[] arr2 = actual.toCharArray();

    RingBuffer<Character> buffer1 = new RingBuffer<Character>(100);
    RingBuffer<Character> buffer2 = new RingBuffer<Character>(100);

    for (int i = 0; i < arr1.length; i++) {
      char c1 = arr1[i];
      char c2 = arr2[i];

      // System.out.println("'" + c1 + "' : '" + c2 + "'");

      buffer1.enqueue(c1);
      buffer2.enqueue(c2);

      if (c1 != c2) {
        System.out.println("Expected: '" + Util.toString(buffer1) + "'");
        System.out.println("Actual  : '" + Util.toString(buffer2) + "'");
        return false;
      }
    }

    return true;
  }
}

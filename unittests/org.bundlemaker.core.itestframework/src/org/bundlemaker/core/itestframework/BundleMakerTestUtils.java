package org.bundlemaker.core.itestframework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.junit.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BundleMakerTestUtils {

  /**
   * <p>
   * </p>
   * 
   * @param content
   * @param fileName
   */
  public static File writeToFile(String content, String fileName) {
    File result = new File(System.getProperty("user.dir"), fileName);
    writeToFile(content, result);
    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @param content
   * @param fileName
   * @return
   */
  public static void writeToFile(String content, File file) {

    try {

      //
      file.getParentFile().mkdirs();

      FileWriter fileWriter = new FileWriter(file);
      fileWriter.write(content);

      fileWriter.flush();
      fileWriter.close();

    } catch (IOException e) {
      e.printStackTrace();
      Assert.fail(e.getMessage());
    }
  }

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
   * @param <T>
   * @param collection
   * @return
   */
  public static <T> List<T> asSortedList(Collection<T> collection, Comparator<T> comparator) {

    //
    List<T> arrayList = new ArrayList<T>(collection);

    //
    Collections.sort(arrayList, comparator);

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
}

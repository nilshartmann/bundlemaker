package org.bundlemaker.core.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListUtils {

  /**
   * <p>
   * </p>
   * 
   * @param list
   * @param file
   */
  public static void outputListToFile(List<String> list, File file) {
    try {
      PrintWriter printWriter = new PrintWriter(new FileWriter(file));
      for (Object o : list) {
        printWriter.println(o);
      }
      printWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param file
   * @return
   * @throws FileNotFoundException
   */
  public static Set<String> getFrom(File file) throws FileNotFoundException {

    Set<String> result = new HashSet<String>();

    try {
      // Open the file that is the first
      // command line parameter
      FileInputStream fstream = new FileInputStream(file);

      // Get the object of DataInputStream
      DataInputStream in = new DataInputStream(fstream);
      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      String strLine;

      // Read File Line By Line
      while ((strLine = br.readLine()) != null) {
        // Print the content on the console
        result.add(strLine);
      }

      // Close the input stream
      in.close();
    } catch (Exception e) {// Catch exception if any
      System.err.println("Error: " + e.getMessage());
    }

    //
    return result;
  }
}

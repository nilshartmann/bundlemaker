package org.bundlemaker.core.util;

public class MemoryUtils {

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public static String getMemoryUsage() {
    long totalMem = Runtime.getRuntime().totalMemory();
    long freeMem = Runtime.getRuntime().freeMemory();
    return "Memory used: " + (totalMem - freeMem) / (1024 * 1024) + " MB (total: " + totalMem / (1024 * 1024) + " MB )";
  }
}

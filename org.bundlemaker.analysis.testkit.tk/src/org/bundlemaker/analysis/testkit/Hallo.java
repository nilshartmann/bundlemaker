package org.bundlemaker.analysis.testkit;

import org.bundlemaker.analysis.testkit.framework.AbstractTestKitTest;
import org.junit.Test;

public class Hallo extends AbstractTestKitTest {

  @Test
  public void test() {
    for (int i = 0; i < 100; i++) {
      System.out.println("Hallo");
    }
  }
}

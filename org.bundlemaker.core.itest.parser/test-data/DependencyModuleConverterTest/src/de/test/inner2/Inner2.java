package de.test.inner2;

import de.test.inner.Inner1;

public class Inner2 {

  public static void main(String[] args) {
    Inner1 inner1 = new Inner1();
    Inner1.Inner1_Inside insideInner = new Inner1.Inner1_Inside();
  }
}

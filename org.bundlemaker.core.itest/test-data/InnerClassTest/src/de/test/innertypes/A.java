package de.test.innertypes;

import java.io.Serializable;

import de.test.referenced.X;
import de.test.referenced.X2;
import de.test.referenced.X3;
import de.test.referenced.Y;
import de.test.referenced.Y2;
import de.test.referenced.Y3;

public class A {

  //
  class AA {
    @Override
    public String toString() {
      return "AA [toString()=" + new X3().toString() + "]";
    }
  }

  //
  public A() {

    //
    X x = new X();

    //
    Serializable serializable = new Serializable() {
      @Override
      public String toString() {
        return new X2().toString();
      }
    };
  }
}

//
class B {

  //
  class BB {
    @Override
    public String toString() {
      return "BB [toString()=" + new Y3().toString() + "]";
    }
  }

  public B() {
    
    //
    Y y = new Y();

    //
    Serializable serializable = new Serializable() {
      @Override
      public String toString() {

        return new Y2().toString();
      }
    };
  }
}

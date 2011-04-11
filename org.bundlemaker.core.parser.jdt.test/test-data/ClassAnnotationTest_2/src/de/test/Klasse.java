package de.test;

import java.io.IOException;

import javax.activation.CommandObject;
import javax.activation.DataHandler;

@Test
public abstract class Klasse implements TestInterface<Klasse>, CommandObject {

  @Override
  public void setCommandContext(String arg0, DataHandler arg1) throws IOException {
  }

}

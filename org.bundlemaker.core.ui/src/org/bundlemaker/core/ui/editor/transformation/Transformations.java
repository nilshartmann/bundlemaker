package org.bundlemaker.core.ui.editor.transformation;

import java.util.LinkedList;
import java.util.List;

public class Transformations {

  private List<NewModule> _newModules = new LinkedList<NewModule>();

  public NewModule addNewModule(String name, String version) {
    NewModule newModule = new NewModule(name, version);
    _newModules.add(newModule);
    return newModule;
  }

  @Override
  public String toString() {
    return "Transformations [_newModules=" + _newModules + "]";
  }

  public List<NewModule> getNewModules() {
    return _newModules;
  }

  // addNewModule("m", "1").from("om", "o1").withResources("*.*")

}

package org.bundlemaker.core.ui.editor.transformation;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Evaluator {
  public Transformations evaluate(String javascript) throws Exception {
    ScriptEngineManager factory = new ScriptEngineManager();
    // create a JavaScript engine
    ScriptEngine engine = factory.getEngineByName("JavaScript");
    final Transformations transformations = new Transformations();
    engine.put("transformations", transformations);

    // evaluate JavaScript code from String
    engine.eval(javascript);

    return transformations;

  }
}

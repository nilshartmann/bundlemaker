package org.bundlemaker.core.projectdescription.file;

import static org.junit.Assert.assertEquals;

import org.bundlemaker.core.internal.projectdescription.gson.GsonProjectDescriptionHelper;
import org.bundlemaker.core.projectdescription.VariablePath;
import org.eclipse.core.runtime.Path;
import org.junit.Test;

public class IPathDeserializerTest {
  
  @Test
  public void testVariableWithColon() {
    VariablePath variablePath = new VariablePath("${project_log:Abc}/some/path");
    
    String json = GsonProjectDescriptionHelper.gson().toJson(variablePath);
    
    System.out.println("json: " + json);
    
    VariablePath x = GsonProjectDescriptionHelper.gson().fromJson(json, VariablePath.class);
    System.out.println("x: " + x);
    assertEquals(variablePath.getUnresolvedPath(), x.getUnresolvedPath());
  }

}

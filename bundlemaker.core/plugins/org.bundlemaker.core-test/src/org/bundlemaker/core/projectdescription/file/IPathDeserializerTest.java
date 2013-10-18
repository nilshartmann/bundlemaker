package org.bundlemaker.core.projectdescription.file;

import static org.junit.Assert.assertEquals;

import org.bundlemaker.core.project.VariablePath;
import org.bundlemaker.core.project.internal.gson.GsonProjectDescriptionHelper;
import org.junit.Test;

public class IPathDeserializerTest {
  
  @Test
  public void testVariableWithColon() {
    VariablePath variablePath = new VariablePath("${project_log:Abc}/some/path");
    String json = GsonProjectDescriptionHelper.gson().toJson(variablePath);
    VariablePath x = GsonProjectDescriptionHelper.gson().fromJson(json, VariablePath.class);
    assertEquals(variablePath.getUnresolvedPath(), x.getUnresolvedPath());
  }
}

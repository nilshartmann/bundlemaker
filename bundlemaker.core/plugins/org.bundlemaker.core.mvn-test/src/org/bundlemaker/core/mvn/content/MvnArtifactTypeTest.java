package org.bundlemaker.core.mvn.content;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.google.gson.Gson;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MvnArtifactTypeTest {

  @Test
  public void test() {

    Gson gson = new Gson();

    MvnArtifactType artifactType = new MvnArtifactType();
    artifactType.setGroupId("groupId");
    artifactType.setArtifactId("artifactId");
    artifactType.setVersion("version");

    String jsonRepresentation = gson.toJson(artifactType);

    assertThat(jsonRepresentation,
        is("{\"groupId\":\"groupId\",\"artifactId\":\"artifactId\",\"version\":\"version\"}"));

    assertThat(gson.fromJson(jsonRepresentation, MvnArtifactType.class), is(artifactType));
  }
}

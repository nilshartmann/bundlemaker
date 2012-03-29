package org.bundlemaker.core.itest.complex.analysis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.junit.Test;

/**
 * <p>
 * Example: group1/group2/jedit_1.0.0 velocity_1.5 jdk16_jdk16 << Missing Types >>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class GroupAndModuleContainerTest extends AbstractJeditArtifactTest {

  @Test
  public void testAddModuleWithoutRemove() {

    //
    IRootArtifact rootArtifact = (IRootArtifact) getRootArtifact();

    //
    assertNull("Group 'groupTest1' does not exist!", rootArtifact.getChild("groupTest1"));
    IModuleArtifact moduleArtifact = rootArtifact.getOrCreateModule("groupTest1/groupTest2/MyModule", "1.0.0");
    assertNotNull(moduleArtifact);
    assertNotNull("Group 'groupTest1' does not exist!", rootArtifact.getChild("groupTest1"));
    assertNotNull("Group 'groupTest1|groupTest2' does not exist!", rootArtifact.getChild("groupTest1|groupTest2"));
    assertNotNull("Group 'groupTest1|groupTest2|MyModule_1.0.0' does not exist!", rootArtifact.getChild("groupTest1|groupTest2|MyModule_1.0.0"));
    
    assertNotNull(moduleArtifact.getParent());
    assertEquals("groupTest2", moduleArtifact.getParent().getName());
    assertEquals("groupTest1/groupTest2/MyModule_1.0.0", moduleArtifact.getQualifiedName());
    
    IModuleArtifact module2Artifact = rootArtifact.getOrCreateModule("groupTest1/groupTest2/MyModule", "1.0.0");
    assertSame(module2Artifact, moduleArtifact);
    
    IGroupArtifact groupTest2Artifact = (IGroupArtifact) moduleArtifact.getParent();
    module2Artifact = groupTest2Artifact.getOrCreateModule("/groupTest1/groupTest2/MyModule", "1.0.0");
    assertSame(module2Artifact, moduleArtifact);
  }
}

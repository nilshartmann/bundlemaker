package org.bundlemaker.core.itest.simple_artifact_model.analysis;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AddArtifactsToPackage_Test.class, ArtifactModelModifiedListenerTest.class, ArtifactNamingTest.class,
    ArtifactSelector_addModuleSelectorTest.class, DependenciesFromToTest.class, GetChildByPathTest.class,
    GroupAddTest.class, GroupCreateNewTest.class, GroupRemoveTest.class, GroupRenameTest.class,
    InitializeCachesTest.class, MementoTest.class, ModuleChangeClassificationTest.class, ModuleCreateNewTest.class,
    ModuleRemoveTest.class, ModuleRenameTest.class, TransformationHistoryTest.class,
    TypeModuleChangeClassificationTest.class, Undo_AddArtifactsToGroup_Test.class,
    Undo_AddArtifactsToModule_Test.class, Undo_AddArtifactsToModuleMultipleTimes_Test.class,
    Undo_AddArtifactsToPackage_Test.class, Undo_GroupCreateNew_Test.class, Undo_GroupGetExisting_Test.class,
    Undo_ModuleCreateNew_Test.class, Undo_ModuleGetExisting_Test.class, Undo_ModuleRename_Test.class })
public class AllTests {

}

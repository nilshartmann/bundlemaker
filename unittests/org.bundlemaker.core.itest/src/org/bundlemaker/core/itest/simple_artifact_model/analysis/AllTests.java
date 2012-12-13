package org.bundlemaker.core.itest.simple_artifact_model.analysis;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ArtifactModelModifiedListenerTest.class, ArtifactSelector_addModuleSelectorTest.class,
    DependenciesFromToTest.class, GetChildByPathTest.class, GroupAddTest.class, GroupCreateNewTest.class,
    GroupRemoveTest.class, GroupRenameTest.class, InitializeCachesTest.class, MementoTest.class,
    ModuleChangeClassificationTest.class, ModuleCreateNewTest.class, ModuleRemoveTest.class, ModuleRenameTest.class,
    TransformationHistoryTest.class, TypeModuleChangeClassificationTest.class, Undo_AddArtifacts_Test.class,
    Undo_GroupCreateNew_Test.class, Undo_ModuleCreateNew_Test.class, Undo_ModuleRename_Test.class })
public class AllTests {

}

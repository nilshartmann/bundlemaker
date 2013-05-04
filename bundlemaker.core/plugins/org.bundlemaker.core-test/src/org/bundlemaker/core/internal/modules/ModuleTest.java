package org.bundlemaker.core.internal.modules;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.bundlemaker.core.framework.ResourceFactory;
import org.bundlemaker.core.internal.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.ITypeModule;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.projectdescription.ProjectContentType;
import org.eclipse.core.runtime.CoreException;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModuleTest {

  @Test
  public void test() throws CoreException {

    //
    IModifiableModularizedSystem modularizedSystem = mock(IModifiableModularizedSystem.class);

    //
    Module module = new Module(new ModuleIdentifier("Test", "1.2.3"), modularizedSystem);
    module.addAll(ResourceFactory.getResources(), ProjectContentType.BINARY);

    //
    assertThat(module.adaptAs(ITypeModule.class).getContainedTypes().size(), is(7));
    assertThat(module.adaptAs(ITypeModule.class).getContainedTypeNames().size(), is(7));

    assertTrue(module.adaptAs(ITypeModule.class).containsType("com.ClassInCom"));
    assertTrue(module.adaptAs(ITypeModule.class).containsType("com.example.Class2$StaticInnerClass2"));
    assertTrue(module.adaptAs(ITypeModule.class).containsType("com.example.Class1"));
    assertTrue(module.adaptAs(ITypeModule.class).containsType("com.example.Class2"));
    assertTrue(module.adaptAs(ITypeModule.class).containsType("ClassInDefaultPackage"));
    assertTrue(module.adaptAs(ITypeModule.class).containsType("com.example.Interface1"));
    assertTrue(module.adaptAs(ITypeModule.class).containsType("com.example.Class2$InnerClass2"));
  }
}

package org.bundlemaker.core.parser;

import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.itestframework.AbstractBundleMakerProjectTest;
import org.bundlemaker.core.itestframework.ExpectedReference;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.util.ProgressMonitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.launching.JavaRuntime;
import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractParserTest extends AbstractBundleMakerProjectTest {

  /**
   * <p>
   * </p>
   */
  @Test
  public void testParser() throws CoreException {

    addProjectDescription();

    // initialize the project
    log("Initializing project...");
    getBundleMakerProject().initialize(null);

    // parse the project
    log("Parsing project...");
    getBundleMakerProject().parseAndOpen(new ProgressMonitor());

    //
    IModularizedSystem modularizedSystem = getBundleMakerProject().getModularizedSystemWorkingCopy(getTestProjectName());

    // apply the transformation
    modularizedSystem.applyTransformations(null);

    // get the resource module
    IResourceModule resourceModule = modularizedSystem.getResourceModule(getTestProjectName(), TEST_PROJECT_VERSION);

    //
    testResult(modularizedSystem, resourceModule);
  }

  /**
   * <p>
   * </p>
   */
  protected void log(String message) {
    System.out.println(String.format("[%s] %s", this.getClass().getName(), message));
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected static String getDefaultVmName() {
    return JavaRuntime.getDefaultVMInstall().getName();
  }

  /**
   * <p>
   * </p>
   * 
   * @param path
   */
  protected void assertAllReferences(IType type, ExpectedReference... expectedReferences) {

    //
    List<ExpectedReference> expectedReferencesList = new LinkedList<ExpectedReference>(
        Arrays.asList(expectedReferences));

    //
    if (expectedReferences.length != type.getReferences().size()) {
      dumpReferences(type, expectedReferencesList, expectedReferences);
    }

    //
    for (IReference actualReference : type.getReferences()) {

      for (Iterator<ExpectedReference> iterator = expectedReferencesList.iterator(); iterator.hasNext();) {
        ExpectedReference expectedReference = (ExpectedReference) iterator.next();
        if (expectedReference.matches(actualReference)) {
          iterator.remove();
        }
      }
    }

    //
    if (!expectedReferencesList.isEmpty()) {
      dumpReferences(type, expectedReferencesList, expectedReferences);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param type
   * @param missingReferencesList
   * @param expectedReferences
   */
  private void dumpReferences(IType type, List<ExpectedReference> missingReferencesList,
      ExpectedReference... expectedReferences) {

    System.out.println("Actual references:");

    for (IReference actualReference : type.getReferences()) {
      System.out.println(" - " + actualReference);
    }

    System.out.println("Expected references:");
    for (ExpectedReference expectedReference : expectedReferences) {
      System.out.println(" - " + expectedReference);
    }

    System.out.println("Missing references:");
    for (ExpectedReference missingReference : missingReferencesList) {
      System.out.println(" - " + missingReference);
    }

    Assert.fail();
  }

  /**
   * <p>
   * </p>
   * 
   * @param resourceModule
   * @param path
   * @param contentType
   * @param expectedReferences
   * @throws CoreException
   */
  protected void assertResourceWithSingleTypeAndAllReferences(IResourceModule resourceModule, String path,
      ContentType contentType, ExpectedReference... expectedReferences) throws CoreException {

    //
    IResource resource = resourceModule.getResource(path, contentType);

    assertNotNull(resource);

    IType type = resource.getContainedType();
    assertNotNull(String.format("Resource '%s' does not contain a type!", resource.getPath()), type);

    assertAllReferences(type, expectedReferences);
  }

  /**
   * <p>
   * </p>
   * 
   * @param modularizedSystem
   * @throws CoreException
   */
  protected abstract void testResult(IModularizedSystem modularizedSystem, IResourceModule resourceModule)
      throws CoreException;
}

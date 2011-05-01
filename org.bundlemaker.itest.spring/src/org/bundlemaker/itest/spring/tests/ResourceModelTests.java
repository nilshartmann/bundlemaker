package org.bundlemaker.itest.spring.tests;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.projectdescription.IFileBasedContent;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;

public class ResourceModelTests {

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerProject
   */
  public static void checkResourceModel(IBundleMakerProject bundleMakerProject) {

    // check each file content entry
    for (IFileBasedContent fileBasedContent : bundleMakerProject.getProjectDescription().getFileBasedContent()) {

      //
      Assert.assertTrue(fileBasedContent.isResourceContent());

      //
      Map<String, IType> typeMap = new HashMap<String, IType>();

      // step 1: assert binary content
      for (IResource resource : fileBasedContent.getBinaryResources()) {

        // assert that the binary resources don't have any references
        Assert.assertEquals(0, resource.getReferences().size());

        // additional asserts if the resource is a class
        if (resource.getPath().endsWith(".class")) {

          // TODO
          // Assert.assertEquals(resourceStandin.getPath(), 1,
          // resourceStandin.getResource().getContainedTypes()
          // .size());

          for (IType type : resource.getContainedTypes()) {

            Assert.assertNotNull(type.getBinaryResource());
            Assert.assertTrue(type.hasBinaryResource());
            Assert.assertEquals(resource, type.getBinaryResource());

            //
            typeMap.put(type.getFullyQualifiedName(), type);

            Map<String, IReference> referenceMap = new HashMap<String, IReference>();

            for (IReference reference : type.getReferences()) {

              Assert.assertNotNull(reference.getFullyQualifiedName());
              Assert.assertFalse(reference.hasAssociatedResource());
              Assert.assertTrue(reference.hasAssociatedType());
              Assert.assertEquals(type, reference.getType());

              //
              Assert.assertFalse(
                  String.format("Duplicate reference '%s' -> '%s'", reference, referenceMap.get(reference)),
                  referenceMap.containsKey(reference.getFullyQualifiedName()));

              referenceMap.put(reference.getFullyQualifiedName(), reference);
            }
          }
        }
      }

      // step 2: assert source content
      for (IResource resource : fileBasedContent.getSourceResources()) {

        // assert that the binary resources don't have any references
        // Assert.assertEquals(0, resource.getReferences().size());

        // additional asserts if the resource is a class
        if (resource.getPath().endsWith(".java")) {

          // TODO
          // Assert.assertTrue(sourceResource.getPath(),
          // sourceResource.getContainedTypes().size() > 0);
          if (resource.getContainedTypes().size() == 0) {
            System.out.println(resource.getPath());
          }

          // TODO
          // Assert.assertEquals(resourceStandin.getPath(), 1,
          // resourceStandin.getResource().getContainedTypes()
          // .size());

          for (IType type : resource.getContainedTypes()) {

            Assert.assertNotNull(type.getSourceResource());
            Assert.assertTrue(type.hasSourceResource());
            Assert.assertEquals(resource, type.getSourceResource());

            Assert.assertTrue(
                String.format("Type '%s' is not contained in the binary type map.", type.getFullyQualifiedName()),
                typeMap.containsKey(type.getFullyQualifiedName()));

            Assert.assertSame(
                String.format("Type '%s' is not contained in the binary type map.", type.getFullyQualifiedName()),
                type, typeMap.get(type.getFullyQualifiedName()));

            Map<String, IReference> referenceMap = new HashMap<String, IReference>();

            for (IReference reference : type.getReferences()) {

              Assert.assertNotNull(reference.getFullyQualifiedName());
              Assert.assertFalse(reference.hasAssociatedResource());
              Assert.assertTrue(reference.hasAssociatedType());
              Assert.assertEquals(type, reference.getType());

              //
              Assert.assertFalse(
                  String.format("Duplicate reference '%s' -> '%s'", reference, referenceMap.get(reference)),
                  referenceMap.containsKey(reference.getFullyQualifiedName()));

              referenceMap.put(reference.getFullyQualifiedName(), reference);
            }
          }
        }
      }
    }
  }

}

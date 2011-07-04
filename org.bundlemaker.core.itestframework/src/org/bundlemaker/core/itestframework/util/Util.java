package org.bundlemaker.core.itestframework.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IReferencedModulesQueryResult;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.query.ReferenceQueryFilters;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;

public class Util {
  /**
   * <p>
   * </p>
   * 
   * @param module
   * @return
   */
  public static void dump(IResourceModule module, IModularizedSystem modularizedSystem) {

    StringBuilder builder = new StringBuilder();
    builder.append(module.getModuleIdentifier().toString() + "\n");

    builder.append("\n");
    builder.append("Source-Content: \n");

    for (IResource resource : asSortedList(module.getResources(ContentType.SOURCE))) {
      builder.append(resource.getPath() + "\n");

      for (IReference reference : resource.getReferences()) {
        builder.append(" * " + reference.toString() + "\n");
      }

      for (IType type : resource.getContainedTypes()) {
        builder.append(" - " + type.getFullyQualifiedName() + "\n");

        for (IReference reference : type.getReferences()) {
          builder.append("   * " + reference.toString() + "\n");
        }
      }
    }

    builder.append("\n");
    builder.append("Binary-Content: \n");
    for (IResource resource : asSortedList(module.getResources(ContentType.BINARY))) {
      builder.append(resource.getPath() + "\n");

      for (IReference reference : resource.getReferences()) {
        builder.append(" * " + reference.toString() + "\n");
      }

      for (IResource stickyResources : resource.getStickyResources()) {
        builder.append(" ~sticky~ " + stickyResources.getPath() + "\n");
      }

      for (IType type : resource.getContainedTypes()) {
        builder.append(" - " + type.getFullyQualifiedName() + "\n");

        for (IReference reference : type.getReferences()) {
          builder.append("   * " + reference.toString() + "\n");
        }
      }
    }

    builder.append("\n");
    builder.append("Referenced Types: \n");
    Set<String> referencedTypes = module
        .getReferencedTypeNames(ReferenceQueryFilters.ALL_DIRECT_EXTERNAL_REFERENCES_QUERY_FILTER);
    for (String referencedType : asSortedList(referencedTypes)) {
      builder.append(referencedType + "\n");
    }

    builder.append("\n");
    builder.append("Indirectly referenced Types: \n");
    Set<String> indirectlyReferencedTypes = module
        .getReferencedTypeNames(ReferenceQueryFilters.ALL_DIRECT_EXTERNAL_REFERENCES_QUERY_FILTER);
    for (String referencedType : asSortedList(indirectlyReferencedTypes)) {
      if (!referencedTypes.contains(referencedType)) {
        builder.append(referencedType + "\n");
      }
    }

    builder.append("\n");
    builder.append("Referenced Modules: \n");
    IReferencedModulesQueryResult queryResult = modularizedSystem.getReferencedModules(module, null);

    for (IModule referencedModule : queryResult.getReferencedModules()) {
      builder.append(referencedModule.getModuleIdentifier().toString() + "\n");
    }

    // TODO
    builder.append("\n");
    builder.append("Missing Types: \n");
    for (IReference missingType : queryResult.getUnsatisfiedReferences()) {
      builder.append(missingType + "\n");
    }

    //
    System.out.println(builder.toString());
  }

  /**
   * <p>
   * </p>
   * 
   * @param <T>
   * @param set
   * @return
   */
  public static <T extends Comparable<T>> List<T> asSortedList(Set<T> set) {

    //
    List<T> arrayList = new ArrayList<T>(set);

    //
    Collections.sort(arrayList);

    //
    return arrayList;
  }

  /**
   * <p>
   * </p>
   * 
   * @param is
   * @return
   * @throws IOException
   */
  public static String convertStreamToString(InputStream is) {

    try {
      if (is != null) {
        Writer writer = new StringWriter();

        char[] buffer = new char[1024];
        try {
          Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
          int n;
          while ((n = reader.read(buffer)) != -1) {
            writer.write(buffer, 0, n);
          }
        } finally {
          is.close();
        }
        return writer.toString();
      } else {
        return "";
      }
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  public static String toString(RingBuffer<Character> buffer) {

    StringBuilder builder = new StringBuilder();
    while (!buffer.isEmpty()) {
      builder.append(buffer.dequeue());
    }
    return builder.toString();
  }

  /**
   * <p>
   * </p>
   * 
   * @param string1
   * @param string2
   * @return
   */
  public static boolean equalsIgnoreWhitespace(String expected, String actual) {

    expected = expected.replaceAll("\r\n", "\n");
    actual = actual.replaceAll("\r\n", "\n");
    char[] arr1 = expected.toCharArray();
    char[] arr2 = actual.toCharArray();

    RingBuffer<Character> buffer1 = new RingBuffer<Character>(100);
    RingBuffer<Character> buffer2 = new RingBuffer<Character>(100);

    for (int i = 0; i < arr1.length; i++) {
      char c1 = arr1[i];
      char c2 = arr2[i];

      // System.out.println("'" + c1 + "' : '" + c2 + "'");

      buffer1.enqueue(c1);
      buffer2.enqueue(c2);

      if (c1 != c2) {
        System.out.println("Expected: '" + Util.toString(buffer1) + "'");
        System.out.println("Actual  : '" + Util.toString(buffer2) + "'");
        return false;
      }
    }

    return true;
  }

}

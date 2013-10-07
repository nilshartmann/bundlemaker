package org.bundlemaker.core.jtype;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.jtype.internal.Reference;
import org.bundlemaker.core.jtype.internal.Type;
import org.bundlemaker.core.resource.IModuleResource;

public class HelperUtil {

  // TODO: clean up!
  public static void connectParsedResourceToModel(IModuleResource resource, boolean isSource) {

    // set the references
    Set<Reference> resourceReferences = new HashSet<Reference>();
    for (Reference reference : resource.adaptAs(IParsableTypeResource.class).getModifiableReferences()) {
      Reference newReference = new Reference(reference);
      newReference.setResource(resource);
      resourceReferences.add(newReference);
    }
    resource.adaptAs(IParsableTypeResource.class).getModifiableReferences().clear();
    resource.adaptAs(IParsableTypeResource.class).getModifiableReferences().addAll(resourceReferences);

    // set the type-back-references
    for (Type type : resource.adaptAs(IParsableTypeResource.class).getModifiableContainedTypes()) {

      //
      if (isSource) {
        type.setSourceResource(resource);
      } else {
        type.setBinaryResource(resource);
      }

      // set the references
      Map<String, Reference> typeReferences = new HashMap<String, Reference>();
      for (Reference reference : type.getModifiableReferences()) {
        // TODO
        if (reference == null) {
          continue;
        }
        Reference newReference = new Reference(reference);
        newReference.setType(type);
        if (typeReferences.containsKey(newReference)) {
          throw new RuntimeException();
        } else {
          typeReferences.put(newReference.getFullyQualifiedName(), newReference);
        }
      }
      type.getModifiableReferences().clear();
      type.getModifiableReferences().addAll(typeReferences.values());
    }
  }

}

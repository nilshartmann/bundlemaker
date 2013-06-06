package org.bundlemaker.core.transformations.script;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.bundlemaker.core.common.ResourceType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TransformationModelConfiguration {
  boolean hierarchicalPackages() default false;

  ResourceType contentType() default ResourceType.BINARY;

  boolean useVirtualModuleForMissingTypes() default false;
}
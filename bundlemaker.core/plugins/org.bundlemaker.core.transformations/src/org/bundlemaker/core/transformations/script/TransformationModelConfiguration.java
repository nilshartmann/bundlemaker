package org.bundlemaker.core.transformations.script;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.bundlemaker.core.projectdescription.ProjectContentType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TransformationModelConfiguration {
  boolean hierarchicalPackages() default false;

  ProjectContentType contentType() default ProjectContentType.BINARY;

  boolean useVirtualModuleForMissingTypes() default false;
}
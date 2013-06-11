/*******************************************************************************
 * Copyright (c) 2011 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.artifact;

import java.net.URL;

import org.bundlemaker.core._type.analysis.ITypeArtifact;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

/**
 * This enumeration provides icons for several {@link IBundleMakerArtifact} types
 * 
 * <p>
 * BundleMaker clients should use this class for getting icons for artifacts in order to provide consistent UI look and
 * feel.
 * 
 * <p>
 * Taken from http://blogs.itemis.de/wendehal/2010/07/08/pretty-elegant-way-to-provide-images-in-eclipse-ui-plug-ins/
 * 
 * @author Nils Hartmann
 */
public enum ArtifactImages {

  /**
   * The icon for <b>Root</b> artifacts
   */
  ROOT_ARTIFACT_ICON("icons/artifacts/root-artifact-icon.gif"),

  /**
   * The icon for <b>Group</b> artifacts
   */
  GROUP_ARTIFACT_ICON("icons/artifacts/group-artifact-icon.gif"),

  /**
   * The icon for <b>Module</b> artifacts
   */
  MODULE_ARTIFACT_ICON("icons/artifacts/module-artifact-icon.gif"),

  /**
   * The icon for <b>Package</b> artifacts
   */
  PACKAGE_ARTIFACT_ICON("icons/artifacts/package-artifact-icon.gif"),

  /**
   * The icon for <b>Resource</b> artifacts
   */
  RESOURCE_ARTIFACT_ICON("icons/artifacts/resource-artifact-icon.gif"),

  /**
   * The icon for <b>Type</b> artifacts
   */
  TYPE_ARTIFACT_ICON("icons/artifacts/type-artifact-icon.gif"),

  /**
   * The icon for <b>Class Type</b> artifacts
   */
  CLASS_TYPE_ARTIFACT_ICON("icons/artifacts/type-artifact-icon.gif"),

  /**
   * The icon for <b>Interface Type</b> artifacts
   */
  INTERFACE_TYPE_ARTIFACT_ICON("icons/artifacts/interface-type-artifact-icon.gif"),

  /**
   * The icon for <b>Enum Type</b> artifacts
   */
  ENUM_TYPE_ARTIFACT_ICON("icons/artifacts/enum-type-artifact-icon.gif"),

  /**
   * The icon for <b>Annotation Type</b> artifacts
   */
  ANNOTATION_TYPE_ARTIFACT_ICON("icons/artifacts/type-artifact-icon.gif"),

  // --- ==== DISABLED ICONS ==== ---
  /**
   * The <b>disabled</b> icon for <b>Root</b> artifacts
   */
  ROOT_ARTIFACT_DISABLED_ICON("icons/artifacts/root-artifact-disabled-icon.png"),

  /**
   * The <b>disabled</b> icon for <b>Group</b> artifacts
   */
  GROUP_ARTIFACT_DISABLED_ICON("icons/artifacts/group-artifact-disabled-icon.png"),

  /**
   * The <b>disabled</b> icon for <b>Module</b> artifacts
   */
  MODULE_ARTIFACT_DISABLED_ICON("icons/artifacts/module-artifact-disabled-icon.png"),

  /**
   * The <b>disabled</b> icon for <b>Package</b> artifacts
   */
  PACKAGE_ARTIFACT_DISABLED_ICON("icons/artifacts/package-artifact-disabled-icon.png"),

  /**
   * The <b>disabled</b> icon for <b>Resource</b> artifacts
   */
  RESOURCE_ARTIFACT_DISABLED_ICON("icons/artifacts/resource-artifact-disabled-icon.png"),

  /**
   * The <b>disabled</b> icon for <b>Type</b> artifacts
   */
  TYPE_ARTIFACT_DISABLED_ICON("icons/artifacts/type-artifact-disabled-icon.png"),

  /**
   * The <b>disabled</b> icon for <b>Class Type</b> artifacts
   */
  CLASS_TYPE_ARTIFACT_DISABLED_ICON("icons/artifacts/type-artifact-disabled-icon.png"),

  /**
   * The <b>disabled</b> icon for <b>Interface Type</b> artifacts
   */
  INTERFACE_TYPE_ARTIFACT_DISABLED_ICON("icons/artifacts/interface-type-artifact-disabled-icon.png"),

  /**
   * The <b>disabled</b> icon for <b>Enum Type</b> artifacts
   */
  ENUM_TYPE_ARTIFACT_DISABLED_ICON("icons/artifacts/enum-type-artifact-disabled-icon.png"),

  /**
   * The <b>disabled</b> icon for <b>Annotation Type</b> artifacts
   */
  ANNOTATION_TYPE_ARTIFACT_DISABLED_ICON("icons/artifacts/type-artifact-disabled-icon.png"),

  // ---- * ----
  /**
   * 
   */
  ARTIFACT_TREE_CONFIGURATION_FLAT_PACKAGES("icons/artifact-tree-configuration-flat.png"),

  /**
   * 
   */
  ARTIFACT_TREE_CONFIGURATION_HIERARCHICAL_PACKAGES("icons/artifact-tree-configuration-hierarchical.png"),

  //
  ;

  /**
   * The bundle-relative path to the icon
   */
  private final String path;

  private ArtifactImages(final String path) {
    this.path = path;
  }

  /**
   * Returns an image. Clients do not need to dispose the image, it will be disposed automatically.
   * 
   * @return an {@link Image}
   */
  public Image getImage() {
    final ImageRegistry imageRegistry = Activator.getDefault().getImageRegistry();
    Image image = imageRegistry.get(this.path);
    if (image == null) {
      addImageDescriptor();
      image = imageRegistry.get(this.path);
    }

    return image;
  }

  /**
   * Returns an image descriptor.
   * 
   * @return an {@link ImageDescriptor}
   */
  public ImageDescriptor getImageDescriptor() {
    final ImageRegistry imageRegistry = Activator.getDefault().getImageRegistry();
    ImageDescriptor imageDescriptor = imageRegistry.getDescriptor(this.path);
    if (imageDescriptor == null) {
      addImageDescriptor();
      imageDescriptor = imageRegistry.getDescriptor(this.path);
    }

    return imageDescriptor;
  }

  public URL getImageUrl() {
    final Activator plugin = Activator.getDefault();
    return plugin.getBundle().getEntry(this.path);
  }

  public static ArtifactImages forArtifact(IBundleMakerArtifact artifact) {
    if (artifact.isInstanceOf(IRootArtifact.class)) {
      return ROOT_ARTIFACT_ICON;
    }
    else if (artifact.isInstanceOf(IGroupArtifact.class)) {
      return GROUP_ARTIFACT_ICON;
    }
    else if (artifact.isInstanceOf(IModuleArtifact.class)) {
      return MODULE_ARTIFACT_ICON;
    }
    else if (artifact.isInstanceOf(IPackageArtifact.class)) {
      return PACKAGE_ARTIFACT_ICON;
    }
    else if (artifact.isInstanceOf(IResourceArtifact.class)) {
      return RESOURCE_ARTIFACT_ICON;
    }
    else if (artifact.isInstanceOf(ITypeArtifact.class)) {
      ITypeArtifact typeHolder = (ITypeArtifact) artifact;

      if (typeHolder.getAssociatedType() != null) {
        switch (typeHolder.getAssociatedType().getType()) {
        case CLASS: {
          return CLASS_TYPE_ARTIFACT_ICON;
        }
        case INTERFACE: {
          return INTERFACE_TYPE_ARTIFACT_ICON;
        }
        case ENUM: {
          return ArtifactImages.ENUM_TYPE_ARTIFACT_ICON;
        }
        case ANNOTATION: {
          return ArtifactImages.ANNOTATION_TYPE_ARTIFACT_ICON;
        }
        default:
          break;
        }
      } else {
        return ArtifactImages.CLASS_TYPE_ARTIFACT_ICON;
      }
      return TYPE_ARTIFACT_ICON;

    }

    // TODO
    System.out.println("WARN: UNKNOWN ARTIFACT TYPE " + artifact);
    return ROOT_ARTIFACT_ICON;

  }

  private void addImageDescriptor() {
    final Activator plugin = Activator.getDefault();
    final ImageDescriptor id = ImageDescriptor.createFromURL(getImageUrl());
    plugin.getImageRegistry().put(this.path, id);
  }

}

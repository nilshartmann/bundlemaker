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

package org.bundlemaker.core.ui.artifact.tree;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.bundlemaker.core.ui.artifact.ArtifactImages;
import org.bundlemaker.core.util.collections.GenericCache;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;

/**
 * <p>
 * A default {@link ILabelProvider} implementation for {@link IBundleMakerArtifact}s
 * </p>
 * 
 * <p>
 * Clients may use this class as-is or may subclass it for specific needs
 * 
 * <p>
 * A collection of Icon-Images for the Artifact types can be received via {@link ArtifactImages}.
 * 
 * 
 * @see ArtifactImages
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class DefaultArtifactLabelProvider implements ILabelProvider {

  /** - */
  private GenericCache<ImageDescriptor, Image> _imageMap = new GenericCache<ImageDescriptor, Image>() {
                                                           @Override
                                                           protected Image create(ImageDescriptor key) {
                                                             return key.createImage();
                                                           }
                                                         };

  /**
   * This implementation delegates to {@link #getImageForArtifact(IBundleMakerArtifact)} if <tt>element</tt> is an
   * {@link IBundleMakerArtifact}. Otherwise null is returned
   */
  @Override
  public Image getImage(Object element) {
    if (!(element instanceof IBundleMakerArtifact)) {
      return null;
    }

    IBundleMakerArtifact artifact = (IBundleMakerArtifact) element;
    return getImageForArtifact(artifact);
  }

  /**
   * This implementation delegates to {@link #getTextForArtifact(IBundleMakerArtifact)} if <tt>element</tt> is an
   * {@link IBundleMakerArtifact} . Otherwise null is returned
   */
  @Override
  public String getText(Object element) {
    if (!(element instanceof IBundleMakerArtifact)) {
      return null;
    }

    IBundleMakerArtifact artifact = (IBundleMakerArtifact) element;
    return getTextForArtifact(artifact);
  }

  /**
   * This method delegates to specific getTextForXyzArtifact-methods on this class. Subclasses may override this method
   * or one of the specific methods in order to provide another or more labels.
   * <p>
   * </p>
   * 
   * @param artifact
   * @return
   */
  protected String getTextForArtifact(IBundleMakerArtifact artifact) {

    if (artifact.isInstanceOf(IRootArtifact.class)) {
      return getTextForRootArtifact(artifact);
    }
    else if (artifact.isInstanceOf(IGroupArtifact.class)) {
      return getTextForGroupArtifact(artifact);
    }
    else if (artifact.isInstanceOf(IModuleArtifact.class)) {
      return getTextForModuleArtifact(artifact);
    }
    else if (artifact.isInstanceOf(IPackageArtifact.class)) {
      return getTextForPackageArtifact(artifact);
    }
    else if (artifact.isInstanceOf(IResourceArtifact.class)) {
      return getTextForResourceArtifact(artifact);
    }
    else if (artifact.isInstanceOf(ITypeArtifact.class)) {
      return getTextForTypeArtifact(artifact);
    }

    // No image
    return null;

  }

  /**
   * <p>
   * Returns the label text for the specified <b>Root</b>-Artifact
   * </p>
   * <p>
   * Subclasses may override this method to provide another label.
   * </p>
   * 
   * @param artifact
   * @return
   */
  protected String getTextForRootArtifact(IBundleMakerArtifact artifact) {
    return artifact.getName();
  }

  /**
   * <p>
   * Returns the label text for the specified <b>Group</b>-Artifact
   * </p>
   * <p>
   * Subclasses may override this method to provide another label.
   * </p>
   * 
   * @param artifact
   * @return
   */
  protected String getTextForGroupArtifact(IBundleMakerArtifact artifact) {
    return artifact.getName();
  }

  /**
   * <p>
   * Returns the label text for the specified <b>Module</b>-Artifact
   * </p>
   * <p>
   * Subclasses may override this method to provide another label.
   * </p>
   * 
   * @param artifact
   * @return
   */
  protected String getTextForModuleArtifact(IBundleMakerArtifact artifact) {
    return artifact.getName();
  }

  /**
   * <p>
   * Returns the label text for the specified <b>Package</b>-Artifact
   * </p>
   * <p>
   * Subclasses may override this method to provide another label.
   * </p>
   * 
   * @param artifact
   * @return
   */
  protected String getTextForPackageArtifact(IBundleMakerArtifact artifact) {
    return artifact.getQualifiedName();
  }

  /**
   * <p>
   * Returns the label text for the specified <b>Resource</b>-Artifact
   * </p>
   * <p>
   * Subclasses may override this method to provide another label.
   * </p>
   * 
   * @param artifact
   * @return
   */
  protected String getTextForResourceArtifact(IBundleMakerArtifact artifact) {
    return artifact.getName();
  }

  /**
   * <p>
   * Returns the label text for the specified <b>Type</b>-Artifact
   * </p>
   * <p>
   * Subclasses may override this method to provide another label.
   * </p>
   * 
   * @param artifact
   * @return
   */
  protected String getTextForTypeArtifact(IBundleMakerArtifact artifact) {
    return artifact.getName();
  }

  /**
   * This method delegates to specific <em>getImageForXyzArtifact</em>-methods on this class.
   * <p>
   * Subclasses may override this method or one of the specific methods in order to provide another or more icons.
   * 
   * @param artifact
   *          the artifact. Never null.
   * @return
   */
  protected Image getImageForArtifact(IBundleMakerArtifact artifact) {

    //
    if (artifact.isInstanceOf(IRootArtifact.class)) {
      return getImageForRootArtifact(artifact);
    }
    else if (artifact.isInstanceOf(IGroupArtifact.class)) {
      return getImageForGroupArtifact(artifact);
    }
    else if (artifact.isInstanceOf(IModuleArtifact.class)) {
      return getImageForModuleArtifact(artifact);
    }
    else if (artifact.isInstanceOf(IPackageArtifact.class)) {
      return getImageForPackageArtifact(artifact);
    }
    else if (artifact.isInstanceOf(IResourceArtifact.class)) {
      return getImageForResourceArtifact((IResourceArtifact) artifact);
    }
    else if (artifact.isInstanceOf(ITypeArtifact.class)) {
      return getImageForTypeArtifact(artifact);
    }

    // No image
    return null;
  }

  /**
   * <p>
   * Returns the image that should be used for artifacts of type <b>Root</b>
   * </p>
   * 
   * @param rootArtifact
   *          the artifact of type Root. Never null
   * 
   */
  protected Image getImageForRootArtifact(IBundleMakerArtifact rootArtifact) {
    return ArtifactImages.ROOT_ARTIFACT_ICON.getImage();
  }

  /**
   * <p>
   * Returns the image that should be used for artifacts of type <b>Group</b>
   * </p>
   * 
   * @param groupArtifact
   *          the artifact of type Group. Never null
   */
  protected Image getImageForGroupArtifact(IBundleMakerArtifact groupArtifact) {
    return ArtifactImages.GROUP_ARTIFACT_ICON.getImage();
  }

  /**
   * <p>
   * Returns the image that should be used for artifacts of type <b>Module</b>
   * </p>
   * 
   * @param moduleArtifact
   *          the artifact of type Module. Never null
   */
  protected Image getImageForModuleArtifact(IBundleMakerArtifact moduleArtifact) {
    return ArtifactImages.MODULE_ARTIFACT_ICON.getImage();
  }

  /**
   * <p>
   * Returns the image that should be used for artifacts of type <b>Package</b>
   * </p>
   * 
   * @param packageArtifact
   *          the artifact of type Package. Never null
   */
  protected Image getImageForPackageArtifact(IBundleMakerArtifact packageArtifact) {
    return ArtifactImages.PACKAGE_ARTIFACT_ICON.getImage();
  }

  /**
   * <p>
   * Returns the image that should be used for artifacts of type <b>Type</b>
   * </p>
   * 
   * @param typeArtifact
   *          the artifact of type Type. Never null
   */
  protected Image getImageForTypeArtifact(IBundleMakerArtifact typeArtifact) {
    return ArtifactImages.TYPE_ARTIFACT_ICON.getImage();
  }

  /**
   * <p>
   * Returns the image that should be used for artifacts of type <b>Resource</b>
   * </p>
   * 
   * @param resourceArtifact
   *          the artifact of type Resource. Never null
   */
  protected Image getImageForResourceArtifact(IResourceArtifact resourceArtifact) {

    ImageDescriptor imageDescriptor = PlatformUI.getWorkbench().getEditorRegistry()
        .getImageDescriptor(resourceArtifact.getAssociatedResource().getName());

    return _imageMap.getOrCreate(imageDescriptor);
    // return ArtifactImages.RESOURCE_ARTIFACT_ICON.getImage();
  }

  // ------------------------------------------------------------------
  /**
   * {@inheritDoc}
   */
  @Override
  public void addListener(ILabelProviderListener listener) {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void dispose() {
    for (Image image : _imageMap.values()) {
      image.dispose();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isLabelProperty(Object element, String property) {
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeListener(ILabelProviderListener listener) {
  }

}

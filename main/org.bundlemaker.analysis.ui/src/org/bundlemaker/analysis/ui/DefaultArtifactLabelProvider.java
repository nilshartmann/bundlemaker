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

package org.bundlemaker.analysis.ui;

import org.bundlemaker.analysis.model.IArtifact;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

/**
 * <p>
 * A default {@link ILabelProvider} implementation for {@link IArtifact}s
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
  /**
   * This implementation delegates to {@link #getImageForArtifact(IArtifact)} if <tt>element</tt> is an
   * {@link IArtifact}. Otherwise null is returned
   */
  @Override
  public Image getImage(Object element) {
    if (!(element instanceof IArtifact)) {
      return null;
    }

    IArtifact artifact = (IArtifact) element;
    return getImageForArtifact(artifact);
  }

  /**
   * This implementation delegates to {@link #getTextForArtifact(IArtifact)} if <tt>element</tt> is an {@link IArtifact}
   * . Otherwise null is returned
   */
  @Override
  public String getText(Object element) {
    if (!(element instanceof IArtifact)) {
      return null;
    }

    IArtifact artifact = (IArtifact) element;
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
  protected String getTextForArtifact(IArtifact artifact) {
    switch (artifact.getType()) {
    case Root:
      return getTextForRootArtifact(artifact);
    case Group:
      return getTextForGroupArtifact(artifact);
    case Module:
      return getTextForModuleArtifact(artifact);
    case Package:
      return getTextForPackageArtifact(artifact);
    case Resource:
      return getTextForResourceArtifact(artifact);
    case Type:
      return getTextForTypeArtifact(artifact);
    default:
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
  protected String getTextForRootArtifact(IArtifact artifact) {
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
  protected String getTextForGroupArtifact(IArtifact artifact) {
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
  protected String getTextForModuleArtifact(IArtifact artifact) {
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
  protected String getTextForPackageArtifact(IArtifact artifact) {
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
  protected String getTextForResourceArtifact(IArtifact artifact) {
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
  protected String getTextForTypeArtifact(IArtifact artifact) {
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
  protected Image getImageForArtifact(IArtifact artifact) {
    switch (artifact.getType()) {
    case Root:
      return getImageForRootArtifact(artifact);
    case Group:
      return getImageForGroupArtifact(artifact);
    case Module:
      return getImageForModuleArtifact(artifact);
    case Package:
      return getImageForPackageArtifact(artifact);
    case Resource:
      return getImageForResourceArtifact(artifact);
    case Type:
      return getImageForTypeArtifact(artifact);
    default:
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
  protected Image getImageForRootArtifact(IArtifact rootArtifact) {
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
  protected Image getImageForGroupArtifact(IArtifact groupArtifact) {
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
  protected Image getImageForModuleArtifact(IArtifact moduleArtifact) {
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
  protected Image getImageForPackageArtifact(IArtifact packageArtifact) {
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
  protected Image getImageForTypeArtifact(IArtifact typeArtifact) {
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
  protected Image getImageForResourceArtifact(IArtifact resourceArtifact) {
    return ArtifactImages.RESOURCE_ARTIFACT_ICON.getImage();
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

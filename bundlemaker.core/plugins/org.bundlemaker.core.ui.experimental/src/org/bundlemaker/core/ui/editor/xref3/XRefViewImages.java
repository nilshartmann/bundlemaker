package org.bundlemaker.core.ui.editor.xref3;

import org.bundlemaker.core.ui.editor.xref3.internal.Activator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

/**
 * This enumeration provides a number of images for the plugin.
 * 
 * <p>
 * Taken from http://blogs.itemis.de/wendehal/2010/07/08/pretty-elegant-way-to-provide -images-in-eclipse-ui-plug-ins/
 */
public enum XRefViewImages {

  NAV_FORWARD_DISABLED("icons/dtool/forward_nav.gif"), //
  NAV_FORWARD_ENABLED("icons/etool/forward_nav.gif"), //
  NAV_BACKWARD_DISABLED("icons/dtool/backward_nav.gif"), //
  NAV_BACKWARD_ENABLED("icons/etool/backward_nav.gif"), //
  ;

  /**
   * The bundle-relative path to the icon
   */
  private final String path;

  private XRefViewImages(final String path) {
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

  private void addImageDescriptor() {
    final Activator plugin = Activator.getDefault();
    final ImageDescriptor id = ImageDescriptor.createFromURL(plugin.getBundle().getEntry(this.path));
    plugin.getImageRegistry().put(this.path, id);
  }

}

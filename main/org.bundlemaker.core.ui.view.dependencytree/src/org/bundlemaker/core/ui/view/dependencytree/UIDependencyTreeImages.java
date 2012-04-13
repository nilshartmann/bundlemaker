package org.bundlemaker.core.ui.view.dependencytree;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

/**
 * This enumeration provides a number of images for the plugin.
 */
public enum UIDependencyTreeImages {

  /** - */
  ENABLED_PIN_SELECTION_ADD("icons/etool/pin-selection-add.png"),

  /** - */
  ENABLED_PIN_SELECTION_CLEAR("icons/etool/pin-selection-clear.png"),

  /** - */
  DISABLED_PIN_SELECTION_ADD("icons/dtool/pin-selection-add.png"),

  /** - */
  DISABLED_PIN_SELECTION_CLEAR("icons/dtool/pin-selection-clear.png"),

  /** - */
  ENABLED_BACKWARD_NAV("icons/etool/backward_nav.gif"),

  /** - */
  ENABLED_FORWARD_NAV("icons/etool/forward_nav.gif"),

  /** - */
  DISABLED_BACKWARD_NAV("icons/dtool/backward_nav.gif"),

  /** - */
  DISABLED_FORWARD_NAV("icons/dtool/forward_nav.gif");

  private final String path;

  private UIDependencyTreeImages(final String path) {
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
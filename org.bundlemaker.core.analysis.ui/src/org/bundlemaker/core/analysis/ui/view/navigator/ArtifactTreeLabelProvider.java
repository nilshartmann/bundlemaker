package org.bundlemaker.core.analysis.ui.view.navigator;

import org.bundlemaker.core.analysis.model.IArtifact;
import org.bundlemaker.core.analysis.ui.Activator;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ArtifactTreeLabelProvider implements ILabelProvider {

  /**
   * {@inheritDoc}
   */
  @Override
  public Image getImage(Object obj) {

    if (obj instanceof String || obj instanceof IModularizedSystem) {
      return Activator.getDefault().getIcon("navigator/root.gif");
    } else if (obj instanceof IArtifact) {
      IArtifact artifact = (IArtifact) obj;

      switch (artifact.getType()) {
      case Root:
        return Activator.getDefault().getIcon("navigator/root.gif");
      case Group:
        return Activator.getDefault().getIcon("navigator/folder_obj.gif");
      case Module:
        return Activator.getDefault().getIcon("navigator/jar_obj.gif");
      case Package:
        return Activator.getDefault().getIcon("navigator/package_obj.gif");
      case Resource:
        return Activator.getDefault().getIcon("navigator/file_obj.gif");
      case Type:
        return Activator.getDefault().getIcon("navigator/class_obj.gif");
      default:
        break;
      }
    }

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getText(Object obj) {

    if (obj instanceof String) {
      return (String) obj;
    } else if (obj instanceof IModularizedSystem) {
      return ((IModularizedSystem) obj).getName();
    } else if (obj instanceof IArtifact) {
      IArtifact artifact = (IArtifact) obj;
      switch (artifact.getType()) {
      case Root:
        return artifact.getName();
      case Group:
        return artifact.getName();
      case Module:
        return artifact.getName();
      case Package:
        return artifact.getQualifiedName();
      case Resource:
        return artifact.getName();
      case Type:
        return artifact.getName();
      default:
        break;
      }
    }

    return null;
  }

  @Override
  public void addListener(ILabelProviderListener ilabelproviderlistener) {
    //
  }

  @Override
  public void dispose() {
    //
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isLabelProperty(Object obj, String s) {
    //
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeListener(ILabelProviderListener ilabelproviderlistener) {
    //
  }
}
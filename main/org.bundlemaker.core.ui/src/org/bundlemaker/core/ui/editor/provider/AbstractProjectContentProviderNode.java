package org.bundlemaker.core.ui.editor.provider;

import org.bundlemaker.core.projectdescription.IModifiableProjectDescription;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

/**
 * Abstract implementation of the IWizardNode interface providing a consistent look and feel for the table displaying a
 * list of possible bundle container types.
 */
abstract class AbstractProjectContentProviderNode implements IWizardNode {
  private String                              _typeName;

  private String                              _typeDescription;

  private Image                               _typeImage;

  private final IModifiableProjectDescription _modifiableProjectDescription;

  private IWizard                             _wizard;

  public AbstractProjectContentProviderNode(IModifiableProjectDescription projectDescription, String name,
      String description, Image image) {
    _modifiableProjectDescription = projectDescription;
    _typeName = name;
    _typeDescription = description;
    _typeImage = image;
  }

  public abstract IWizard createWizard();

  @Override
  public void dispose() {
    if (_wizard != null) {
      _wizard.dispose();
      _wizard = null;
    }
  }

  @Override
  public Point getExtent() {
    return new Point(-1, -1);
  }

  @Override
  public IWizard getWizard() {
    if (_wizard == null) {
      _wizard = createWizard();
    }
    return _wizard;
  }

  @Override
  public boolean isContentCreated() {
    return _wizard != null;
  }

  public String getName() {
    return _typeName;
  }

  public String getDescription() {
    return _typeDescription;
  }

  public Image getImage() {
    return _typeImage;
  }

  /**
   * @return the modifiableProjectDescription
   */
  public IModifiableProjectDescription getModifiableProjectDescription() {
    return _modifiableProjectDescription;
  }

}
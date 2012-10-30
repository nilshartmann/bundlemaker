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
package org.bundlemaker.core.ui.artifact.configuration;

import org.bundlemaker.core.analysis.AnalysisModelConfiguration;
import org.bundlemaker.core.projectdescription.ContentType;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ArtifactModelConfigurationDialog extends TitleAreaDialog {

  private final static EnumValueLabelProvider enumValueLabelProvider = new EnumValueLabelProvider();

  private final AnalysisModelConfiguration    _originalConfiguration;

  public ArtifactModelConfigurationDialog(Shell parentShell, AnalysisModelConfiguration currentConfiguration) {
    super(parentShell);

    _originalConfiguration = currentConfiguration;

    configureDialog();
  }

  @Override
  protected void configureShell(Shell newShell) {
    super.configureShell(newShell);

    newShell.setText("Configure Dependency Tree");
  }

  private void configureDialog() {
    setShellStyle(SWT.CLOSE | SWT.MAX | SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL | SWT.RESIZE
        | getDefaultOrientation());
    setHelpAvailable(false);

  }

  private Button      _hierarchicalButton;

  private ComboViewer _contentTypeComboViewer;

  private Button      _virtualModuleButton;

  @Override
  protected Control createDialogArea(Composite parent) {
    setTitle("Customize Dependency Tree");
    setMessage("Set options according to your needs");

    final Composite areaComposite = (Composite) super.createDialogArea(parent);

    Composite dialogComposite = new Composite(areaComposite, SWT.NONE);
    dialogComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

    dialogComposite.setLayout(new GridLayout(2, false));

    _hierarchicalButton = new Button(dialogComposite, SWT.CHECK);
    _hierarchicalButton.setText("Show hierarchical Packages");
    GridData layoutData = new GridData();
    layoutData.horizontalSpan = 2;
    _hierarchicalButton.setLayoutData(layoutData);

    Label label = new Label(dialogComposite, SWT.NONE);
    label.setText("Show content type");

    Combo contentTypeCombo = new Combo(dialogComposite, SWT.READ_ONLY);
    _contentTypeComboViewer = new ComboViewer(contentTypeCombo);
    _contentTypeComboViewer.setLabelProvider(enumValueLabelProvider);
    _contentTypeComboViewer.add(new EnumValue(ContentType.SOURCE, "Sources"));
    _contentTypeComboViewer.add(new EnumValue(ContentType.BINARY, "Binaries"));

    _virtualModuleButton = new Button(dialogComposite, SWT.CHECK);
    _virtualModuleButton.setText("Show missing types in 'virtual' module");
    layoutData = new GridData();
    layoutData.horizontalSpan = 2;
    _virtualModuleButton.setLayoutData(layoutData);

    Dialog.applyDialogFont(areaComposite);

    prepopulateDialog();

    return areaComposite;
  }

  private void prepopulateDialog() {
    selectEnumValue(_contentTypeComboViewer, _originalConfiguration.getContentType());
    _hierarchicalButton.setSelection(_originalConfiguration.isHierarchicalPackages());
    // selectEnumValue(_resourcesComboViewer, _originalConfiguration.getResourcePresentation());
    _virtualModuleButton.setSelection(_originalConfiguration.isIncludeVirtualModuleForMissingTypes());
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.dialogs.Dialog#okPressed()
   */
  @Override
  protected void okPressed() {
    _originalConfiguration.setHierarchicalPackages(_hierarchicalButton.getSelection());
    _originalConfiguration.setIncludeVirtualModuleForMissingTypes(_virtualModuleButton.getSelection());

    int selectionIndex = _contentTypeComboViewer.getCombo().getSelectionIndex();
    EnumValue value = (EnumValue) _contentTypeComboViewer.getElementAt(selectionIndex);
    _originalConfiguration.setContentType((ContentType) value.getValue());

    super.okPressed();
  }

  private void selectEnumValue(ComboViewer viewer, Enum<?> value) {
    int itemCount = viewer.getCombo().getItemCount();
    for (int i = 0; i < itemCount; i++) {
      EnumValue elementValue = (EnumValue) viewer.getElementAt(i);
      if (value.equals(elementValue.getValue())) {
        viewer.getCombo().select(i);
        break;
      }
    }
  }

  class EnumValue {
    private final Enum<?> _value;

    private final String  _label;

    /**
     * @param value
     * @param label
     */
    public EnumValue(Enum<?> value, String label) {
      super();
      _value = value;
      _label = label;
    }

    /**
     * @return the value
     */
    public Enum<?> getValue() {
      return _value;
    }

    /**
     * @return the label
     */
    public String getLabel() {
      return _label;
    }
  }

  static class EnumValueLabelProvider implements ILabelProvider {

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
     */
    @Override
    public void addListener(ILabelProviderListener listener) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
     */
    @Override
    public void dispose() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
     */
    @Override
    public boolean isLabelProperty(Object element, String property) {
      return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
     */
    @Override
    public void removeListener(ILabelProviderListener listener) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
     */
    @Override
    public Image getImage(Object element) {
      return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
     */
    @Override
    public String getText(Object element) {
      EnumValue value = (EnumValue) element;
      return value.getLabel();
    }

  }

}

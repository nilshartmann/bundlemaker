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
package org.bundlemaker.core.ui.editor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * <p>
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
class ResourceChooser {

  private String _selectedPath = "";

  /** - */
  private Text   _pathTextField;

  public ResourceChooser(Composite parent) {
    Composite composite = new Composite(parent, SWT.NONE);
    GridData gridData = new GridData();
    gridData.horizontalAlignment = SWT.FILL;
    gridData.grabExcessHorizontalSpace = true;
    composite.setLayoutData(gridData);
    GridLayout layout = new GridLayout(2, false);
    composite.setLayout(layout);
    _pathTextField = new Text(composite, SWT.BORDER);
    _pathTextField.addModifyListener(new ModifyListener() {

      @Override
      public void modifyText(ModifyEvent e) {
        _selectedPath = _pathTextField.getText();
      }
    });
    gridData = new GridData();
    gridData.horizontalAlignment = SWT.FILL;
    gridData.grabExcessHorizontalSpace = true;
    _pathTextField.setLayoutData(gridData);

    Button button = new Button(composite, SWT.PUSH);
    button.setText("Browse...");
    button.addSelectionListener(new SelectionListener() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        DirectoryDialog dd = new DirectoryDialog(new Shell());
        if (isPathSelected()) {
          dd.setFilterPath(getSelectedPath());
        }
        String path = dd.open();
        if (path != null) {
          setSelectedPath(path);
        }
      }

      @Override
      public void widgetDefaultSelected(SelectionEvent e) {

      }
    });

  }

  public void addModifyListener(ModifyListener modifyListener) {
    _pathTextField.addModifyListener(modifyListener);
  }

  public void setSelectedPath(String path) {
    if (path == null) {
      path = "";
    }
    _selectedPath = path;
    _pathTextField.setText(_selectedPath);
  }

  public String getSelectedPath() {
    return _selectedPath;
  }

  public boolean isPathSelected() {
    return (getSelectedPath().isEmpty() == false);
  }
}

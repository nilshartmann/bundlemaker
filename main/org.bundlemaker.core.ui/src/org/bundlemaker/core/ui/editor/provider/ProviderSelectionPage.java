/*******************************************************************************
 * Copyright (c) 2011 Nils Hartmann
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Nils Hartmann - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.editor.provider;

import java.util.ArrayList;
import java.util.List;

import org.bundlemaker.core.projectdescription.IModifiableProjectDescription;
import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.WizardSelectionPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ProviderSelectionPage extends WizardSelectionPage {

  private final IModifiableProjectDescription _projectDescription;

  private Text                                _description;

  protected ProviderSelectionPage(IModifiableProjectDescription projectDescription) {
    super("SelectionPage"); //$NON-NLS-1$

    // set current project description
    _projectDescription = projectDescription;

    // set title and message
    setTitle("Choose content provider");
    setMessage("Select a provder of project content");
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
   */
  @Override
  public void createControl(Composite parent) {
    Composite comp = new Composite(parent, SWT.NONE);
    comp.setLayout(new GridLayout(1, false));
    comp.setFont(parent.getFont());
    GridData gd = new GridData(GridData.FILL_BOTH);
    gd.grabExcessHorizontalSpace = true;
    comp.setLayoutData(gd);

    SashForm sashForm = new SashForm(comp, SWT.VERTICAL);
    gd = new GridData(GridData.FILL_BOTH);
    // limit the width of the sash form to avoid the wizard opening very wide.
    gd.widthHint = 300;
    sashForm.setLayoutData(gd);
    sashForm.setFont(comp.getFont());

    TableViewer wizardSelectionViewer = new TableViewer(sashForm, SWT.BORDER);
    wizardSelectionViewer.setContentProvider(new ArrayContentProvider());
    wizardSelectionViewer.setLabelProvider(new LabelProvider() {
      @Override
      public String getText(Object element) {
        if (element instanceof AbstractProjectContentProviderNode) {
          return ((AbstractProjectContentProviderNode) element).getName();
        }
        return super.getText(element);
      }

      @Override
      public Image getImage(Object element) {
        if (element instanceof AbstractProjectContentProviderNode) {
          return ((AbstractProjectContentProviderNode) element).getImage();
        }
        return super.getImage(element);
      }
    });
    wizardSelectionViewer.addDoubleClickListener(new IDoubleClickListener() {
      @Override
      public void doubleClick(DoubleClickEvent event) {
        IStructuredSelection selection = (IStructuredSelection) event.getSelection();
        if (!selection.isEmpty()) {
          setSelectedNode((IWizardNode) selection.getFirstElement());
          getContainer().showPage(getNextPage());
        }
      }
    });
    wizardSelectionViewer.addSelectionChangedListener(new ISelectionChangedListener() {
      @Override
      public void selectionChanged(SelectionChangedEvent event) {
        IStructuredSelection selection = (IStructuredSelection) event.getSelection();
        if (!selection.isEmpty()) {
          _description.setText(((AbstractProjectContentProviderNode) selection.getFirstElement()).getDescription());
          setSelectedNode((AbstractProjectContentProviderNode) selection.getFirstElement());
        }
      }
    });
    wizardSelectionViewer.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));
    wizardSelectionViewer.getTable().setFont(sashForm.getFont());

    _description = SWTFactory.createText(sashForm, SWT.READ_ONLY | SWT.BORDER | SWT.MULTI | SWT.WRAP, 1);

    sashForm.setWeights(new int[] { 70, 30 });
    initViewerContents(wizardSelectionViewer);
    setControl(comp);
  }

  /**
   * Creates the IWizardNode instances that provide choices for the user to select
   * 
   * @param wizardSelectionViewer
   */
  private void initViewerContents(TableViewer wizardSelectionViewer) {
    List<AbstractProjectContentProviderNode> choices = new ArrayList<AbstractProjectContentProviderNode>();
    choices.add(new FileBasedProjectContentProviderNode(_projectDescription));
    choices.add(new JdtProjectContentProviderNode(_projectDescription));
    wizardSelectionViewer.setInput(choices.toArray(new IWizardNode[choices.size()]));
  }

}

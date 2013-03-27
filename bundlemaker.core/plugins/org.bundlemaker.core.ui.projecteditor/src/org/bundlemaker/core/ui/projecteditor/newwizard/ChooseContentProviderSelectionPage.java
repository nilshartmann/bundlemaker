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
package org.bundlemaker.core.ui.projecteditor.newwizard;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.ui.projecteditor.provider.INewProjectContentProviderWizardContribution;
import org.bundlemaker.core.ui.projecteditor.provider.internal.ProjectEditorContributionRegistry;
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
 * A Wizard page displaying all available {@link INewProjectContentProviderWizardContribution
 * NewProjectContentProviderWizards}
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ChooseContentProviderSelectionPage extends WizardSelectionPage {

  private final IBundleMakerProject               _bundleMakerProject;

  private final ProjectEditorContributionRegistry _newProjectContentProviderWizardRegistry;

  private Text                                    _description;

  protected ChooseContentProviderSelectionPage(IBundleMakerProject bundleMakerProject,
      ProjectEditorContributionRegistry registry) {
    super("SelectionPage"); //$NON-NLS-1$

    // set current project
    _bundleMakerProject = bundleMakerProject;

    //
    _newProjectContentProviderWizardRegistry = registry;

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
        if (element instanceof ChooseContentProviderWizardNode) {
          return ((ChooseContentProviderWizardNode) element).getLabel();
        }
        return super.getText(element);
      }

      @Override
      public Image getImage(Object element) {
        if (element instanceof ChooseContentProviderWizardNode) {
          return ((ChooseContentProviderWizardNode) element).getImage();
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
          _description.setText(((ChooseContentProviderWizardNode) selection.getFirstElement()).getDescription());
          setSelectedNode(((ChooseContentProviderWizardNode) selection.getFirstElement()));
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
    Set<INewProjectContentProviderWizardContribution> registeredWizardContributionss = _newProjectContentProviderWizardRegistry
        .getNewProjectContentProviderWizardContributions();
    List<ChooseContentProviderWizardNode> nodes = new LinkedList<ChooseContentProviderWizardNode>();

    for (INewProjectContentProviderWizardContribution wizardContribution : registeredWizardContributionss) {

      // Create node for contribution
      ChooseContentProviderWizardNode node = new ChooseContentProviderWizardNode(_bundleMakerProject,
          wizardContribution);

      // add to result set
      nodes.add(node);
    }

    Collections.sort(nodes, new Comparator<ChooseContentProviderWizardNode>() {

      @Override
      public int compare(ChooseContentProviderWizardNode o1, ChooseContentProviderWizardNode o2) {
        return o1.getLabel().compareTo(o2.getLabel());
      }

    });

    wizardSelectionViewer.setInput(nodes);

    // List<AbstractProjectContentProviderNode> choices = new ArrayList<AbstractProjectContentProviderNode>();
    // choices.add(new FileBasedProjectContentProviderNode(_projectDescription));
    // choices.add(new JdtProjectContentProviderNode(_projectDescription));
    // wizardSelectionViewer.setInput(choices.toArray(new IWizardNode[choices.size()]));
  }

}

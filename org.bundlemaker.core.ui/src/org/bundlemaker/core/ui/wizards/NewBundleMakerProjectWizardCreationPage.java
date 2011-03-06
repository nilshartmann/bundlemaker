package org.bundlemaker.core.ui.wizards;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.IVMInstallType;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

/**
 * <p>
 * A wizard page to enter the required settings for a BundleMaker project.
 * </p>
 * 
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class NewBundleMakerProjectWizardCreationPage extends WizardNewProjectCreationPage {

  /** A combo box containing all JREs */
  private Combo _jreCombo;

  public NewBundleMakerProjectWizardCreationPage() {
    super("NewBundleMakerProjectWizardCreationPage");

    setTitle("Create a Bundlemaker project");
    setDescription("Set project name and JRE.");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void createControl(Composite parent) {
    // add controls from default basic wizard page
    super.createControl(parent);
    Composite control = (Composite) getControl();

    // Create the JRE selection box
    createJreGroup(control);

  }

  /**
   * <p>
   * Creates the group for selecting the project JRE
   * </p>
   * 
   * @param parent
   */
  private final void createJreGroup(Composite parent) {
    // jre setting group
    Composite jreGroup = new Composite(parent, SWT.NONE);
    jreGroup.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
    jreGroup.setLayout(new GridLayout(2, false));

    // new JRE label
    Label jreLabel = new Label(jreGroup, SWT.NONE);
    jreLabel.setText("&JRE:");
    jreLabel.setFont(parent.getFont());

    // add JRE ComboBox
    _jreCombo = new Combo(jreGroup, SWT.READ_ONLY);
    GridData textData = new GridData(SWT.LEFT, SWT.CENTER, true, false);
    textData.horizontalIndent = 20;
    _jreCombo.setLayoutData(textData);
    _jreCombo.setFont(parent.getFont());

    // populate combo box with installed JREs
    populateJreComboBox();

  }

  /**
   * <p>
   * Adds all installed JREs to the JRE combobox
   * </p>
   * 
   */
  private void populateJreComboBox() {
    // Create a viewer for the JRE combo box
    ComboViewer viewer = new ComboViewer(_jreCombo);

    // Get all installed JRE descriptions
    List<JreDescription> availableJreDescriptions = getInstalledJreDescriptions();

    // Use an ArrayContentProvider
    viewer.setContentProvider(ArrayContentProvider.getInstance());

    // Set the label provider
    viewer.setLabelProvider(new LabelProvider() {

      @Override
      public String getText(Object element) {
        // use the JRE name as label inside the combo box
        JreDescription jreDescription = (JreDescription) element;
        return super.getText(jreDescription.getName());
      }
    });

    // set the input (list of all JREs)
    viewer.setInput(availableJreDescriptions);

    // pre-select the default JRE
    for (JreDescription jreDescription : availableJreDescriptions) {
      if (jreDescription.isDefaultJre()) {
        viewer.setSelection(new StructuredSelection(jreDescription));
        break;
      }
    }
  }

  /**
   * 
   * <p>
   * Gets a list of {@link JreDescription JreDescriptions} of all installed JREs
   * </p>
   * 
   * @return
   */
  private List<JreDescription> getInstalledJreDescriptions() {
    List<JreDescription> installedJres = new LinkedList<JreDescription>();

    // Get default JreId
    final String defaultJreId = JavaRuntime.getDefaultVMInstall().getId();

    // Get all VM Install types
    IVMInstallType[] types = JavaRuntime.getVMInstallTypes();

    // Iterate over all VM-Types then over all installations of each type
    for (int i = 0; i < types.length; i++) {
      IVMInstallType type = types[i];

      // Iterate over all vm installs
      IVMInstall[] jres = type.getVMInstalls();
      for (int j = 0; j < jres.length; j++) {
        // Create JreDescription for VM
        JreDescription jreDescription = new JreDescription(jres[j].getId(), jres[j].getName(), jres[j].getId().equals(
            defaultJreId));

        // add to result
        installedJres.add(jreDescription);
      }
    }

    // return the list of installed JREs
    return installedJres;
  }

  /**
   * <p>
   * A description of an installed JRE
   * </p>
   * 
   * @author Nils Hartmann (nils@nilshartmann.net)
   * 
   */
  class JreDescription {
    private final String  _id;

    private final String  _name;

    private final boolean _defaultJre;

    public JreDescription(String id, String name, boolean defaultJre) {
      super();
      _id = id;
      _name = name;
      _defaultJre = defaultJre;
    }

    /**
     * <p>
     * </p>
     * 
     * @return the Id of the JRE
     */
    public String getId() {
      return _id;
    }

    /**
     * <p>
     * </p>
     * 
     * @return the name of the JRE
     */
    public String getName() {
      return _name;
    }

    /**
     * <p>
     * </p>
     * 
     * @return true if this is the default JRE
     */
    public boolean isDefaultJre() {
      return _defaultJre;
    }
  }

  public String getSelectedJreId() {
    int selectionIndex = _jreCombo.getSelectionIndex();
    String item = _jreCombo.getItem(selectionIndex);
    return item;
  }
}

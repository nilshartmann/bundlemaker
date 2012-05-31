package org.bundlemaker.core.ui.preferences;

import org.bundlemaker.core.ui.internal.Activator;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class BundleMakerPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

  private RadioGroupFieldEditor _bmPerspectiveSwitchField;

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
   */
  @Override
  public void init(IWorkbench workbench) {

  }

  /**
   * Creates the page's UI content.
   */
  @Override
  protected Control createContents(Composite parent) {

    Composite composite = createComposite(parent);

    createPerspectiveGroup(composite);

    return composite;
  }

  /**
   * Creates the composite which will contain all the preference controls for this page.
   * 
   * @param parent
   *          the parent composite
   * @return the composite for this page
   */
  protected Composite createComposite(Composite parent) {
    Composite composite = new Composite(parent, SWT.NONE);
    GridData data = new GridData(GridData.FILL_BOTH);
    composite.setLayoutData(data);
    composite.setFont(parent.getFont());
    GridLayout layout = new GridLayout();
    layout.marginWidth = 0;
    layout.marginHeight = 0;
    layout.verticalSpacing = 10;
    composite.setLayout(layout);
    return composite;
  }

  /**
   * Creates a composite that contains buttons for selecting the preference opening new project selections.
   */
  private void createPerspectiveGroup(Composite composite) {

    Composite perspectiveComposite = new Composite(composite, SWT.NONE);
    perspectiveComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    perspectiveComposite.setFont(composite.getFont());

    String[][] namesAndValues = {
        { "Always", "always" },
        { "Never", "never" },
        { "Prompt", "prompt" } };
    _bmPerspectiveSwitchField = new RadioGroupFieldEditor(
        BundleMakerPreferences.PREF_SWITCH_TO_PERSPECTIVE_ON_PROJECT_OPEN,
        "Open BundleMaker Perspective after opening a BundleMaker project", namesAndValues.length,
        namesAndValues, perspectiveComposite, true);
    _bmPerspectiveSwitchField.setPreferenceStore(getBundleMakerPreferenceStore());
    _bmPerspectiveSwitchField.setPage(this);
    _bmPerspectiveSwitchField.load();
  }

  /**
   * @return the BundleMaker preference store
   */
  protected IPreferenceStore getBundleMakerPreferenceStore() {
    return Activator.getDefault().getPreferenceStore();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
   */
  @Override
  protected void performDefaults() {
    _bmPerspectiveSwitchField.loadDefault();
    super.performDefaults();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.preference.PreferencePage#performOk()
   */
  @Override
  public boolean performOk() {
    _bmPerspectiveSwitchField.store();
    return super.performOk();
  }

}

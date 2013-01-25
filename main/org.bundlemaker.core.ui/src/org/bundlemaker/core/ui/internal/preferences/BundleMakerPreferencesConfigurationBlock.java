package org.bundlemaker.core.ui.internal.preferences;

import org.bundlemaker.core.ui.preferences.AbstractPropertyAndPreferencesPage;
import org.bundlemaker.core.ui.preferences.ConfigurationBlock;
import org.bundlemaker.core.ui.preferences.RadioGroupDialogField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BundleMakerPreferencesConfigurationBlock extends ConfigurationBlock {

  /** the radio group */
  private RadioGroupDialogField _radioGroup;

  /**
   * <p>
   * Creates a new instance of type {@link BundleMakerPreferencesConfigurationBlock}.
   * </p>
   * 
   * @param parent
   */
  public BundleMakerPreferencesConfigurationBlock(Composite parent, AbstractPropertyAndPreferencesPage page) {
    super(parent, SWT.NONE, page);

    // set the layout
    this.setLayout(new FillLayout(SWT.VERTICAL));

    // create the radio group
    _radioGroup = createRadioGroup(this, "Open BM Perspective after opening a BM project:",
        new String[] { "Always",
            "Never", "Prompt" },
        new String[] { "Always", "Never", "Prompt" }, SWT.HORIZONTAL);
  }

  /**
   * <p>
   * </p>
   */
  @Override
  public void performDefaults() {

    //
    String currentSelection = getPage().getPreferenceStore().getString(
        BundleMakerPreferenceInitializer.PREF_SWITCH_TO_PERSPECTIVE_ON_PROJECT_OPEN);

    //
    currentSelection = currentSelection != null ? currentSelection : "Prompt";

    //
    _radioGroup.setSelection(currentSelection);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String[] getPreferenceKeys() {
    return new String[] { BundleMakerPreferenceInitializer.PREF_SWITCH_TO_PERSPECTIVE_ON_PROJECT_OPEN };
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  @Override
  public boolean performOk() {

    //
    getPage().getPreferenceStore().setValue(
        BundleMakerPreferenceInitializer.PREF_SWITCH_TO_PERSPECTIVE_ON_PROJECT_OPEN,
        (String) _radioGroup.getSelection());

    //
    return true;
  }
}

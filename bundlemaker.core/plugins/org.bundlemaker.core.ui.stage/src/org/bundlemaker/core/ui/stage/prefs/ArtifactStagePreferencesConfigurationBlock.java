package org.bundlemaker.core.ui.stage.prefs;

import org.bundlemaker.core.ui.preferences.AbstractPropertyAndPreferencesPage;
import org.bundlemaker.core.ui.preferences.ConfigurationBlock;
import org.bundlemaker.core.ui.preferences.RadioGroupDialogField;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ArtifactStagePreferencesConfigurationBlock extends ConfigurationBlock {

  /** the radio group */
  private RadioGroupDialogField _radioGroup;

  /**
   * <p>
   * Creates a new instance of type {@link ArtifactStagePreferencesConfigurationBlock}.
   * </p>
   * 
   * @param parent
   */
  public ArtifactStagePreferencesConfigurationBlock(Composite parent, AbstractPropertyAndPreferencesPage page) {
    super(parent, SWT.NONE, page);
  }

  @Override
  protected void createContent() {

    // set the layout
    this.setLayout(new FillLayout(SWT.VERTICAL));

    // create the radio group
    _radioGroup = createRadioGroup(this, "Set Artifact Stage automatically to Manual Mode if an Action requires it:",
        new String[] { MessageDialogWithToggle.ALWAYS, MessageDialogWithToggle.NEVER, MessageDialogWithToggle.PROMPT },
        new Object[] { MessageDialogWithToggle.ALWAYS, MessageDialogWithToggle.NEVER, MessageDialogWithToggle.PROMPT },
        SWT.HORIZONTAL);
  }

  @Override
  public void initialize() {

    //
    String pref = getPage().getPreferenceStore().getString(
        ArtifactStagePreferenceInitializer.PREF_SWITCH_TO_MANUAL_ADD_MODE);

    //
    _radioGroup.setSelection(pref != null ? pref : MessageDialogWithToggle.PROMPT);
  }

  /**
   * <p>
   * </p>
   */
  @Override
  public void performDefaults() {

    //
    String pref = getPage().getPreferenceStore().getString(
        ArtifactStagePreferenceInitializer.PREF_SWITCH_TO_MANUAL_ADD_MODE);

    //
    _radioGroup.setSelection(pref != null ? pref : MessageDialogWithToggle.PROMPT);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String[] getPreferenceKeys() {
    return new String[] { ArtifactStagePreferenceInitializer.PREF_SWITCH_TO_MANUAL_ADD_MODE };
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
    getPage().getPreferenceStore().setValue(ArtifactStagePreferenceInitializer.PREF_SWITCH_TO_MANUAL_ADD_MODE,
        (String) _radioGroup.getSelection());

    //
    return true;
  }
}

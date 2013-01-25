package org.bundlemaker.core.ui.mvn.internal.preference;

import org.bundlemaker.core.mvn.preferences.MvnPreferencesUtils;
import org.bundlemaker.core.ui.preferences.AbstractPropertyAndPreferencesPage;
import org.bundlemaker.core.ui.preferences.ConfigurationBlock;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class MvnPropertyAndPreferenceConfigurationBlock extends ConfigurationBlock {

  /** - */
  private static final String LOCAL_REPO_TITLE  = "Local Repository:";

  /** - */
  private static final String REMOTE_REPO_TITLE = "Remote Repository:";

  /** - */
  private Text                _text_localRepositoryPath;

  /** - */
  private Text                _text_remoteRepositoryPath;

  /** - */
  private Text                _text_settingsXml;

  /**
   * <p>
   * Creates a new instance of type {@link MvnPropertyAndPreferenceConfigurationBlock}.
   * </p>
   * 
   * @param parent
   * @param page
   */
  public MvnPropertyAndPreferenceConfigurationBlock(Composite parent, AbstractPropertyAndPreferencesPage page) {
    super(parent, SWT.NONE, page);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void createContent() {

    //
    GridLayout layout = new GridLayout();
    layout.numColumns = 3;
    setLayout(layout);

    //
    GridData data = null;

    //
    Button check_m2eSettings = new Button(this, SWT.CHECK);
    Label label_m2eSettings = new Label(this, SWT.NONE);
    label_m2eSettings.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
    label_m2eSettings.setText("Use M2E settings");

    //
    Button check_settingsXml = new Button(this, SWT.CHECK);
    Label label_settingsXml = new Label(this, SWT.NONE);
    label_settingsXml.setText("Specify 'settings.xml' file");
    label_settingsXml.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

    // the settingsXml block
     new Label(this, SWT.NONE); // dummy label
     Label label_settingsXmlFile = new Label(this, SWT.NONE);
     label_settingsXmlFile.setText(LOCAL_REPO_TITLE);
     _text_settingsXml = new Text(this, SWT.SINGLE | SWT.BORDER);
     _text_settingsXml.setLayoutData(new GridData(GridData.FILL_BOTH));

    //
    Button check_configureRepositories = new Button(this, SWT.CHECK);
    Label label_configureRepositories = new Label(this, SWT.NONE);
    label_configureRepositories.setText("Directly configure repositories");
    label_configureRepositories.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

    // the repo block
    new Label(this, SWT.NONE); // dummy label
    Label label_localRepoPath = new Label(this, SWT.NONE);
    label_localRepoPath.setText(LOCAL_REPO_TITLE);
    _text_localRepositoryPath = new Text(this, SWT.SINGLE | SWT.BORDER);
    _text_localRepositoryPath.setLayoutData(new GridData(GridData.FILL_BOTH));

    //
    new Label(this, SWT.NONE); // dummy label
    Label label_remoteRepoPath = new Label(this, SWT.NONE);
    label_remoteRepoPath.setText(REMOTE_REPO_TITLE);
    _text_remoteRepositoryPath = new Text(this, SWT.SINGLE | SWT.BORDER);
    _text_remoteRepositoryPath.setLayoutData(new GridData(GridData.FILL_BOTH));
    
    //
    performDefaults();
  }

  @Override
  public void performDefaults() {

    //
    _text_localRepositoryPath.setText(getPage().getPreferenceStore().getString(
        MvnPreferencesUtils.PREF_MVN_LOCAL_REPO));

    //
    _text_remoteRepositoryPath.setText(getPage().getPreferenceStore().getString(
        MvnPreferencesUtils.PREF_MVN_REMOTE_REPO));
  }

  @Override
  protected String[] getPreferenceKeys() {
    return new String[] { MvnPreferencesUtils.PREF_MVN_LOCAL_REPO, MvnPreferencesUtils.PREF_MVN_REMOTE_REPO };
  }

  @Override
  public boolean performOk() {

    //
    getPage().getPreferenceStore().putValue(MvnPreferencesUtils.PREF_MVN_LOCAL_REPO,
        _text_localRepositoryPath.getText());
    getPage().getPreferenceStore().putValue(MvnPreferencesUtils.PREF_MVN_REMOTE_REPO,
        _text_remoteRepositoryPath.getText());

    //
    return true;
  }
}

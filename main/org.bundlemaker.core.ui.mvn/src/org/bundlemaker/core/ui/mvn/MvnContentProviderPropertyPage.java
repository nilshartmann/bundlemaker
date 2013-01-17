package org.bundlemaker.core.ui.mvn;

import org.bundlemaker.core.mvn.BmMvnCoreConstants;
import org.bundlemaker.core.mvn.content.IRepositoryLocationProvider;
import org.bundlemaker.core.mvn.content.PropertiesAndPreferencesBasedRepositoryLocationProvider;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;
import org.osgi.service.prefs.BackingStoreException;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MvnContentProviderPropertyPage extends PropertyPage {

  /** - */
  private static final String LOCAL_REPO_TITLE  = "Local Repository Path:";

  /** - */
  private static final String REMOTE_REPO_TITLE = "Remote Repository Path:";

  /** - */
  private static final int    TEXT_FIELD_WIDTH  = 50;

  /** - */
  private Text                text_localRepositoryPath;

  /** - */
  private Text                text_remoteRepositoryPath;

  /**
   * @see PreferencePage#createContents(Composite)
   */
  protected Control createContents(Composite parent) {

    //
    Composite composite = new Composite(parent, SWT.NONE);
    GridLayout layout = new GridLayout();
    composite.setLayout(layout);
    GridData data = new GridData(GridData.FILL);
    data.grabExcessHorizontalSpace = true;
    composite.setLayoutData(data);

    //
    text_localRepositoryPath = createLabelTextField(parent, LOCAL_REPO_TITLE);
    text_remoteRepositoryPath = createLabelTextField(parent, REMOTE_REPO_TITLE);

    //
    performDefaults();

    //
    return composite;
  }

  /**
   * <p>
   * </p>
   * 
   * @param parent
   * @return
   */
  private Text createLabelTextField(Composite parent, String label) {

    //
    Assert.isNotNull(parent);
    Assert.isNotNull(label);

    //
    Composite composite = createDefaultComposite(parent);
    Label pathLabel = new Label(composite, SWT.NONE);
    pathLabel.setText(label);

    Text result =
        new Text(composite, SWT.SINGLE | SWT.BORDER);
    GridData gd = new GridData();
    gd.widthHint =
        convertWidthInCharsToPixels(TEXT_FIELD_WIDTH);
    result.setLayoutData(gd);

    //
    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @param parent
   * @return
   */
  private Composite createDefaultComposite(
      Composite parent)
  {
    Composite composite = new Composite(parent, SWT.NULL);
    GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    composite.setLayout(layout);

    GridData data = new GridData();
    data.verticalAlignment = GridData.FILL;
    data.horizontalAlignment = GridData.FILL;
    composite.setLayoutData(data);

    return composite;
  }

  @Override
  protected void performDefaults() {

    super.performDefaults();

    try {
      //
      IProject project = (IProject) getElement().getAdapter(IProject.class);

      //
      IRepositoryLocationProvider provider = new PropertiesAndPreferencesBasedRepositoryLocationProvider();
      
      //
      this.text_localRepositoryPath.setText(provider.getLocalRepo(project));
      this.text_remoteRepositoryPath.setText(provider.getRemoteRepo(project));
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean performOk() {

    // store the value in the owner text field
    IScopeContext projectScope = new ProjectScope((IProject) getElement().getAdapter(IProject.class));
    IEclipsePreferences preferences = projectScope.getNode(BmMvnCoreConstants.PLUGIN_ID);
    //
    try {
      preferences.sync();

      // preferences.

      preferences.put(BmMvnCoreConstants.PREF_MVN_LOCAL_REPO, this.text_localRepositoryPath.getText());
      preferences.put(BmMvnCoreConstants.PREF_MVN_REMOTE_REPO, this.text_remoteRepositoryPath.getText());

      preferences.flush(); // Exception occurs here!!!
    } catch (BackingStoreException e) {
      e.printStackTrace();
    }

    //
    return true;
  }
}

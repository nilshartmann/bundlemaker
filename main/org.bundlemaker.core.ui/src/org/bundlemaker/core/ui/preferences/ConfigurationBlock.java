package org.bundlemaker.core.ui.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.swt.widgets.Composite;

/**
 * <p>
 * </p>
 * 
 * <p>
 * This source was copied (and than modified) from the internal class
 * {@link org.eclipse.jdt.internal.ui.preferences.PropertyAndPreferencePage}.
 * </p>
 */
public abstract class ConfigurationBlock extends Composite {

  /** - */
  private AbstractPropertyAndPreferencesPage _page;

  /**
   * <p>
   * </p>
   * 
   * @param parent
   * @param style
   */
  public ConfigurationBlock(Composite parent, int style, AbstractPropertyAndPreferencesPage page) {
    super(parent, style);

    //
    Assert.isNotNull(page);

    //
    _page = page;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public AbstractPropertyAndPreferencesPage getPage() {
    return _page;
  }

  /**
   * <p>
   * </p>
   * 
   */
  public abstract void performDefaults();

  protected abstract String[] getPreferenceKeys();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public abstract boolean performOk();

  public boolean performApply() {
    return performOk();
  }

  public boolean performCancel() {
    return true;
  }

  public void performHelp() {

  }

  /**
   * <p>
   * </p>
   * 
   * @param project
   * @return
   */
  public boolean hasProjectSpecificOptions(IProject project) {

    //
    IEclipsePreferences eclipsePreferences = new ProjectScope(project).getNode(getPage().getStoreIdentifier());

    for (String string : getPreferenceKeys()) {
      if (eclipsePreferences.get(string, null) != null) {
        return true;
      }
    }

    //
    return false;
  }

  /**
   * Creates a composite that contains buttons for selecting the preference opening new project selections.
   */
  protected RadioGroupDialogField createRadioGroup(Composite parent, String label, String[] names, Object[] values,
      int layoutType) {

    //
    return new RadioGroupDialogField(parent, label, names, values, layoutType);
  }
}

package org.bundlemaker.core.util.prefs;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BundleMakerPreferences implements IBundleMakerPreferences {

  /** - */
  private IScopeContext[]     _contexts;

  /** - */
  private IPreferencesService _service;

  /** - */
  private String              _qualifier;

  /**
   * <p>
   * Creates a new instance of type {@link BundleMakerPreferences}.
   * </p>
   * 
   * @param contexts
   */
  private BundleMakerPreferences(String qualifier, IScopeContext... contexts) {

    //
    Assert.isNotNull(qualifier);
    Assert.isNotNull(contexts);

    //
    _service = Platform.getPreferencesService();
    _contexts = contexts;
    _qualifier = qualifier;
  }

  public boolean getBoolean(String key, boolean defaultValue) {
    return _service.getBoolean(_qualifier, key, defaultValue, _contexts);
  }

  public byte[] getByteArray(String key, byte[] defaultValue) {
    return _service.getByteArray(_qualifier, key, defaultValue, _contexts);
  }

  public double getDouble(String key, double defaultValue) {
    return _service.getDouble(_qualifier, key, defaultValue, _contexts);
  }

  public float getFloat(String key, float defaultValue) {
    return _service.getFloat(_qualifier, key, defaultValue, _contexts);
  }

  public int getInt(String key, int defaultValue) {
    return _service.getInt(_qualifier, key, defaultValue, _contexts);
  }

  public long getLong(String key, long defaultValue) {
    return _service.getLong(_qualifier, key, defaultValue, _contexts);
  }

  public String getString(String key, String defaultValue) {
    return _service.getString(_qualifier, key, defaultValue, _contexts);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void reload() {
    // throw new RuntimeException("reload");
  }

  /**
   * <p>
   * </p>
   * 
   * @param identifier
   * @param project
   * @return
   */
  public static BundleMakerPreferences getBundleMakerPreferences(String identifier, IProject project) {
    Assert.isNotNull(identifier);
    return new BundleMakerPreferences(identifier, new ProjectScope(project),
        InstanceScope.INSTANCE);
  }
}

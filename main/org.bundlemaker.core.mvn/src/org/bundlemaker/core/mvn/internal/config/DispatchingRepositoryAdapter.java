package org.bundlemaker.core.mvn.internal.config;

import java.util.List;

import org.apache.maven.settings.Settings;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.repository.RemoteRepository;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 */
public class DispatchingRepositoryAdapter implements IAetherRepositoryAdapter {

  /** - */
  private IAetherRepositoryAdapter _adapter;

  /**
   * <p>
   * Creates a new instance of type {@link DispatchingRepositoryAdapter}.
   * </p>
   */
  public DispatchingRepositoryAdapter() {
    
    // get the settings
    Settings settings = AetherUtils.getSettings(AetherUtils.getM2eUserSettings(),
        AetherUtils.getM2eGlobalSettings());
    
    // create the adapter
    _adapter = new MvnSettingsBasedRepositoryAdapter(settings);
  }

  /**
   * {@inheritDoc}
   */
  public RepositorySystem getRepositorySystem() {
    return _adapter.getRepositorySystem();
  }

  /**
   * {@inheritDoc}
   */
  public RepositorySystemSession newSession() {
    return _adapter.newSession();
  }

  /**
   * {@inheritDoc}
   */
  public List<RemoteRepository> getRemoteRepositories() {
    return _adapter.getRemoteRepositories();
  }
}

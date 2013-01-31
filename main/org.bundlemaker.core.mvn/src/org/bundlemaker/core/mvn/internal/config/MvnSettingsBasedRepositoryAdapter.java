package org.bundlemaker.core.mvn.internal.config;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.maven.repository.internal.MavenRepositorySystemSession;
import org.apache.maven.settings.Mirror;
import org.apache.maven.settings.Proxy;
import org.apache.maven.settings.Settings;
import org.apache.maven.settings.building.DefaultSettingsBuilderFactory;
import org.apache.maven.settings.building.DefaultSettingsBuildingRequest;
import org.apache.maven.settings.building.SettingsBuilder;
import org.apache.maven.settings.building.SettingsBuildingException;
import org.bundlemaker.core.mvn.internal.aether.ManualRepositorySystemFactory;
import org.eclipse.core.runtime.Assert;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.repository.Authentication;
import org.sonatype.aether.repository.LocalRepository;
import org.sonatype.aether.repository.MirrorSelector;
import org.sonatype.aether.repository.ProxySelector;
import org.sonatype.aether.repository.RemoteRepository;
import org.sonatype.aether.util.repository.DefaultMirrorSelector;
import org.sonatype.aether.util.repository.DefaultProxySelector;

/**
 * Aether based, drop in replacement for mvn protocol
 */
public class MvnSettingsBasedRepositoryAdapter implements IAetherRepositoryAdapter {

  /** - */
  private static final SettingsBuilder settingsBuilder      = new DefaultSettingsBuilderFactory()
                                                                .newInstance();

  /** - */
  private File                         _userSettingsFile;

  /** - */
  private File                         _globalSettingsFile;

  private static final String          LATEST_VERSION_RANGE = "(0.0,]";

  private static final String          REPO_TYPE            = "default";

  final private MirrorSelector         _mirrorSelector;

  final private ProxySelector          _proxySelector;

  final private RepositorySystem       _repositorySystem;

  private Settings                     _settings;

  /**
   * <p>
   * Creates a new instance of type {@link MvnSettingsBasedRepositoryAdapter}.
   * </p>
   * 
   * @param userSettingsFile
   * @param globalSettingsFile
   */
  public MvnSettingsBasedRepositoryAdapter(File userSettingsFile, File globalSettingsFile) {

    //
    _userSettingsFile = userSettingsFile;
    _globalSettingsFile = globalSettingsFile;

    //
    _settings = getSettings(userSettingsFile, globalSettingsFile);

    //
    _proxySelector = selectProxies();
    _mirrorSelector = selectMirrors();
    _repositorySystem = ManualRepositorySystemFactory.newRepositorySystem();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public File getUserSettingsFile() {
    return _userSettingsFile;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public File getGlobalSettingsFile() {
    return _globalSettingsFile;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public LocalRepository getLocalRepository() {
    Assert.isNotNull(_settings.getLocalRepository());
    File local = new File(_settings.getLocalRepository());
    MavenRepositorySystemSession session = new MavenRepositorySystemSession();
    return new LocalRepository(local);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   * @throws IOException
   */
  public List<RemoteRepository> getRemoteRepositories() {

    //
    List<RemoteRepository> remoteRepos = AetherUtils.getRemoteRepositories(_settings);

    // add the default repo
    RemoteRepository defaultRepo = new RemoteRepository();
    defaultRepo.setId("central");
    defaultRepo.setContentType("default");
    defaultRepo.setUrl("http://repo1.maven.org/maven2");
    defaultRepo.setPolicy(false, null);
    remoteRepos.add(defaultRepo);

    assignProxyAndMirrors(remoteRepos);

    //
    for (RemoteRepository repository : remoteRepos) {

      //
      repository.getId();
    }

    //
    return remoteRepos;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public RepositorySystem getRepositorySystem() {
    return _repositorySystem;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public RepositorySystemSession newSession() {

    Assert.isNotNull(_settings.getLocalRepository());

    File local = new File(_settings.getLocalRepository());

    MavenRepositorySystemSession session = new MavenRepositorySystemSession();

    LocalRepository localRepo = new LocalRepository(local);

    session.setLocalRepositoryManager(_repositorySystem.newLocalRepositoryManager(localRepo));
    session.setMirrorSelector(_mirrorSelector);
    session.setProxySelector(_proxySelector);
    return session;
  }

  /**
   * <p>
   * </p>
   * 
   * @param remoteRepos
   */
  private void assignProxyAndMirrors(List<RemoteRepository> remoteRepos)
  {
    Map<String, List<String>> map = new HashMap<String, List<String>>();
    Map<String, RemoteRepository> naming = new HashMap<String, RemoteRepository>();

    for (RemoteRepository r : remoteRepos) {
      naming.put(r.getId(), r);

      r.setProxy(_proxySelector.getProxy(r));

      RemoteRepository mirror = _mirrorSelector.getMirror(r);
      if (mirror != null) {
        String key = mirror.getId();
        naming.put(key, mirror);
        if (!map.containsKey(key)) {
          map.put(key, new ArrayList<String>());
        }
        List<String> mirrored = map.get(key);
        mirrored.add(r.getId());
      }
    }

    for (String mirrorId : map.keySet()) {
      RemoteRepository mirror = naming.get(mirrorId);
      List<RemoteRepository> mirroedRepos = new ArrayList<RemoteRepository>();

      for (String rep : map.get(mirrorId)) {
        mirroedRepos.add(naming.get(rep));
      }
      mirror.setMirroredRepositories(mirroedRepos);
      remoteRepos.removeAll(mirroedRepos);
      remoteRepos.add(0, mirror);
    }

  }

  private ProxySelector selectProxies()
  {
    DefaultProxySelector proxySelector = new DefaultProxySelector();

    //
    for (Proxy proxy : _settings.getProxies()) {

      // The fields are user, pass, host, port, nonProxyHosts, protocol.
      String nonProxyHosts = proxy.getNonProxyHosts();
      org.sonatype.aether.repository.Proxy proxyObj = new org.sonatype.aether.repository.Proxy(proxy.getProtocol(),
          proxy.getHost(),
          proxy.getPort(),
          getAuthentication(proxy)
          );
      proxySelector.add(proxyObj, nonProxyHosts);
    }

    //
    return proxySelector;
  }

  private MirrorSelector selectMirrors()
  {
    // configure mirror
    DefaultMirrorSelector selector = new DefaultMirrorSelector();

    //
    for (Mirror mirror : _settings.getMirrors()) {

      // type can be null in this implementation (1.11)
      selector.add(mirror.getId(), mirror.getUrl(), null, false, mirror.getMirrorOf(), "*");
    }

    return selector;
  }

  // private List<RemoteRepository> selectRepositories(List<MavenRepositoryURL> repos)
  // {
  // List<RemoteRepository> list = new ArrayList<RemoteRepository>();
  // for (MavenRepositoryURL r : repos) {
  // if (r.isMulti()) {
  // addSubDirs(list, r.getFile());
  // } else {
  // addRepo(list, r);
  // }
  // }
  // return list;
  // }
  //
  // private void addSubDirs(List<RemoteRepository> list, File parentDir) {
  // if (!parentDir.isDirectory()) {
  // LOG.debug("Repository marked with @multi does not resolve to a directory: " + parentDir);
  // return;
  // }
  // for (File repo : parentDir.listFiles()) {
  // if (repo.isDirectory()) {
  // try {
  // String repoURI = repo.toURI().toString() + "@id=" + repo.getName();
  // LOG.debug("Adding repo from inside multi dir: " + repoURI);
  // addRepo(list, new MavenRepositoryURL(repoURI));
  // } catch (MalformedURLException e) {
  // LOG.error("Error resolving repo url of a multi repo " + repo.toURI());
  // }
  // }
  // }
  // }
  //
  // private void addRepo(List<RemoteRepository> list, MavenRepositoryURL repoUrl) {
  // list.add(new RemoteRepository(repoUrl.getId(), REPO_TYPE, repoUrl.getURL().toExternalForm()));
  // }

  // private File resolve(RepositorySystemSession session, List<RemoteRepository> remoteRepos, Artifact artifact)
  // throws IOException
  // {
  // try {
  // artifact = resolveLatestVersionRange(session, remoteRepos, artifact);
  // return m_repoSystem.resolveArtifact(session, new ArtifactRequest(artifact, remoteRepos, null)).getArtifact()
  // .getFile();
  // } catch (ArtifactResolutionException e) {
  // /**
  // * Do not add root exception to avoid NotSerializableException on DefaultArtifact. To avoid loosing information
  // * log the root cause. We can remove this again as soon as DefaultArtifact is serializeable. See
  // * http://team.ops4j.org/browse/PAXURL-206
  // */
  // LOG.warn("Error resolving artifact + artifact.toString() :" + e.getMessage(), e);
  // throw new IOException("Error resolving artifact " + artifact.toString() + ": " + e.getMessage());
  // } catch (RepositoryException e) {
  // throw new IOException("Error resolving artifact " + artifact.toString(), e);
  // }
  // }

  // /**
  // * Tries to resolve versions = LATEST using an open range version query. If it succeeds, version of artifact is set
  // to
  // * the highest available version.
  // *
  // * @param session
  // * to be used.
  // * @param artifact
  // * to be used
  // *
  // * @return an artifact with version set properly (highest if available)
  // *
  // * @throws org.sonatype.aether.resolution.VersionRangeResolutionException
  // * in case of resolver errors.
  // */
  // private Artifact resolveLatestVersionRange(RepositorySystemSession session, List<RemoteRepository> remoteRepos,
  // Artifact artifact)
  // throws VersionRangeResolutionException
  // {
  // if (artifact.getVersion().equals(VERSION_LATEST)) {
  // artifact = artifact.setVersion(LATEST_VERSION_RANGE);
  //
  // VersionRangeResult versionResult = m_repoSystem.resolveVersionRange(session, new VersionRangeRequest(artifact,
  // remoteRepos, null));
  // if (versionResult != null) {
  // Version v = versionResult.getHighestVersion();
  // if (v != null) {
  //
  // artifact = artifact.setVersion(v.toString());
  // }
  // else {
  // throw new VersionRangeResolutionException(versionResult, "Not highest version found for " + artifact);
  // }
  // }
  // }
  // return artifact;
  // }

  /**
   * <p>
   * </p>
   * 
   * @param proxy
   * @return
   */
  private Authentication getAuthentication(Proxy proxy)
  {

    // user, pass
    if (proxy.getUsername() != null) {
      return new Authentication(proxy.getUsername(), proxy.getPassword());
    }

    //
    return null;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public static Settings getSettings(File userSettingsFile, File globalSettingsFile)
  {
    //
    Settings settings;

    //
    DefaultSettingsBuildingRequest request = new DefaultSettingsBuildingRequest();
    request.setUserSettingsFile(userSettingsFile);
    request.setGlobalSettingsFile(globalSettingsFile);

    // TODO:
    // request.setSystemProperties(getSystemProperties());
    // request.setUserProperties(getUserProperties());

    try
    {
      settings = settingsBuilder.build(request).getEffectiveSettings();

      // SettingsDecryptionResult result =
      // settingDecrypter.decrypt(new DefaultSettingsDecryptionRequest(settings));
      // settings.setServers(result.getServers());
      // settings.setProxies(result.getProxies());

      return settings;

    } catch (SettingsBuildingException e)
    {
      e.printStackTrace();
      // project.log("Could not process settings.xml: " + e.getMessage(), e, Project.MSG_WARN);

      throw new RuntimeException(e.getMessage(), e);
    }
  }

}
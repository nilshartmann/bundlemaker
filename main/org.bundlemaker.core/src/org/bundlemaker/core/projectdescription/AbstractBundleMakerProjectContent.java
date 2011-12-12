package org.bundlemaker.core.projectdescription;

import java.util.Collection;

import org.bundlemaker.core.internal.resource.ResourceStandin;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractBundleMakerProjectContent implements IBundleMakerProjectContent {

  /** - */
  protected boolean     _isInitialized;

  /** the internal identifier of this content entry */
  protected String      _id;

  /** the name of this entry */
  protected String      _name;

  /** the version of this entry */
  protected String      _version;

  /** the analyze mode of this entry */
  protected AnalyzeMode _analyze;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractBundleMakerProjectContent}.
   * </p>
   */
  public AbstractBundleMakerProjectContent() {
    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getId() {
    return _id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return _name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getVersion() {
    return _version;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isAnalyze() {
    return _analyze.isAnalyze();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AnalyzeMode getAnalyzeMode() {
    return _analyze;
  }

  /**
   * <p>
   * </p>
   * 
   * @param id
   */
  public void setId(String id) {
    _id = id;
  }

  /**
   * <p>
   * </p>
   * 
   * @param name
   */
  public void setName(String name) {
    _name = name;
  }

  /**
   * <p>
   * </p>
   * 
   * @param version
   */
  public void setVersion(String version) {
    _version = version;
  }

  /**
   * <p>
   * </p>
   * 
   * @param analyzeMode
   */
  public void setAnalyzeMode(AnalyzeMode analyzeMode) {
    Assert.isNotNull(analyzeMode, "Paramter 'analyzeMode' must not be null");

    //
    _analyze = analyzeMode;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public abstract Collection<ResourceStandin> getBinaryResourceStandins();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public abstract Collection<ResourceStandin> getSourceResourceStandins();
}
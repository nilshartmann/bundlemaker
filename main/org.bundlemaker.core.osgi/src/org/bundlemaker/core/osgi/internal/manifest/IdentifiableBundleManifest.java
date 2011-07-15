package org.bundlemaker.core.osgi.internal.manifest;

import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.osgi.framework.Version;

import com.springsource.util.osgi.manifest.BundleActivationPolicy;
import com.springsource.util.osgi.manifest.BundleManifest;
import com.springsource.util.osgi.manifest.BundleSymbolicName;
import com.springsource.util.osgi.manifest.DynamicImportPackage;
import com.springsource.util.osgi.manifest.ExportPackage;
import com.springsource.util.osgi.manifest.FragmentHost;
import com.springsource.util.osgi.manifest.ImportBundle;
import com.springsource.util.osgi.manifest.ImportLibrary;
import com.springsource.util.osgi.manifest.ImportPackage;
import com.springsource.util.osgi.manifest.RequireBundle;

public class IdentifiableBundleManifest implements BundleManifest {

  /** - */
  public static final int BUNDLE_MANIFEST   = 0;

  /** - */
  public static final int MANIFEST_TEMPLATE = 1;

  /** - */
  public static final int ORIGINAL_MANIFEST = 2;

  /** - */
  private BundleManifest  _bundleManifest;

  /** - */
  private int             _role;

  private Set<String>     _transitiveClosure;

  /**
   * <p>
   * Creates a new instance of type {@link IdentifiableBundleManifest}.
   * </p>
   * 
   * @param bundleManifest
   * @param role
   */
  public IdentifiableBundleManifest(BundleManifest bundleManifest, int role) {
    _bundleManifest = bundleManifest;
    _role = role;
  }

  public final int getRole() {
    return _role;
  }

  public BundleActivationPolicy getBundleActivationPolicy() {
    return _bundleManifest.getBundleActivationPolicy();
  }

  public List<String> getBundleClasspath() {
    return _bundleManifest.getBundleClasspath();
  }

  public String getBundleDescription() {
    return _bundleManifest.getBundleDescription();
  }

  public void setBundleDescription(String bundleDescription) {
    _bundleManifest.setBundleDescription(bundleDescription);
  }

  public int getBundleManifestVersion() {
    return _bundleManifest.getBundleManifestVersion();
  }

  public void setBundleManifestVersion(int bundleManifestVersion) {
    _bundleManifest.setBundleManifestVersion(bundleManifestVersion);
  }

  public String getBundleName() {
    return _bundleManifest.getBundleName();
  }

  public void setBundleName(String bundleName) {
    _bundleManifest.setBundleName(bundleName);
  }

  public BundleSymbolicName getBundleSymbolicName() {
    return _bundleManifest.getBundleSymbolicName();
  }

  public URL getBundleUpdateLocation() {
    return _bundleManifest.getBundleUpdateLocation();
  }

  public void setBundleUpdateLocation(URL bundleUpdateLocation) {
    _bundleManifest.setBundleUpdateLocation(bundleUpdateLocation);
  }

  public DynamicImportPackage getDynamicImportPackage() {
    return _bundleManifest.getDynamicImportPackage();
  }

  public ExportPackage getExportPackage() {
    return _bundleManifest.getExportPackage();
  }

  public FragmentHost getFragmentHost() {
    return _bundleManifest.getFragmentHost();
  }

  public ImportBundle getImportBundle() {
    return _bundleManifest.getImportBundle();
  }

  public ImportLibrary getImportLibrary() {
    return _bundleManifest.getImportLibrary();
  }

  public ImportPackage getImportPackage() {
    return _bundleManifest.getImportPackage();
  }

  public String getModuleScope() {
    return _bundleManifest.getModuleScope();
  }

  public void setModuleScope(String moduleScope) {
    _bundleManifest.setModuleScope(moduleScope);
  }

  public String getModuleType() {
    return _bundleManifest.getModuleType();
  }

  public void setModuleType(String moduleType) {
    _bundleManifest.setModuleType(moduleType);
  }

  public RequireBundle getRequireBundle() {
    return _bundleManifest.getRequireBundle();
  }

  public Version getBundleVersion() {
    return _bundleManifest.getBundleVersion();
  }

  public void setBundleVersion(Version bundleVersion) {
    _bundleManifest.setBundleVersion(bundleVersion);
  }

  public String getHeader(String name) {
    return _bundleManifest.getHeader(name);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public List<String> getHeaders() {

    // create the result list
    List<String> result = new LinkedList<String>();

    // get the enumeration
    Enumeration<String> enumeration = _bundleManifest.toDictionary().keys();

    // fill the result list
    while (enumeration.hasMoreElements()) {
      result.add((String) enumeration.nextElement());
    }

    // return the result
    return result;
  }

  /**
   * {@inheritDoc}
   */
  public void setHeader(String name, String value) {
    _bundleManifest.setHeader(name, value);
  }

  public Dictionary<String, String> toDictionary() {
    return _bundleManifest.toDictionary();
  }

  public void write(Writer writer) throws IOException {
    _bundleManifest.write(writer);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Set<String> getTransitiveClosure() {

    if (_transitiveClosure == null) {
      _transitiveClosure = new HashSet<String>();
    }

    return _transitiveClosure;
  }

  /**
   * <p>
   * </p>
   */
  public void finish() {

    if (_transitiveClosure != null) {
      
      //
      StringBuilder builder = new StringBuilder();
      //
      for (Iterator<String> iterator = _transitiveClosure.iterator(); iterator.hasNext();) {

        //
        builder.append(iterator.next());

        //
        if (iterator.hasNext()) {
          builder.append(",");
        }
      }
      //
      setHeader("TRANSITIVE-CLOSURE", builder.toString());
    }
  }
}

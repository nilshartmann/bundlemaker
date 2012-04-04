package org.bundlemaker.core.osgi.manifest;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.core.analysis.ArtifactType;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.visitors.DuplicatePackagesVisitor;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.osgi.utils.ArtifactUtils;
import org.bundlemaker.core.osgi.utils.ManifestUtils;
import org.bundlemaker.core.util.collections.GenericCache;
import org.eclipse.core.runtime.Assert;
import org.osgi.framework.Constants;
import org.osgi.framework.Version;

import com.springsource.util.osgi.VersionRange;
import com.springsource.util.osgi.manifest.ExportedPackage;
import com.springsource.util.osgi.manifest.ImportedPackage;
import com.springsource.util.osgi.manifest.RequiredBundle;
import com.springsource.util.osgi.manifest.RequiredBundle.Visibility;
import com.springsource.util.osgi.manifest.Resolution;
import com.springsource.util.osgi.manifest.parse.HeaderDeclaration;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DefaultManifestCreator extends AbstractManifestCreator {

  /** - */
  private DuplicatePackagesVisitor _duplicatePackagesVisitor;

  // TODO
  boolean                          _specifyBundleNameIfMultipleExporterExist = true;

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onCreateManifest() {

    setBundleManifestVersion();
    setBundleSymbolicName();
    setBundleName();
    setBundleVersion();
    setFragmentHost();

    setEclipseExtensibleAPI();

    setExportPackage();
    setImportPackageAndRequireBundle();

    importExportedPackages();

    setSpiProviderHeader();

    setTransitiveClosure();

    copyOriginalManifestHeader();
    copyTemplateManifestHeader();
  }

  /**
   * <p>
   * Determines if the specified package should be imported using Import-Package.
   * </p>
   * 
   * @param packageName
   *          The package name that is required by the bundle
   * @param exportingModules
   *          the modules that the required package provide
   * @return true if the package should be imported or false if a Require-Bundle header should be added to the Manifest
   */
  protected boolean useImportPackage(String packageName, List<IModuleArtifact> exportingModules) {
    switch (getManifestPreferences().getDependencyStyle()) {
    case STRICT_IMPORT_PACKAGE:
      // always use Import-Package
      return true;
    case STRICT_REQUIRE_BUNDLE:
      // force Require-Bundle
      return false;
    case PREFER_IMPORT_PACKAGE:
      // use import-package if at most one exporter exists, otherwise use
      // Require-Bundle
      return exportingModules.size() <= 1;
    default:
      throw new IllegalArgumentException("Unknown DependencyStyle enum value '"
          + getManifestPreferences().getDependencyStyle() + "'");
    }
  }

  /**
   * <p>
   * Determine if the reference to the given package should be skipped, i.e. if it should <b>not</b> be added to
   * manifest via Import-Package or Require-Bundle at all.
   * </p>
   * 
   * <p>
   * This implementation does <b>not</b> skip any references but keeps them all. Override this method to provide
   * different behavior, for example to skip references to packages from the boot classloader (e.g. sun.*)
   * 
   * @param packageName
   *          The package name that is required by the bundle
   * @param value
   *          modules that the required package provide
   * @return true to skip the reference (don't add it to the manifest) or true to include it via Import-Package or
   *         Require-Bundle)
   */
  protected boolean skipReferencedPackage(String packageName, List<IModuleArtifact> exportingModules) {
    return false;
  }

  /**
   * <p>
   * Sets the value of the <code>Bundle-ManifestVersion</code> header to '2'.
   * </p>
   */
  protected void setBundleManifestVersion() {
    getBundleManifest().setBundleManifestVersion(2);
  }

  /**
   * <p>
   * Sets the bundle's symbolic name to the name of the associated {@link IResourceModule}.
   * </p>
   */
  protected void setBundleSymbolicName() {
    getBundleManifest().getBundleSymbolicName().setSymbolicName(getResourceModule().getModuleIdentifier().getName());
  }

  /**
   * <p>
   * Sets the bundle's name to the name of the associated {@link IResourceModule}.
   * </p>
   */
  protected void setBundleName() {
    getBundleManifest().setBundleName(getResourceModule().getModuleIdentifier().getName());
  }

  /**
   * <p>
   * Sets the bundle's version to the version of the associated {@link IResourceModule}, or '0.0.0' if the module's
   * version is not a valid OSGi version.
   * </p>
   */
  protected void setBundleVersion() {
    if (ManifestUtils.isValidOSGiVersion(getResourceModule().getModuleIdentifier().getVersion())) {
      getBundleManifest().setBundleVersion(new Version(getResourceModule().getModuleIdentifier().getVersion()));
    } else {
      getBundleManifest().setBundleVersion(new Version("0.0.0"));
    }
  }

  /**
   * <p>
   * Sets the fragment host for the bundle if the associated module is tagged as a fragment module.
   * </p>
   */
  protected void setFragmentHost() {
    if (ManifestUtils.isFragment(getResourceModule())) {
      IModule hostModule = ManifestUtils.getFragmentHost(getResourceModule());
      if (hostModule != null) {
        getBundleManifest().getFragmentHost().setBundleSymbolicName(hostModule.getModuleIdentifier().getName());
      } else {
        // TODO
      }
    }
  }

  /**
   * <p>
   * Sets the header 'Eclipse-ExtensibleAPI' to 'true'.
   * </p>
   */
  protected void setEclipseExtensibleAPI() {
    if (!ManifestUtils.isFragment(getResourceModule())) {
      getBundleManifest().setHeader("Eclipse-ExtensibleAPI", "true");
    }
  }

  /**
   * <p>
   * Copies all headers from the original manifest except the headers listed in
   * {@link IManifestConstants#ORIGINAL_HEADERS_NOT_TO_COPY}.
   * </p>
   */
  protected void copyOriginalManifestHeader() {
    for (String key : ManifestUtils.getHeaders(getOriginalManifest())) {
      if (!containsIgnoreCase(key, IManifestConstants.ORIGINAL_HEADERS_NOT_TO_COPY)) {
        getBundleManifest().setHeader(key, getOriginalManifest().getHeader(key));
      }

    }
  }

  /**
   * <p>
   * Copies all headers from the original manifest except the headers listed in
   * {@link IManifestConstants#TEMPLATE_HEADERS_NOT_TO_COPY}.
   * </p>
   */
  protected void copyTemplateManifestHeader() {
    for (String key : ManifestUtils.getHeaders(getManifestTemplate())) {
      if (!containsIgnoreCase(key, IManifestConstants.TEMPLATE_HEADERS_NOT_TO_COPY)) {
        getBundleManifest().setHeader(key, getManifestTemplate().getHeader(key));
      }

    }
  }

  /**
   * <p>
   * </p>
   */
  protected void setExportPackage() {

    // iterate over all the contained packages
    for (IPackageArtifact packageArtifact : ArtifactUtils.getAllChildPackages(getModuleArtifact())) {

      // only export packages that contain types - do NOT export the
      // default package
      if (packageArtifact.containsTypes() && packageArtifact.getQualifiedName().trim().length() > 0) {

        //
        if (hasMostSpecificPackageTemplate(IManifestConstants.HEADER_EXCLUDED_EXPORTS,
            packageArtifact.getQualifiedName())) {
          continue;
        }

        //
        ExportedPackage exportedPackage = getBundleManifest().getExportPackage().addExportedPackage(
            packageArtifact.getQualifiedName());

        // RULE: set export package version
        if (exportedPackage.getVersion().toString() != getResourceModule().getModuleIdentifier().getVersion()
            && !getManifestPreferences().noExportPackageVersion()
            && ManifestUtils.isValidOSGiVersion(getResourceModule().getModuleIdentifier().getVersion())) {

          //
          exportedPackage.setVersion(new Version(getResourceModule().getModuleIdentifier().getVersion()));
        }

        // rule "set attributes and directives from template manifest"
        if (hasMostSpecificPackageTemplate(IManifestConstants.HEADER_EXPORT_TEMPLATE, exportedPackage.getPackageName())) {

          HeaderDeclaration header = getMostSpecificPackageTemplate(IManifestConstants.HEADER_EXPORT_TEMPLATE,
              exportedPackage.getPackageName());
          exportedPackage.getAttributes().putAll(header.getAttributes());
          exportedPackage.getDirectives().putAll(header.getDirectives());
        }
      }
    }
  }

  /**
   * <p>
   * </p>
   * 
   */
  protected void setImportPackageAndRequireBundle() {

    // step 1: get the map of all required packages (with the associated
    // modules)
    GenericCache<String, List<IModuleArtifact>> requiredPackages = createPackageToExportingModulesCache();

    //
    List<IModule> requiredBundles = new LinkedList<IModule>();

    List<Entry<String, List<IModuleArtifact>>> entrySet = new LinkedList<Map.Entry<String, List<IModuleArtifact>>>(
        requiredPackages.entrySet());

    Collections.sort(entrySet, new Comparator<Entry<String, List<IModuleArtifact>>>() {

      @Override
      public int compare(Entry<String, List<IModuleArtifact>> arg0, Entry<String, List<IModuleArtifact>> arg1) {
        return arg0.getKey().compareTo(arg1.getKey());
      }

    });

    // step 2: iterate over all the packages
    for (Entry<String, List<IModuleArtifact>> packageEntry : entrySet) {

      // Check if this reference really should be added to the manifest
      if (skipReferencedPackage(packageEntry.getKey(), packageEntry.getValue())) {
        continue;

      }

      // step 2a: if the package is contained in exactly one module, we
      // can use 'Import-Package'
      if (useImportPackage(packageEntry.getKey(), packageEntry.getValue())) {

        //
        importWithImportPackage(packageEntry.getKey(), packageEntry.getValue());
      }

      // step 2b: if we have a split package, we have to use
      // 'Require-Bundle' instead
      else {

        //
        importWithRequiredBundle(packageEntry.getKey(), packageEntry.getValue(), requiredBundles);
      }
    }

    //
    for (ImportedPackage importedPackage : getManifestTemplate().getImportPackage().getImportedPackages()) {
      if (!containsImportedPackage(importedPackage.getPackageName())) {
        getBundleManifest().getImportPackage().getImportedPackages().add(importedPackage);
      }
    }

    //
    for (RequiredBundle requiredBundle : getManifestTemplate().getRequireBundle().getRequiredBundles()) {
      if (!containsBundle(requiredBundle.getBundleSymbolicName())) {
        getBundleManifest().getRequireBundle().getRequiredBundles().add(requiredBundle);
      }
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param moduleArtifacts
   * @param packageName
   */
  protected void importWithImportPackage(String packageName, List<IModuleArtifact> moduleArtifacts) {

    Assert.isNotNull(packageName);
    Assert.isNotNull(moduleArtifacts);

    //
    if (moduleArtifacts.size() > 1) {
      System.out.println(" ********************************** ");
      System.out.println();
      System.out.println(" Split-packages can not be imported with Import-Package! " + packageName + " : "
          + moduleArtifacts);
      System.out.println();
      System.out.println(" ********************************** ");
    }

    // Rule: filter self-hosted packages
    for (IModuleArtifact moduleArtifact : moduleArtifacts) {

      if (moduleArtifact.getAssociatedModule() != null
          && (moduleArtifact.getAssociatedModule().equals(getResourceModule()) || ManifestUtils
              .isHostForResourceModule(moduleArtifact.getAssociatedModule(), getResourceModule()))) {

        // TODO: Error?
        System.out.println(" **** filter self-hosted packages: " + packageName + " : " + moduleArtifacts);
        return;
      }
    }

    // Rule: filter excluded imports
    if (hasMostSpecificPackageTemplate(IManifestConstants.HEADER_EXCLUDED_IMPORTS, packageName)) {
      return;
    }

    // Rule: import the required package
    ImportedPackage importedPackage = getBundleManifest().getImportPackage().addImportedPackage(packageName);

    // get the first moduleArtifact
    IModuleArtifact moduleArtifact = moduleArtifacts.get(0);

    // Rule: add Bundle-SymbolicName to imported package if multiple
    // exporters exist
    if (moduleArtifact.getAssociatedModule() != null) {
      Collection<IPackageArtifact> duplicatePackageProvider = getDuplicatePackageProvider(packageName);

      if (duplicatePackageProvider != null) {
        int hostModules = 0;

        for (IPackageArtifact iPackageArtifact : duplicatePackageProvider) {
          IBundleMakerArtifact exportingModuleArtifact = iPackageArtifact.getParent(ArtifactType.Module);

          IModule associatedModule = ((IModuleArtifact) exportingModuleArtifact).getAssociatedModule();

          if (associatedModule == null
              || !associatedModule.getUserAttributes().containsKey(IManifestConstants.OSGI_FRAGMENT_HOST)) {
            hostModules++;
          }
        }

        if (hostModules > 1 && _specifyBundleNameIfMultipleExporterExist) {

          // get the module
          IModule module = moduleArtifact.getAssociatedModule();

          // get the module name
          String moduleName = getModularizedSystem().getExecutionEnvironment().equals(module) ? Constants.SYSTEM_BUNDLE_SYMBOLICNAME
              : module.getModuleIdentifier().getName();

          // set the module name
          importedPackage.setBundleSymbolicName(moduleName);
        }
      }
    }

    // Rule: set missing types 'optional'
    // TODO "<< Missing Types >>"
    if (moduleArtifact.getName().equals("<< Missing Types >>")
        || forceOptionalImportPackage(packageName, moduleArtifacts)) {
      importedPackage.setResolution(Resolution.OPTIONAL);
    }

    // Rule: add templates attributes and parameter
    HeaderDeclaration headerDeclaration = getMostSpecificPackageTemplate(IManifestConstants.HEADER_IMPORT_TEMPLATE,
        packageName);
    if (headerDeclaration != null) {
      importedPackage.getAttributes().putAll(headerDeclaration.getAttributes());
      importedPackage.getDirectives().putAll(headerDeclaration.getDirectives());
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param packageName
   * @param moduleArtifacts
   * @return
   */
  protected boolean forceOptionalImportPackage(String packageName, List<IModuleArtifact> moduleArtifacts) {
    return false;
  }

  protected Collection<IPackageArtifact> getDuplicatePackageProvider(String packageName) {

    if (_duplicatePackagesVisitor == null) {
      _duplicatePackagesVisitor = new DuplicatePackagesVisitor();
      getRootArtifact().accept(_duplicatePackagesVisitor);
    }

    return _duplicatePackagesVisitor.getDuplicatePackageProvider(packageName);
  }

  /**
   * <p>
   * </p>
   * 
   * @param packageName
   * @param modules
   * @param requiredBundles
   */
  protected void importWithRequiredBundle(String packageName, List<IModuleArtifact> modules,
      List<IModule> requiredBundles) {

    for (IModuleArtifact exportingModuleArtifact : modules) {

      // rule: filter 'self-requires'
      if (exportingModuleArtifact.isVirtual()) {
        continue;
      }

      // get the module
      IModule exportingModule = exportingModuleArtifact.getAssociatedModule();

      // rule: filter 'self-requires'
      if (exportingModule.equals(getModuleArtifact().getAssociatedModule())) {
        continue;
      }

      // get the host module
      IModule exportingHostModule = ManifestUtils.getFragmentHost(exportingModule);

      // filter 'self-requires'
      if (exportingHostModule.equals(getModuleArtifact().getAssociatedModule())) {
        continue;
      }

      //
      if (requiredBundles.contains(exportingHostModule)) {
        continue;
      }

      if (isExcludedRequiredBundle(exportingHostModule)) {
        continue;
      }

      if (ManifestUtils.isHostForResourceModule(exportingModuleArtifact.getAssociatedModule(), getResourceModule())) {
        continue;
      }

      // create a required bundle
      String symbolicName = getModularizedSystem().getExecutionEnvironment().equals(exportingHostModule) ? Constants.SYSTEM_BUNDLE_SYMBOLICNAME
          : exportingHostModule.getModuleIdentifier().getName();

      //
      RequiredBundle requiredBundle = getBundleManifest().getRequireBundle().addRequiredBundle(symbolicName);

      // set a version range if multiple versions exist
      if (getModularizedSystem().getModules(exportingHostModule.getModuleIdentifier().getName()).size() > 1) {

        requiredBundle.setBundleVersion(new VersionRange("[" + exportingHostModule.getModuleIdentifier().getVersion()
            + "," + exportingHostModule.getModuleIdentifier().getVersion() + "]"));
      }

      // set the visibility to reexport
      if (getManifestPreferences().isReexportRequiredBundles()) {
        requiredBundle.setVisibility(Visibility.REEXPORT);
      }

      // Rule: add templates attributes and parameter
      HeaderDeclaration headerDeclaration = getMostSpecificPackageTemplate(
          IManifestConstants.HEADER_REQUIRE_BUNDLE_TEMPLATE, exportingHostModule.getModuleIdentifier().getName());
      if (headerDeclaration != null) {
        requiredBundle.getAttributes().putAll(headerDeclaration.getAttributes());
        requiredBundle.getDirectives().putAll(headerDeclaration.getDirectives());
      }

      // add to memory
      requiredBundles.add(exportingHostModule);
    }
  }

  /**
   * <p>
   * Add a list of all transitive required bundles.
   * </p>
   */
  protected void setTransitiveClosure() {

    List<String> transitiveClosure = new LinkedList<String>();
    //
    for (IModule module : getModularizedSystem().getTransitiveReferencedModules(getResourceModule())
        .getReferencedModules()) {

      //
      if (!containsBundle(module.getModuleIdentifier().getName())
          && !module.equals(getModularizedSystem().getExecutionEnvironment())) {

        //
        transitiveClosure.add(module.getModuleIdentifier().getName());
      }
    }

    //
    if (!transitiveClosure.isEmpty()) {

      Collections.sort(transitiveClosure);

      //
      StringBuilder stringBuilder = new StringBuilder();
      for (Iterator<String> iterator = transitiveClosure.iterator(); iterator.hasNext();) {
        stringBuilder.append(iterator.next());
        if (iterator.hasNext()) {
          stringBuilder.append(", ");
        }
      }
      getBundleManifest().setHeader(IManifestConstants.TRANSITIVE_CLOSURE, stringBuilder.toString());
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected final GenericCache<String, List<IModuleArtifact>> createPackageToExportingModulesCache() {

    // required packages
    GenericCache<String, List<IModuleArtifact>> requiredPackages = new GenericCache<String, List<IModuleArtifact>>() {

      /** - */
      private static final long serialVersionUID = 1L;

      @Override
      protected List<IModuleArtifact> create(String key) {
        return new LinkedList<IModuleArtifact>();
      }
    };

    //
    for (IDependency dependency : getModuleArtifact().getDependencies()) {

      // TODO
      IBundleMakerArtifact bundleMakerArtifact = (IBundleMakerArtifact) dependency.getTo();

      // TODO
      IPackageArtifact packageArtifact = (IPackageArtifact) bundleMakerArtifact.getParent(ArtifactType.Package);
      IModuleArtifact moduleArtifact = (IModuleArtifact) packageArtifact.getParent(ArtifactType.Module);

      //
      List<IModuleArtifact> modules = requiredPackages.getOrCreate(packageArtifact.getQualifiedName());
      if (!modules.contains(moduleArtifact)) {
        modules.add(moduleArtifact);
      }
    }
    return requiredPackages;
  }

  /**
   * <p>
   * </p>
   * 
   * @param templateManifest
   * @param headerKey
   * @param packageName
   * @return
   */
  public boolean hasMostSpecificPackageTemplate(String headerKey, String packageName) {

    //
    return getMostSpecificPackageTemplate(headerKey, packageName) != null;
  }

  /**
   * <p>
   * </p>
   */
  protected void setSpiProviderHeader() {

    // TODO
    if (getModuleArtifact().findChild(IPackageArtifact.class, "META-INF.services") != null) {
      getBundleManifest().setHeader("SPI-Provider", "*");
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param templateManifest
   * @param headerKey
   * @param packageName
   * @return
   */
  protected HeaderDeclaration getMostSpecificPackageTemplate(String headerKey, String packageName) {

    // get the import package template
    String templateHeader = getManifestTemplate().getHeader(headerKey);

    //
    List<HeaderDeclaration> packageTemplates = ManifestUtils.parseManifestValue(templateHeader);

    // get the template
    return ManifestUtils.findMostSpecificDeclaration(packageTemplates, packageName);
  }

  /**
   * <p>
   * </p>
   * 
   * @param templateManifest
   * @param bundleSymbolicName
   * @return
   */
  protected boolean isExcludedRequiredBundle(IModule module) {

    //
    Assert.isNotNull(module);

    //
    if (getManifestTemplate() == null) {
      return false;
    }

    // get the import package template
    String templateHeader = getManifestTemplate().getHeader(
        IManifestConstants.HEADER_EXCLUDED_REQUIRED_BUNDLES_TEMPLATE);

    if (templateHeader == null) {
      return false;
    }

    //
    List<HeaderDeclaration> excludedRequiredBundleTemplates = ManifestUtils.parseManifestValue(templateHeader);

    // get the template
    HeaderDeclaration declaration = ManifestUtils.findMostSpecificDeclaration(excludedRequiredBundleTemplates, module
        .getModuleIdentifier().getName());

    // return the result
    return declaration != null;
  }

  /**
   * <p>
   * </p>
   */
  protected void importExportedPackages() {

    //
    for (ExportedPackage exportedPackage : getBundleManifest().getExportPackage().getExportedPackages()) {
      //
      if (!containsImportedPackage(exportedPackage.getPackageName())) {
        // TODO: Attributes?
        getBundleManifest().getImportPackage().addImportedPackage(exportedPackage.getPackageName());
      }
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param symbolicName
   * @param requiredBundles
   * @return
   */
  private boolean containsBundle(String requiredBundleName) {

    //
    for (RequiredBundle alreadyRequiredBundle : getBundleManifest().getRequireBundle().getRequiredBundles()) {

      //
      if (alreadyRequiredBundle.getBundleSymbolicName().equalsIgnoreCase(requiredBundleName)) {
        return true;
      }
    }

    //
    return false;
  }

  /**
   * <p>
   * </p>
   * 
   * @param importedPackage
   * @param importedPackages
   * @return
   */
  private boolean containsImportedPackage(String importedPackageName) {

    //
    for (ImportedPackage alreadyImportedPackage : getBundleManifest().getImportPackage().getImportedPackages()) {

      //
      if (alreadyImportedPackage.getPackageName().equalsIgnoreCase(importedPackageName)) {
        return true;
      }
    }

    //
    return false;
  }

  /**
   * <p>
   * </p>
   * 
   * @param value
   * @param values
   * @return
   */
  private boolean containsIgnoreCase(String value, Collection<String> values) {

    //
    if (value == null) {
      return false;
    }

    //
    for (String v : values) {

      //
      if (value.equalsIgnoreCase(v)) {
        return true;
      }
    }

    //
    return false;
  }
}

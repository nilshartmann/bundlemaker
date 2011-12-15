package org.bundlemaker.core.osgi.manifest;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.core.analysis.IArtifactTreeVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.visitors.DuplicatePackagesVisitor;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.osgi.manifest.IManifestPreferences.DependencyStyle;
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

  private DuplicatePackagesVisitor _duplicatePackagesVisitor;
  
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

    setTransitiveClosure();

    copyOriginalManifestHeader();
    copyTemplateManifestHeader();
  }

  /**
   * <p>
   * Overwrite this method to force an 'Import-Package' for the given package dependency.
   * </p>
   * 
   * @param packageName
   * @param exportingModules
   * @return
   */
  protected boolean forceImportPackage(String packageName, List<IModuleArtifact> exportingModules) {
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

      // only export packages that contain types - do NOT export the default package
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

    // step 1: get the map of all required packages (with the associated modules)
    GenericCache<String, List<IModuleArtifact>> requiredPackages = createPackageToExportingModulesCache();

    //
    List<IModule> requiredBundles = new LinkedList<IModule>();

    // step 2: iterate over all the packages
    for (Entry<String, List<IModuleArtifact>> packageEntry : requiredPackages.entrySet()) {

      // step 2a: if the package is contained in exactly one module, we can use 'Import-Package'
      if (forceImportPackage(packageEntry.getKey(), packageEntry.getValue())
          || DependencyStyle.STRICT_IMPORT_PACKAGE.equals(getManifestPreferences().getDependencyStyle())
          || (packageEntry.getValue().size() == 1 && !DependencyStyle.STRICT_REQUIRE_BUNDLE
              .equals(getManifestPreferences().getDependencyStyle()))) {

        //
        importWithImportPackage(packageEntry.getKey(), packageEntry.getValue());
      }

      // step 2b: if we have a split package, we have to use 'Require-Bundle' instead
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

    // get the first moduleArtifact
    IModuleArtifact moduleArtifact = moduleArtifacts.get(0);

    // Rule: filter self hosted packages
    if (moduleArtifact.getAssociatedModule() != null
        && (moduleArtifact.getAssociatedModule().equals(getResourceModule()) || ManifestUtils.isHostForResourceModule(
            moduleArtifact.getAssociatedModule(), getResourceModule()))) {
      return;
    }

    // Rule: filter excluded imports
    if (hasMostSpecificPackageTemplate(IManifestConstants.HEADER_EXCLUDED_IMPORTS, packageName)) {
      return;
    }

    // Rule: import the required package
    ImportedPackage importedPackage = getBundleManifest().getImportPackage().addImportedPackage(packageName);

    // Add Bundle-SymbolicName to imported package
    if (moduleArtifact.getAssociatedModule() != null) {
      Collection<IPackageArtifact> duplicatePackageProvider = getDuplicatePackageProvider(packageName);
      if (duplicatePackageProvider != null) {
        String moduleName = moduleArtifact.getAssociatedModule().getModuleIdentifier().getName();
        
        StringBuilder builder = new StringBuilder();
        for (IPackageArtifact iPackageArtifact : duplicatePackageProvider) {
          if (builder.length()>0) {
            builder.append(',');
          }
          builder.append(iPackageArtifact.getParent(ArtifactType.Module).getName());
        }

        String msg = String.format("Bundle '%s' imports duplicate package '%s' (exported by %s). Choosing exporter bundle '%s'",
            getResourceModule().getModuleIdentifier(),
            packageName,
            builder, moduleName);
        
        System.out.println(msg);
        importedPackage.setBundleSymbolicName(moduleName);
      }
    }

    // Rule: set missing types 'optional'
    if (moduleArtifact.getName().equals("<< Missing Types >>")) {
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

  protected Collection<IPackageArtifact> getDuplicatePackageProvider(String packageName) {

    if (_duplicatePackagesVisitor == null) {
      _duplicatePackagesVisitor = new DuplicatePackagesVisitor();
      getRootArtifact().accept(_duplicatePackagesVisitor);

      System.out.println("Detected duplicate packages: ");
      for (String pn : _duplicatePackagesVisitor.getDuplicatePackages().keySet()) {
        System.out.println("  " + pn);
      }
      System.out.println("======================= ");
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
   * @param exportedPackageName
   * @return
   */
  private boolean containsExportedPackage(String exportedPackageName) {

    //
    for (ExportedPackage alreadyExportedPackage : getBundleManifest().getExportPackage().getExportedPackages()) {

      //
      if (alreadyExportedPackage.getPackageName().equalsIgnoreCase(exportedPackageName)) {
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

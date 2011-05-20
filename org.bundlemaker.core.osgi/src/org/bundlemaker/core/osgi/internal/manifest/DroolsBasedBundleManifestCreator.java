package org.bundlemaker.core.osgi.internal.manifest;

import java.util.Map;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.osgi.manifest.IBundleManifestCreator;
import org.bundlemaker.core.osgi.manifest.IManifestPreferences;
import org.bundlemaker.core.osgi.utils.ManifestUtils;
import org.bundlemaker.core.util.StopWatch;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseConfiguration;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.ResourceType;
import org.drools.runtime.StatefulKnowledgeSession;
import org.eclipse.core.runtime.Assert;

import com.springsource.bundlor.util.BundleManifestUtils;
import com.springsource.util.osgi.manifest.BundleManifest;
import com.springsource.util.osgi.manifest.BundleManifestFactory;
import com.springsource.util.parser.manifest.ManifestContents;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DroolsBasedBundleManifestCreator implements IBundleManifestCreator {

  private static final String   MANIFEST_PER_ENTRY_ATTRIBUTES_NAME = "Name";

  /** - */
  private DroolsServiceAccessor _serviceAccessor;

  /** - */
  private KnowledgeBase         _knowledgeBase;

  /**
   * <p>
   * Creates a new instance of type {@link DroolsBasedBundleManifestCreator}.
   * </p>
   * 
   * @param serviceAccessor
   */
  public DroolsBasedBundleManifestCreator() {

    _serviceAccessor = new DroolsServiceAccessor();

    // init
    KnowledgeBaseConfiguration kbaseConfig = _serviceAccessor.getKnowledgeBaseFactoryService()
        .newKnowledgeBaseConfiguration(null, this.getClass().getClassLoader());

    _knowledgeBase = _serviceAccessor.getKnowledgeBaseFactoryService().newKnowledgeBase(kbaseConfig);

    KnowledgeBuilder kbuilder = _serviceAccessor.getKnowledgeBuilderFactoryService()
        .newKnowledgeBuilder(_knowledgeBase);

    //
    kbuilder.add(
        _serviceAccessor.getResourceFactoryService().newClassPathResource(
            "org/bundlemaker/core/osgi/manifest/rules/rules-exportPackage.drl", this.getClass().getClassLoader()),
        ResourceType.DRL);
    if (kbuilder.hasErrors()) {
      System.err.println(kbuilder.getErrors().toString());
    }

    //
    kbuilder.add(
        _serviceAccessor.getResourceFactoryService().newClassPathResource(
            "org/bundlemaker/core/osgi/manifest/rules/rules-importPackage.drl", this.getClass().getClassLoader()),
        ResourceType.DRL);
    if (kbuilder.hasErrors()) {
      System.err.println(kbuilder.getErrors().toString());
    }

    //
    kbuilder.add(
        _serviceAccessor.getResourceFactoryService().newClassPathResource(
            "org/bundlemaker/core/osgi/manifest/rules/rules-general.drl", this.getClass().getClassLoader()),
        ResourceType.DRL);
    if (kbuilder.hasErrors()) {
      System.err.println(kbuilder.getErrors().toString());
    }

    //
    _knowledgeBase.addKnowledgePackages(kbuilder.getKnowledgePackages());
  }

  public ManifestContents createManifest(IModularizedSystem modularizedSystem, IResourceModule resourceModule,
      ManifestContents manifestTemplate, ManifestContents originalManifest, IManifestPreferences manifestPreferences) {

    Assert.isNotNull(modularizedSystem);
    Assert.isNotNull(resourceModule);
    Assert.isNotNull(manifestTemplate);
    Assert.isNotNull(originalManifest);
    Assert.isNotNull(manifestPreferences);

    System.out.println("createManifest - start");
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();

    // create a new bundle manifest
    BundleManifest result = BundleManifestFactory.createBundleManifest();

    // execute the knowledge base
    StatefulKnowledgeSession ksession = _knowledgeBase.newStatefulKnowledgeSession();

    // set 'global' values
    ksession.setGlobal("_bundleManifest", new IdentifiableBundleManifest(result,
        IdentifiableBundleManifest.BUNDLE_MANIFEST));
    ksession.setGlobal("_originalManifest",
        new IdentifiableBundleManifest(BundleManifestUtils.createBundleManifest(originalManifest),
            IdentifiableBundleManifest.ORIGINAL_MANIFEST));
    ksession.setGlobal("_manifestTemplate",
        new IdentifiableBundleManifest(BundleManifestUtils.createBundleManifest(manifestTemplate),
            IdentifiableBundleManifest.MANIFEST_TEMPLATE));

    // ksession.setGlobal("_resourceModule", new DelegatingResourceModule(resourceModule));
    ksession.setGlobal("_resourceModule", resourceModule);
    ksession.setGlobal("_modularizedSystem", modularizedSystem);
    ksession.setGlobal("_manifestPreferences", manifestPreferences);
    ksession.setGlobal("_referencesCache",
        new ReferencesCache(modularizedSystem, resourceModule, manifestPreferences.isSourceManifest(),
            manifestPreferences.isSourceManifest()));

    ksession.fireAllRules();

    stopWatch.stop();
    System.out.println("createManifest - done");
    System.out.println(stopWatch.getElapsedTime());

    // copy all 'per-entry' attributes from the manifest template to the final manifest
    ManifestContents manifestContents = ManifestUtils.toManifestContents(result);

    // copy all 'per-entry' attributes from the template
    for (String sectionName : manifestTemplate.getSectionNames()) {

      // get the section attributes
      Map<String, String> sectionAttributes = manifestTemplate.getAttributesForSection(sectionName);

      // copy the section attributes
      for (String key : sectionAttributes.keySet()) {
        if (!key.equalsIgnoreCase(MANIFEST_PER_ENTRY_ATTRIBUTES_NAME)) {
          manifestContents.getAttributesForSection(sectionName).put(key, sectionAttributes.get(key));
        }
      }
    }

    // get the new manifest contents
    return manifestContents;
  }
}

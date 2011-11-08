package org.bundlemaker.core.itest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependencyModel;
import org.bundlemaker.analysis.transformations.ArtifactTransformer;
import org.bundlemaker.analysis.transformations.BundlePathName;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

/**
 * TODO: Als ITransformer implementieren
 * <p>
 * </p>
 * 
 * @author Nils Hartmann, P217194
 * 
 */
public class ArtifactTransformationProcessor {

  private final String           CLAZZ         = "class";

  private final String           IS_PRODUCTION = "isProduction";

  private final IDependencyModel _dependencyModel;

  public ArtifactTransformationProcessor(IDependencyModel dependencyModel) {
    _dependencyModel = dependencyModel;
  }

  private Collection<ArtifactTransformer> getArtifactTransformers() throws CoreException {

    final List<ArtifactTransformer> result = new LinkedList<ArtifactTransformer>();

    IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
    IExtensionPoint extensionPoint = extensionRegistry.getExtensionPoint("dependency-core.transformationModel");
    if (extensionPoint != null) {
      for (IExtension extension : extensionPoint.getExtensions()) {
        for (IConfigurationElement element : extension.getConfigurationElements()) {
          String production = element.getAttribute(IS_PRODUCTION);
          Boolean isProduction = Boolean.valueOf(production);
          if (isProduction) {
            ArtifactTransformer artifactTransformer = (ArtifactTransformer) element.createExecutableExtension(CLAZZ);
            result.add(artifactTransformer);
          }
        }
      }
    }
    System.out.printf("Transformer: %s%n", result);
    return result;
  }

  public void performTransformation() throws CoreException {
    IArtifact root = _dependencyModel.getRoot();
    Collection<IArtifact> leafs = root.getLeafs();

    Collection<ArtifactTransformer> artifactTransformers = getArtifactTransformers();

    // Initialize Transformer
    for (ArtifactTransformer artifactTransformer : artifactTransformers) {
      System.out.printf("Initialize Transformer '%s'%n", artifactTransformer);
      artifactTransformer.initialize(_dependencyModel);
    }

    System.out.printf("Running ArtifactTransformers on %d artifacts%n", leafs.size());

    System.setProperty("dont-reinitialize", "true");

    final long start = System.currentTimeMillis();
    int movedTypes = 0;
    for (IArtifact artifact : leafs) {

      IArtifact osgiBundle = artifact.getParent(ArtifactType.Module);
      if (osgiBundle != null) {

        BundlePathName bundlePathName = getBundlePathName(osgiBundle);

        for (ArtifactTransformer artifactTransformer : artifactTransformers) {
          String oldQualifiedName = artifact.getQualifiedName();
          BundlePathName newBundlePathName = artifactTransformer.getArtifactBundlePath(artifact, bundlePathName);

          if (!bundlePathName.equals(newBundlePathName)) {
            System.out.printf("  %s '%s' verschoben von %n  '%s' nach %n  '%s'. %n  Neuer QualifiedName: '%s'%n", //
                artifact.getType(), //
                oldQualifiedName, //
                bundlePathName, //
                newBundlePathName, //
                artifact.getQualifiedName());
            dumpArtifact(artifact, 2);

            movedTypes++;
            moveArtifact(artifact, newBundlePathName, _dependencyModel);

          }
        }
      }
    }

    System.setProperty("dont-reinitialize", "false");

    System.out.printf(
        "%d ArtifactTransformers executed on %d artifacts in %d sec.%n  %d Types moved to new location.%n",
        artifactTransformers.size(), leafs.size(), ((System.currentTimeMillis() - start) / 1000), movedTypes);
  }

  private static void dumpArtifact(IArtifact artifact, int level) {
    if (artifact == null) {
      return;
    }
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < level; i++) {
      builder.append("  ");
    }

    System.out.println(builder.toString() + artifact.getType() + " : " + artifact.getName() + "("
        + artifact.getQualifiedName() + ")");

    dumpArtifact(artifact.getParent(), level + 1);

  }

  public static void moveArtifact(final IArtifact typeArtifact, final BundlePathName newBundlePath,
      final IDependencyModel model) {

    List<String> classificationPath = newBundlePath.getClassificationPath();
    IBundleMakerArtifact currentArtifact = (IBundleMakerArtifact) model.getRoot();

    StringBuilder qualifiedName = new StringBuilder();

    for (String string : classificationPath) {
      if (qualifiedName.length() > 0) {
        qualifiedName.append('/');
      }

      qualifiedName.append(string);

      IBundleMakerArtifact child = (IBundleMakerArtifact) currentArtifact.getChild(string);
      if (child == null) {
        System.out.printf("  Erzeuge neue Gruppe: '%s' (%s) an '%s' %n", string, qualifiedName.toString(),
            currentArtifact.getQualifiedName());
        child = (IBundleMakerArtifact) model.createArtifactContainer(string, qualifiedName.toString(), ArtifactType.Group);
        // TODO
        model.getRoot().removeArtifact(child);
        currentArtifact.addArtifact(child);
      }

      currentArtifact = child;
    }

    System.out.printf("  Ziel-Gruppe fuer Artifact '%s': '%s'%n", typeArtifact.getQualifiedName(),
        currentArtifact.getQualifiedName());

    IBundleMakerArtifact bundleArtifact = (IBundleMakerArtifact) currentArtifact.getChild(newBundlePath
        .getBundleName());
    // TODO Sicherstellen, dass bundleArtifact entweder null (neues Bundle) oder vom Typ Module ist
    if (bundleArtifact == null) {
      System.out.printf("  Erzeuge neues Bundle '%s' %n", newBundlePath.getBundleName());
      bundleArtifact = (IBundleMakerArtifact) model.createArtifactContainer(newBundlePath.getBundleName(),
          newBundlePath.getBundleName(), ArtifactType.Module);
      // TODO
      model.getRoot().removeArtifact(bundleArtifact);
      currentArtifact.addArtifact(bundleArtifact);
    }

    System.out.printf("  Verschiebe %s in Bundle %s%n", typeArtifact.getQualifiedName(),
        bundleArtifact.getQualifiedName());
    // Aus altem Modul loeschen
    typeArtifact.getParent().removeArtifact(typeArtifact);

    // In neues Modul schieben // PACKAGES ???

    IBundleMakerArtifact packageArtifact = (IBundleMakerArtifact) typeArtifact.getParent(ArtifactType.Package);
    IArtifact newPackageArtifact = bundleArtifact.getChild(packageArtifact.getQualifiedName());
    if (newPackageArtifact == null) {
      System.out.printf("  Erzeuge Package '%s' in Bundle '%s'%n", packageArtifact.getQualifiedName(),
          bundleArtifact.getQualifiedName());

      newPackageArtifact = (IBundleMakerArtifact) model.createArtifactContainer(packageArtifact.getName(),
          packageArtifact.getQualifiedName(), ArtifactType.Package);
      // TODO
      model.getRoot().removeArtifact(newPackageArtifact);
      bundleArtifact.addArtifact(newPackageArtifact);

    }
    newPackageArtifact.addArtifact(typeArtifact);

  }

  private BundlePathName getBundlePathName(IArtifact osgiBundle) {
    List<String> pathList = new ArrayList<String>();
    getBundlePath(osgiBundle, pathList);
    BundlePathName pathName = new BundlePathName(pathList);

    return pathName;
  }

  private void getBundlePath(IArtifact artifact, List<String> pathList) {
    if (artifact.getParent() == null) {
      String[] path = artifact.getQualifiedName().split("/");
      for (int i = 0; i < path.length; i++) {
        pathList.add(path[i]);
      }
      if (artifact.getType() != ArtifactType.Root) {
        System.out.println("getBundlePath( " + artifact.getName() + ") ohne Parent");
      }
    } else if (artifact.getParent().getType() == ArtifactType.Root) {
      pathList.add(artifact.getName());
    } else {
      getBundlePath(artifact.getParent(), pathList);
      pathList.add(artifact.getName());
    }
  }
}
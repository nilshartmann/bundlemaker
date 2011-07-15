package org.bundlemaker.core.osgi.internal.manifest;

import java.util.List;

import org.bundlemaker.core.osgi.utils.ManifestUtils;

import com.springsource.util.osgi.manifest.BundleManifest;
import com.springsource.util.osgi.manifest.parse.HeaderDeclaration;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DroolsManifestUtils {

  /**
   * <p>
   * </p>
   * 
   * @param templateManifest
   * @param headerKey
   * @param packageName
   * @return
   */
  public static boolean hasMostSpecificPackageTemplate(BundleManifest templateManifest, String headerKey,
      String packageName) {

    //
    return getMostSpecificPackageTemplate(templateManifest, headerKey, packageName) != null;
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
  public static HeaderDeclaration getMostSpecificPackageTemplate(BundleManifest templateManifest, String headerKey,
      String packageName) {

    // get the import package template
    String templateHeader = templateManifest.getHeader(headerKey);

    //
    List<HeaderDeclaration> packageTemplates = ManifestUtils.parseManifestValue(templateHeader);

    // get the template
    return ManifestUtils.findMostSpecificDeclaration(packageTemplates, packageName);
  }
}

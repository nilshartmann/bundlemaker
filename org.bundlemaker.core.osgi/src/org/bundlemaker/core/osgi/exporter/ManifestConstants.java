package org.bundlemaker.core.osgi.exporter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.osgi.framework.Constants;

import com.springsource.util.osgi.manifest.parse.HeaderDeclaration;

public interface ManifestConstants {

  /** - */
  public static final List<HeaderDeclaration> EMPTY_HEADERDECLARATION_LIST   = Collections.emptyList();

  /** - */
  public static final List<String>            ORIGINAL_HEADERS_NOT_TO_COPY   = Arrays.asList(new String[] {
      Constants.BUNDLE_MANIFESTVERSION, Constants.BUNDLE_SYMBOLICNAME, Constants.BUNDLE_VERSION,
      Constants.IMPORT_PACKAGE, Constants.REQUIRE_BUNDLE, Constants.EXPORT_PACKAGE });

  /** - */
  public static final List<String>            TEMPLATE_HEADERS_NOT_TO_COPY   = Arrays.asList(new String[] {
      Constants.BUNDLE_MANIFESTVERSION, Constants.BUNDLE_SYMBOLICNAME, Constants.BUNDLE_VERSION,
      Constants.IMPORT_PACKAGE, Constants.REQUIRE_BUNDLE, Constants.EXPORT_PACKAGE,
      ManifestConstants.HEADER_EXCLUDED_IMPORTS, ManifestConstants.HEADER_IMPORT_TEMPLATE,
      ManifestConstants.HEADER_EXPORT_TEMPLATE, ManifestConstants.HEADER_REQUIRE_BUNDLE_TEMPLATE });

  /** - */
  public static final String                  HEADER_EXCLUDED_IMPORTS        = "Excluded-Imports";

  /** - */
  public static final String                  HEADER_IMPORT_TEMPLATE         = "Import-Template";

  /** - */
  public static final String                  HEADER_EXPORT_TEMPLATE         = "Export-Template";

  /** - */
  public static final String                  HEADER_REQUIRE_BUNDLE_TEMPLATE = "RequiredBundle-Template";

}

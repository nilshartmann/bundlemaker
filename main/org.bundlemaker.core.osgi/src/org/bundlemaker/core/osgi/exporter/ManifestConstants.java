/*******************************************************************************
 * Copyright (c) 2011 Gerd Wuetherich (gerd@gerd-wuetherich.de).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Gerd Wuetherich (gerd@gerd-wuetherich.de) - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.osgi.exporter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.osgi.framework.Constants;

import com.springsource.util.osgi.manifest.parse.HeaderDeclaration;

public interface ManifestConstants {

  // TODO
  public static final String                  OSGI_FRAGMENT_HOST                        = "OSGI_FRAGMENT_HOST";

  /** - */
  public static final List<HeaderDeclaration> EMPTY_HEADERDECLARATION_LIST              = Collections.emptyList();

  /** - */
  public static final List<String>            ORIGINAL_HEADERS_NOT_TO_COPY              = Arrays.asList(new String[] {
      Constants.BUNDLE_MANIFESTVERSION, Constants.BUNDLE_SYMBOLICNAME, Constants.BUNDLE_VERSION,
      Constants.IMPORT_PACKAGE, Constants.REQUIRE_BUNDLE, Constants.EXPORT_PACKAGE     });

  /** - */
  public static final List<String>            TEMPLATE_HEADERS_NOT_TO_COPY              = Arrays.asList(new String[] {
      Constants.BUNDLE_MANIFESTVERSION, Constants.BUNDLE_SYMBOLICNAME, Constants.BUNDLE_VERSION,
      Constants.IMPORT_PACKAGE, Constants.REQUIRE_BUNDLE, Constants.EXPORT_PACKAGE,
      ManifestConstants.HEADER_EXCLUDED_IMPORTS, ManifestConstants.HEADER_IMPORT_TEMPLATE,
      ManifestConstants.HEADER_EXPORT_TEMPLATE, ManifestConstants.HEADER_REQUIRE_BUNDLE_TEMPLATE,
      ManifestConstants.HEADER_EXCLUDED_REQUIRED_BUNDLES_TEMPLATE                      });

  /** - */
  public static final String                  HEADER_EXCLUDED_IMPORTS                   = "Excluded-Imports";

  /** - */
  public static final String                  HEADER_IMPORT_TEMPLATE                    = "Import-Template";

  /** - */
  public static final String                  HEADER_EXPORT_TEMPLATE                    = "Export-Template";

  /** - */
  public static final String                  HEADER_REQUIRE_BUNDLE_TEMPLATE            = "RequiredBundle-Template";

  /** - */
  public static final String                  HEADER_EXCLUDED_REQUIRED_BUNDLES_TEMPLATE = "Excluded-Required-Bundles";

}

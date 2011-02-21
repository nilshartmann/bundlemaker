package org.bundlemaker.core.exporter.manifest.internal;

import java.util.Collections;
import java.util.List;

import com.springsource.util.osgi.manifest.parse.HeaderDeclaration;

public interface ManifestConstants {

	/** - */
	public static final List<HeaderDeclaration> EMPTY_HEADERDECLARATION_LIST = Collections
			.emptyList();

	/** - */
	public static final String HEADER_EXCLUDED_IMPORTS = "Excluded-Imports";

	/** - */
	public static final String HEADER_IMPORT_TEMPLATE = "Import-Template";

	/** - */
	public static final String HEADER_REQUIRE_BUNDLE_TEMPLATE = "RequiredBundle-Template";

}

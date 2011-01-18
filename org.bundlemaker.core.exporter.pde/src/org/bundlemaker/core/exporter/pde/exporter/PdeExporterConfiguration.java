package org.bundlemaker.core.exporter.pde.exporter;

import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class PdeExporterConfiguration {

	/** the key */
	public static final String KEY = PdeExporterConfiguration.class.getName();

	/** - */
	public static final String STRICT_IMPORT_PACKAGE = "STRICT_IMPORT_PACKAGE";

	/** - */
	public static final String STRICT_REQUIRE_BUNDLE = "STRICT_REQUIRE_BUNDLE";

	/** - */
	private String _dependencyDescriptionStyle = STRICT_IMPORT_PACKAGE;

	/** - */
	private boolean _useClassifcationForExportDestination;

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public boolean isUseClassifcationForExportDestination() {
		
		return _useClassifcationForExportDestination;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param value
	 */
	public void setUseClassifcationForExportDestination(boolean value) {

		_useClassifcationForExportDestination = value;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param dependencyDescriptionStyle
	 */
	public void setDependencyDescriptionStyle(String dependencyDescriptionStyle) {

		Assert.isNotNull(dependencyDescriptionStyle);
		Assert.isTrue(STRICT_IMPORT_PACKAGE.equals(dependencyDescriptionStyle)
				|| STRICT_REQUIRE_BUNDLE.equals(dependencyDescriptionStyle));

		_dependencyDescriptionStyle = dependencyDescriptionStyle;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public String getDependencyDescriptionStyle() {
		return _dependencyDescriptionStyle;
	}
}

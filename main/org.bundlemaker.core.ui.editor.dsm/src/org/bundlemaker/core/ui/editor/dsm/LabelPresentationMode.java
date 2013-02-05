package org.bundlemaker.core.ui.editor.dsm;

/**
 * Represents the presentation mode that should be used to show the labels
 *
*/
public enum LabelPresentationMode {
	
	/**
	 * Display fully qualified name
	 */
	qualifiedName,
	
	/**
	 * Display 'shortened' qualified name (shorten all segment parts but not the last one)
	 */
	shortendedQualifiedName,
	
	/**
	 * Display only the simple name
	 */
	simpleName

}

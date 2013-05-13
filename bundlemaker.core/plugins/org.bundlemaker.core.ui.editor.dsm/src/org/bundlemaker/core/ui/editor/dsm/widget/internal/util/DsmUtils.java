package org.bundlemaker.core.ui.editor.dsm.widget.internal.util;

public class DsmUtils {

	public static String getLongestString(String[][] values) {

		// create the result
		String result = "";

		// iterate over all strings
		for (int row = 0; row < values.length; row++) {
			String[] zeile = values[row];
			for (int col = 0; col < zeile.length; col++) {
				if (row != col) {
					String string = zeile[col];
					if (string != null) {

						if (result == null) {
							result = string;
						} else if (result.length() < string.length()) {
							result = string;
						}
					}
				}
			}
		}

		// return the result
		return result;
	}

	/**
   * <p>
   * Helper method that returns the longest string from the string array.
   * </p>
	 *
   * @param strings
   *          the string array
	 * @return  the longest string from the string array.
	 */
	public static String getLongestString(String[] strings) {

		// create the result
		String result = null;

		// iterate over all strings
		for (String string : strings) {
			if (result == null) {
				result = string;
			} else if (result.length() < string.length()) {
				result = string;
			}
		}

		// return the result
		return (result == null ? "" : result);
	}
}


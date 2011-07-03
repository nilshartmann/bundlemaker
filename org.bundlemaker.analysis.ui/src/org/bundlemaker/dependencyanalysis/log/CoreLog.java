package org.bundlemaker.dependencyanalysis.log;

/**
 * Liefert den Logger des Plug-ins.
 *
 */
public class CoreLog {

	/**
	 * @return  den Logger des Plug-ins.
	 */
	public static Log get() {
		return Log.getLog("dependencyanalysis");
	}
}

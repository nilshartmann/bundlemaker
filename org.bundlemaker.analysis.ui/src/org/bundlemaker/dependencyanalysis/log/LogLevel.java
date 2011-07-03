package org.bundlemaker.dependencyanalysis.log;

/**
 * Das Enum definiert die möglichen Loglevel.
 */

public enum LogLevel {

	/**
	 * Nur die Logs aus den <code>log*</code>-Methoden werden ausgegeben.
	 */
	NORMAL,

	/**
	 * Nur die Logs aus den <code>log*</code>- und den <code>debug*</code>- Methoden werden
	 * ausgegeben.
	 */

	DEBUG,

	/**
	 * Alle Logs werden ausgegeben.
	 */
	TRACE
}

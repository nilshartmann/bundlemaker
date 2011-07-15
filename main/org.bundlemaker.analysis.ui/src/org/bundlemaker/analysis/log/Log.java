package org.bundlemaker.analysis.log;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.Bundle;

/**
 * Logging API.
 *
 */
public class Log {

	/**
	 * Symbolischer Bundle-Name des loggenden Bundles.
	 */
	private final String symbolicBundleName;

	/**
	 * Eclipse Logger des loggenden Bundles.
	 */

	private final ILog log;

	/**
	 * Aktiver Log-Level.
	 */

	private LogLevel logLevel;

	/**
	 * HashMap, in der die <code>Log</code>-Logger der loggenden Bundles gecached werden.
	 */
	private static final Map<String, Log> logs = new ConcurrentHashMap<String, Log>();

	private Log(String symbolicBundleName) {
		this.symbolicBundleName = symbolicBundleName;
		this.log = Platform.getLog(Platform.getBundle(symbolicBundleName));
		this.logLevel = LogLevel.NORMAL;
	}

	/**
	 * @param   bundle  Bundle, dessen Logger zurückgegeben werden soll.
	 *
	 * @return  den Logger zum übergebenen Bundle
	 */
	public static Log getLog(Bundle bundle) {
		return getLog(bundle.getSymbolicName());
	}

	/**
	 * @param   symbolicBundleName  Symbolischer Name des Bundles, dessen Logger zurückgegeben
	 *                              werden soll.
	 *
	 * @return  den Logger zum Bundle, dessen symbolischer Name übergeben wurde
	 */

	public static Log getLog(String symbolicBundleName) {
		if (logs.containsKey(symbolicBundleName) == false) {
			logs.put(symbolicBundleName, new Log(symbolicBundleName));
		}
		return logs.get(symbolicBundleName);
	}

	/**
	 * Schreibt die <code>exception</code> mit einer Standardfehlermeldung in den Eclipse Error
	 * Log-View.<br>
	 * <br>
	 * Beispiel einer Standardfehlermeldung:<br>
	 * "org.eclipse.core.runtime.CoreException:
	 * org.eclipse.core.internal.resources.ResourceException: The file product.propertiesdoes not
	 * exists."
	 *
	 * @param  exception  Fehler, der protokolliert werden soll.
	 */

	public void logCoreException(CoreException exception) {
		log.log(newStatus(exception, symbolicBundleName));
	}

	/**
	 * Schreibt die <code>exception</code> mit einer Standardfehlermeldung in den Eclipse Error
	 * Log-View. Die Schwere der Meldung ist {@link IStatus#ERROR}.<br>
	 *
	 * @param  exception          Fehler, der protokolliert werden soll.
	 * @param  additionalMessage  zusätzliche Fehlermeldung.
	 */
	public void logException(Throwable exception, String additionalMessage) {
		logException(exception, additionalMessage, IStatus.ERROR);
	}

	/**
	 * Schreibt die <code>exception</code> mit einer Standardfehlermeldung in den Eclipse Error
	 * Log-View. Die Schwere der Meldung ist {@link IStatus#ERROR}.<br>
	 *
	 * @param  exception          Fehler, der protokolliert werden soll.
	 * @param  additionalMessage  zusätzliche Fehlermeldung.
	 * @param  status             Statuskonstante aus {@link IStatus}.
	 */
	public void logException(Throwable exception, String additionalMessage, int status) {
		StringBuffer message = new StringBuffer(50);
		final String localizedMessage = exception.getLocalizedMessage();

		if (localizedMessage == null || localizedMessage.isEmpty()) {
			message.append(exception.toString());
		} else {
			message.append(localizedMessage);
		}
		if (additionalMessage != null && !additionalMessage.isEmpty()) {
			message.append(" (");
			message.append(additionalMessage);
			message.append(")");
		}

		logCoreException(
			new CoreException(
				new Status(status, symbolicBundleName, status, message.toString(), exception)
			)
		);
	}

	/**
	 * Schreibt die <code>exception</code> mit einer Standardfehlermeldung in den Eclipse Error
	 * Log-View.
	 *
	 * @param  exception  Fehler, der protokolliert werden soll.
	 * @param  status     Statuskonstante aus {@link IStatus}.
	 */

	public void logException(Throwable exception, int status) {
		logException(exception, "", status);
	}

	/**
	 * Schreibt die <code>exception</code> mit einer Standardfehlermeldung in den Eclipse Error
	 * Log-View.
	 *
	 * @param  exception  Fehler, der protokolliert werden soll.
	 */

	public void logException(Throwable exception) {
		logException(exception, "");
	}

	/**
	 * Schreibt die <code>message</code> in den Eclipse Error Log-View. Die Schwere der Meldung muss
	 * im Parameter <code>status</code> angegeben werden und einen der in <code>IStatus</code>
	 * definierten Werte haben.
	 *
	 * @param  message  Meldung, die protokolliert werden soll.
	 * @param  status   Schwere der Meldung (siehe {@link IStatus}).
	 */
	public void log(String message, int status) {
		log.log(new Status(status, symbolicBundleName, status, message, null));
	}

	/**
	 * @param   exception  Exception, zu der ein Fehlerstatus erzeugt werden soll.
	 * @param   pluginId   ID des plug-ins, in dem der Fehler aufgetreten ist.
	 *
	 * @return  einen Fehlerstatus ({@link IStatus}) mit einer Standardfehlermeldung zu dem
	 *          übergebenen Fehler.
	 */
	public IStatus newStatus(CoreException exception, String pluginId) {

		IStatus status = exception.getStatus();
		return new Status(
			status.getSeverity(),
			pluginId,
			status.getCode(),
			exception.getMessage(),
			exception
		);
	}

	/**
	 * Schreibt die <code>message</code> in den Eclipse Error Log-View, wenn als Loglevel {@link
	 * LogLevel#TRACE} eingestellt ist.
	 *
	 * @param  message  Meldung, die protokolliert werden soll.
	 */
	public void trace(String message) {
		if (logLevel == LogLevel.TRACE) {
			log(message, IStatus.INFO);
		}
	}

	/**
	 * Schreibt die <code>message</code> einschließlich des Stacktraces von <code>t</code> in den
	 * Eclipse Error Log-View, wenn als Loglevel {@link LogLevel#TRACE} eingestellt ist.
	 *
	 * @param  message  Meldung, die protokolliert werden soll.
	 * @param  t        Die Exception, die protokolliert werden soll (einschließlich Stacktrace).
	 */

	public void trace(String message, Throwable t) {
		if (logLevel == LogLevel.TRACE) {
			logException(t, message, IStatus.INFO);
		}
	}

	/**
	 * Schreibt die <code>message</code> in den Eclipse Error Log-View, wenn als Loglevel {@link
	 * LogLevel#DEBUG} oder {@link LogLevel#TRACE} eingestellt ist.
	 *
	 * @param  message  Meldung, die protokolliert werden soll.
	 */
	public void debug(String message) {
		if (logLevel == LogLevel.DEBUG || logLevel == LogLevel.TRACE) {
			log(message, IStatus.INFO);
		}
	}

	/**
	 * Schreibt die <code>message</code> einschließlich des Stacktraces von <code>t</code> in den
	 * Eclipse Error Log-View, wenn als Loglevel {@link LogLevel#DEBUG} oder {@link
	 * LogLevel#TRACE} eingestellt ist.
	 *
	 * @param  message  Meldung, die protokolliert werden soll.
	 * @param  t        Die Exception, die protokolliert werden soll (einschließlich Stacktrace).
	 */
	public void debug(String message, Throwable t) {
		if (logLevel == LogLevel.DEBUG || logLevel == LogLevel.TRACE) {
			logException(t, message, IStatus.INFO);
		}
	}

	/**
	 * Kannn den Logelevel überschreiben, der aus der Plug-in Property-Datei gelesen wird.
	 *
	 * @param  level  der zu setzende Loglevel.
	 *
	 * @see    TKCorePrefs.LOGGING#LOG_LEVEL
	 */
	public void setLogLevel(LogLevel level) {
		logLevel = level;
	}
}

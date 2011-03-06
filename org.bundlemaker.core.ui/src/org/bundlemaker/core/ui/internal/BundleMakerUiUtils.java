package org.bundlemaker.core.ui.internal;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * <p>
 * Common utils needed for UI parts of Bundlemaker
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class BundleMakerUiUtils {

	public static IStatus newWarning(String message, Throwable throwable) {
		return new Status(IStatus.WARNING, Activator.PLUGIN_ID, message,
				throwable);
	}

	public static IStatus newError(String message, Throwable throwable) {
		return new Status(IStatus.ERROR, Activator.PLUGIN_ID, message,
				throwable);
	}

	/**
	 * <p>
	 * Constructs a new {@link IStatus} for the given {@link CoreException}.
	 * </p>
	 * 
	 * @param coreException
	 *            The coreException
	 * @param message
	 *            An optional message. If null the message from the
	 *            coreException will be used
	 * @return
	 */
	public static IStatus newStatus(CoreException coreException, String message) {
		String actualMessage = (message == null ? coreException.getMessage()
				: message);
		return new Status(coreException.getStatus().getSeverity(),
				Activator.PLUGIN_ID, actualMessage, coreException);
	}

}

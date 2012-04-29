/*******************************************************************************
 * Copyright (c) 2012 BundleMaker Project Team
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Nils Hartmann - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.utils;

import org.bundlemaker.core.ui.internal.Activator;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 * @credits Mostly inspired and taken from org.eclipse.debug.internal.ui.launchConfigurations.PerspectiveManager
 */
public class BundleMakerPerspectiveHelper {
  // see org.eclipse.debug.internal.ui.launchConfigurations.PerspectiveManager

  /**
   * Id of BundleMaker perspective as defined in the app.perspective bundle
   */
  private final static String BUNDLEMAKER_PERSPECTIVE_ID = "org.bundlemaker.core.ui.app.perspective";

  public static void openBundleMakerPerspectiveIfWanted(String preferenceKey) {

    BundleMakerPerspectiveHelper helper = new BundleMakerPerspectiveHelper();

    helper.openBundleMakerPerspective(BUNDLEMAKER_PERSPECTIVE_ID, preferenceKey);

  }

  protected void openBundleMakerPerspective(String perspectiveId, String preferenceKey) {
    IWorkbenchWindow window = getWindowForPerspective(perspectiveId);
    if (window != null && shouldSwitchPerspective(window, perspectiveId, preferenceKey)) {
      switchToPerspective(window, perspectiveId);
    }
  }

  /**
   * Returns the workbench window in which the given perspective should be shown. First, check the current window to see
   * if it is already showing the perspective. Then check any other windows.
   * 
   * @param perspectiveId
   *          the perspective identifier
   * @return which window the given perspective should be shown in or <code>null</code> if there are no windows
   *         available
   */
  private IWorkbenchWindow getWindowForPerspective(String perspectiveId) {
    IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
    if (isWindowShowingPerspective(window, perspectiveId)) {
      return window;
    }
    IWorkbenchWindow[] windows = PlatformUI.getWorkbench().getWorkbenchWindows();
    for (int i = 0; i < windows.length; i++) {
      window = windows[i];
      if (isWindowShowingPerspective(window, perspectiveId)) {
        return window;
      }
    }
    window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
    if (window != null) {
      return window;
    }
    if (windows.length > 0) {
      return windows[0];
    }
    return null;
  }

  /**
   * Returns whether or not the user wishes to switch to the specified perspective when a launch occurs.
   * 
   * @param perspectiveName
   *          the name of the perspective that will be presented to the user for confirmation if they've asked to be
   *          prompted about perspective switching
   * @param message
   *          a message to be presented to the user. This message is expected to contain a slot for the perspective name
   *          to be inserted ("{0}").
   * @param preferenceKey
   *          the preference key of the perspective switching preference
   * @return whether or not the user wishes to switch to the specified perspective automatically
   */
  private boolean shouldSwitchPerspective(IWorkbenchWindow window, String perspectiveId, String preferenceKey) {
    if (isCurrentPerspective(window, perspectiveId)) {
      return false;
    }
    String perspectiveName = getPerspectiveLabel(perspectiveId);
    if (perspectiveName == null) {
      return false;
    }
    String perspectiveDesc = getPerspectiveDescription(perspectiveId);
    String[] args;
    if (perspectiveDesc != null) {
      args = new String[] { perspectiveName, perspectiveDesc };
    } else {
      args = new String[] { perspectiveName };
    }
    String switchPerspective = Activator.getDefault().getPreferenceStore().getString(preferenceKey);
    if (MessageDialogWithToggle.ALWAYS.equals(switchPerspective)) {
      return true;
    } else if (MessageDialogWithToggle.NEVER.equals(switchPerspective)) {
      return false;
    }

    Shell shell = window.getShell();
    if (shell == null) {
      return false;
    }

    // Activate the shell if necessary so the prompt is visible
    Shell modal = getModalDialogOpen(shell);
    if (shell.getMinimized()) {
      shell.setMinimized(false);
      if (modal != null) {
        modal.setFocus();
      }
    }
    if (Activator.getDefault().getPreferenceStore().getBoolean(IDebugUIConstants.PREF_ACTIVATE_WORKBENCH)) {
      if (modal == null) {
        shell.forceActive();
      }
    }
    // String message = IInternalDebugCoreConstants.EMPTY_STRING;
    // if (IInternalDebugUIConstants.PREF_SWITCH_PERSPECTIVE_ON_SUSPEND.equals(preferenceKey)) {
    // if (getPerspectiveDescription(perspectiveId) != null) {
    // message = LaunchConfigurationsMessages.PerspectiveManager_suspend_description;
    // } else {
    // message = LaunchConfigurationsMessages.PerspectiveManager_13;
    // }
    // } else if (IInternalDebugUIConstants.PREF_SWITCH_TO_PERSPECTIVE.equals(preferenceKey)) {
    // if (getPerspectiveDescription(perspectiveId) != null) {
    // message = LaunchConfigurationsMessages.PerspectiveManager_launch_description;
    // } else {
    // message = LaunchConfigurationsMessages.PerspectiveManager_15;
    // }
    // }

    MessageDialogWithToggle dialog = MessageDialogWithToggle.openYesNoQuestion(
        shell,
        "Switch to BundleMaker perspective", //
        "This action is associated with the BundleMaker perspective. Do you want to switch to this perspective?", null,
        true, Activator.getDefault().getPreferenceStore(), preferenceKey);
    boolean answer = (dialog.getReturnCode() == IDialogConstants.YES_ID);
    // synchronized (this) {
    // fPrompting = false;
    // notifyAll();
    // }
    if (isCurrentPerspective(window, perspectiveId)) {
      answer = false;
    }
    return answer;
  }

  /**
   * Returns if the specified window is showing the perspective denoted by the specified id
   * 
   * @param window
   *          the window to query
   * @param perspectiveId
   *          the perspective to ask about
   * @return true if the specified window is showing the perspective, false otherwise
   */
  private boolean isWindowShowingPerspective(IWorkbenchWindow window, String perspectiveId) {
    if (window != null) {
      IWorkbenchPage page = window.getActivePage();
      if (page != null) {
        IPerspectiveDescriptor perspectiveDescriptor = page.getPerspective();
        if (perspectiveDescriptor != null && perspectiveDescriptor.getId().equals(perspectiveId)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Returns whether the given perspective identifier matches the identifier of the current perspective.
   * 
   * @param perspectiveId
   *          the identifier
   * @return whether the given perspective identifier matches the identifier of the current perspective
   */
  protected boolean isCurrentPerspective(IWorkbenchWindow window, String perspectiveId) {
    boolean isCurrent = false;
    if (window != null) {
      IWorkbenchPage page = window.getActivePage();
      if (page != null) {
        IPerspectiveDescriptor perspectiveDescriptor = page.getPerspective();
        if (perspectiveDescriptor != null) {
          isCurrent = perspectiveId.equals(perspectiveDescriptor.getId());
        }
      }
    }
    return isCurrent;
  }

  /**
   * Returns the label of the perspective with the given identifier or <code>null</code> if no such perspective exists.
   * 
   * @param perspectiveId
   *          the identifier
   * @return the label of the perspective with the given identifier or <code>null</code> if no such perspective exists
   */
  protected String getPerspectiveLabel(String perspectiveId) {
    IPerspectiveDescriptor newPerspective = PlatformUI.getWorkbench().getPerspectiveRegistry()
        .findPerspectiveWithId(perspectiveId);
    if (newPerspective == null) {
      return null;
    }
    return newPerspective.getLabel();
  }

  /**
   * Returns the label of the perspective with the given identifier or <code>null</code> if no such perspective exists.
   * 
   * @param perspectiveId
   *          the identifier
   * @return the label of the perspective with the given identifier or <code>null</code> if no such perspective exists
   */
  protected String getPerspectiveDescription(String perspectiveId) {
    IPerspectiveDescriptor newPerspective = PlatformUI.getWorkbench().getPerspectiveRegistry()
        .findPerspectiveWithId(perspectiveId);
    if (newPerspective == null) {
      return null;
    }
    return newPerspective.getDescription();
  }

  /**
   * Returns a modal dialog currently open on the given shell or <code>null</code> if none.
   * 
   * @param shell
   *          shell to check
   * @return a modal dialog currently open on the given shell or <code>null</code> if none
   */
  private Shell getModalDialogOpen(Shell shell) {
    Shell[] shells = shell.getShells();
    for (int i = 0; i < shells.length; i++) {
      Shell dialog = shells[i];
      if ((dialog.getStyle() & (SWT.APPLICATION_MODAL | SWT.PRIMARY_MODAL | SWT.SYSTEM_MODAL)) > 0) {
        return dialog;
      }
    }
    return null;
  }

  /**
   * Switches to the specified perspective
   * 
   * @param id
   *          perspective identifier
   */
  protected void switchToPerspective(IWorkbenchWindow window, String id) {
    try {
      // don't loose the focus dialog if there is one
      Shell dialog = getModalDialogOpen(window.getShell());
      window.getWorkbench().showPerspective(id, window);
      if (dialog != null) {
        dialog.setFocus();
      }
    } catch (WorkbenchException e) {
      e.printStackTrace();
      // DebugUIPlugin.errorDialog(DebugUIPlugin.getShell(),
      // LaunchConfigurationsMessages.PerspectiveManager_Error_1,
      // MessageFormat.format(LaunchConfigurationsMessages.PerspectiveManager_Unable_to_switch_to_perspective___0__2,
      // new String[]{id}),
      // e);
    }
  }

}

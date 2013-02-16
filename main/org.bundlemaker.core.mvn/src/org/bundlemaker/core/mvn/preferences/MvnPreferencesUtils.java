package org.bundlemaker.core.mvn.preferences;

import java.io.File;

import org.bundlemaker.core.mvn.MvnCoreActivator;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MvnPreferencesUtils {

  // /**
  // * <p>
  // * </p>
  // *
  // * @param bundleMakerProject
  // * @return
  // */
  // public String getLocalRepo(IProject bundleMakerProject) {
  // return Platform.getPreferencesService().getString(MvnCoreActivator.PLUGIN_ID,
  // MvnCoreActivator.PREF_MVN_LOCAL_REPO,
  // System.getProperty("user.home")
  // + File.separatorChar + ".m2", getScopeContexts(bundleMakerProject.getProject()));
  // }
  //
  // /**
  // * <p>
  // * </p>
  // *
  // * @param bundleMakerProject
  // * @return
  // */
  // public String getRemoteRepo(IProject bundleMakerProject) {
  // return Platform.getPreferencesService().getString(MvnCoreActivator.PLUGIN_ID,
  // MvnCoreActivator.PREF_MVN_REMOTE_REPO,
  // "http://repo1.maven.org/maven2/", getScopeContexts(bundleMakerProject.getProject()));
  // }
  //
  // /**
  // * <p>
  // * </p>
  // *
  // * @param project
  // * @return
  // */
  // private IScopeContext[] getScopeContexts(IProject project) {
  // return new IScopeContext[] { new ProjectScope(project), InstanceScope.INSTANCE };
  // }
}

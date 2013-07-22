package org.bundlemaker.core.internal.projectdescription.gson;

import org.bundlemaker.core.internal.BundleMakerProject;
import org.bundlemaker.core.internal.projectdescription.BundleMakerProjectDescription;
import org.bundlemaker.core.project.IProjectContentProvider;
import org.bundlemaker.core.project.IProjectDescriptionAwareBundleMakerProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class GsonProjectDescriptionHelper {

  /**
   * <p>
   * </p>
   * 
   * @param project
   * @return
   */
  public static Gson gson() {
    return gson(null);
  }

  /**
   * <p>
   * </p>
   * 
   * @param project
   * @return
   */
  public static Gson gson(IProjectDescriptionAwareBundleMakerProject project) {

    try {

      GsonBuilder builder = new GsonBuilder();
      builder.excludeFieldsWithoutExposeAnnotation();
      builder.setPrettyPrinting();
      builder.registerTypeAdapter(IPath.class, new IPathDeserializer());
      builder.registerTypeAdapter(IProjectContentProvider.class,
          new ProjectContentProviderJsonAdapter(new ContentProviderCompoundClassLoader()));

      //
      if (project != null) {
        builder.registerTypeAdapter(BundleMakerProjectDescription.class,
            new BundleMakerProjectDescriptionInstanceCreator((BundleMakerProject) project));
      }

      return builder.create();
    } catch (CoreException e) {
      throw new RuntimeException(e);
    }
  }
}

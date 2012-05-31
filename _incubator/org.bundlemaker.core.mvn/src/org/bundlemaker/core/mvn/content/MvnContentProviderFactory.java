package org.bundlemaker.core.mvn.content;

import org.bundlemaker.core.projectdescription.IModifiableProjectDescription;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MvnContentProviderFactory {

  /**
   * <p>
   * </p>
   * 
   * @param description
   * @param name
   * @param version
   * @param binaryRoot
   * @param sourceRoot
   * @return
   */
  public static void addNewMvnContentProvider(IModifiableProjectDescription description,
      String groupId, String artifactId, String version) {

    //
    MvnContentProvider mvnContentProvider = new MvnContentProvider();
    mvnContentProvider.addMvnArtifact(groupId, artifactId, version);

    //
    description.addContentProvider(mvnContentProvider);
  }
}
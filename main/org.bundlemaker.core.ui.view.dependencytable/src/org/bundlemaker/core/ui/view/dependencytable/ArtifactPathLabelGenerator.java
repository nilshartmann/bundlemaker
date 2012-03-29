package org.bundlemaker.core.ui.view.dependencytable;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;

/**
 * Generates a path to an IArtifact that can be used either as a title or as a label.
 * 
 * <p>
 * The "title path" is the path from the root (not including) to the bundle (included) of a base artifact.
 * <p>
 * The "label path" is the path from the title (not including) to the IArtifact itself
 * 
 * <p>
 * Example:
 * <p>
 * IArtifact is: root/GROUP1/bundle1/de.test.example/Main
 * <p>
 * Title is: GROUP1/bundle1
 * <p>
 * Label is: de.test.example.Main
 * 
 * <p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ArtifactPathLabelGenerator {

  /**
   * The common base artifact that is used to calculate the labels
   */
  private IArtifact _baseArtifact;

  private IArtifact _titleArtifact;

  /**
   * @return the baseArtifact
   */
  public IArtifact getBaseArtifact() {
    return _baseArtifact;
  }

  /**
   * @param baseArtifact
   *          the baseArtifact to set
   */
  public void setBaseArtifact(IArtifact baseArtifact) {
    _baseArtifact = baseArtifact;
    _titleArtifact = null;
  }

  /**
   * returns the last segment of the path of IArtifacts that is used to build the title
   * 
   * @return
   */
  protected IArtifact getTitleArtifact() {
    if (_titleArtifact != null) {
      return _titleArtifact;
    }

    IArtifact artifact = _baseArtifact;
    if (artifact == null) {
      return null;
    }
    if (artifact.getType() == ArtifactType.Root) {
      _titleArtifact = artifact;
      return artifact;
    }

    while (artifact != null && artifact.getType().ordinal() > ArtifactType.Module.ordinal()) {
      artifact = artifact.getParent();
    }

    _titleArtifact = artifact;

    return _titleArtifact;

  }

  public String getTitle() {
    IArtifact artifact = getTitleArtifact();
    if (artifact == null) {
      return "";
    }

    if (artifact.getType() == ArtifactType.Root) {
      return artifact.getName();
    }

    String path = "";

//    while (artifact != null && artifact.getType().ordinal() > ArtifactType.Root.ordinal()) {
//      path = artifact.getName() + "/" + path;
//      artifact = artifact.getParent();
//    }

    path = ((IBundleMakerArtifact)artifact).getFullPath().toPortableString();
    
    if (path.endsWith("/")) {
      path = path.substring(0, path.length() - 1);
    }
    return path;
  }

  /**
   * @return
   */
  public String getLabel(IArtifact typeArtifact) {

    IArtifact titleArtifact = getTitleArtifact();
    if (titleArtifact == null) {
      return "";
    }

    IArtifact artifact = typeArtifact;

    String path = "";
    boolean inPackage = false;
    while (artifact != null && !artifact.equals(titleArtifact)) {
      if (artifact.getType() == ArtifactType.Package) {
        if (!inPackage) {
          inPackage = true;
          path = artifact.getQualifiedName() + "." + path;
        }
      } else {
        path = artifact.getName() + "/" + path;
        inPackage = false;
      }
      artifact = artifact.getParent();
    }

    if (path.endsWith("/")) {
      path = path.substring(0, path.length() - 1);
    }
    return path;
  }

}
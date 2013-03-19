package org.bundlemaker.core.selection.stage;

import java.util.Collection;
import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IArtifactStage {

  boolean hasStagedArtifacts();

  void setStagedArtifacts(List<IBundleMakerArtifact> stagedArtifacts);

  void addToStage(List<IBundleMakerArtifact> selectedArtifacts);

  void removeStagedArtifacts(Collection<IBundleMakerArtifact> artifacts);

  void setAddMode(ArtifactStageAddMode addMode);

  ArtifactStageAddMode getAddMode();

  void addArtifactStageChangeListener(IArtifactStageChangeListener listener);

  void removeArtifactStageChangeListener(IArtifactStageChangeListener listener);

}

package org.bundlemaker.analysis.ui.internal.selection;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.ui.selection.IArtifactSelection;
import org.bundlemaker.analysis.ui.selection.IArtifactSelectionChangedEvent;
import org.bundlemaker.analysis.ui.selection.IArtifactSelectionListener;
import org.bundlemaker.analysis.ui.selection.IArtifactSelectionService;
import org.eclipse.core.runtime.Assert;

/**
 * The selection service implementation
 * 
 * @author Nils Hartmann
 * 
 */
public class ArtifactSelectionService extends
    AbstractSelectionService<IArtifactSelectionListener, IArtifactSelectionChangedEvent> implements
    IArtifactSelectionService {

  /**
   * A map containing all current selections
   */
  private final ConcurrentHashMap<String, IArtifactSelection> _currentSelections = new ConcurrentHashMap<String, IArtifactSelection>();

  @Override
  public IArtifactSelection getSelection(String selectionProviderId) {
    Assert.isNotNull(selectionProviderId, "The parameter 'selectionProviderId' must not be null");

    return _currentSelections.get(selectionProviderId);
  }

  @Override
  public void setSelection(String providerId, Collection<IArtifact> selectedArtifacts) {
    Assert.isNotNull(providerId, "The parameter 'providerId' must not be null");
    Assert.isNotNull(selectedArtifacts, "The parameter 'selectedArtifacts' must not be null");
    ArtifactSelection artifactSelection = new ArtifactSelection(providerId,
        new LinkedList<IArtifact>(selectedArtifacts));

    // add selection
    _currentSelections.put(providerId, artifactSelection);

    // notify listeners
    fireSelectionChanged(providerId, new ArtifactSelectionChangedEvent(artifactSelection));
  }

  @Override
  public void addArtifactSelectionListener(String providerId, IArtifactSelectionListener listener) {
    Assert.isNotNull(listener, "The parameter 'listener' must not be null");

    addSelectionListener(providerId, listener);
  }

  @Override
  public void addArtifactSelectionListener(IArtifactSelectionListener listener) {
    addArtifactSelectionListener(null, listener);
  }

  @Override
  public void removeArtifactSelectionListener(IArtifactSelectionListener listener) {
    Assert.isNotNull(listener, "The parameter 'listener' must not be null");

    removeSelectionListener(listener);
    }

    @Override
  protected void invokeListener(IArtifactSelectionListener listener, IArtifactSelectionChangedEvent event) {
    listener.artifactSelectionChanged(event);

    }

  }

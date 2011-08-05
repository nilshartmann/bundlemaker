package org.bundlemaker.analysis.ui.internal.selection;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.ui.selection.IArtifactSelection;
import org.bundlemaker.analysis.ui.selection.IArtifactSelectionListener;
import org.bundlemaker.analysis.ui.selection.IArtifactSelectionService;
import org.eclipse.core.runtime.Assert;

/**
 * The selection service implementation
 * 
 * @author Nils Hartmann
 * 
 */
public class ArtifactSelectionService implements IArtifactSelectionService {

  /**
   * A set containing the registered listeners
   */
  private final CopyOnWriteArraySet<SelectionListenerWrapper> _listenerList      = new CopyOnWriteArraySet<SelectionListenerWrapper>();

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
    fireArtifactSelectionChanged(artifactSelection);
  }

  @Override
  public void addArtifactSelectionListener(String providerId, IArtifactSelectionListener listener) {
    Assert.isNotNull(listener, "The parameter 'listener' must not be null");

    // Create wrapper
    SelectionListenerWrapper wrapper = new SelectionListenerWrapper(providerId, listener);

    // add to listener list
    _listenerList.add(wrapper);
  }

  @Override
  public void addArtifactSelectionListener(IArtifactSelectionListener listener) {
    addArtifactSelectionListener(null, listener);
  }

  @Override
  public void removeArtifactSelectionListener(IArtifactSelectionListener listener) {
    Assert.isNotNull(listener, "The parameter 'listener' must not be null");

    for (SelectionListenerWrapper wrapper : _listenerList) {
      if (listener.equals(wrapper.getListener())) {
        // Remove from listener list
        _listenerList.remove(wrapper);
        break;
      }
    }
  }

  private void fireArtifactSelectionChanged(IArtifactSelection artifactSelection) {
    // get the provider of the selection
    final String providerId = artifactSelection.getProviderId();

    // Create the event
    final ArtifactSelectionEvent event = new ArtifactSelectionEvent(artifactSelection);

    for (SelectionListenerWrapper wrapper : _listenerList) {
      // check if listener is registered for the provider
      if (wrapper.matches(providerId)) {

        // invoke!
        wrapper.getListener().artifactSelectionChanged(event);
      }

    }

  }

  private static class SelectionListenerWrapper {
    private final String                     _providerId;

    private final IArtifactSelectionListener _listener;

    private SelectionListenerWrapper(String providerId, IArtifactSelectionListener listener) {
      super();
      _providerId = providerId;
      _listener = listener;
    }

    public boolean matches(String providerId) {
      return (_providerId == null // matches all
      || _providerId.equals(providerId));
    }

    public IArtifactSelectionListener getListener() {
      return _listener;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((_listener == null) ? 0 : _listener.hashCode());
      result = prime * result + ((_providerId == null) ? 0 : _providerId.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      SelectionListenerWrapper other = (SelectionListenerWrapper) obj;
      if (_listener == null) {
        if (other._listener != null)
          return false;
      } else if (!_listener.equals(other._listener))
        return false;
      if (_providerId == null) {
        if (other._providerId != null)
          return false;
      } else if (!_providerId.equals(other._providerId))
        return false;
      return true;
    }

  }
}

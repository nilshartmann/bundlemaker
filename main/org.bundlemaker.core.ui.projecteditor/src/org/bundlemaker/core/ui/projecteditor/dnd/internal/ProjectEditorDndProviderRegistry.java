package org.bundlemaker.core.ui.projecteditor.dnd.internal;

import java.util.LinkedHashSet;
import java.util.Set;

import org.bundlemaker.core.ui.projecteditor.dnd.IProjectEditorDropProvider;
import org.bundlemaker.core.ui.projecteditor.filebased.FileBasedContentDropProvider;

public class ProjectEditorDndProviderRegistry {

  private final Set<IProjectEditorDropProvider> _registeredProviders = new LinkedHashSet<IProjectEditorDropProvider>();

  public ProjectEditorDndProviderRegistry() {
    _registeredProviders.add(new FileBasedContentDropProvider());
  }

  public Set<IProjectEditorDropProvider> getRegisteredDndProviders() {
    return this._registeredProviders;

  }

}

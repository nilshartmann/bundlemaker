package org.bundlemaker.analysis.ui.dsmview.handlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bundlemaker.analysis.ui.Analysis;
import org.bundlemaker.analysis.ui.handlers.AbstractArtifactBasedHandler;
import org.bundlemaker.dependencyanalysis.base.model.ArtifactType;
import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
import org.bundlemaker.dependencyanalysis.model.DependencyGraph;
import org.bundlemaker.dependencyanalysis.ui.editor.GenericEditor;
import org.bundlemaker.dependencyanalysis.ui.view.table.DependencyTreeTableView;
import org.bundlemaker.dependencyanalysis.ui.view.table.JavaEditor;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.internal.part.NullEditorInput;

@SuppressWarnings("restriction")
public class OpenDsmViewHandler extends AbstractArtifactBasedHandler {

  private IEditorInput nullInputEditor = new NullEditorInput();

  @Override
  protected void execute(ExecutionEvent event, List<IArtifact> selectedArtifacts) throws Exception {

    if (isSingleClassArtifactSelected(selectedArtifacts)) {
      IArtifact singleClassArtifact = selectedArtifacts.get(0);
      JavaEditor.openTypeInEditor(singleClassArtifact.getQualifiedName());
      Collection<IArtifact> artifacts = new ArrayList<IArtifact>();
      artifacts.add(singleClassArtifact);
      artifacts.add(singleClassArtifact.getParent(ArtifactType.Root));
      setNewDependencyGraph(artifacts);
      openEditorAndViews(event);
      return;
    }

    Set<IArtifact> artifacts = new HashSet<IArtifact>();
    Iterator<IArtifact> iterator = selectedArtifacts.iterator();
    while (iterator.hasNext()) {
      artifacts.addAll(iterator.next().getChildren());
    }
    setNewDependencyGraph(new HashSet<IArtifact>(artifacts));
    openEditorAndViews(event);
  }

  private void setNewDependencyGraph(Collection<IArtifact> artifacts) {
    DependencyGraph dependencyGraph = DependencyGraph.calculateDependencyGraph(artifacts);
    Analysis.instance().getContext().setDependencyGraph(dependencyGraph);

  }

  private boolean isSingleClassArtifactSelected(List<IArtifact> selectedArtifacts) {
    return (selectedArtifacts.size() == 1 && selectedArtifacts.get(0).getType().equals(ArtifactType.Type));
  }

  private void openEditorAndViews(ExecutionEvent event) {
    try {
      IWorkbenchPage page = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage();
      page.openEditor(nullInputEditor, GenericEditor.ID);
      page.showView(DependencyTreeTableView.ID);
    } catch (PartInitException e) {
      e.printStackTrace();
    }
  }

}

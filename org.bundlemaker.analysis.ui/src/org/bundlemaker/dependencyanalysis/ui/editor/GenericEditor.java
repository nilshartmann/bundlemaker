package org.bundlemaker.dependencyanalysis.ui.editor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
import org.bundlemaker.dependencyanalysis.model.DependencyGraph;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

public class GenericEditor extends EditorPart {

	public static final String ID = "org.bundlemaker.dependencyanalysis.ui.editor.GenericEditor";

	private final String NAME = "name";
	private final String CLAZZ = "class";
	
	private List<DependencyPart> dependencyParts;
	
	private static DependencyPart dsmView;
	
	private TabFolder tabFolder;
	
	@Override
	public void doSave(IProgressMonitor monitor) {

	}
	
	@Override
	public void doSaveAs() {

	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		setInput(input);
		setSite(site);
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	public static void showDependencies( Collection<IArtifact> artifacts) {
		DependencyGraph graph = new DependencyGraph(artifacts);
		graph.calculateDependencies();
		dsmView.useDependencyGraph(graph);
		dsmView.selectViewTab();
	}

	@Override
	public void createPartControl(Composite parent) {
		
		dependencyParts = new ArrayList<DependencyPart>();
		
		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = extensionRegistry.getExtensionPoint("org.bundlemaker.dependencyanalysis.dependencyView");
		
		tabFolder = new TabFolder(parent, SWT.NONE);
		for(IExtension extension : extensionPoint.getExtensions()){
			for(IConfigurationElement element : extension.getConfigurationElements()){
				String title = element.getAttribute(NAME);
				TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
				
				
				try {
					DependencyPart dependencyPart = (DependencyPart)element.createExecutableExtension(CLAZZ);
					if( dependencyPart.isDSMView() ) {
						dsmView = dependencyPart;
					}
					dependencyParts.add(dependencyPart);
					dependencyPart.init(tabFolder);
					dependencyPart.setTabItem(tabItem);
					dependencyPart.setTabFolder(tabFolder);
					tabItem.setText(title);
					tabItem.setControl(dependencyPart.getComposite());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		registerListener();
		
	}

	private void registerListener() {
		tabFolder.addSelectionListener(new SelectionAdapter() {
		      public void widgetSelected(org.eclipse.swt.events.SelectionEvent event) {
		    	  setTabItemFocus(tabFolder.getSelectionIndex());
		      }
		 });

		
	}

	@Override
	public void setFocus() {
		int selectedItemIndex = tabFolder.getSelectionIndex();
		this.setTabItemFocus(selectedItemIndex);
	}
	
	public void setTabItemFocus(int index){
		dependencyParts.get(index).setFocus();
	}
	
	public void dispose(){
		super.dispose();
		for(DependencyPart dependencyPart : dependencyParts){
			dependencyPart.dispose();
		}
		
	}

}

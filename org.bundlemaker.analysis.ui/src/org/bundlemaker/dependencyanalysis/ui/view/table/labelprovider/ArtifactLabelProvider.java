package org.bundlemaker.dependencyanalysis.ui.view.table.labelprovider;

import org.bundlemaker.dependencyanalysis.base.model.ArtifactType;
import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
import org.bundlemaker.dependencyanalysis.base.model.IDependency;
import org.eclipse.jface.viewers.ColumnLabelProvider;

public class ArtifactLabelProvider extends ColumnLabelProvider {
	
	
	public String getText( Object element, boolean showFrom ) {
		if(element instanceof IDependency){
			if( showFrom ) {
				return ((IDependency) element).getFrom().getName();
			} else {
				return ((IDependency) element).getTo().getName();
			}
		}
		return element == null ? "" : element.toString();
	}
	
	public String getTooltipText( IArtifact artifact ) {
		StringBuilder tooltipText = new StringBuilder();
		IArtifact bundle = artifact.getParent(ArtifactType.Module);
		IArtifact group = artifact.getParent(ArtifactType.Group);
		IArtifact artifactPackage = artifact.getParent(ArtifactType.Package);
		if( (bundle != null) && (group != null)) {
			tooltipText.append("Group:     ");
			tooltipText.append(group.getQualifiedName());
			tooltipText.append("\n");
			tooltipText.append("Bundle:    ");
			tooltipText.append(bundle.getName());
			tooltipText.append("\n");
			tooltipText.append("Ordinal:   ");
			tooltipText.append(bundle.getOrdinal());
			tooltipText.append("\n");			
		}
		if( artifactPackage != null ) {
			tooltipText.append("Package: ");
			tooltipText.append(artifactPackage.getName());
			tooltipText.append("\n");
			tooltipText.append("Klasse:      ");
			tooltipText.append(artifact.getName());
			
		} else {
			tooltipText.append(artifact.getQualifiedName());
		}
		
		return tooltipText.toString();
	}
}

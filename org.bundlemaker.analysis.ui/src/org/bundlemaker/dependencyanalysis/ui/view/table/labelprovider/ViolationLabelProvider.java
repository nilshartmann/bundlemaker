package org.bundlemaker.dependencyanalysis.ui.view.table.labelprovider;

import org.bundlemaker.dependencyanalysis.base.model.IDependency;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;

public class ViolationLabelProvider extends ColumnLabelProvider {
	
	@Override
	public Image getImage(Object element) {
		if(element instanceof IDependency){
			IDependency dependency = (IDependency) element;
			if(dependency.hasViolations()){
				return null;
			}
			
		}
		return null;
	}

	@Override
	public String getText(Object element) {
		if(element instanceof IDependency){
			IDependency dependency = (IDependency) element;
			if(dependency.hasViolations()){
				return String.valueOf(dependency.getViolationWeight());
			}			
		}
		return "";
	}

	@Override
	public String getToolTipText(Object element) {
		if(element instanceof IDependency){
			IDependency dependency = (IDependency) element;
			if(dependency.hasViolations()){
				return dependency.getViolations().toString();
			}
		}
		return "";
	}
}

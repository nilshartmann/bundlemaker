package org.bundlemaker.core.ui.editor.dsm.widget;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Menu;

public class DsmViewContextMenu {

	private Canvas canvas;

	private IDsmContentProvider model;

	public DsmViewContextMenu(Canvas canvas) {
		this.canvas = canvas;
		createContextMenu();
	}

	public void setModel(IDsmContentProvider model) {
		this.model = model;
	}

	private void createContextMenu() {

		MenuManager menuManager = new MenuManager("#PopupMenu");
		menuManager.setRemoveAllWhenShown(false);

		IConfigurationElement[] dsmSorters =
			Platform.getExtensionRegistry().getConfigurationElementsFor(
				"org.bundlemaker.core.ui.editor.dsm.DsmViewAction"
			);

		for (IConfigurationElement dsmSorter : dsmSorters) {
			IDsmViewAction dsmViewAction;
			try {
				dsmViewAction = (IDsmViewAction) dsmSorter.createExecutableExtension("class");
				Action action = createAction(dsmViewAction);
				menuManager.add(action);

			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		Menu menu = menuManager.createContextMenu(canvas);
		canvas.setMenu(menu);
	}

	private Action createAction(final IDsmViewAction dsmViewAction) {

		Action action =
			new Action(dsmViewAction.getActionName()) {
				@Override
				public void run() {
					dsmViewAction.run(model);
				}
			};
		return action;
	}
}

package org.bundlemaker.core.ui.editor.dsm.widget;

public interface IDsmViewAction {
	public String getActionName();

	public void run(IDsmContentProvider model);
}

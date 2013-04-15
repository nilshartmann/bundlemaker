package org.bundlemaker.core.ui.editor.dsm.widget;

public interface IDsmContentProvider {

  int getItemCount();

  boolean isToggled();

  Object getDependency(int j, int i);

  Object[][] getDependencies();

  Object[] getNodes();
}

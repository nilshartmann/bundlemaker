package org.bundlemaker.core.ui.editor.dsm.figures;

public interface IMatrixContentProvider {

  int getItemCount();

  boolean isToggled();

  Object getDependency(int j, int i);

  Object[][] getDependencies();

  Object[] getNodes();
}

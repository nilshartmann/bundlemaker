package org.bundlemaker.core.ui.editor.dsm.widget;

import java.util.Observer;

public interface IDsmContentProvider {

  void addObserver(Observer observer);

  void deleteObserver(Observer dsmViewWidget);
  
  int getItemCount();

  boolean isToggled();

  Object getDependency(int j, int i);

  Object[][] getDependencies();

  Object[] getNodes();
  
  int[][] getCycles();

  boolean isInCycle(int x, int y);

  boolean isInCycle(int i);
}

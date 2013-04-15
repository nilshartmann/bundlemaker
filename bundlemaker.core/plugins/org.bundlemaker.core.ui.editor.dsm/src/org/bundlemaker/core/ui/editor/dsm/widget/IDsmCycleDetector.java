package org.bundlemaker.core.ui.editor.dsm.widget;

public interface IDsmCycleDetector {

  int[][] getCycles();

  boolean isInCycle(int x, int y);

  boolean isInCycle(int i);
}

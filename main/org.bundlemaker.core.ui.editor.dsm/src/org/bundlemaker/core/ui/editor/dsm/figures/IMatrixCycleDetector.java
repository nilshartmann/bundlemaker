package org.bundlemaker.core.ui.editor.dsm.figures;

public interface IMatrixCycleDetector {

  int[][] getCycles();

  boolean isInCycle(int x, int y);

  boolean isInCycle(int i);
}

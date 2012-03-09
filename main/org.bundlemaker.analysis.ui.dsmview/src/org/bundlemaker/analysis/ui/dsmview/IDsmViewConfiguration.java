package org.bundlemaker.analysis.ui.dsmview;

import org.eclipse.swt.graphics.Color;

public interface IDsmViewConfiguration {

  int getVerticalBoxSize();

  int getHorizontalBoxSize();

  void setHorizontalBoxSize(int size);

  Color getSideMarkerBackgroundColor();

  Color getSideMarkerEvenColor();

  Color getSideMarkerMarkedColor();

  Color getSideMarkerSeparatorColor();

  Color getSideMarkerTextColor();

  Color getMatrixSeparatorColor();

  Color getMatrixBackgroundColor();

  Color getMatrixTextColor();

  Color getMatrixDiagonalColor();

  Color getMatrixMarkedColumnRowColor();

  Color getMatrixMarkedCellColor();

  Color getCycleSideMarkerColor();

  Color getCycleMatrixMarkedColumnRowColor();

  Color getCycleSideMarkerSeparatorColor();

  Color getCycleMatrixMarkedCellColor();

  Color getCycleMatrixDiagonalColor();

  Color getCycleSideMarkerMarkedColor();
}

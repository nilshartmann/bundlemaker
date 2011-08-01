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

	// final Font DSM_FONT = new Font(Display.getCurrent(), "ARIAL", 15,
	// SWT.NONE);
	//
	// Color COLOR_VIOLATION =
	// Display.getDefault().getSystemColor(SWT.COLOR_RED);
	//
	// Color COLOR_IGNORED = Display.getDefault()
	// .getSystemColor(SWT.COLOR_MAGENTA);
	//
	// Color COLOR_TEXT = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
	//
	// Color COLOR_HEADER = Display.getDefault().getSystemColor(SWT.COLOR_GRAY);
	//
	// Color COLOR_BACKGROUND = Display.getDefault().getSystemColor(
	// SWT.COLOR_WHITE);
	//
	// Color COLOR_DEPENDENCY = Display.getDefault().getSystemColor(
	// SWT.COLOR_GREEN);
}

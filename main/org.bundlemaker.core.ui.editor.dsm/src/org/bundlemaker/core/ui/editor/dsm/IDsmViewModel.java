package org.bundlemaker.core.ui.editor.dsm;

import java.util.Collection;
import java.util.Observer;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.ui.editor.dsm.figures.IMatrixContentProvider;
import org.bundlemaker.core.ui.editor.dsm.figures.IMatrixCycleDetector;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IDsmViewModel extends IMatrixContentProvider,
		IMatrixCycleDetector {

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	IDsmViewConfiguration getConfiguration();

	void refresh(Collection<IBundleMakerArtifact> artifacts);

	@Override
	int getItemCount();

	String[] getLabels();

	/**
	 * @return the labels according to the user-selected presentation mode
	 */
	String[] getDisplayLabels();

	String[][] getValues();

	void addObserver(Observer observer);

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	@Override
	int[][] getCycles();

	@Override
	boolean isInCycle(int i);

	@Override
	boolean isInCycle(int i, int j);

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	@Override
	boolean isToggled();
}

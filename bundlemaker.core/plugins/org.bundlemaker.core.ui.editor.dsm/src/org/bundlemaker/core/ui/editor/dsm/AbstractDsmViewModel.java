package org.bundlemaker.core.ui.editor.dsm;

import java.util.Observable;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractDsmViewModel extends Observable implements
		IDsmViewModel {

	private String[] _labels;

	private String[][] _values;

	private String[] _displayLabels;

	private LabelPresentationMode _labelPresentationMode = LabelPresentationMode.qualifiedName;

	public LabelPresentationMode getLabelPresentationMode() {
    return _labelPresentationMode;
  }

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	@Override
	public final String[] getLabels() {

		// create
		if (_labels == null) {
			_labels = createLabels();
		}

		// return values
		return _labels;
	}

	@Override
	public String[] getDisplayLabels() {
		if (_displayLabels == null) {
			_displayLabels = doGetDisplayLabels();
		}

		return _displayLabels;
	}

	public void setLabelPresentationMode(
			LabelPresentationMode labelPresentationMode) {
		if (labelPresentationMode != _labelPresentationMode) {
			_labelPresentationMode = labelPresentationMode;
			_displayLabels = null;
			notifyObservers();
		}
	}
	
	/**
	 * Returns the shortend version of a label
	 * 
	 * @param label
	 * @return
	 */
	protected String getDisplayLabel(IBundleMakerArtifact artifact) {
		if (_labelPresentationMode == LabelPresentationMode.simpleName) {
			
			if (artifact instanceof IModuleArtifact) {
				IModuleArtifact moduleArtifact = (IModuleArtifact) artifact;
				return moduleArtifact.getModuleName();
			}
			
			return artifact.getName();
		}

		String label = artifact.getQualifiedName();

		if (_labelPresentationMode == LabelPresentationMode.qualifiedName) {
			return label;
		}

		int v = label.indexOf("_");
		if (v > 0) {
			label = label.substring(0, v);
		}

		boolean lastPart = true;

		String shortendLabel = "";
		label = "." + label;
		char previousChar = label.charAt(label.length() - 1);
		for (int i = label.length() - 1; i >= 0; i--) {
			char c = label.charAt(i);
			if (c == '.' || c == '/') {
				if (!lastPart) {
					shortendLabel = previousChar + shortendLabel;
				}
				if (i > 0) {
					shortendLabel = c + shortendLabel;
				}
				lastPart = false;
			} else {
				if (lastPart) {
					shortendLabel = c + shortendLabel;
				}

			}

			previousChar = c;
		}
		return shortendLabel;

	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	@Override
	public final String[][] getValues() {

		// create
		if (_values == null) {
			_values = createValues();
		}

		// return values
		return _values;
	}

	protected abstract String[] doGetDisplayLabels();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	protected abstract String[][] createValues();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	protected abstract String[] createLabels();

	/**
	 * <p>
	 * </p>
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public abstract String getToolTip(int x, int y);
}

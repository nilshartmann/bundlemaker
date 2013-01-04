package org.bundlemaker.core.ui.view.dependencytable;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;

/**
 * Generates a path to an IArtifact that can be used either as a title or as a
 * label.
 * 
 * <p>
 * The "title path" is the path from the root (not including) to the bundle
 * (included) of a base artifact.
 * <p>
 * The "label path" is the path from the title (not including) to the IArtifact
 * itself
 * 
 * <p>
 * Example:
 * <p>
 * IArtifact is: root/GROUP1/bundle1/de.test.example/Main
 * <p>
 * Title is: GROUP1/bundle1
 * <p>
 * Label is: de.test.example.Main
 * 
 * <p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ArtifactPathLabelGenerator {

	/**
	 * The common base artifact that is used to calculate the labels
	 */
	private IBundleMakerArtifact _baseArtifact;

	private IBundleMakerArtifact _titleArtifact;

	private boolean _shortLabel;

	/**
	 * @return the baseArtifact
	 */
	public IBundleMakerArtifact getBaseArtifact() {
		return _baseArtifact;
	}

	/**
	 * @param baseArtifact
	 *            the baseArtifact to set
	 */
	public void setBaseArtifact(IBundleMakerArtifact baseArtifact) {
		_baseArtifact = baseArtifact;
		_titleArtifact = null;
	}

	public void setUseShortLabel(boolean shortLabel) {
		this._shortLabel = shortLabel;
	}

	public boolean isUseShortLabel() {
		return this._shortLabel;
	}

	/**
	 * returns the last segment of the path of IArtifacts that is used to build
	 * the title
	 * 
	 * @return
	 */
	protected IBundleMakerArtifact getTitleArtifact() {
		if (_titleArtifact != null) {
			return _titleArtifact;
		}

		IBundleMakerArtifact artifact = _baseArtifact;
		if (artifact == null) {
			return null;
		}
		if (artifact.isInstanceOf(IRootArtifact.class)) {
			_titleArtifact = artifact;
			return artifact;
		}

		while (artifact != null
				&& artifact.getParent(IModuleArtifact.class) != null) {
			artifact = artifact.getParent();
		}

		_titleArtifact = artifact;

		return _titleArtifact;

	}

	public String getTitle() {
		IBundleMakerArtifact artifact = getTitleArtifact();
		if (artifact == null) {
			return "";
		}

		if (artifact.isInstanceOf(IRootArtifact.class)) {
			return artifact.getName();
		}

		String path = "";

		// while (artifact != null && artifact.getType().ordinal() >
		// ArtifactType.Root.ordinal()) {
		// path = artifact.getName() + "/" + path;
		// artifact = artifact.getParent();
		// }

		path = ((IBundleMakerArtifact) artifact).getFullPath()
				.toPortableString();

		if (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}
		return path;
	}

	/**
	 * @return
	 */
	public String getLabel(IBundleMakerArtifact typeArtifact) {

		if (isUseShortLabel()) {
			return getShortLabel(typeArtifact);
		}

		return getFullLabel(typeArtifact);

	}

	protected String getShortLabel(IBundleMakerArtifact typeArtifact) {
		String name = typeArtifact.getName();

		if (!(typeArtifact instanceof ITypeArtifact)) {
			// why would this happen?
			return name;
		}

		IBundleMakerArtifact parent = typeArtifact
				.getParent(IPackageArtifact.class);

		if (parent == null) {
			return name;
		}

		if (parent instanceof IPackageArtifact) {
			name = parent.getQualifiedName() + "." + name;
		}

		return name;
	}

	protected String getFullLabel(IBundleMakerArtifact typeArtifact) {
		IBundleMakerArtifact titleArtifact = getTitleArtifact();
		if (titleArtifact == null) {
			return "";
		}

		IBundleMakerArtifact artifact = typeArtifact;

		String path = "";
		boolean inPackage = false;
		while (artifact != null && !artifact.equals(titleArtifact)) {
			if (artifact.isInstanceOf(IPackageArtifact.class)) {
				if (!inPackage) {
					inPackage = true;
					path = artifact.getQualifiedName() + "." + path;
				}
			} else {
				path = artifact.getName() + "/" + path;
				inPackage = false;
			}
			artifact = artifact.getParent();
		}

		if (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}
		return path;

	}

}

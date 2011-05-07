package org.bundlemaker.dependencyanalysis.base.transformations;

import java.util.ArrayList;
import java.util.List;

import org.bundlemaker.dependencyanalysis.base.model.ArtifactType;
import org.bundlemaker.dependencyanalysis.base.model.IArtifact;

public class BundlePathName {

	private String bundleName = null;
	private List<String> classificationPath = new ArrayList<String>();

	private BundlePathName() {
	}

	/**
	 * @param bundleName
	 * @param classificationPath
	 */
	public BundlePathName(String bundleName, List<String> classificationPath) {

		this.bundleName = bundleName;
		this.classificationPath = classificationPath;
	}

	/**
	 * @param bundleName
	 * @param classifications
	 */
	public BundlePathName(String bundleName, String... classifications) {

		this.bundleName = bundleName;
		classificationPath = new ArrayList<String>();
		for(String classification: classifications ) {
			classificationPath.add(classification);
		}
	}

	public BundlePathName(String[] bundlePath) {
		for( int i=0; i<(bundlePath.length-1); i++ ) {
			classificationPath.add(bundlePath[i]);
		}
		bundleName = bundlePath[bundlePath.length-1];
	}
	
	public BundlePathName(List<String> bundlePathList) {
		int lastIndex = bundlePathList.size() - 1;
		if( lastIndex >= 0) {
			bundleName = bundlePathList.get(lastIndex);
			bundlePathList.remove(lastIndex);
			classificationPath.addAll(bundlePathList);
		} else {
			bundleName = "Unbekannt";
		}
	}

	public BundlePathName(IArtifact bundle) {
		if (!bundle.getType().equals(ArtifactType.Module)) {
			throw new RuntimeException("Das artefakt: "
					+ bundle.getQualifiedName()
					+ " ist kein OsgiBundle, sondern vom Typ: "
					+ bundle.getType());
		}
		bundleName = bundle.getName();
		initClassificationPath(bundle.getParent(), classificationPath);
	}

	public BundlePathName getCopy(String bundleName) {
		BundlePathName copy = new BundlePathName();
		copy.bundleName = bundleName;
		copy.classificationPath.addAll(classificationPath);

		return copy;
	}

	private void initClassificationPath(IArtifact artifact,
			List<String> classificationPath) {
		if (artifact.getParent() != null) {
			initClassificationPath(artifact.getParent(), classificationPath);
		}
		if (artifact.getType() != ArtifactType.Root) {
			classificationPath.add(artifact.getName());
		} else {
			System.out.println("Strip Root in BundlePathName: "
					+ artifact.getName());
		}
	}

	public String getBundleName() {
		return bundleName;
	}

	// public void appendBundleName(String bundleExtensionName ) {
	// bundleName = bundleName + bundleExtensionName;
	// }

	public List<String> getClassificationPath() {
		return classificationPath;
	}

	public void addPathName(String pathName) {
		if (bundleName != null) {
			classificationPath.add(bundleName);
		}
		bundleName = pathName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bundleName == null) ? 0 : bundleName.hashCode());
		result = prime
				* result
				+ ((classificationPath == null) ? 0 : classificationPath
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BundlePathName other = (BundlePathName) obj;
		if (bundleName == null) {
			if (other.bundleName != null)
				return false;
		} else if (!bundleName.equals(other.bundleName))
			return false;
		if (classificationPath == null) {
			if (other.classificationPath != null)
				return false;
		} else if (!classificationPath.equals(other.classificationPath))
			return false;
		return true;
	}

	public String getQualifiedName() {
		StringBuilder info = new StringBuilder(300);

		for (String path : classificationPath) {
			info.append(path);
			info.append("/");
		}
		info.append( bundleName );

		return info.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder info = new StringBuilder(300);

		info.append(bundleName);
		info.append("( ");
		for (String path : classificationPath) {
			info.append(path);
			info.append(" ");
		}
		info.append(")");

		return info.toString();
	}
}

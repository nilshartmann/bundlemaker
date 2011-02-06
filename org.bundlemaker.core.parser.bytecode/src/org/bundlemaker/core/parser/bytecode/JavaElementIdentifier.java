package org.bundlemaker.core.parser.bytecode;

import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.bundlemaker.core.resource.IModifiableResource;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class JavaElementIdentifier {

	/**
	 */
	public static enum FileType {

		JAVA_SOURCE_FILE,

		CLASS_FILE,

		OTHER
	}

	/** the content id as specified in the project content definition */
	private String _contentId;

	/** the root of the element (e.g. 'c:\tmp\xy.jar' or 'c:\tmp\classes') */
	private String _root;

	/**
	 * the relative path (e.g. 'de/example/xy/MyClass.class' or
	 * 'de/example/xy/MyClass.java')
	 */
	private String _path;

	/** the fully qualified name (e.g. 'de.example.xy.MyClass') */
	private String _fullyQualifiedName;

	/** the file type (e.g. 'SOURCE_FILE' or 'CLASS_FILE') */
	private FileType _fileType;

	/** the associated referencing element */
	private IModifiableResource _referencingElement;

	/**
	 * <p>
	 * </p>
	 * 
	 * @param contentId
	 * @param root
	 * @param jarEntry
	 */
	public JavaElementIdentifier(String contentId, JarFile root,
			JarEntry jarEntry) {
		Assert.isNotNull(contentId);
		Assert.isNotNull(root);
		Assert.isNotNull(jarEntry);

		_contentId = contentId;
		_root = root.getName();

		_path = jarEntry.getName();

		_fileType = _path.endsWith(".class") ? FileType.CLASS_FILE
				: FileType.JAVA_SOURCE_FILE;
	}

	/**
	 * @param element
	 */
	public JavaElementIdentifier(IModifiableResource element) {
		Assert.isNotNull(element);

		_contentId = element.getContentId();
		_root = element.getRoot();
		_path = element.getPath();
		_fileType = _path.endsWith(".class") ? FileType.CLASS_FILE
				: FileType.JAVA_SOURCE_FILE;
		getFullQualifiedName();
	}

	/**
	 * @param contentId
	 * @param root
	 * @param relativePath
	 * @param fileName
	 */
	public JavaElementIdentifier(String contentId, String root, String path) {
		Assert.isNotNull(contentId);
		Assert.isNotNull(root);
		Assert.isNotNull(path);

		_contentId = contentId;
		_root = root;
		_path = path;
		_fileType = _path.endsWith(".class") ? FileType.CLASS_FILE
				: FileType.JAVA_SOURCE_FILE;
	}

	public JavaElementIdentifier(String contentId, String root,
			String fullQualifiedName, FileType type) {
		Assert.isNotNull(contentId);
		Assert.isNotNull(fullQualifiedName);
		Assert.isNotNull(type);
		Assert.isTrue(fullQualifiedName.indexOf('/') == -1, fullQualifiedName);
		Assert.isTrue(fullQualifiedName.indexOf('\\') == -1, fullQualifiedName);

		_contentId = contentId;
		_root = root;
		_fullyQualifiedName = fullQualifiedName;
		_path = fullQualifiedName.replace('.', '/')
				+ (type.equals(FileType.JAVA_SOURCE_FILE) ? ".java" : ".class");
		_fileType = type;
	}

	public String getContentId() {
		return _contentId;
	}

	public String getRoot() {
		return _root;
	}

	public String getPath() {
		return _path;
	}

	// /**
	// * <p>
	// * </p>
	// *
	// * @return
	// */
	// public Resource getReferencingElement() {
	//
	// if (_referencingElement == null) {
	//
	// //
	// _referencingElement = new Resource(getContentId(), getRoot(),
	// getPath());
	// }
	//
	// // return referencing element
	// return _referencingElement;
	// }

	/**
	 * @param location
	 * @return
	 */
	public String getFullQualifiedName() {

		Assert.isTrue(_fileType.equals(FileType.CLASS_FILE)
				|| Assert.isTrue(_fileType.equals(FileType.JAVA_SOURCE_FILE)));

		if (_fullyQualifiedName == null) {

			String className = null;

			Assert.isTrue(_path.toLowerCase().endsWith(".class")
					|| _path.toLowerCase().endsWith(".java"));

			if (_path.toLowerCase().endsWith(".class")) {

				//
				className = _path.substring(0,
						_path.length() - ".class".length());

			} else if (_path.toLowerCase().endsWith(".java")) {

				//
				className = _path.substring(0,
						_path.length() - ".java".length());

			}

			_fullyQualifiedName = className.replace('/', '.');
		}

		return _fullyQualifiedName;
	}

	/**
	 * @return
	 */
	public String getEnclosingNonLocalAndNonAnonymousType() {
		Assert.isTrue(_fileType.equals(FileType.CLASS_FILE),
				"File type has to be '" + FileType.CLASS_FILE + "'.");

		// local or anonymous?
		if (isLocalOrAnonymousType(getFullQualifiedName())) {

			String[] parts = getFullQualifiedName().split("\\$\\d");
			return parts[0];

		} else {
			return getFullQualifiedName();
		}
	}

	public JavaElementIdentifier getIdForEnclosingNonLocalAndNonAnonymousType() {
		Assert.isTrue(_fileType.equals(FileType.CLASS_FILE));

		if (isLocalOrAnonymousType(getFullQualifiedName())) {
			return new JavaElementIdentifier(getContentId(), getRoot(),
					getEnclosingNonLocalAndNonAnonymousType(),
					FileType.CLASS_FILE);
		} else {
			return this;
		}
	}

	@Override
	public String toString() {
		return "ElementIdentifier [_contentId=" + _contentId + ", _root="
				+ _root + ", _path=" + _path + ", _fileType=" + _fileType + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((_contentId == null) ? 0 : _contentId.hashCode());
		result = prime * result + ((_path == null) ? 0 : _path.hashCode());
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
		JavaElementIdentifier other = (JavaElementIdentifier) obj;
		if (_contentId == null) {
			if (other._contentId != null)
				return false;
		} else if (!_contentId.equals(other._contentId))
			return false;
		if (_path == null) {
			if (other._path != null)
				return false;
		} else if (!_path.equals(other._path))
			return false;
		return true;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param fullQualifiedName
	 * @return
	 */
	public static boolean isLocalOrAnonymousType(String fullQualifiedName) {
		Assert.isNotNull(fullQualifiedName);

		return fullQualifiedName.matches(".*\\$\\d.*");
	}

}

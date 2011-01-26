package org.bundlemaker.core.parser.bytecode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.IProblem;
import org.bundlemaker.core.parser.IDirectory;
import org.bundlemaker.core.parser.IDirectoryFragment;
import org.bundlemaker.core.parser.IFolderBasedDirectoryFragment;
import org.bundlemaker.core.parser.IJarFileBasedDirectoryFragment;
import org.bundlemaker.core.parser.IParser;
import org.bundlemaker.core.parser.IResourceCache;
import org.bundlemaker.core.projectdescription.IFileBasedContent;
import org.bundlemaker.core.resource.Resource;
import org.bundlemaker.core.resource.ResourceKey;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.springsource.bundlor.support.asm.AsmTypeArtefactAnalyser;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ByteCodeParser implements IParser {

	/** the bundlor class file analyzer */
	private AsmTypeArtefactAnalyser _analyser;

	/**
	 * <p>
	 * </p>
	 * 
	 * @param bundleMakerProject
	 * @throws CoreException
	 */
	public ByteCodeParser() {

		// create the class file analyzer
		_analyser = new AsmTypeArtefactAnalyser();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ParserType getParserType() {
		return ParserType.BINARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void parseBundleMakerProjectStart(
			IBundleMakerProject bundleMakerProject) {

		// ignore
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<IProblem> parse(IFileBasedContent content,
			List<IDirectory> directoryList, IResourceCache cache,
			IProgressMonitor progressMonitor) throws CoreException {

		List<IProblem> _errors = new LinkedList<IProblem>();

		// iterate over the directories and parse the directory fragments
		for (IDirectory directory : directoryList) {

			for (IDirectoryFragment directoryFragment : directory
					.getBinaryDirectoryFragments()) {

				parseDirectoryFragment(directoryFragment, cache,
						progressMonitor);
			}
		}

		//
		return _errors;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void parseBundleMakerProjectStop(
			IBundleMakerProject bundleMakerProject) {

		// ignore
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param directoryFragment
	 * @param cache
	 * @param progressMonitor
	 * @throws CoreException
	 */
	private void parseDirectoryFragment(IDirectoryFragment directoryFragment,
			IResourceCache cache, IProgressMonitor progressMonitor)
			throws CoreException {

		// handle jar file based content
		if (directoryFragment instanceof IJarFileBasedDirectoryFragment) {

			IJarFileBasedDirectoryFragment jfbdContent = (IJarFileBasedDirectoryFragment) directoryFragment;

			// parse each class file
			for (JarEntry jarEntry : jfbdContent.getJarEntries()) {

				if (jarEntry.getName().endsWith(".class")) {
					try {
						JavaElementIdentifier elementID = new JavaElementIdentifier(
								directoryFragment.getDirectory()
										.getFileBasedContent().getId(),
								jfbdContent.getJarFile(), jarEntry);

						// parse
						// TODO
						parseClassFile(
								jfbdContent.getJarFile().getInputStream(
										jarEntry), elementID, cache);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				//
				progressMonitor.worked(1);
			}
		} else if (directoryFragment instanceof IFolderBasedDirectoryFragment) {

			IFolderBasedDirectoryFragment fdbContent = (IFolderBasedDirectoryFragment) directoryFragment;

			// parse each class file
			for (String content : fdbContent.getContent()) {

				if (content.endsWith(".class")) {

					try {
						JavaElementIdentifier elementID = new JavaElementIdentifier(
								directoryFragment.getDirectory()
										.getFileBasedContent().getId(),
								fdbContent.getDirectoryFragmentRoot()
										.getAbsolutePath(), content);

						// parse
						// TODO
						parseClassFile(
								new FileInputStream(new File(
										fdbContent.getDirectoryFragmentRoot(),
										content)), elementID, cache);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				//
				progressMonitor.worked(1);
			}
		}
	}

	/**
	 * <p>
	 * Parses a single class file.
	 * </p>
	 * 
	 * @param classFile
	 * @throws CoreException
	 */
	private void parseClassFile(InputStream inputStream,
			JavaElementIdentifier elementID, IResourceCache cache)
			throws CoreException {

		JavaElementIdentifier enclosingJavaElementIdentifier = elementID
				.getIdForEnclosingNonLocalAndNonAnonymousType();

		ResourceKey key = new ResourceKey(
				enclosingJavaElementIdentifier.getContentId(),
				enclosingJavaElementIdentifier.getRoot(),
				enclosingJavaElementIdentifier.getPath());

		// get the additional type info
		Resource resource = cache.getOrCreateResource(key);

		// get the fake manifest
		BundlorPartialManifest fakePartialManifest = new BundlorPartialManifest(
				elementID.getFullQualifiedName(),
				enclosingJavaElementIdentifier.getFullQualifiedName(), resource);

		try {

			// analyze the class file
			_analyser.analyse(inputStream, elementID.getFullQualifiedName(),
					fakePartialManifest);

			// TODO
			// _progressMonitor.worked(1);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

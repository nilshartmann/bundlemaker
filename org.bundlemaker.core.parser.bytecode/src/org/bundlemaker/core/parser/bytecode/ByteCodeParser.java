package org.bundlemaker.core.parser.bytecode;

import org.bundlemaker.core.parser.AbstractParser;
import org.bundlemaker.core.parser.IResourceCache;
import org.bundlemaker.core.parser.bytecode.asm.ArtefactAnalyserClassVisitor;
import org.bundlemaker.core.parser.bytecode.asm.AsmReferenceRecorder;
import org.bundlemaker.core.resource.IResourceKey;
import org.bundlemaker.core.resource.ResourceKey;
import org.bundlemaker.core.resource.modifiable.IModifiableResource;
import org.bundlemaker.core.util.JavaTypeUtils;
import org.eclipse.core.runtime.Assert;
import org.objectweb.asm.ClassReader;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ByteCodeParser extends AbstractParser {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ParserType getParserType() {
		return ParserType.BINARY;
	}

	@Override
	protected void parseResource(IResourceKey resourceKey, IResourceCache cache) {

		if (resourceKey.getPath().endsWith(".class")) {

			IModifiableResource resource = cache
					.getOrCreateResource(resourceKey);

			if (!resource.getContainedTypes().isEmpty()) {
				return;
			}

			IModifiableResource enclosingResource = resource;

			// get fully qualified type name
			String fullyQualifiedName = JavaTypeUtils
					.convertToFullyQualifiedName(resource.getPath());

			//
			if (JavaTypeUtils.isLocalOrAnonymousTypeName(fullyQualifiedName)) {

				String enclosingName = JavaTypeUtils
						.getEnclosingNonLocalAndNonAnonymousTypeName(fullyQualifiedName);

				ResourceKey enclosingKey = new ResourceKey(
						resourceKey.getContentId(), resourceKey.getRoot(),
						JavaTypeUtils
								.convertFromFullyQualifiedName(enclosingName));

				enclosingResource = cache.getOrCreateResource(enclosingKey);

				if (enclosingResource.getContainedTypes().isEmpty()) {
					parseResource(enclosingKey, cache);
					Assert.isTrue(!enclosingResource.getContainedTypes()
							.isEmpty());
				}
			}

			//
			try {

				AsmReferenceRecorder referenceRecorder = new AsmReferenceRecorder(
						resource, enclosingResource);

				ClassReader reader = new ClassReader(resource.getInputStream());
				reader.accept(new ArtefactAnalyserClassVisitor(
						referenceRecorder), 0);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
}

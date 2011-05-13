/**
 * 
 */
package org.bundlemaker.core.ui.editor;

import java.util.List;

import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.projectdescription.modifiable.IModifiableBundleMakerProjectDescription;
import org.bundlemaker.core.projectdescription.modifiable.IModifiableFileBasedContent;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * A wrapper around an {@link IBundleMakerProjectDescription} that filters either the binary resource content or the
 * type content
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 *         TODO is this class neccessary ???
 */
public class BundleMakerProjectDescriptionWrapper {

  private final IModifiableBundleMakerProjectDescription _description;

  public BundleMakerProjectDescriptionWrapper(IModifiableBundleMakerProjectDescription description) {
    Assert.isNotNull(description);
    _description = description;
  }

  public IModifiableFileBasedContent[] getContent() {
    List<? extends IModifiableFileBasedContent> fileBasedContent = _description.getModifiableFileBasedContent();

    //
    // List<IModifiableFileBasedContent> resourceContent = new LinkedList<IModifiableFileBasedContent>();
    // for (IModifiableFileBasedContent iFileBasedContent : fileBasedContent) {
    // if (iFileBasedContent.isResourceContent()) {
    // resourceContent.add(iFileBasedContent);
    // }
    // }
    //
    // for (IModifiableFileBasedContent iFileBasedContent : fileBasedContent) {
    // if (!iFileBasedContent.isResourceContent()) {
    // resourceContent.add(iFileBasedContent);
    // }
    // }

    return fileBasedContent.toArray(new IModifiableFileBasedContent[0]);

  }

}

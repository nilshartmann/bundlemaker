/**
 * 
 */
package org.bundlemaker.core.ui.editor;

import java.util.LinkedList;
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
 */
public class BundleMakerProjectDescriptionWrapper {

  private final IModifiableBundleMakerProjectDescription _description;

  private final boolean                                  _resources;

  private BundleMakerProjectDescriptionWrapper(IModifiableBundleMakerProjectDescription description, boolean resources) {
    _description = description;
    _resources = resources;
  }

  public static BundleMakerProjectDescriptionWrapper forResources(IModifiableBundleMakerProjectDescription description) {
    Assert.isNotNull(description);

    return new BundleMakerProjectDescriptionWrapper(description, true);
  }

  public static BundleMakerProjectDescriptionWrapper forTypes(IModifiableBundleMakerProjectDescription description) {
    Assert.isNotNull(description);

    return new BundleMakerProjectDescriptionWrapper(description, false);
  }

  public IModifiableFileBasedContent[] getContent() {
    List<? extends IModifiableFileBasedContent> fileBasedContent = _description.getModifiableFileBasedContent();
    List<IModifiableFileBasedContent> filteredContent = new LinkedList<IModifiableFileBasedContent>();
    for (IModifiableFileBasedContent iFileBasedContent : fileBasedContent) {
      if (iFileBasedContent.isResourceContent() == _resources) {
        filteredContent.add(iFileBasedContent);
      }
    }

    return filteredContent.toArray(new IModifiableFileBasedContent[0]);

  }

}

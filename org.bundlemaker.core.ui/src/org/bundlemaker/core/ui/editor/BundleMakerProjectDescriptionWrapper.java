/**
 * 
 */
package org.bundlemaker.core.ui.editor;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.projectdescription.IFileBasedContent;
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

  private final IBundleMakerProjectDescription _description;

  private final boolean                        _resources;

  private BundleMakerProjectDescriptionWrapper(IBundleMakerProjectDescription description, boolean resources) {
    _description = description;
    _resources = resources;
  }

  public static BundleMakerProjectDescriptionWrapper forResources(IBundleMakerProjectDescription description) {
    Assert.isNotNull(description);

    return new BundleMakerProjectDescriptionWrapper(description, true);
  }

  public static BundleMakerProjectDescriptionWrapper forTypes(IBundleMakerProjectDescription description) {
    Assert.isNotNull(description);

    return new BundleMakerProjectDescriptionWrapper(description, false);
  }

  public IFileBasedContent[] getContent() {
    List<? extends IFileBasedContent> fileBasedContent = _description.getFileBasedContent();
    List<IFileBasedContent> filteredContent = new LinkedList<IFileBasedContent>();
    for (IFileBasedContent iFileBasedContent : fileBasedContent) {
      if (iFileBasedContent.isResourceContent() == _resources) {
        filteredContent.add(iFileBasedContent);
      }
    }

    return filteredContent.toArray(new IFileBasedContent[0]);

  }

}

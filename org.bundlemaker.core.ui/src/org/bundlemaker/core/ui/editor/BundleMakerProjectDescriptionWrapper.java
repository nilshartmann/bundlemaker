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
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class BundleMakerProjectDescriptionWrapper {

  private final IFileBasedContent[] _content;

  public BundleMakerProjectDescriptionWrapper(IFileBasedContent[] content) {
    super();
    _content = content;
  }

  public static BundleMakerProjectDescriptionWrapper forResources(IBundleMakerProjectDescription description) {
    Assert.isNotNull(description);

    return new BundleMakerProjectDescriptionWrapper(getContent(description, true));
  }

  public static BundleMakerProjectDescriptionWrapper forTypes(IBundleMakerProjectDescription description) {
    Assert.isNotNull(description);

    return new BundleMakerProjectDescriptionWrapper(getContent(description, false));
  }

  private static IFileBasedContent[] getContent(IBundleMakerProjectDescription description, boolean resources) {
    List<? extends IFileBasedContent> fileBasedContent = description.getFileBasedContent();
    List<IFileBasedContent> filteredContent = new LinkedList<IFileBasedContent>();
    for (IFileBasedContent iFileBasedContent : fileBasedContent) {
      if (iFileBasedContent.isResourceContent() == resources) {
        filteredContent.add(iFileBasedContent);
      }
    }

    System.out.println("getContent (" + resources + "): " + filteredContent);

    return filteredContent.toArray(new IFileBasedContent[0]);

  }

  public IFileBasedContent[] getContent() {
    return _content;
  }

}

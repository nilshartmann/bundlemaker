package org.bundlemaker.core.ui.mvn;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.mvn.content.MvnContentProvider;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ReloadFromRepositoryAction extends AbstractMvnProjectContentProviderEditorAction {

  /** - */
  private boolean             _useRemote;

  /** - */
  private IBundleMakerProject _bundleMakerProject;

  /**
   * <p>
   * Creates a new instance of type {@link ReloadFromLocalRepositoryAction}.
   * </p>
   * 
   */
  public ReloadFromRepositoryAction(String text, MvnProjectContentProviderEditor editor,
      IBundleMakerProject bundleMakerProject, boolean useRemote) {
    super(text, editor);
    
    Assert.isNotNull(bundleMakerProject);

    //
    _useRemote = useRemote;
    _bundleMakerProject = bundleMakerProject;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doWithMvnContentProvider(MvnContentProvider contentProvider) throws Exception {
    contentProvider.reloadContent(_useRemote, _bundleMakerProject);
  }
}

package org.bundlemaker.core.ui.mvn;

import org.bundlemaker.core.mvn.content.MvnContentProvider;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ReloadFromRepositoryAction extends AbstractMvnProjectContentProviderEditorAction {

  /** - */
  private boolean _useRemote;

  /**
   * <p>
   * Creates a new instance of type {@link ReloadFromLocalRepositoryAction}.
   * </p>
   * 
   */
  public ReloadFromRepositoryAction(String text, MvnProjectContentProviderEditor editor, boolean useRemote) {
    super(text, editor);

    //
    _useRemote = useRemote;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doWithMvnContentProvider(MvnContentProvider contentProvider) throws Exception {
    contentProvider.reloadContent(_useRemote);
  }
}

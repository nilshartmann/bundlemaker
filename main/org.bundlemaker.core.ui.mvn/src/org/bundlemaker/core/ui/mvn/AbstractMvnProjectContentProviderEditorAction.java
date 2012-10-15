package org.bundlemaker.core.ui.mvn;

import org.bundlemaker.core.mvn.content.MvnContentProvider;
import org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditorElement;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressService;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractMvnProjectContentProviderEditorAction extends Action {

  /** the editor */
  private MvnProjectContentProviderEditor _editor;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractMvnProjectContentProviderEditorAction}.
   * </p>
   * 
   * @param text
   * @param editor
   */
  public AbstractMvnProjectContentProviderEditorAction(String text, MvnProjectContentProviderEditor editor) {
    super(text);

    // assert
    Assert.isNotNull(editor);

    //
    _editor = editor;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void run() {

    // get the progress services
    IProgressService ps = PlatformUI.getWorkbench().getProgressService();

    //
    try {
      ps.busyCursorWhile(new IRunnableWithProgress() {
        public void run(IProgressMonitor pm) {

          for (IProjectContentProviderEditorElement element : _editor.getCurrentSelection()) {

            //
            try {
              doWithMvnContentProvider((MvnContentProvider) element.getProjectContentProvider());
            } catch (Exception e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          }
        }
      });
    } catch (Exception e) {

      // TODO
      MessageDialog dialog = new MessageDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "My Title",
          null,
          "My message", MessageDialog.ERROR, new String[] { "First",
              "Second", "Third" }, 0);

      dialog.open();
    }
  }

  /**
   * <p>
   * Must be overridden to 
   * </p>
   * 
   * @param element
   */
  protected abstract void doWithMvnContentProvider(MvnContentProvider element) throws Exception;
}

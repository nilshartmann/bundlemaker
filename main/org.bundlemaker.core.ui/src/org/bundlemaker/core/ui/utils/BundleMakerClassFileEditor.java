package org.bundlemaker.core.ui.utils;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaModelStatusConstants;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.internal.corext.util.Messages;
import org.eclipse.jdt.internal.ui.JavaPlugin;
import org.eclipse.jdt.internal.ui.JavaUIStatus;
import org.eclipse.jdt.internal.ui.javaeditor.IClassFileEditorInput;
import org.eclipse.jdt.internal.ui.javaeditor.JavaEditor;
import org.eclipse.jdt.internal.ui.javaeditor.JavaSourceViewer;
import org.eclipse.jdt.internal.ui.javaeditor.SemanticHighlightingReconciler;
import org.eclipse.jdt.ui.SharedASTProvider;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.IWidgetTokenKeeper;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.IDocumentProvider;

public class BundleMakerClassFileEditor extends JavaEditor {

  public static String ID = BundleMakerClassFileEditor.class.getName();

  /**
   * Default constructor.
   */
  public BundleMakerClassFileEditor() {
    setDocumentProvider(JavaPlugin.getDefault().getClassFileDocumentProvider());
    //    setEditorContextMenuId("#ClassFileEditorContext"); //$NON-NLS-1$
    setRulerContextMenuId("#ClassFileRulerContext"); //$NON-NLS-1$
    setOutlinerContextMenuId("#ClassFileOutlinerContext"); //$NON-NLS-1$
    // don't set help contextId, we install our own help context
  }

  @Override
  protected IMenuListener createContextMenuListener() {
    return new IMenuListener() {
      @Override
      public void menuAboutToShow(IMenuManager manager) {
        // do nothing
      }
    };
  }

  /*
   * @see JavaEditor#getElementAt(int)
   */
  @Override
  protected IJavaElement getElementAt(int offset) {
    if (getEditorInput() instanceof IClassFileEditorInput) {
      try {
        IClassFileEditorInput input = (IClassFileEditorInput) getEditorInput();
        return input.getClassFile().getElementAt(offset);
      } catch (JavaModelException x) {
      }
    }
    return null;
  }

  @Override
  protected void createActions() {
    //
  }

  /*
   * @see JavaEditor#getCorrespondingElement(IJavaElement)
   */
  @Override
  protected IJavaElement getCorrespondingElement(IJavaElement element) {
    if (getEditorInput() instanceof IClassFileEditorInput) {
      IClassFileEditorInput input = (IClassFileEditorInput) getEditorInput();
      IJavaElement parent = element.getAncestor(IJavaElement.CLASS_FILE);
      if (input.getClassFile().equals(parent))
        return element;
    }
    return null;
  }

  @Override
  public boolean isBreadcrumbActive() {
    return false;
  }

  @Override
  protected boolean isBreadcrumbShown() {
    return false;
  }

  @Override
  public boolean isEditable() {
    return false;
  }

  @Override
  public boolean isEditorInputReadOnly() {
    return true;
  }

  /*
   * @see AbstractTextEditor#doSetInput(IEditorInput)
   */
  @Override
  protected void doSetInput(IEditorInput input) throws CoreException {
    uninstallOccurrencesFinder();

    if (!(input instanceof IClassFileEditorInput)) {
      String inputClassName = input != null ? input.getClass().getName() : "null"; //$NON-NLS-1$
      String message = Messages.format("", inputClassName);
      throw new CoreException(JavaUIStatus.createError(IJavaModelStatusConstants.INVALID_RESOURCE_TYPE, message, null));
    }

    JavaModelException e = probeInputForSource(input);
    if (e != null) {
      IClassFileEditorInput classFileEditorInput = (IClassFileEditorInput) input;
      IClassFile file = classFileEditorInput.getClassFile();
      IJavaProject javaProject = file.getJavaProject();
      if (!javaProject.exists() || !javaProject.isOnClasspath(file)) {
        throw new CoreException(JavaUIStatus.createError(IJavaModelStatusConstants.INVALID_RESOURCE, "", null));
      } else {
        throw e;
      }
    }

    IDocumentProvider documentProvider = getDocumentProvider();

    super.doSetInput(input);

    documentProvider = getDocumentProvider();

    if (fSemanticManager != null)
      installSemanticHighlighting();

  }

  /*
   * @see org.eclipse.jdt.internal.ui.javaeditor.JavaEditor#installSemanticHighlighting()
   * 
   * @since 3.7
   */
  @Override
  protected void installSemanticHighlighting() {
    super.installSemanticHighlighting();
    Job job = new Job("") {
      /*
       * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
       * 
       * @since 3.0
       */
      @Override
      protected IStatus run(IProgressMonitor monitor) {
        CompilationUnit ast = SharedASTProvider.getAST(getInputJavaElement(), SharedASTProvider.WAIT_YES, null);
        if (fOverrideIndicatorManager != null)
          // fOverrideIndicatorManager.reconciled(ast, true, monitor);
          if (fSemanticManager != null) {
            SemanticHighlightingReconciler reconciler = fSemanticManager.getReconciler();
            if (reconciler != null)
              reconciler.reconciled(ast, false, monitor);
          }
        if (isMarkingOccurrences())
          installOccurrencesFinder(false);
        return Status.OK_STATUS;
      }
    };
    job.setPriority(Job.DECORATE);
    job.setSystem(true);
    job.schedule();
  }

  private JavaModelException probeInputForSource(IEditorInput input) {
    if (input == null)
      return null;

    IClassFileEditorInput classFileEditorInput = (IClassFileEditorInput) input;
    IClassFile file = classFileEditorInput.getClassFile();

    try {
      file.getSourceRange();
    } catch (JavaModelException e) {
      return e;
    }

    return null;
  }

  /*
   * @see JavaEditor#createJavaSourceViewer(Composite, IVerticalRuler, int)
   */
  protected ISourceViewer createJavaSourceViewer(Composite parent, IVerticalRuler ruler, int styles,
      IPreferenceStore store) {
    return new JavaSourceViewer(parent, ruler, null, false, styles, store) {

      @Override
      public boolean requestWidgetToken(IWidgetTokenKeeper requester) {
        if (PlatformUI.getWorkbench().getHelpSystem().isContextHelpDisplayed())
          return false;
        return super.requestWidgetToken(requester);
      }

      @Override
      public boolean requestWidgetToken(IWidgetTokenKeeper requester, int priority) {
        if (PlatformUI.getWorkbench().getHelpSystem().isContextHelpDisplayed())
          return false;
        return super.requestWidgetToken(requester, priority);
      }
    };
  }
}
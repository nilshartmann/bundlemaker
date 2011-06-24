package org.bundlemaker.core.transformations.dsl.ui.quickfix;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.transformations.dsl.ui.contentassist.TransformationDslProposalProvider;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.xtext.ui.editor.model.edit.IModification;
import org.eclipse.xtext.ui.editor.model.edit.IModificationContext;
import org.eclipse.xtext.ui.editor.quickfix.DefaultQuickfixProvider;
import org.eclipse.xtext.ui.editor.quickfix.Fix;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionAcceptor;
import org.eclipse.xtext.validation.Issue;

public class TransformationDslQuickfixProvider extends DefaultQuickfixProvider {

  @Fix("BMProjectNotOpen")
  public void openBundleMakerProject(final Issue issue, IssueResolutionAcceptor acceptor) {
    URI uriToProblem = issue.getUriToProblem();
    final IBundleMakerProject bundleMakerProject = TransformationDslProposalProvider
        .getBundleMakerProject(uriToProblem);
    if (bundleMakerProject != null) {
    String projectName = bundleMakerProject.getProject().getName();
    acceptor.accept(issue, "Open " + projectName, "Open BundleMaker project " + projectName, "upcase.png",
        new IModification() {
          public void apply(IModificationContext context) throws BadLocationException {
            if (bundleMakerProject != null) {
              openProject(bundleMakerProject);
            }

          }
        });
    }
  }

  private void openProject(final IBundleMakerProject project) {
    try {
      PlatformUI.getWorkbench().getProgressService().busyCursorWhile(new IRunnableWithProgress() {
        public void run(final IProgressMonitor monitor) {
          try {
            project.initialize(monitor);
            project.parseAndOpen(monitor);
          } catch (Exception ex) {
            ex.printStackTrace();
          }
        }
      });
    } catch (Exception ex) {
      ex.printStackTrace();
    }

  }

  // @Fix(MyJavaValidator.INVALID_NAME)
  // public void capitalizeName(final Issue issue, IssueResolutionAcceptor acceptor) {
  // acceptor.accept(issue, "Capitalize name", "Capitalize the name.", "upcase.png", new IModification() {
  // public void apply(IModificationContext context) throws BadLocationException {
  // IXtextDocument xtextDocument = context.getXtextDocument();
  // String firstLetter = xtextDocument.get(issue.getOffset(), 1);
  // xtextDocument.replace(issue.getOffset(), 1, firstLetter.toUpperCase());
  // }
  // });
  // }

}

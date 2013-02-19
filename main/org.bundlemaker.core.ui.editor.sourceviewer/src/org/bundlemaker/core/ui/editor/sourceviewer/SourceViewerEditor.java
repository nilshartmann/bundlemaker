package org.bundlemaker.core.ui.editor.sourceviewer;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.internal.ui.JavaPlugin;
import org.eclipse.jdt.ui.PreferenceConstants;
import org.eclipse.jdt.ui.text.JavaTextTools;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.texteditor.SourceViewerDecorationSupport;

/**
 */
public class SourceViewerEditor extends EditorPart {

  /** - */
  private SourceViewer _sourceViewer;

  /** - */
  private IDocument    _document;

  /**
   * {@inheritDoc}
   */
  public void init(IEditorSite site, IEditorInput input) throws PartInitException {

    System.out.println("init" + site + " , " + input);

    //
    setSite(site);
    setInput(input);

    // //
    // IResourceArtifactEditorInput fileEditorInput = (IResourceArtifactEditorInput) input
    // .getAdapter(IFileEditorInput.class);

    ResourceArtifactEditorInput editorInput = (ResourceArtifactEditorInput) input;

    // try {

    //
    String content = new String(editorInput.getResourceArtifact().getAssociatedResource().getContent());

    //
    _document = new Document();
    _document.set(content);

    setPartName(editorInput.getName());
    
    // } catch (CoreException e) {
    // e.printStackTrace();
    // }
  }

  /**
   * {@inheritDoc}
   */
  public void createPartControl(Composite parent) {

    System.out.println("createPartControl" + parent);

    //
    _sourceViewer = new SourceViewer(parent, null, SWT.V_SCROLL | SWT.H_SCROLL);
    _sourceViewer.setEditable(false);

    // set up font
    Font font = JFaceResources.getFont(PreferenceConstants.EDITOR_TEXT_FONT);
    _sourceViewer.getTextWidget().setFont(font);

    // Setting up the Java Syntax Highlighting
    JavaTextTools tools = JavaPlugin.getDefault().getJavaTextTools();
    tools.setupJavaDocumentPartitioner(_document);

    //
    CustomJavaSourceViewerConfiguration config = new CustomJavaSourceViewerConfiguration(
        tools.getColorManager(), JavaPlugin.getDefault().getCombinedPreferenceStore(),
        null, null);

    //
    _sourceViewer.configure(config);
    _sourceViewer.setDocument(_document);
  }

  //
  // This stuff below is just needed to make the EditorPart happy
  //

  public void doSave(IProgressMonitor monitor) {
  }

  public void doSaveAs() {
  }

  public boolean isDirty() {
    return false;
  }

  public boolean isSaveAsAllowed() {
    return false;
  }

  public void setFocus() {
    //
  }

  public static String convertStreamToString(java.io.InputStream is) {
    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
    String result = s.hasNext() ? s.next() : "";
    try {
      is.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return result;
  }
}

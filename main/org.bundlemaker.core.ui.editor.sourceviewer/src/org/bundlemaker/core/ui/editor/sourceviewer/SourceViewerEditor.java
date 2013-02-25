package org.bundlemaker.core.ui.editor.sourceviewer;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.modules.modifiable.IMovableUnit;
import org.bundlemaker.core.modules.modifiable.MovableUnit;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.ui.artifact.cnf.ResourceArtifactEditorInput;
import org.bundlemaker.core.ui.editor.sourceviewer.referencedetail.IReferenceDetailParser;
import org.bundlemaker.core.ui.editor.sourceviewer.referencedetail.ReferenceDetailParser;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.internal.ui.JavaPlugin;
import org.eclipse.jdt.ui.PreferenceConstants;
import org.eclipse.jdt.ui.text.IColorManager;
import org.eclipse.jdt.ui.text.JavaSourceViewerConfiguration;
import org.eclipse.jdt.ui.text.JavaTextTools;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.IOverviewRuler;
import org.eclipse.jface.text.source.ISharedTextColors;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.LineNumberRulerColumn;
import org.eclipse.jface.text.source.OverviewRuler;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.internal.editors.text.EditorsPlugin;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.texteditor.AnnotationPreference;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.SourceViewerDecorationSupport;

/**
 */
public class SourceViewerEditor extends EditorPart {

  /** - */
  private SourceViewer                _sourceViewer;

  /** - */
  private IDocument                   _document;

  /** - */
  private IResourceArtifact           _resourceArtifact;

  /** - */
  private Map<String, List<Position>> _positions;

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IResourceArtifact getResourceArtifact() {
    return _resourceArtifact;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Map<String, List<Position>> getPositions() {
    return _positions;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IDocument getDocument() {
    return _document;
  }

  /**
   * {@inheritDoc}
   */
  public void init(IEditorSite site, IEditorInput input) throws PartInitException {
    //
    setSite(site);
    setInput(input);
  }

  /**
   * {@inheritDoc}
   */
  public void createPartControl(Composite parent) {

    String ANNO_TYPE = "com.mycompany.element";
    String ANNO_KEY_HIGHLIGHT = "annotateElemHighlight";
    String ANNO_KEY_OVERVIEW = "annotateElemOverviewRuler";
    String ANNO_KEY_VERTICAL = "annotateElemVertialRuler";
    String ANNO_KEY_TEXT = "annotateElemText";
    String ANNO_KEY_COLOR = "annotateElemColor";

    int VERTICAL_RULER_WIDTH = 12;

    int styles = SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION;
    ISharedTextColors sharedColors = EditorsPlugin.getDefault().getSharedTextColors();
    IOverviewRuler overviewRuler = new OverviewRuler(null, VERTICAL_RULER_WIDTH, sharedColors);
    CompositeRuler ruler = new CompositeRuler(VERTICAL_RULER_WIDTH);

    /***********/

    _document = new Document();

    AnnotationModel _annotationModel = new AnnotationModel();
    _annotationModel.connect(_document);

    ResourceArtifactEditorInput editorInput = (ResourceArtifactEditorInput) getEditorInput();

    _resourceArtifact = editorInput.getResourceArtifact();

    //
    _document.set(new String(editorInput.getResourceArtifact().getAssociatedResource().getContent()));

    setPartName(editorInput.getName());

    _sourceViewer = new SourceViewer(parent, null, SWT.V_SCROLL | SWT.H_SCROLL);
    _sourceViewer.setEditable(false);

    // set up font
    Font font = JFaceResources.getFont(PreferenceConstants.EDITOR_TEXT_FONT);
    _sourceViewer.getTextWidget().setFont(font);

    // Setting up the Java Syntax Highlighting
    JavaTextTools tools = JavaPlugin.getDefault().getJavaTextTools();
    tools.setupJavaDocumentPartitioner(_document);

    //
    CustomJavaSourceViewerConfiguration config = new CustomJavaSourceViewerConfiguration(tools.getColorManager(),
        JavaPlugin.getDefault().getCombinedPreferenceStore(), null, null);
    _sourceViewer.configure(config);

    /***********/

    SourceViewerDecorationSupport _sds = new SourceViewerDecorationSupport(_sourceViewer, overviewRuler, null,
        sharedColors);

    AnnotationPreference ap = new AnnotationPreference();
    ap.setColorPreferenceKey(ANNO_KEY_COLOR);
    ap.setHighlightPreferenceKey(ANNO_KEY_HIGHLIGHT);
    ap.setVerticalRulerPreferenceKey(ANNO_KEY_VERTICAL);
    ap.setOverviewRulerPreferenceKey(ANNO_KEY_OVERVIEW);
    ap.setTextPreferenceKey(ANNO_KEY_TEXT);
    ap.setAnnotationType(ANNO_TYPE);
    _sds.setAnnotationPreference(ap);

    _sds.install(EditorsPlugin.getDefault().getPreferenceStore());

    _sourceViewer.setDocument(_document, _annotationModel);
    _sourceViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

    ruler.addDecorator(0, new LineNumberRulerColumn());

    //
    IReferenceDetailParser detailParser = new ReferenceDetailParser();
    IResource resource = _resourceArtifact.getAssociatedResource();
    IMovableUnit movableUnit = MovableUnit.createFromResource(resource, _resourceArtifact.getModularizedSystem());
    IResource sourceResource = movableUnit.getAssociatedBinaryResources().get(0);
    _positions = detailParser.parseReferencePositions(sourceResource, _resourceArtifact.getModularizedSystem());

    //
    // for (Entry<String, List<Position>> entry : _positions.entrySet()) {
    //
    // for (Position position : entry.getValue()) {
    //
    // //
    // Annotation annotation = new Annotation(false);
    // annotation.setType(ANNO_TYPE);
    // _annotationModel.addAnnotation(annotation, position);
    // }
    // }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void doSave(IProgressMonitor monitor) {
    // empty implementation
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void doSaveAs() {
    // empty implementation
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDirty() {
    // empty implementation
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isSaveAsAllowed() {
    // empty implementation
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFocus() {
    // empty implementation
  }

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  class CustomJavaSourceViewerConfiguration extends JavaSourceViewerConfiguration {

    /**
     * <p>
     * </p>
     * 
     * @param colorManager
     * @param preferenceStore
     * @param editor
     * @param partitioning
     */
    public CustomJavaSourceViewerConfiguration(IColorManager colorManager, IPreferenceStore preferenceStore,
        ITextEditor editor, String partitioning) {
      super(colorManager, preferenceStore, editor, partitioning);
    }

    @Override
    public IHyperlinkDetector[] getHyperlinkDetectors(ISourceViewer sourceViewer) {
      return new IHyperlinkDetector[] { new ReferenceHyperlinkDetector(SourceViewerEditor.this) };
    }
  }
}

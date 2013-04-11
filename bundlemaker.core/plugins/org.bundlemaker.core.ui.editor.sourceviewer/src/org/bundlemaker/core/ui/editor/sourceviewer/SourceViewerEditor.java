package org.bundlemaker.core.ui.editor.sourceviewer;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bundlemaker.core.analysis.AnalysisModelQueries;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.modules.IMovableUnit;
import org.bundlemaker.core.modules.MovableUnit;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.selection.IDependencySelection;
import org.bundlemaker.core.selection.Selection;
import org.bundlemaker.core.ui.artifact.cnf.ResourceArtifactEditorInput;
import org.bundlemaker.core.ui.editor.sourceviewer.referencedetail.IReferenceDetailParser;
import org.bundlemaker.core.ui.editor.sourceviewer.referencedetail.ReferenceDetailParser;
import org.bundlemaker.core.ui.event.selection.workbench.editor.AbstractDependencySelectionAwareEditorPart;
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
import org.eclipse.ui.internal.editors.text.EditorsPlugin;
import org.eclipse.ui.texteditor.AnnotationPreference;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.SourceViewerDecorationSupport;

/**
 */
public class SourceViewerEditor extends AbstractDependencySelectionAwareEditorPart {

  /** - */
  private String                      PROVIDER_ID = "org.bundlemaker.core.ui.editor.sourceviewer";

  /** - */
  private SourceViewer                _sourceViewer;

  /** - */
  private IDocument                   _document;

  /** - */
  private IResourceArtifact           _resourceArtifact;

  /** - */
  private Map<String, List<Position>> _positions;

  /** - */
  private AnnotationModel             _annotationModel;

  /** - */
  private Set<String>                 _dependencies;

  /**
   * <p>
   * Creates a new instance of type {@link SourceViewerEditor}.
   * </p>
   */
  public SourceViewerEditor() {

    //
    _dependencies = new HashSet<String>();
  }

  @Override
  public void analysisModelModified() {
    // TODO Auto-generated method stub
  }

  @Override
  public void onPartBroughtToTop() {

    //
    if (!getCurrentDependencySelection().hasDependencies()
        || getCurrentDependencySelection().getProviderId().equals(PROVIDER_ID)) {

      Selection
          .instance()
          .getDependencySelectionService()
          .setSelection(Selection.MAIN_DEPENDENCY_SELECTION_ID, PROVIDER_ID,
              AnalysisModelQueries.getCoreDependencies(_resourceArtifact.getDependenciesTo()));

      setCurrentDependencies(Selection.instance().getDependencySelectionService()
          .getSelection(Selection.MAIN_DEPENDENCY_SELECTION_ID));
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected String getSelectionId() {
    return Selection.DETAIL_DEPENDENCY_SELECTION_ID;
  }

  @Override
  protected void onSetDependencySelection(IDependencySelection dependencySelection) {
    super.onSetDependencySelection(dependencySelection);

    //
    recomputeAnnotationModel();
  }

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
  public void createPartControl(Composite parent) {

    String ANNO_KEY_HIGHLIGHT = "org.bundlemaker.core.ui.reference.highlight";
    String ANNO_KEY_OVERVIEW = "org.bundlemaker.core.ui.reference.overview";
    String ANNO_KEY_VERTICAL = "org.bundlemaker.core.ui.reference.ruler";
    String ANNO_KEY_TEXT = "org.bundlemaker.core.ui.reference.text";
    String ANNO_KEY_COLOR = "org.bundlemaker.core.ui.reference.color";

    int VERTICAL_RULER_WIDTH = 12;

    int styles = SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION;
    ISharedTextColors sharedColors = EditorsPlugin.getDefault().getSharedTextColors();
    IOverviewRuler overviewRuler = new OverviewRuler(null, VERTICAL_RULER_WIDTH, sharedColors);
    CompositeRuler ruler = new CompositeRuler(VERTICAL_RULER_WIDTH);

    /***********/

    _document = new Document();

    _annotationModel = new AnnotationModel();
    _annotationModel.connect(_document);

    ResourceArtifactEditorInput editorInput = (ResourceArtifactEditorInput) getEditorInput();

    _resourceArtifact = editorInput.getResourceArtifact();

    //
    _document.set(readEditorInput(editorInput));

    _sourceViewer = new SourceViewer(parent, null, overviewRuler, true, SWT.V_SCROLL | SWT.H_SCROLL);
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
    ap.setAnnotationType(ReferenceAnnotation.TYPE);
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
    recomputeAnnotationModel();
  }

  /**
   * <p>
   * </p>
   * 
   */
  private void recomputeAnnotationModel() {

    //
    _dependencies.clear();

    //
    if (getCurrentDependencySelection() != null) {

      //
      for (IDependency dependency : AnalysisModelQueries.getCoreDependencies(getCurrentDependencySelection()
          .getSelectedDependencies())) {

        //
        _dependencies.add(dependency.getTo().getQualifiedName());
      }
    }
    //
    _annotationModel.removeAllAnnotations();

    //
    for (Entry<String, List<Position>> entry : _positions.entrySet()) {

      if (_dependencies.isEmpty() || _dependencies.contains(entry.getKey())) {

        //
        for (Position position : entry.getValue()) {

          //
          _annotationModel.addAnnotation(new ReferenceAnnotation(position, entry.getKey()), position);
        }
      }
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param editorInput
   * @return
   */
  private String readEditorInput(ResourceArtifactEditorInput editorInput) {

    //
    IResource resource = editorInput.getResourceArtifact().hasAssociatedSourceResource() ? editorInput
        .getResourceArtifact().getAssociatedSourceResource() : editorInput.getResourceArtifact()
        .getAssociatedResource();

    //
    setPartName(resource.getName());

    //
    return new String(resource.getContent());
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

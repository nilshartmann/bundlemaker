package org.bundlemaker.core.ui.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.AnnotationModelEvent;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.IAnnotationModelExtension;
import org.eclipse.jface.text.source.IAnnotationModelListener;
import org.eclipse.jface.text.source.IAnnotationModelListenerExtension;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public final class ReferenceAnnotationModel implements IAnnotationModel {

  /** REFERENCE_ANNOTATION_MODEL_KEY */
  private static final String            REFERENCE_ANNOTATION_MODEL_KEY = "REFERENCE_ANNOTATION_MODEL_KEY";

  /** List of current ReferenceAnnotation objects */
  private List<ReferenceAnnotation>      _annotations                   = new ArrayList<ReferenceAnnotation>(32);

  /** - */
  private final IDocument                _document;

  /** - */
  private Map<String, List<Position>>    _references;

  /** List of registered IAnnotationModelListener */
  private List<IAnnotationModelListener> _listeners                     = new ArrayList<IAnnotationModelListener>(2);

  /**
   * <p>
   * Creates a new instance of type {@link ReferenceAnnotationModel}.
   * </p>
   * 
   * @param editor
   * @param document
   * @param references
   */
  private ReferenceAnnotationModel(ITextEditor editor, IDocument document, Map<String, List<Position>> references) {
    this._document = document;
    _references = references;

    createAnnotations();
  }

  /**
   * <p>
   * </p>
   * 
   * @param editor
   * @param fullyQualifiedName
   * @param references
   */
  public static void attach(ITextEditor editor, String fullyQualifiedName, List<Position> references) {

    //
    Map<String, List<Position>> referencesMap = new HashMap<String, List<Position>>();

    //
    referencesMap.put(fullyQualifiedName, references);

    //
    attach(editor, referencesMap);
  }

  /**
   * Attaches a coverage annotation model for the given editor if the editor can be annotated. Does nothing if the model
   * is already attached.
   * 
   * @param editor
   *          Editor to attach a annotation model to
   */
  public static void attach(ITextEditor editor, Map<String, List<Position>> references) {
    IDocumentProvider provider = editor.getDocumentProvider();

    if (provider == null)
      return;

    IAnnotationModel model = provider.getAnnotationModel(editor.getEditorInput());

    if (!(model instanceof IAnnotationModelExtension))
      return;

    //
    IAnnotationModelExtension modelExtension = (IAnnotationModelExtension) model;

    //
    if (modelExtension.getAnnotationModel(REFERENCE_ANNOTATION_MODEL_KEY) != null) {
      modelExtension.removeAnnotationModel(REFERENCE_ANNOTATION_MODEL_KEY);
    }

    //
    IDocument document = provider.getDocument(editor.getEditorInput());
    modelExtension.addAnnotationModel(REFERENCE_ANNOTATION_MODEL_KEY, new ReferenceAnnotationModel(editor, document,
        references));
  }

  /**
   * Detaches the coverage annotation model from the given editor. If the editor does not have a model attached, this
   * method does nothing.
   * 
   * @param editor
   *          Editor to detach the annotation model from
   */
  public static void detach(ITextEditor editor) {
    IDocumentProvider provider = editor.getDocumentProvider();
    // there may be text editors without document providers (SF #1725100)
    if (provider == null)
      return;
    IAnnotationModel model = provider.getAnnotationModel(editor.getEditorInput());
    if (!(model instanceof IAnnotationModelExtension))
      return;
    IAnnotationModelExtension modelex = (IAnnotationModelExtension) model;
    modelex.removeAnnotationModel(REFERENCE_ANNOTATION_MODEL_KEY);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addAnnotationModelListener(IAnnotationModelListener listener) {
    if (!_listeners.contains(listener)) {
      _listeners.add(listener);
      fireModelChanged(new AnnotationModelEvent(this, true));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeAnnotationModelListener(IAnnotationModelListener listener) {
    _listeners.remove(listener);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void connect(IDocument document) {
    if (this._document != document)
      throw new RuntimeException("Can't connect to different document."); //$NON-NLS-1$
    for (final ReferenceAnnotation ca : _annotations) {
      try {
        document.addPosition(ca.getPosition());
      } catch (BadLocationException ex) {
        ex.printStackTrace();
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void disconnect(IDocument document) {
    if (this._document != document)
      throw new RuntimeException("Can't disconnect from different document."); //$NON-NLS-1$
    for (final ReferenceAnnotation ca : _annotations) {
      document.removePosition(ca.getPosition());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addAnnotation(Annotation annotation, Position position) {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeAnnotation(Annotation annotation) {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterator<?> getAnnotationIterator() {
    return _annotations.iterator();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Position getPosition(Annotation annotation) {
    if (annotation instanceof ReferenceAnnotation) {
      return ((ReferenceAnnotation) annotation).getPosition();
    } else {
      return null;
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param event
   */
  private void fireModelChanged(AnnotationModelEvent event) {
    event.markSealed();
    if (!event.isEmpty()) {
      for (final IAnnotationModelListener l : _listeners) {
        if (l instanceof IAnnotationModelListenerExtension) {
          ((IAnnotationModelListenerExtension) l).modelChanged(event);
        } else {
          l.modelChanged(this);
        }
      }
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param event
   */
  private void clear(AnnotationModelEvent event) {
    for (final ReferenceAnnotation ca : _annotations) {
      event.annotationRemoved(ca, ca.getPosition());
    }
    _annotations.clear();
  }

  /**
   * <p>
   * </p>
   */
  private void createAnnotations() {
    //
    AnnotationModelEvent event = new AnnotationModelEvent(this);
    clear(event);

    //
    for (Map.Entry<String, List<Position>> entry : _references.entrySet()) {

      for (Position position : entry.getValue()) {
        ReferenceAnnotation ca = new ReferenceAnnotation(position.offset, position.length, entry.getKey());
        _annotations.add(ca);
        event.annotationAdded(ca);
      }
    }

    fireModelChanged(event);
  }
}

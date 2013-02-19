package org.bundlemaker.core.ui.editor.sourceviewer.old;

import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ReferenceAnnotation extends Annotation {

  /** the TYPE of this reference annotation */
  private static final String TYPE = "org.bundlemaker.core.ui.referencemarker";

  /** the position of the reference */
  private final Position      _position;

  /**
   * <p>
   * Creates a new instance of type {@link ReferenceAnnotation}.
   * </p>
   * 
   * @param offset
   * @param length
   * @param fullyQualifiedName
   */
  public ReferenceAnnotation(int offset, int length, String fullyQualifiedName) {
    super(TYPE, false, fullyQualifiedName);

    //
    _position = new Position(offset, length);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Position getPosition() {
    return _position;
  }
}

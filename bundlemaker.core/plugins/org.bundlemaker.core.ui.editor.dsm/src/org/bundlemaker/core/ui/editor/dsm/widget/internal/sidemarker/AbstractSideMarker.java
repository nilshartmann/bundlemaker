package org.bundlemaker.core.ui.editor.dsm.widget.internal.sidemarker;

import org.bundlemaker.core.ui.editor.dsm.widget.IDsmColorScheme;
import org.bundlemaker.core.ui.editor.dsm.widget.IDsmContentProvider;
import org.bundlemaker.core.ui.editor.dsm.widget.internal.util.BoxSize;
import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontMetrics;

/**
 * <p>
 * Abstract base class for all side marker implementations.
 * </p>
 *
 * @author  Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
/**
 * <p>
 * </p>
 *
 * @author  Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 */
public abstract class AbstractSideMarker extends Figure {

  /** - */
	private IDsmContentProvider _contentProvider;

  /** - */
	private ILabelProvider _labelProvider;

  /** - */
	private IDsmColorScheme _colorScheme;

  /** the marked item */
	private int _markedItem = -1;

  /** - */
	private FontMetrics _fontMetrics;

  /** - */
	private BoxSize _boxSize;

	/**
   * <p>
   * Creates a new instance of type {@link AbstractSideMarker}.
   * </p>
	 *
	 * @param  contentProvider
	 * @param  cycleDetector
	 * @param  labelProvider
	 * @param  colorScheme
	 */
  public AbstractSideMarker(IDsmContentProvider contentProvider, ILabelProvider labelProvider,
      IDsmColorScheme colorScheme) {

		//
		Assert.isNotNull(contentProvider);
		Assert.isNotNull(labelProvider);
		Assert.isNotNull(colorScheme);

		//
		_contentProvider = contentProvider;
		_labelProvider = labelProvider;
		_colorScheme = colorScheme;

		//
		_boxSize = new BoxSize();

		//
		addMouseListener(
			new MouseListener.Stub() {

				@Override
				public void mousePressed(MouseEvent me) {
					onMousePressed(me);
				}

				@Override
				public void mouseReleased(MouseEvent me) {

					if ((me.getState() & SWT.ALT) != 0) {
						System.out.println("ALT");
					}

					if ((me.getState() & SWT.SHIFT) != 0) {
						System.out.println("SHIFT");
					}

					if ((me.getState() & SWT.CONTROL) != 0) {
						System.out.println("CONTROL");
					}

					onMouseReleased(me);
				}
    });
			}

	public ILabelProvider getLabelProvider() {
		return _labelProvider;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 */
	public BoxSize getBoxSize() {
		return _boxSize;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param  boxSize
	 */
	public void setBoxSize(BoxSize boxSize) {

		Assert.isNotNull(boxSize);

		_boxSize = boxSize;
	}

	protected void onMousePressed(MouseEvent me) {
	}

	protected abstract void onMouseReleased(MouseEvent me);

	/**
	 * {@inheritDoc}
	 */
	public final void mark(int i) {

		int oldValue = _markedItem;
		_markedItem = i;

		onMark(oldValue, _markedItem);
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 */
	public IDsmContentProvider getContentProvider() {
		return _contentProvider;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param  oldValue
	 * @param  x
	 */
	protected abstract void onMark(int oldValue, int x);

	/**
	 * <p>
	 * </p>
   * 
	 */
	protected abstract void resetSize();

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 */
	protected IDsmColorScheme getColorScheme() {
		return _colorScheme;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param   count
	 * @return
	 */
	protected final int getHorizontalSliceSize(int count) {
		return (getBoxSize().getHorizontalBoxSize() * count);
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param   count
	 * @return
	 */
	protected final int getVerticalSliceSize(int count) {
		return (getBoxSize().getVerticalBoxSize() * count);
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 */
	public final int getMarkedItem() {
		return _markedItem;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 */
	public int getFontHeight() {
		if (_fontMetrics == null) {
			_fontMetrics = FigureUtilities.getFontMetrics(getFont());
		}
		return _fontMetrics.getHeight();
	}

	public void setModel(IDsmContentProvider contentProvider) {
		_contentProvider = contentProvider;
	}
}

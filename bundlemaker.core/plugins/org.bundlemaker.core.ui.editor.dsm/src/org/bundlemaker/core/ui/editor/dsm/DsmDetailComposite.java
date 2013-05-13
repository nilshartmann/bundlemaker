package org.bundlemaker.core.ui.editor.dsm;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.bundlemaker.core.analysis.algorithms.sorter.DsmSorterRegistry;
import org.bundlemaker.core.ui.editor.dsm.cycle.CycleDetector;
import org.bundlemaker.core.ui.editor.dsm.widget.DsmViewWidget;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class DsmDetailComposite extends Composite {

  private final Set<PropertyChangeListener> _propertyChangeListeners = new CopyOnWriteArraySet<PropertyChangeListener>();

  /** - */
  private Label                             _fromLabel;

  /** - */
  private Label                             _toLabel;

  /** - */
  private Label                             _selectionCountLabel;

  /** - */
  private Label                             _violationCountLabel;

  /** - */
  private Combo                             _dsmSorterNames;

  /** - */
  private Button                            _qualifiedNamesButton;

  /** - */
  private Button                            _shortendNamesButton;

  /** - */
  private LabelPresentationMode             _labelPresentationMode;

  /**
   * <p>
   * Creates a new instance of type {@link DsmDetailComposite}.
   * </p>
   * 
   * @param parent
   * @param viewModel
   */
  public DsmDetailComposite(Composite parent, DsmViewWidget viewWidget) {
    super(parent, 0);

    //
    Assert.isNotNull(viewWidget);

    // this.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
    GridLayout l = new GridLayout(1, true);
    this.setLayout(l);
    GridDataFactory.swtDefaults().grab(true, false).align(SWT.FILL, SWT.FILL).applyTo(this);

    Composite top = new Composite(this, SWT.NONE);
    GridLayout gridLayout = new GridLayout(6, false);
    gridLayout.marginHeight = 0;
    gridLayout.marginWidth = 0;
    top.setLayout(gridLayout);
    GridDataFactory.swtDefaults().grab(true, false).align(SWT.FILL, SWT.FILL).applyTo(top);

    //

    _violationCountLabel = new Label(top, SWT.NONE);
    _violationCountLabel.setText("violations");
    GridDataFactory.swtDefaults().grab(true, false).align(SWT.FILL, SWT.FILL).applyTo(_violationCountLabel);

    _selectionCountLabel = new Label(top, SWT.NONE);
    GridDataFactory.swtDefaults().grab(true, false).align(SWT.FILL, SWT.FILL).applyTo(_selectionCountLabel);

    _dsmSorterNames = new Combo(top, SWT.NONE);
    GridDataFactory.swtDefaults().grab(true, false).align(SWT.FILL, SWT.FILL).applyTo(_dsmSorterNames);
    for (String dsmSorterName : DsmSorterRegistry.getArtifactSorterNames()) {
      _dsmSorterNames.add(dsmSorterName);
    }

    _dsmSorterNames.select(0);
    String dsmSorterName = _dsmSorterNames.getItem(_dsmSorterNames.getSelectionIndex());
    CycleDetector.setDsmSorterName(dsmSorterName);

    _dsmSorterNames.addSelectionListener(new SelectionListener() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        String dsmSorterName = _dsmSorterNames.getItem(_dsmSorterNames.getSelectionIndex());
        CycleDetector.setDsmSorterName(dsmSorterName);
      }

      @Override
      public void widgetDefaultSelected(SelectionEvent e) {
      }
    });

    _qualifiedNamesButton = new Button(top, SWT.CHECK);
    _qualifiedNamesButton.setText("Show qualified names");
    _qualifiedNamesButton.setSelection(true);

    _shortendNamesButton = new Button(top, SWT.CHECK);
    _shortendNamesButton.setText("Shorten labels");

    _qualifiedNamesButton.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        _shortendNamesButton.setEnabled(_qualifiedNamesButton.getSelection());
        labelPresentationModeChanged();
      }

    });

    _shortendNamesButton.addSelectionListener(new SelectionAdapter() {

      @Override
      public void widgetSelected(SelectionEvent e) {
        labelPresentationModeChanged();
      }

    });

    Composite bottom = new Composite(this, SWT.NONE);
    gridLayout = new GridLayout(5, false);
    bottom.setLayout(gridLayout);
    GridDataFactory.swtDefaults().grab(true, false).align(SWT.FILL, SWT.FILL).applyTo(bottom);

    _selectionCountLabel = new Label(bottom, SWT.TRAIL);
    GridDataFactory.swtDefaults().grab(false, false).align(SWT.FILL, SWT.FILL).applyTo(_selectionCountLabel);
    _fromLabel = createFieldWithLabel(bottom, "from");
    _toLabel = createFieldWithLabel(bottom, "to");

    _labelPresentationMode = determineLabelPresentationMode();
  }

  /**
   * <p>
   * </p>
   * 
   * @return the fromLabel
   */
  protected final void setFrom(String from) {
    if (_fromLabel != null) {
      _fromLabel.setText(from);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @return the toLabel
   */
  protected final void setTo(String to) {
    if (_toLabel != null) {
      _toLabel.setText(to);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @return the selectionCountLabel
   */
  protected final void setSelectionCount(String selectionCount) {
    if (_selectionCountLabel != null) {
      _selectionCountLabel.setText("" + selectionCount + " dependencies");
    }
  }

  protected final void setViolationCount(int violationCount) {
    _violationCountLabel.setText("" + violationCount + " violations");
  }

  /**
   * <p>
   * </p>
   * 
   * @param label
   * @return
   */
  private Label createFieldWithLabel(Composite parent, String label) {

    //
    Label theLabel = new Label(parent, SWT.TRAIL);
    theLabel.setText(label);
    GridDataFactory.swtDefaults().grab(false, false).align(SWT.FILL, SWT.FILL).applyTo(theLabel);

    //
    // Label result = new Label(parent, SWT.LEAD);
    Label result = new Label(parent, SWT.FILL | SWT.BORDER);

    GridDataFactory.swtDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(result);

    //
    return result;
  }

  public LabelPresentationMode determineLabelPresentationMode() {

    if (!_qualifiedNamesButton.getSelection()) {
      return LabelPresentationMode.simpleName;
    }

    if (_shortendNamesButton.getSelection()) {
      return LabelPresentationMode.shortendedQualifiedName;
    }

    return LabelPresentationMode.qualifiedName;
  }

  protected void labelPresentationModeChanged() {
    LabelPresentationMode current = _labelPresentationMode;

    LabelPresentationMode newLabelPresentationMode = determineLabelPresentationMode();

    if (current != newLabelPresentationMode) {
      _labelPresentationMode = newLabelPresentationMode;

      PropertyChangeEvent event = new PropertyChangeEvent(this, "labelPresentationMode", current,
          newLabelPresentationMode);
      firePropertyChangeEvent(event);
    }
  }

  public void addPropertyChangeListener(PropertyChangeListener listener) {
    _propertyChangeListeners.add(listener);
  }

  private void firePropertyChangeEvent(final PropertyChangeEvent event) {
    for (PropertyChangeListener listener : _propertyChangeListeners) {
      listener.propertyChange(event);

    }
  }

  public LabelPresentationMode getLabelPresentationMode() {
    return this._labelPresentationMode;
  }
}


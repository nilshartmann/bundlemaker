package org.bundlemaker.core.ui.editor.dsm;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class DsmDetailComposite extends Composite {
	
	private final Set<PropertyChangeListener> _propertyChangeListeners = new CopyOnWriteArraySet<PropertyChangeListener>();

  /** - */
  private Label  _fromLabel;

  /** - */
  private Label  _toLabel;

  /** - */
  private Label  _selectionCountLabel;

  /** - */
  private Button _visualizeChildren;

  /** - */
  private Button _pinSelection;
  
  /** - */
  private Button _qualifiedNamesButton;
  
  /** - */
  private Button _shortendNamesButton;
  
  private LabelPresentationMode _labelPresentationMode;

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
    GridLayout l = new GridLayout(3, false);
    this.setLayout(l);

    Composite composite = new Composite(this, SWT.NONE);
    GridLayout gridLayout = new GridLayout(2, false);
    gridLayout.marginHeight = 0;
    gridLayout.marginWidth = 0;
    composite.setLayout(gridLayout);
    GridDataFactory.swtDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(composite);
    
    //
    _selectionCountLabel = new Label(composite, SWT.TRAIL);
    GridDataFactory.swtDefaults().grab(false, false).align(SWT.FILL, SWT.FILL).applyTo(_selectionCountLabel);
    Label theLabel = new Label(composite, SWT.LEAD);
    theLabel.setText("dependencies");
    GridDataFactory.swtDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(theLabel);

    _fromLabel = createFieldWithLabel(composite, "from");
    _toLabel = createFieldWithLabel(composite, "to");

    composite = new Composite(this, SWT.NONE);
    gridLayout = new GridLayout(1, false);
    gridLayout.marginHeight = 0;
    gridLayout.marginWidth = 0;
    composite.setLayout(gridLayout);
    _visualizeChildren = new Button(composite, SWT.CHECK);
    _visualizeChildren.setText("Visualize children of selected artifacts");
    _pinSelection = new Button(composite, SWT.CHECK);
    _pinSelection.setText("Pin Selection");
    
    Composite presentationModeComposite = new Composite(this, SWT.BORDER);
    gridLayout = new GridLayout(1, false);
    gridLayout.marginHeight = 0;
    gridLayout.marginWidth = 0;
    presentationModeComposite.setLayout(gridLayout);
    
    _qualifiedNamesButton = new Button(presentationModeComposite, SWT.CHECK);
    _qualifiedNamesButton.setText("Show qualified names");
    _qualifiedNamesButton.setSelection(true);
    
    _shortendNamesButton = new Button(presentationModeComposite, SWT.CHECK);
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
    

    
    _labelPresentationMode = determineLabelPresentationMode();
  }

  /**
   * <p>
   * </p>
   * 
   * @return the fromLabel
   */
  protected final Label getFromLabel() {
    return _fromLabel;
  }

  /**
   * <p>
   * </p>
   * 
   * @return the toLabel
   */
  protected final Label getToLabel() {
    return _toLabel;
  }

  /**
   * <p>
   * </p>
   * 
   * @return the selectionCountLabel
   */
  protected final Label getSelectionCountLabel() {
    return _selectionCountLabel;
  }

  /**
   * <p>
   * </p>
   * 
   * @return the visualizeChildren
   */
  public final Button getVisualizeChildrenButton() {
    return _visualizeChildren;
  }
  
  /**
   * <p>
   * </p>
   *
   * @return
   */
  public Button getPinSelectionButton() {
    return _pinSelection;
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
    Label result = new Label(parent, SWT.LEAD);
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
			
			PropertyChangeEvent event = new PropertyChangeEvent(this, "labelPresentationMode", current, newLabelPresentationMode);
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

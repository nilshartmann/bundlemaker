package org.bundlemaker.core.ui.choice;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class ChoiceDialog extends TrayDialog {

  private String             _title;

  private String             _message;

  private Choice             _currentSelection = null;

  private final List<Choice> _choices;

  private final List<Button> _choiceButtons    = new LinkedList<Button>();

  public static Choice choose(Shell shell, String title, String message, Choice defaultChoice, Choice... choices) {
    ChoiceDialog choiceDialog = new ChoiceDialog(shell, title, message, Arrays.asList(choices), defaultChoice);

    if (choiceDialog.open() != OK) {
      return null;
    }

    return choiceDialog.getSelection();

  }

  public ChoiceDialog(Shell shell, String title, String message, List<Choice> choices, Choice defaultChoice) {
    super(shell);

    this._title = title;
    this._message = message;
    this._choices = choices;

    // set default selection
    if (defaultChoice != null) {
      this._currentSelection = defaultChoice;
    } else {
      this._currentSelection = choices.get(0);
    }
  }

  @Override
  protected void configureShell(Shell shell) {
    super.configureShell(shell);
    shell.setText(_title);
    setHelpAvailable(false);

  }

  @Override
  protected Control createDialogArea(Composite parent) {

    createMessageArea(parent);
    Composite composite = new Composite(parent, 0);
    GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
    composite.setLayoutData(gridData);
    composite.setFont(parent.getFont());

    GridLayout layout = new GridLayout();
    layout.numColumns = 1;
    layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
    layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
    layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);

    composite.setLayout(layout);
    SelectionListener listener = new SelectionListener() {
      @Override
      public void widgetDefaultSelected(SelectionEvent e) {
        _currentSelection = (Choice) e.widget.getData();
        refreshSelection();
      }

      @Override
      public void widgetSelected(SelectionEvent e) {
        _currentSelection = (Choice) e.widget.getData();
        refreshSelection();
      }
    };

    for (Choice choice : _choices) {
      Button choiceButton = new Button(composite, SWT.RADIO);
      choiceButton.setText(choice.getLabel());
      gridData = new GridData(GridData.FILL_HORIZONTAL);
      choiceButton.setLayoutData(gridData);
      choiceButton.setData(choice);
      choiceButton.addSelectionListener(listener);
      choiceButton.setFont(parent.getFont());

      _choiceButtons.add(choiceButton);
    }

    refreshSelection();
    return composite;
  }

  private void refreshSelection() {

    for (Button choiceButton : _choiceButtons) {
      choiceButton.setSelection(_currentSelection == choiceButton.getData());
    }

  }

  protected void createMessageArea(Composite parent) {
    if (_message != null) {
      Composite composite = new Composite(parent, 0);
      GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
      composite.setLayoutData(gridData);
      composite.setFont(parent.getFont());

      GridLayout layout = new GridLayout();
      layout.numColumns = 1;
      layout.marginTop = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
      layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
      composite.setLayout(layout);

      // create message
      Label messageLabel = new Label(composite, SWT.WRAP);
      messageLabel.setFont(parent.getFont());
      messageLabel.setText(_message);
      gridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
      messageLabel.setLayoutData(gridData);
    }
  }

  /**
   * Get the user selection from the dialog.
   * 
   */
  public Choice getSelection() {
    return _currentSelection;
  }

}

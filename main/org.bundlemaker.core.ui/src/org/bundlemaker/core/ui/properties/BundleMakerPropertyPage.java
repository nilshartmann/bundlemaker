package org.bundlemaker.core.ui.properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.dialogs.PropertyPage;

public class BundleMakerPropertyPage extends PropertyPage {

  // private static final String PATH_TITLE = "Path:";

  /**
   * {@inheritDoc}
   */
  @Override
  protected Control createContents(Composite parent) {

    // //
    // Composite composite = createDefaultComposite(parent);
    //
    // // Label for path field
    // Label pathLabel = new Label(composite, SWT.NONE);
    // pathLabel.setText(PATH_TITLE);
    //
    // // Path text field
    // Text pathValueText =
    // new Text(composite, SWT.WRAP | SWT.READ_ONLY);
    // pathValueText.setText(
    // ((IResource) getElement()).getFullPath().toString());
    //
    // return composite;

    return new Composite(parent, SWT.NULL);
  }

  // private Composite createDefaultComposite(
  // Composite parent)
  // {
  // Composite composite = new Composite(parent, SWT.NULL);
  // GridLayout layout = new GridLayout();
  // layout.numColumns = 2;
  // composite.setLayout(layout);
  //
  // GridData data = new GridData();
  // data.verticalAlignment = GridData.FILL;
  // data.horizontalAlignment = GridData.FILL;
  // composite.setLayoutData(data);
  //
  // return composite;
  // }
}

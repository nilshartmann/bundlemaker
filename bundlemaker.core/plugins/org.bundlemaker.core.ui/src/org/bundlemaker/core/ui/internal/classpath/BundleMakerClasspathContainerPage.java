package org.bundlemaker.core.ui.internal.classpath;

import org.bundlemaker.core.project.BundleMakerCore;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.ui.wizards.IClasspathContainerPage;
import org.eclipse.jdt.ui.wizards.IClasspathContainerPageExtension;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class BundleMakerClasspathContainerPage extends WizardPage implements IClasspathContainerPage,
    IClasspathContainerPageExtension {

  private IClasspathEntry _entry;

  public BundleMakerClasspathContainerPage() {
    super("bundleMakerClasspathContainerPage");
    setTitle("BundleMaker Libraries");
    setDescription("Libraries required for writing BundleMaker transformation scripts");

  }

  @Override
  public void createControl(Composite parent) {

    Composite container = new Composite(parent, SWT.NULL);
    container.setLayout(new FillLayout());
    Label label = new Label(container, SWT.NULL);
    label
        .setText("This container automatically adds all libraries to your classpath that are required to write BundleMaker transformation scripts");
    setControl(container);

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jdt.ui.wizards.IClasspathContainerPageExtension#initialize(org.eclipse.jdt.core.IJavaProject,
   * org.eclipse.jdt.core.IClasspathEntry[])
   */
  @Override
  public void initialize(IJavaProject project, IClasspathEntry[] currentEntries) {

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jdt.ui.wizards.IClasspathContainerPage#finish()
   */
  @Override
  public boolean finish() {
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jdt.ui.wizards.IClasspathContainerPage#getSelection()
   */
  @Override
  public IClasspathEntry getSelection() {
    return _entry;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jdt.ui.wizards.IClasspathContainerPage#setSelection(org.eclipse.jdt.core.IClasspathEntry)
   */
  @Override
  public void setSelection(IClasspathEntry containerEntry) {
    _entry = containerEntry;
    if (_entry == null) {
      _entry = JavaCore.newContainerEntry(BundleMakerCore.BUNDLEMAKER_CONTAINER_PATH);
    }
  }

}

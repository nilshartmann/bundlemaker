package org.bundlemaker.core.ui.projecteditor.filebased;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.projectdescription.IModifiableProjectDescription;
import org.bundlemaker.core.projectdescription.file.FileBasedContentProvider;
import org.bundlemaker.core.projectdescription.file.VariablePath;
import org.bundlemaker.core.ui.projecteditor.choice.Choice;
import org.bundlemaker.core.ui.projecteditor.dnd.IProjectEditorDropEvent;
import org.bundlemaker.core.ui.projecteditor.dnd.IProjectEditorDropProvider;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Shell;

public class FileBasedContentDropProvider implements IProjectEditorDropProvider {

  private final Transfer[]              SUPPORTED_TYPES          = new Transfer[] { //
                                                                 LocalSelectionTransfer.getTransfer(), //
      FileTransfer.getInstance()                                //
                                                                 };

  private final FileBasedContentCreator _fileBasedContentCreator = new FileBasedContentCreator();

  private final ContentTypeDetector     _contentTypeDetector     = new ContentTypeDetector();

  final static Choice                   ADD_AS_BINARY_CONTENT    = new Choice("Add as binary content");

  final static Choice                   ADD_AS_SOURCE_CONTENT    = new Choice("Add as source content");

  @Override
  public Transfer[] getSupportedDropTypes() {
    return SUPPORTED_TYPES;
  }

  @Override
  public boolean canDrop(IProjectEditorDropEvent dropEvent) throws Exception {

    if (dropEvent.isTransferType(LocalSelectionTransfer.getTransfer())) {
      return canDropLocalSelection(dropEvent);
    }

    if (!dropEvent.hasTarget()) {
      return true;
    }

    return (dropEvent.getTarget() instanceof FileBasedContentProvider);

  }

  @Override
  public boolean performDrop(IProjectEditorDropEvent dropEvent) throws Exception {

    if (dropEvent.isTransferType(FileTransfer.getInstance())) {
      return performFileTransferDrop(dropEvent);
    }

    return performLocalSelectionDrop(dropEvent);
  }

  /**
   * @param dropEvent
   * @return
   */
  protected boolean performLocalSelectionDrop(IProjectEditorDropEvent dropEvent) {

    List<?> selectedObjects = getSelectedObjects(dropEvent.getData());

    IModifiableProjectDescription modifiableProjectDescription = dropEvent.getBundleMakerProject()
        .getModifiableProjectDescription();

    // Create a list of project-relative paths
    List<String> projectRelativePaths = new LinkedList<String>();

    for (Object object : selectedObjects) {

      IResource resource = getAsResource(object);

      if (resource == null) {
        continue;
      }

      String path = FileBasedContentEditorUtils.getProjectRelativePath(resource);
      projectRelativePaths.add(path);
    }

    System.out.printf("FileBasedContentDropProvider target: %b (%s), location: %s%n", dropEvent.hasTarget(),
        dropEvent.getTarget(),
        dropEvent.getDropLocation());

    if (!dropEvent.hasTarget()) {
      System.out.println("NO TARGET!");

      // add as individual file based contents
      _fileBasedContentCreator.addFiles(modifiableProjectDescription, projectRelativePaths.toArray(new String[0]));
      // for (String relativePath : projectRelativePaths) {
      // FileBasedContentProviderFactory.addNewFileBasedContentProvider(modifiableProjectDescription, relativePath);
      // }
    } else {
      // add to selected filebasedcontentprovider
      FileBasedContentProvider provider = (FileBasedContentProvider) dropEvent.getProjectContentProvider();
      addFiles(dropEvent.getShell(), provider, projectRelativePaths.toArray(new String[0]));
    }

    return true;
  }

  protected IResource getAsResource(Object object) {
    if (object instanceof IResource) {
      return (IResource) object;
    }

    if (object instanceof IJavaElement) {
      IJavaElement javaElement = (IJavaElement) object;
      return javaElement.getResource();
    }

    return null;

  }

  protected boolean performFileTransferDrop(IProjectEditorDropEvent dropEvent) throws Exception {

    if (!dropEvent.hasTarget()) {
      // add new filebased content
      return createFileBasedContents(dropEvent);
    }
    String[] newFiles = dropEvent.getData(String[].class);
    FileBasedContentProvider provider = (FileBasedContentProvider) dropEvent.getTarget();

    return addFiles(dropEvent.getShell(), provider, newFiles);
  }

  protected boolean addFiles(Shell shell, FileBasedContentProvider provider, String[] newFiles) {

    // String message = "Please choose how to add " + newFiles.length + " resources to your BundleMaker project";
    //
    // Choice choice = ChoiceDialog.choose(shell, message, ADD_AS_BINARY_CONTENT, ADD_AS_BINARY_CONTENT,
    // ADD_AS_SOURCE_CONTENT);
    // if (choice == null) {
    // return false;
    // }

    // Iterate over each dropped file
    for (String newFile : newFiles) {
      VariablePath variablePath = new VariablePath(newFile);

      // Determine ("guess") the content type
      ContentType contentType = _contentTypeDetector.detectContentType(variablePath);

      // add to provider
      provider.addRootPath(variablePath, contentType);
    }

    return true;
  }

  /**
   * @param dropEvent
   * @return
   */
  protected boolean canDropLocalSelection(IProjectEditorDropEvent dropEvent) {

    ISelection selection = LocalSelectionTransfer.getTransfer().getSelection();

    // get selected objects
    List<?> selectedObjects = getSelectedObjects(selection);

    if (selectedObjects == null) {
      return false;
    }

    // drop only possible with folders and files
    for (Object object : selectedObjects) {
      if (object instanceof IFolder || object instanceof IFile) {
        continue;
      }

      if (object instanceof IProject) {
        // don't add Java projects via this drop adapter
        return false;
      }

      if (object instanceof IJavaElement) {
        IJavaElement javaElement = (IJavaElement) object;
        IResource resource = javaElement.getResource();

        if (!(resource instanceof IFolder) && !(resource instanceof IFile)) {
          return false;
        }
      }
    }

    return true;
  }

  /**
   * Returns the selected objects or null if object is not a supported selection or empty
   * 
   * @param object
   * @return
   */
  protected List<?> getSelectedObjects(Object object) {
    if (!(object instanceof IStructuredSelection)) {
      return null;
    }

    IStructuredSelection structuredSelection = (IStructuredSelection) object;

    if (structuredSelection.isEmpty()) {
      return null;
    }

    return structuredSelection.toList();
  }

  private boolean createFileBasedContents(IProjectEditorDropEvent dropEvent) {
    IModifiableProjectDescription modifiableProjectDescription = dropEvent.getBundleMakerProject()
        .getModifiableProjectDescription();

    FileTransfer instance = FileTransfer.getInstance();
    if (dropEvent.isTransferType(instance)) {
      String[] fileNames = dropEvent.getData(String[].class);
      _fileBasedContentCreator.addFiles(modifiableProjectDescription, fileNames);
    }
    return true;
  }

}

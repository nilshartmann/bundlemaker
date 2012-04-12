package org.bundlemaker.core.ui.projecteditor.filebased;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.projectdescription.IModifiableProjectDescription;
import org.bundlemaker.core.projectdescription.file.FileBasedContentProvider;
import org.bundlemaker.core.projectdescription.file.VariablePath;
import org.bundlemaker.core.ui.projecteditor.choice.Choice;
import org.bundlemaker.core.ui.projecteditor.choice.ChoiceDialog;
import org.bundlemaker.core.ui.projecteditor.dnd.IProjectEditorDropEvent;
import org.bundlemaker.core.ui.projecteditor.dnd.IProjectEditorDropProvider;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;

public class FileBasedContentDropProvider implements IProjectEditorDropProvider {

  private final Transfer[]              SUPPORTED_TYPES          = new Transfer[] { //
                                                                 FileTransfer.getInstance() //
                                                                 };

  private final FileBasedContentCreator _fileBasedContentCreator = new FileBasedContentCreator();

  final static Choice                   ADD_AS_BINARY_CONTENT    = new Choice("Add as binary content");

  final static Choice                   ADD_AS_SOURCE_CONTENT    = new Choice("Add as source content");

  @Override
  public Transfer[] getSupportedDropTypes() {
    return SUPPORTED_TYPES;
  }

  @Override
  public boolean canDrop(IProjectEditorDropEvent dropEvent) throws Exception {

    if (!dropEvent.hasTarget()) {
      return true;
    }

    return (dropEvent.getTarget() instanceof FileBasedContentProvider);

  }

  @Override
  public boolean performDrop(IProjectEditorDropEvent dropEvent) throws Exception {

    if (!dropEvent.hasTarget()) {
      // add new filebased content
      return createFileBasedContents(dropEvent);
    }

    String[] newFiles = dropEvent.getData(String[].class);

    String message = "Please choose how to add " + newFiles.length + " resources to your BundleMaker project";
    Choice choice = ChoiceDialog.choose(dropEvent.getShell(), message, ADD_AS_BINARY_CONTENT, ADD_AS_SOURCE_CONTENT);
    if (choice == null) {
      return false;
    }

    FileBasedContentProvider provider = (FileBasedContentProvider) dropEvent.getTarget();
    Set<VariablePath> rootPaths;

    if (choice == ADD_AS_BINARY_CONTENT) {
      rootPaths = provider.getFileBasedContent().getBinaryRootPaths();
    } else {
      rootPaths = provider.getFileBasedContent().getSourceRootPaths();
    }

    // Merge together existing with new (dropped) paths
    List<String> newRootPaths = new LinkedList<String>();
    for (VariablePath variablePath : rootPaths) {
      newRootPaths.add(variablePath.getUnresolvedPath().toString());
    }

    for (String newFile : newFiles) {
      newRootPaths.add(newFile);
    }
    if (choice == ADD_AS_BINARY_CONTENT) {
      provider.setBinaryPaths(newRootPaths.toArray(new String[0]));
    } else {
      provider.setSourcePaths(newRootPaths.toArray(new String[0]));
    }

    return true;
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

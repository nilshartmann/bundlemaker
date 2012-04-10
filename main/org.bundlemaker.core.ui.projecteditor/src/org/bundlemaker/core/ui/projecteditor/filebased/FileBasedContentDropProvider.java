package org.bundlemaker.core.ui.projecteditor.filebased;

import org.bundlemaker.core.projectdescription.IModifiableProjectDescription;
import org.bundlemaker.core.projectdescription.file.FileBasedContentProviderFactory;
import org.bundlemaker.core.ui.projecteditor.dnd.IProjectEditorDropEvent;
import org.bundlemaker.core.ui.projecteditor.dnd.IProjectEditorDropProvider;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;

public class FileBasedContentDropProvider implements IProjectEditorDropProvider {

  private final Transfer[] SUPPORTED_TYPES = new Transfer[] { //
                                           FileTransfer.getInstance() //
                                           };

  @Override
  public Transfer[] getSupportedDropTypes() {
    return SUPPORTED_TYPES;
  }

  @Override
  public boolean canDrop(IProjectEditorDropEvent dropEvent) throws Exception {

    return dropEvent.hasTarget() == false;
  }

  @Override
  public boolean performDrop(IProjectEditorDropEvent dropEvent) throws Exception {

    IModifiableProjectDescription modifiableProjectDescription = dropEvent.getBundleMakerProject()
        .getModifiableProjectDescription();

    FileTransfer instance = FileTransfer.getInstance();
    if (dropEvent.isTransferType(instance)) {
      String[] fileNames = dropEvent.getData(String[].class);
      for (String file : fileNames) {
        System.out.println("ADD FILE: " + file);
        FileBasedContentProviderFactory.addNewFileBasedContentProvider(modifiableProjectDescription, file);
      }

    }

    return true;
  }
}

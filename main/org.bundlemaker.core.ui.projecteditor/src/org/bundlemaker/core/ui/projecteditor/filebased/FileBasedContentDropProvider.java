package org.bundlemaker.core.ui.projecteditor.filebased;

import org.bundlemaker.core.projectdescription.IModifiableProjectDescription;
import org.bundlemaker.core.ui.projecteditor.dnd.IProjectEditorDropEvent;
import org.bundlemaker.core.ui.projecteditor.dnd.IProjectEditorDropProvider;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;

public class FileBasedContentDropProvider implements IProjectEditorDropProvider {

  private final Transfer[]              SUPPORTED_TYPES          = new Transfer[] { //
                                                                 FileTransfer.getInstance() //
                                                                 };

  private final FileBasedContentCreator _fileBasedContentCreator = new FileBasedContentCreator();

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
      _fileBasedContentCreator.addFiles(modifiableProjectDescription, fileNames);
    }

    return true;
  }

}

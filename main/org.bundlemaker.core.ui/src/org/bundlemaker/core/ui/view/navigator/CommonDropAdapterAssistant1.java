package org.bundlemaker.core.ui.view.navigator;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.ui.navigator.CommonDropAdapter;
import org.eclipse.ui.navigator.CommonDropAdapterAssistant;

public class CommonDropAdapterAssistant1 extends CommonDropAdapterAssistant {

  public CommonDropAdapterAssistant1() {
    System.out.println("ASASDASDASd");
  }

  @Override
  public IStatus validateDrop(Object target, int operation, TransferData transferData) {
    System.out.println(target);
    System.out.println(operation);
    System.out.println(LocalSelectionTransfer.getTransfer().nativeToJava(transferData));
    return Status.OK_STATUS;
  }

  @Override
  public IStatus handleDrop(CommonDropAdapter aDropAdapter, DropTargetEvent aDropTargetEvent, Object aTarget) {
    System.out.println(aTarget);
    return Status.OK_STATUS;
  }

  @Override
  public boolean isSupportedType(TransferData transferData) {

    System.out.println(LocalSelectionTransfer.getTransfer().nativeToJava(transferData));

    return true;
  }
}

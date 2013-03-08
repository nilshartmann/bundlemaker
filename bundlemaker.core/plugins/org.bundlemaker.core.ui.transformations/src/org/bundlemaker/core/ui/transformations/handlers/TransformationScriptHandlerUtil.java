package org.bundlemaker.core.ui.transformations.handlers;

import java.util.List;

import org.bundlemaker.core.ui.handler.AbstractBundleMakerHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class TransformationScriptHandlerUtil {

  public static IType getTransformationScript(ExecutionEvent event, ISelection selection) throws Exception {

    IType transformationScriptType = null;

    List<IType> selectedType = AbstractBundleMakerHandler.getSelectedObject(selection, IType.class);
    if (!selectedType.isEmpty()) {
      transformationScriptType = selectedType.get(0);

      if (!RunTransformationScriptHandler.isTransformationScriptType(transformationScriptType)) {
        showError(event, "Selected Type is not a Transformation Script");
        return null;
      }
    } else {
      List<ICompilationUnit> compilationUnits = AbstractBundleMakerHandler.getSelectedObject(selection,
          ICompilationUnit.class);
      if (compilationUnits.isEmpty()) {
        // wrong type selected (should not happen)
        return null;
      }
      ICompilationUnit compilationUnit = compilationUnits.get(0);
      transformationScriptType = getTransformationScript(compilationUnit);

    }

    if (transformationScriptType == null) {
      showError(event, "Selected File does not contain a Transformation Script");
      return null;
    }

    return transformationScriptType;

  }

  protected static IType getTransformationScript(ICompilationUnit unit) throws JavaModelException {
    IType[] types = unit.getTypes();

    for (IType iType : types) {
      if (RunTransformationScriptHandler.isTransformationScriptType(iType)) {
        return iType;
      }
    }

    return null;
  }

  protected static void showError(ExecutionEvent event, String text) {
    MessageDialog.openError(HandlerUtil.getActiveShell(event), "Transformation Script", text);
  }

}

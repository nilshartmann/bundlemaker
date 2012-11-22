package org.bundlemaker.core.ui.editor.dsm.figures;

import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Font;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BoxSizeUtil {

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public static int computeDependencyBoxSize(IMatrixContentProvider contentProvider,
      ILabelProvider dependencyLabelProvider, Font font) {
    String value = getLongestString(contentProvider.getDependencies(), dependencyLabelProvider);
    return FigureUtilities.getTextWidth(value, font) + 6;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public static int computeNodeBoxSize(IMatrixContentProvider contentProvider, ILabelProvider artifactLabelProvider,
      Font font) {
    String value = getLongestString(contentProvider.getNodes(), artifactLabelProvider);
    int result = FigureUtilities.getTextWidth(value, font);

    return result;
  }

  /**
   * <p>
   * Helper method that returns the longest string from the string array.
   * </p>
   * 
   * @param strings
   *          the string array
   * @return the longest string from the string array.
   */
  public static String getLongestString(Object[] nodes, ILabelProvider labelProvider) {

    // create the result
    String result = null;

    // iterate over all strings
    for (Object node : nodes) {
      String text = labelProvider.getText(node);
      if (result == null) {
        result = labelProvider.getText(node);
      } else if (result.length() < text.length()) {
        result = text;
      }
    }

    // return the result
    return (result == null ? "" : result);
  }

  /**
   * <p>
   * </p>
   * 
   * @param nodes
   * @param labelProvider
   * @return
   */
  public static String getLongestString(Object[][] nodes, ILabelProvider labelProvider) {

    // create the result
    String result = "";

    // iterate over all strings
    for (Object[] innerNodes : nodes) {
      for (Object node : innerNodes) {
        if (node != null) {
          String text = labelProvider.getText(node);
          if (result == null) {
            result = labelProvider.getText(node);
          } else if (result.length() < text.length()) {
            result = text;
          }
        }
      }
    }

    // return the result
    return result;
  }
}

package org.bundlemaker.dependencyanalysis.ui.view.table;

import org.bundlemaker.dependencyanalysis.log.CoreLog;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.text.FindReplaceDocumentAdapter;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * Methoden zum Oeffnen einer Klasse im Java-Editor mit Fokus
 * auf einer bestimmten Stelle 
 * 
 * @author Kai Lehmann
 *
 */
public class JavaEditor {
	
	/**
	 * Oeffnet die uebergebene Klasse in einem Java-Editor unter Verwendung der
	 * Java Development Tools.
	 * 
	 * Dabei muss der Klassennamen vollqualifiziert uebergeben werden. Es wird
	 * ueber alle Projekte im Workspace iteriert, die als Nature Java-Projekte
	 * sind.
	 * 
	 * Der geoffnete Editor wird zurueckgegeben.
	 * 
	 * @param className
	 *            Der vollqualifizierende Name der Java-Klasse als String
	 * @return Der Editor oder <code>Null</code> Falls die uebergebenen
	 *         Java-Klasse in keinem Projekt des Workspaces gefunden werden
	 *         konnte
	 */
	public static IEditorPart openTypeInEditor(String className) {
		try {
			for (IProject project : ResourcesPlugin.getWorkspace().getRoot()
					.getProjects()) {
				if( project.isOpen() && project.hasNature(JavaCore.NATURE_ID) ) {
					IJavaProject javaproject = (IJavaProject) project
						.getNature(JavaCore.NATURE_ID);
					IType findType = javaproject.findType(className);
					if (findType != null) {
						return JavaUI.openInEditor(findType);
					}
				}
			}
		} catch (Throwable t) {
			CoreLog.get().logException(t, "Fehler bei openTypeInEditor( " + className + " )");
			return null;
		}
		CoreLog.get().debug( "Type not found in openTypeInEditor( " + className + " )");
		return null;
	}

	/**
	 * Setzt den Fokus im geoeffneten Editor an die Stelle, wo das erste Mal der
	 * uebergebene SearchString gefunden wurde.
	 * 
	 * Im Vorwege wird ein Editor geoeffnet zur uebergebenen Klasse.
	 * 
	 * @param className
	 *            vollqualifizierender Name der Java-Klasse
	 * @param searchString
	 *            der uebergebene SearchString
	 * @return Der EditorPart, der geoeffnet wurde. <code>null</code>, falls
	 *         kein EditorPart geoeffnet werden konnte
	 */
	public static IEditorPart openTypeInEditor(String className, String searchString) {
		try {
			IEditorPart editorPart = openTypeInEditor(className);
//			CoreLog.get().debug("openTypeInEditor( " + className + "  ): " + editorPart );
			if ((editorPart != null) && (editorPart instanceof ITextEditor)) {
				ITextEditor editor = (ITextEditor) editorPart;
				IDocument doc = editor.getDocumentProvider().getDocument(
						editor.getEditorInput());

//				int offset = doc.search(0, searchString, true, true, true);
				int offset = new FindReplaceDocumentAdapter(doc).find(0,
						searchString, true, true, true, false).getOffset();
				editor.setHighlightRange(offset, 100, true);
			}
			return editorPart;
		} catch (Throwable t) {
			return null;
		}
	}
}

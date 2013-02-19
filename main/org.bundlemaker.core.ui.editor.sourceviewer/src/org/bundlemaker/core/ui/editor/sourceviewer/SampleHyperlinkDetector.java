package org.bundlemaker.core.ui.editor.sourceviewer;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;

public class SampleHyperlinkDetector implements IHyperlinkDetector {

	public IHyperlink[] detectHyperlinks(ITextViewer textViewer, IRegion region,
			boolean canShowMultipleHyperlinks) {

		//
		try {
			ITypedRegion typedRegion = textViewer.getDocument().getPartition(region.getOffset());
			return new IHyperlink[] { new SampleHyperlink(typedRegion), new SampleHyperlink(typedRegion) };
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//
		return null;
	}
}
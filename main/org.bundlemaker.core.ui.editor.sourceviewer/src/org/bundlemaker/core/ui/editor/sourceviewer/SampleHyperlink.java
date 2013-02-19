package org.bundlemaker.core.ui.editor.sourceviewer;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.hyperlink.IHyperlink;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class SampleHyperlink implements IHyperlink {

	/** - */
	private ITypedRegion _region;

	/**
	 * <p>
	 * </p>
	 * 
	 * @param region
	 */
	public SampleHyperlink(ITypedRegion region) {
		Assert.isNotNull(region);

		_region = region;
	}

	@Override
	public IRegion getHyperlinkRegion() {
		return _region;
	}

	@Override
	public String getTypeLabel() {
		return "BLA";
	}

	@Override
	public String getHyperlinkText() {
		return "BLUBB";
	}

	@Override
	public void open() {
		System.out.println(_region.toString());
	}
}

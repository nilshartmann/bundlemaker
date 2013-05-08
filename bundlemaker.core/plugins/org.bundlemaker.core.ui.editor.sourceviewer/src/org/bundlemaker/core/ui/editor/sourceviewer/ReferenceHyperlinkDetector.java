package org.bundlemaker.core.ui.editor.sourceviewer;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.bundlemaker.core.analysis.AnalysisModelQueries;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.parser.IReferenceDetailParser.IPosition;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ReferenceHyperlinkDetector implements IHyperlinkDetector {

  /** - */
  private SourceViewerEditor _editor;

  /**
   * <p>
   * Creates a new instance of type {@link ReferenceHyperlinkDetector}.
   * </p>
   * 
   * @param editor
   */
  public ReferenceHyperlinkDetector(SourceViewerEditor editor) {

    //
    Assert.isNotNull(editor);

    //
    _editor = editor;
  }

  /**
   * {@inheritDoc}
   */
  public IHyperlink[] detectHyperlinks(ITextViewer textViewer, IRegion region, boolean canShowMultipleHyperlinks) {

    //
    List<IHyperlink> hyperlinks = new LinkedList<IHyperlink>();

    //
    for (Entry<String, List<IPosition>> entry : _editor.getPositions().entrySet()) {

      //
      IBundleMakerArtifact referencedArtifact = AnalysisModelQueries.getReferencedArtifact(
          _editor.getResourceArtifact(), entry.getKey());

      //
      IResourceArtifact referencedResourceArtifact = referencedArtifact != null
          && !(referencedArtifact instanceof IResourceArtifact) ? referencedArtifact.getParent(IResourceArtifact.class)
          : (IResourceArtifact) referencedArtifact;

      //
      if (referencedResourceArtifact != null) {

        //
        for (IPosition position : entry.getValue()) {

          //
          if (region.getOffset() >= position.getOffset()
              && region.getOffset() <= position.getOffset() + position.getLength()) {

            //
            hyperlinks.add(new ReferenceHyperlink(new ResourceArtifactReferenceRegion(position.getOffset(), position
                .getLength(), entry.getKey(), referencedResourceArtifact)));
          }
        }
      }
    }

    //
    return hyperlinks.isEmpty() ? null : hyperlinks.toArray(new IHyperlink[0]);
  }
}
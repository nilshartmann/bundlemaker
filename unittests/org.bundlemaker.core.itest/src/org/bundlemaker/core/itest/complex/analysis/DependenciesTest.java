package org.bundlemaker.core.itest.complex.analysis;

import org.bundlemaker.core.analysis.ArtifactHelper;
import org.bundlemaker.core.analysis.IAnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.bundlemaker.core.analysis.spi.IReferencingArtifact;
import org.bundlemaker.core.itest.AbstractModularizedSystemTest;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DependenciesTest extends AbstractModularizedSystemTest {

  @Test
  public void testDependencies() {

    //
    IRootArtifact rootArtifact = getModularizedSystem().getArtifactModel(
        IAnalysisModelConfiguration.HIERARCHICAL_BINARY_RESOURCES_CONFIGURATION);

    final int[] count = new int[2];

    //
    rootArtifact.accept(new IAnalysisModelVisitor.Adapter() {

      @Override
      public boolean onVisit(IBundleMakerArtifact artifact) {

        //
        if (artifact instanceof IReferencingArtifact) {
          System.out.println(artifact.getQualifiedName());
          count[0] += ((ITypeArtifact) artifact).getAssociatedType().getReferences().size();
          count[1] += ((IReferencingArtifact) artifact).getDependenciesTo().size();
          
          //
          for (IDependency dependency : artifact.getDependenciesFrom()) {
            System.out.println(" - " + dependency);
          }
        }

        //
        return super.onVisit(artifact);
      }
    });

    // types: 17455
    Assert.assertThat(count[0], is(8415));
    Assert.assertThat(count[1], is(8275));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String computeTestProjectName() {
    return "jedit";
  }
}

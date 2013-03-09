package org.bundlemaker.core.analysis.selectors;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class PackageSelector extends AbstractPatternBasedSelector {

  /**
   * Selects packages by their names.
   * 
   * <p>
   * <b>Example:</b> Select the package 'org.example.test' and it's sub-packages, but do not select package
   * org.example.test.integration
   * </p>
   * 
   * <pre>
   * new PackageSelector(myRootArtifact, &quot;org.example.test**&quot;, &quot;org.example.test.integration&quot;);
   * </pre>
   * 
   * @param artifact
   *          The base artifact used to start the search for packages
   * @param includedPackages
   *          Ant-style pattern of package names to include. Can be null (include everything) and can be comma-delimited
   *          to specify more than one pattern
   * @param excludedPackages
   *          Ant-style pattern of package names to exclude. Can be null (exclude nothing) and can be comma-delimited to
   *          specify more than one pattern
   */
  public PackageSelector(IBundleMakerArtifact artifact, String includedPackages, String excludedPackages) {
    super(artifact, includedPackages, excludedPackages);
  }

  /**
   * Same as <tt>PackageSelector(artifact, includedPackages, null)</tt>
   * 
   * @param artifact
   * @param includedPackages
   * @see #PackageSelector(IBundleMakerArtifact, String, String)
   */
  public PackageSelector(IBundleMakerArtifact artifact, String includedPackages) {
    super(artifact, includedPackages);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.analysis.IArtifactSelector#getBundleMakerArtifacts()
   */
  @Override
  public List<? extends IBundleMakerArtifact> getBundleMakerArtifacts() {

    final List<IBundleMakerArtifact> result = new LinkedList<IBundleMakerArtifact>();

    getBundleMakerArtifact().accept(new IAnalysisModelVisitor.Adapter() {
      @Override
      public boolean visit(IPackageArtifact packageArtifact) {

        //
        String packageName = packageArtifact.getQualifiedName();

        if (isIncluded(packageName) && !isExcluded(packageName))
        {
          result.add(packageArtifact);
        }

        //
        return true;
      }
    });

    return result;
  }

}

package org.bundlemaker.core.analysis.selectors;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.IArtifactSelector;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IAnalysisModelVisitor.Adapter;
import org.bundlemaker.core.util.PatternUtils;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class PackageSelector implements IArtifactSelector {

  private final IBundleMakerArtifact _artifact;

  private final Pattern[]            _includedPackagesPattern;

  private final Pattern[]            _excludedPackagesPattern;

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
    _artifact = artifact;

    //
    _includedPackagesPattern = splitAndConvert(includedPackages);
    _excludedPackagesPattern = splitAndConvert(excludedPackages);

  }

  /**
   * Same as <tt>PackageSelector(artifact, includedPackages, null)</tt>
   * 
   * @param artifact
   * @param includedPackages
   * @see #PackageSelector(IBundleMakerArtifact, String, String)
   */
  public PackageSelector(IBundleMakerArtifact artifact, String includedPackages) {
    this(artifact, includedPackages, null);
  }

  private static Pattern[] splitAndConvert(String s) {
    if (s == null) {
      return null;
    }

    String[] items = s.split(",");
    Pattern[] patterns = new Pattern[items.length];
    for (int i = 0; i < items.length; i++) {
      String regexp = PatternUtils.convertAntStylePattern(items[i].trim());
      patterns[i] = Pattern.compile(regexp);
    }

    return patterns;

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.analysis.IArtifactSelector#getBundleMakerArtifacts()
   */
  @Override
  public List<? extends IBundleMakerArtifact> getBundleMakerArtifacts() {

    final List<IBundleMakerArtifact> result = new LinkedList<IBundleMakerArtifact>();

    _artifact.accept(new IAnalysisModelVisitor.Adapter() {
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

  private boolean isExcluded(String packageName) {
    if (_excludedPackagesPattern == null) {
      // nothing is excluded
      return false;
    }

    return matchesAny(packageName, _excludedPackagesPattern);
  }

  private boolean isIncluded(String packageName) {
    if (_includedPackagesPattern == null) {
      // every package is included
      return true;
    }

    return matchesAny(packageName, _includedPackagesPattern);
  }

  /**
   * @param packageName
   * @param includedPackagesPattern
   * @return
   */
  private boolean matchesAny(String packageName, Pattern[] patterns) {
    for (Pattern pattern : patterns) {
      if (pattern.matcher(packageName).matches()) {
        return true;
      }
    }

    return false;
  }

}

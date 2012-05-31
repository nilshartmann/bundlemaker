package org.bundlemaker.core.reports.exporter.html;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.core.analysis.ArtifactHelper;
import org.bundlemaker.core.analysis.IArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IArtifactTreeVisitor;
import org.bundlemaker.core.analysis.IArtifactTreeVisitor.Adapter;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.util.collections.GenericCache;

public class ModuleDependenciesReportExporter extends AbstractSingleModuleHtmlReportExporter {

  /**
   * {@inheritDoc}
   */
  protected String getReportName() {
    return getCurrentModule().getModuleIdentifier().toString() + "_Module-Dependencies";
  }

  /**
   * <p>
   * </p>
   * 
   * @param bufferedWriter
   * @throws IOException
   */
  protected void writeHtmlBody(BufferedWriter bw) throws IOException {

    bw.write("<table>\n");

    bw.write("  <tr>\n");
    bw.write("     <th>Module</th>\n");
    bw.write("     <th>Depends On</th>\n");
    bw.write("  </tr>\n");

    //
    boolean odd = true;

    //
    Collection<IModuleArtifact> moduleArtifacts = ArtifactHelper.findChildren(getCurrentRootArtifact(),
        IModuleArtifact.class);

    //
    IModuleArtifact moduleArtifact = getCurrentRootArtifact().getModuleArtifact(
        getCurrentModule());

    //
    List<IDependency> dependencies = new LinkedList<IDependency>(moduleArtifact.getDependencies(moduleArtifacts));
    Collections.sort(dependencies, new Comparator<IDependency>() {
      @Override
      public int compare(IDependency arg0, IDependency arg1) {
        return arg0.getTo().compareTo(arg1.getTo());
      }
    });

    //
    for (IDependency dependency : dependencies) {

      if (odd) {
        bw.write("  <tr class=\"oddrow\">\n");
        odd = false;
      } else {
        bw.write("  <tr class=\"evenrow\">\n");
        odd = true;
      }

      bw.write("     <td>");

      if (dependencies.indexOf(dependency) == 0) {
        bw.write(getCurrentRootArtifact().getModuleArtifact(
            getCurrentModule()).getQualifiedName());
      }

      bw.write("     </td>");

      bw.write("     <td>");
      bw.write(dependency.getTo().getQualifiedName());
      bw.write("     </td>");

      bw.write("  </tr>\n");
    }

    bw.write("</table>\n");
  }

  @Override
  protected IArtifactModelConfiguration getModelConfiguration() {
    return IArtifactModelConfiguration.SOURCE_RESOURCES_CONFIGURATION;
  }
}

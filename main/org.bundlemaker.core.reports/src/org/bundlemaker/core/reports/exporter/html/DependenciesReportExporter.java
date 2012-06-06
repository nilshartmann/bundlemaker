package org.bundlemaker.core.reports.exporter.html;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.core.analysis.IArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IArtifactTreeVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;

public class DependenciesReportExporter extends AbstractSingleModuleHtmlReportExporter {

  /**
   * {@inheritDoc}
   */
  protected String getReportName() {
    return getCurrentModule().getModuleIdentifier().toString() + "_Dependencies";
  }

  /**
   * <p>
   * </p>
   * 
   * @param bufferedWriter
   * @throws IOException
   */
  protected void writeHtmlBody(BufferedWriter bw) throws IOException {

    // get the root artifact
    IRootArtifact rootArtifact = getCurrentModularizedSystem()
        .getArtifactModel(IArtifactModelConfiguration.SOURCE_RESOURCES_CONFIGURATION);

    // get the module artifact
    IModuleArtifact moduleArtifact = rootArtifact.getModuleArtifact(getCurrentModule());

    // get all resource module artifacts
    final List<IModuleArtifact> resourceModuleArtifacts = new LinkedList<IModuleArtifact>();
    rootArtifact.accept(new IArtifactTreeVisitor.Adapter() {
      @Override
      public boolean visit(IModuleArtifact moduleArtifact) {
        if (moduleArtifact.isResourceModule()) {
          resourceModuleArtifacts.add(moduleArtifact);
        }
        return false;
      }
    });

    // write the dependencies
    bw.write("<br/>\n");

    // iterate over all module2module dependencies
    for (IDependency dependency : rootArtifact.getModuleArtifact(getCurrentModule()).getDependencies(
        resourceModuleArtifacts)) {

      if (!moduleArtifact.equals(dependency.getTo())) {

        //
        String linkreference = String.format("%1$s_to_%2$s",
            getIdentifier((IModuleArtifact) dependency.getFrom()),
            getIdentifier((IModuleArtifact) dependency.getTo()));

        bw.write("<a name=\"" + linkreference + "\"/>\n");

        bw.write("<h2>\n");
        String reference = String.format("<a href=\"%1$s_Content.html\">%1$s</a> -> <a href=\"%2$s_Content.html\">%2$s</a> (%3$s Abh&auml;ngigkeiten)",
            ((IModuleArtifact) dependency.getFrom()).getAssociatedModule().getModuleIdentifier(),
            ((IModuleArtifact) dependency.getTo()).getAssociatedModule().getModuleIdentifier(),
            dependency.getWeight());
        bw.write(reference);
        bw.write("</h2>\n");

        //
        bw.write("<br/>\n");

        //
        bw.write("<table>\n");

        bw.write("  <tr>\n");
        bw.write("     <th>Resource</th>\n");
        bw.write("     <th>Depends On</th>\n");
        bw.write("  </tr>\n");

        //
        boolean odd = true;

        //
        List<IDependency> dependencies = new LinkedList<IDependency>(dependency.getDependencies());
        Collections.sort(dependencies, new Comparator<IDependency>() {
          @Override
          public int compare(IDependency arg0, IDependency arg1) {
            return arg1.getFrom().compareTo(arg0.getFrom());
          }
        });

        //
        List<IBundleMakerArtifact> alreadyAdded = new LinkedList<IBundleMakerArtifact>();
        for (IDependency dep : dependencies) {

          IResourceArtifact from = dep.getFrom().getParent(IResourceArtifact.class);

          if (!alreadyAdded.contains(from)) {
            odd = !odd;
          }

          if (odd) {
            bw.write("  <tr class=\"oddrow\">\n");
          } else {
            bw.write("  <tr class=\"evenrow\">\n");
          }

          // get the target module and resource
          IModuleArtifact targetModule = dep.getTo().getParent(IModuleArtifact.class);
          IResourceArtifact targetResource = dep.getTo().getParent(IResourceArtifact.class);

          // write "from resource"
          bw.write("     <td>");
          if (!alreadyAdded.contains(from)) {
            bw.write(dep.getFrom().getParent(IResourceArtifact.class).getQualifiedName());
            alreadyAdded.add(from);
          }
          bw.write("     </td>");

          // write "to resource"
          bw.write("     <td>");
          String moduleContentReference = targetModule.getAssociatedModule().getModuleIdentifier()
              .toString()
              + "_Content.html#" + targetResource.getQualifiedName();
          bw.write(String.format("<a href=\"%1$s\">%2$s</a>", moduleContentReference,
              targetResource
                  .getQualifiedName()));
          bw.write("     </td>");

          //
          bw.write("  </tr>\n");
        }
      }

      bw.write("</table>\n");
      bw.write("</h2>\n");
    }

    // //
    // bw.write("<table>\n");
    //
    // bw.write("  <tr>\n");
    // bw.write("     <th>Archive</th>\n");
    // bw.write("     <th>Depends On</th>\n");
    // bw.write("  </tr>\n");
    //
    // //
    // boolean odd = true;
    //
    // //
    // Collection<IResourceArtifact> resourceArtifacts = ArtifactHelper.findChildren(
    // getModuleArtifact(getCurrentModule()), IResourceArtifact.class);
    //
    // //
    // for (IResourceArtifact iResourceArtifact : resourceArtifacts) {
    //
    // //
    // List<IDependency> dependencies = new LinkedList<IDependency>(iResourceArtifact.getDependencies());
    // Collections.sort(dependencies, new Comparator<IDependency>() {
    // @Override
    // public int compare(IDependency arg0, IDependency arg1) {
    // return arg1.getTo().compareTo(arg0.getTo());
    // }
    // });
    //
    // //
    // for (IDependency dependency : dependencies) {
    //
    // if (odd) {
    // bw.write("  <tr class=\"oddrow\">\n");
    // odd = false;
    // } else {
    // bw.write("  <tr class=\"evenrow\">\n");
    // odd = true;
    // }
    //
    // bw.write("     <td>");
    //
    // if (dependencies.indexOf(dependency) == 0) {
    // bw.write(iResourceArtifact.getQualifiedName());
    // }
    //
    // bw.write("     </td>");
    //
    // bw.write("     <td>");
    // bw.write(dependency.getTo().getQualifiedName());
    // bw.write("     </td>");
    //
    // bw.write("  </tr>\n");
    // }
    // }
    //
    // bw.write("</table>\n");
  }

  @Override
  protected IArtifactModelConfiguration getModelConfiguration() {
    return IArtifactModelConfiguration.SOURCE_RESOURCES_CONFIGURATION;
  }

  /**
   * <p>
   * </p>
   * 
   * @param referencedModule
   * @return
   */
  private String getIdentifier(IModuleArtifact referencedModule) {
    return referencedModule.getModuleName().replace(".", "_");
  }
}

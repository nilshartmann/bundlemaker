package org.bundlemaker.core.reports.exporter.html;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.IAnalysisModelConfiguration;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IResource;

public class ModuleContentReportExporter extends AbstractSingleModuleHtmlReportExporter {

  /**
   * {@inheritDoc}
   */
  protected String getReportName() {
    return ModuleIdentifier.asValidFileName(getCurrentModule().getModuleIdentifier(), false) + "_Content";
  }

  /**
   * <p>
   * </p>
   * 
   * @param bufferedWriter
   * @throws IOException
   */
  protected void writeHtmlBody(BufferedWriter bw) throws IOException {

    //
    bw.write("<h1>\n");
    bw.write(getCurrentModule().getModuleIdentifier().toString() + "\n");
    bw.write("</h1>\n");

    //
    bw.write("<table>\n");

    //
    boolean odd = true;

    //
    List<IResource> sources = new LinkedList<IResource>(getCurrentModule().getResources(ContentType.SOURCE));
    Collections.sort(sources, new Comparator<IResource>() {
      @Override
      public int compare(IResource arg0, IResource arg1) {
        return arg0.getPath().compareTo(arg1.getPath());
      }
    });

    //
    for (IResource resource : sources) {

      //
      if (odd) {
        bw.write("  <tr class=\"oddrow\">\n");
        odd = false;
      } else {
        bw.write("  <tr class=\"evenrow\">\n");
        odd = true;
      }

      bw.write("     <td>");
      bw.write(String.format("<a name=\"%1$s\">%1$s</a>", resource.getPath()));
      bw.write("     </td>");

      bw.write("  </tr>\n");
    }

    bw.write("</table>\n");
  }

  @Override
  protected IAnalysisModelConfiguration getModelConfiguration() {
    return IAnalysisModelConfiguration.SOURCE_RESOURCES_CONFIGURATION;
  }
}

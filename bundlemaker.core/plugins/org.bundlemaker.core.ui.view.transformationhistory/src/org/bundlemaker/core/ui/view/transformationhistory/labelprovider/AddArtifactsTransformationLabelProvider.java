/*******************************************************************************
 * Copyright (c) 2012 BundleMaker Project Team
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Nils Hartmann - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.view.transformationhistory.labelprovider;

import java.util.List;

import org.bundlemaker.core.resource.IAddArtifactsTransformation;
import org.eclipse.swt.graphics.Image;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class AddArtifactsTransformationLabelProvider extends
    AbstractTransformationLabelProvider<IAddArtifactsTransformation> {

  public AddArtifactsTransformationLabelProvider() {
    super(IAddArtifactsTransformation.class);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.bundlemaker.core.ui.transformations.history.labelprovider.AbstractTransformationLabelProvider#getTitle(org.
   * bundlemaker.core.transformation.ITransformation)
   */
  @Override
  protected String getTitle(IAddArtifactsTransformation transformation) {

    return "Add Artifacts";
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.bundlemaker.core.ui.transformations.history.labelprovider.AbstractTransformationLabelProvider#getTitleImage
   * (org.bundlemaker.core.transformation.ITransformation)
   */
  @Override
  protected Image getTitleImage(IAddArtifactsTransformation transformation) {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.bundlemaker.core.ui.transformations.history.labelprovider.AbstractTransformationLabelProvider#getDetails(org
   * .bundlemaker.core.transformation.ITransformation)
   */
  @Override
  protected String getDetails(IAddArtifactsTransformation transformation) {

    List<String> artifactsAdded = transformation.getArtifactsAdded();

    return getArtifactsAddedString(artifactsAdded) + " to " + transformation.getTarget();
  }

  protected String getArtifactsAddedString(List<String> artifacts) {
    final int MAX_ARTIFACTS_SHOWN = 3;
    int i = 0;
    StringBuilder what = new StringBuilder();

    for (i = 0; i < artifacts.size() && i < MAX_ARTIFACTS_SHOWN; i++) {
      what.append(artifacts.get(i));
      what.append(", ");
    }

    if (what.length() >= 2) {
      what.setLength(what.length() - 2); // cut off last ', '
    }

    int diff = artifacts.size() - MAX_ARTIFACTS_SHOWN;
    if (diff > 0) {
      what.append(" and ");
      if (diff == 1) {
        what.append("one more artifact");
      } else {
        what.append(diff).append(" more artifacts");
      }
    }

    return what.toString();
  }

  // public static void main(String[] args) {
  // List<String> s = l("a", "b", "c");
  //
  // o(s);
  // o(l("a"));
  // o(l("a", "b"));
  // o(l("a", "b", "c", "d"));
  // o(l("a", "b", "c", "d", "e"));
  // }
  //
  // private static void o(List<String> l) {
  // AddArtifactsTransformationLabelProvider p = new AddArtifactsTransformationLabelProvider();
  // String artifactsAddedString = p.getArtifactsAddedString(l);
  // System.out.println(l + " -> '" + artifactsAddedString + "'");
  // }
  //
  // private static List<String> l(String... args) {
  // LinkedList<String> result = new LinkedList<String>();
  //
  // for (String arg : args) {
  // result.add(arg);
  // }
  //
  // return result;
  // }
}

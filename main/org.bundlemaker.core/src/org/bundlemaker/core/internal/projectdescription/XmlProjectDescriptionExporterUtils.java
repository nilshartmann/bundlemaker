/*******************************************************************************
 * Copyright (c) 2011 Gerd Wuetherich (gerd@gerd-wuetherich.de).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Gerd Wuetherich (gerd@gerd-wuetherich.de) - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.internal.projectdescription;

import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.bundlemaker.core.internal.extensionpoints.projectcontentprovider.ProjectContentProviderExtension;
import org.bundlemaker.core.model.internal.projectdescription.xml.ObjectFactory;
import org.bundlemaker.core.model.internal.projectdescription.xml.XmlProjectDescriptionType;

/**
 * <p>
 * </p>
 */
public class XmlProjectDescriptionExporterUtils {

  /**
   * <p>
   * </p>
   * 
   * @param xmlProjectDescription
   * @param outputStream
   */
  public static String marshal(XmlProjectDescriptionType xmlProjectDescription) {

    try {

      ProjectContentProviderExtension.getAllProjectContentProviderExtension();

      //
      JaxbCompoundClassLoader jaxbCompoundClassLoader = new JaxbCompoundClassLoader();

      // the JAXBContext
      JAXBContext jaxbContext = JAXBContext.newInstance(jaxbCompoundClassLoader.getPackageString(),
          jaxbCompoundClassLoader.getCompoundClassLoader());

      // create the marshaller
      Marshaller marshaller = jaxbContext.createMarshaller();

      // set formatted output
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

      //
      StringWriter result = new StringWriter();

      //
      marshaller.marshal(new ObjectFactory().createXmlProjectDescription(xmlProjectDescription), result);

      //
      return result.toString();

    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param outputStream
   * @return
   */
  public static XmlProjectDescriptionType unmarshal(InputStream inputStream) {

    try {

      //
      JaxbCompoundClassLoader jaxbCompoundClassLoader = new JaxbCompoundClassLoader();

      // the JAXBContext
      JAXBContext jaxbContext = JAXBContext.newInstance(jaxbCompoundClassLoader.getPackageString(),
          jaxbCompoundClassLoader.getCompoundClassLoader());

      // create the marshaller
      Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

      //
      JAXBElement<XmlProjectDescriptionType> root = unmarshaller.unmarshal(new StreamSource(inputStream),
          XmlProjectDescriptionType.class);
      return root.getValue();

    } catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }
}

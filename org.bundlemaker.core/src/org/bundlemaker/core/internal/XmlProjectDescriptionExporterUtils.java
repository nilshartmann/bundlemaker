package org.bundlemaker.core.internal;

import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.bundlemaker.core.model.internal.projectdescription.xml.ObjectFactory;
import org.bundlemaker.core.model.internal.projectdescription.xml.XmlFileBasedContentType;
import org.bundlemaker.core.model.internal.projectdescription.xml.XmlProjectDescriptionType;
import org.bundlemaker.core.model.internal.projectdescription.xml.XmlResourceContentType;

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

      // the JAXBContext
      JAXBContext jaxbContext = JAXBContext.newInstance(XmlFileBasedContentType.class, XmlProjectDescriptionType.class,
          XmlResourceContentType.class);

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

    } catch (JAXBException e) {
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

      // the JAXBContext
      JAXBContext jaxbContext = JAXBContext.newInstance(XmlFileBasedContentType.class, XmlProjectDescriptionType.class,
          XmlResourceContentType.class);

      // create the marshaller
      Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

      //
      JAXBElement<XmlProjectDescriptionType> root = unmarshaller.unmarshal(new StreamSource(inputStream),
          XmlProjectDescriptionType.class);
      return root.getValue();

    } catch (JAXBException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }
}

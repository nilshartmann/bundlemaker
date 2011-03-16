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
package org.bundlemaker.core.exporter.structure101;

import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.bundlemaker.core.exporter.structure101.xml.DataType;
import org.bundlemaker.core.exporter.structure101.xml.DependenciesType;
import org.bundlemaker.core.exporter.structure101.xml.DependencyType;
import org.bundlemaker.core.exporter.structure101.xml.ModuleType;
import org.bundlemaker.core.exporter.structure101.xml.ModulesType;
import org.bundlemaker.core.exporter.structure101.xml.ObjectFactory;

public class Structure101ExporterUtils {

  /**
   * <p>
   * </p>
   * 
   * @param fullyQualifiedType
   * @return
   */
  public static String getPackageName(String fullyQualifiedType) {

    //
    return fullyQualifiedType.indexOf('.') != -1 ? fullyQualifiedType.substring(0, fullyQualifiedType.lastIndexOf('.'))
        : "";
  }

  /**
   * <p>
   * </p>
   * 
   * @param productsType
   * @param outputStream
   */
  public static void marshal(DataType dataType, OutputStream outputStream) {

    try {

      //
      JAXBContext jc = JAXBContext.newInstance(DataType.class, DependenciesType.class, DependencyType.class,
          ModulesType.class, ModuleType.class, ObjectFactory.class);

      //
      Marshaller marshaller = jc.createMarshaller();

      // set formatted output
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

      //
      marshaller.marshal(new ObjectFactory().createData(dataType), outputStream);

    } catch (JAXBException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }
}

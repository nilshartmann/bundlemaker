package org.bundlemaker.analysis.xml.internal;

import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.bundlemaker.analysis.xml.internal.AbstractArtifactType;
import org.bundlemaker.analysis.xml.internal.DependenciesType;
import org.bundlemaker.analysis.xml.internal.DependencyModelType;
import org.bundlemaker.analysis.xml.internal.DependencyType;
import org.bundlemaker.analysis.xml.internal.GroupType;
import org.bundlemaker.analysis.xml.internal.ModuleType;
import org.bundlemaker.analysis.xml.internal.ModulesType;
import org.bundlemaker.analysis.xml.internal.ObjectFactory;
import org.bundlemaker.analysis.xml.internal.PackageLayoutType;
import org.bundlemaker.analysis.xml.internal.PackageType;
import org.bundlemaker.analysis.xml.internal.ResourceType;
import org.bundlemaker.analysis.xml.internal.TypeType;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ArtifactMarshallerUnmarshallerUtils {

  /**
   * <p>
   * </p>
   * @throws JAXBException 
   */
  public static void marshall(DependencyModelType dependencyModel, OutputStream outputStream) throws JAXBException {

    // get the jaxb context
    JAXBContext jc = JAXBContext.newInstance(AbstractArtifactType.class, DependenciesType.class,
        DependencyModelType.class, DependencyType.class, GroupType.class, ModulesType.class, ModuleType.class,
        ObjectFactory.class, PackageLayoutType.class, PackageType.class, ResourceType.class, TypeType.class);

    // create the marshaller
    Marshaller marshaller = jc.createMarshaller();

    // set formatted output
    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

    // marshal the result
    marshaller.marshal(new ObjectFactory().createDependencyModel(dependencyModel), outputStream);
  }
}

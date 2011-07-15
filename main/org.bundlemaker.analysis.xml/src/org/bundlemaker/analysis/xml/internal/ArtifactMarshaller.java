package org.bundlemaker.analysis.xml.internal;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.xml.IArtifactMarshaller;
import org.bundlemaker.analysis.xml.internal.AbstractArtifactType;
import org.bundlemaker.analysis.xml.internal.DependencyModelType;
import org.bundlemaker.analysis.xml.internal.GroupType;
import org.bundlemaker.analysis.xml.internal.ModuleType;
import org.bundlemaker.analysis.xml.internal.ModulesType;
import org.bundlemaker.analysis.xml.internal.ObjectFactory;
import org.bundlemaker.analysis.xml.internal.PackageType;
import org.bundlemaker.analysis.xml.internal.ResourceType;
import org.bundlemaker.analysis.xml.internal.TypeType;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ArtifactMarshaller implements IArtifactMarshaller {

  /** - */
  private ObjectFactory _objectFactory;

  /** - */
  private Object        _currentParent;

  /**
   * <p>
   * Creates a new instance of type {@link ArtifactMarshaller}.
   * </p>
   */
  public ArtifactMarshaller() {

    //
    _objectFactory = new ObjectFactory();
  }

  /**
   * {@inheritDoc}
   * @throws JAXBException 
   */
  @Override
  public void marshal(IArtifact artifact, OutputStream outputStream) throws JAXBException {

    //
    DependencyModelType dependencyModel = _objectFactory.createDependencyModelType();
    dependencyModel.setModules(new ModulesType());

    _currentParent = dependencyModel;

    // add nodes
    for (IArtifact iArtifact : artifact.getChildren()) {
      addNode(iArtifact);
    }

    //
    ArtifactMarshallerUnmarshallerUtils.marshall(dependencyModel, outputStream);
  }

  /**
   * <p>
   * </p>
   * 
   * @param iArtifact
   */
  private void addNode(IArtifact iArtifact) {

    //
    AbstractArtifactType currentArtifactType = null;

    // switch
    switch (iArtifact.getType()) {

    // switch 'group'
    case Group: {

      //
      currentArtifactType = _objectFactory.createGroupType();
      currentArtifactType.setName(iArtifact.getQualifiedName());
      currentArtifactType.setId("123");

      //
      if (_currentParent != null) {
        if (_currentParent instanceof GroupType) {
          ((GroupType) _currentParent).getGroupOrModule().add(currentArtifactType);
        } else if (_currentParent instanceof DependencyModelType) {
          ((DependencyModelType) _currentParent).getModules().getGroupOrModule().add(currentArtifactType);
        }
      }

      //
      break;
    }

      //
    case Module: {

      //
      currentArtifactType = _objectFactory.createModuleType();
      currentArtifactType.setName(iArtifact.getQualifiedName());
      currentArtifactType.setId("123");

      //
      //
      if (_currentParent != null) {
        if (_currentParent instanceof GroupType) {
          ((GroupType) _currentParent).getGroupOrModule().add(currentArtifactType);
        } else if (_currentParent instanceof DependencyModelType) {
          ((DependencyModelType) _currentParent).getModules().getGroupOrModule().add(currentArtifactType);
        }
      }

      //
      break;
    }
    case Package: {

      //
      currentArtifactType = _objectFactory.createPackageType();
      currentArtifactType.setName(iArtifact.getQualifiedName());
      currentArtifactType.setId("123");

      //
      //
      if (_currentParent != null) {
        if (_currentParent instanceof PackageType) {
          ((PackageType) _currentParent).getPackageOrResourceOrType().add(currentArtifactType);
        } else if (_currentParent instanceof ModuleType) {
          ((ModuleType) _currentParent).getPackageOrResourceOrType().add(currentArtifactType);
        }
      }

      //
      break;
    }
    case Resource: {

      //
      currentArtifactType = _objectFactory.createResourceType();
      currentArtifactType.setName(iArtifact.getQualifiedName());
      currentArtifactType.setId("123");

      //
      //
      if (_currentParent != null) {
        if (_currentParent instanceof PackageType) {
          ((PackageType) _currentParent).getPackageOrResourceOrType().add(currentArtifactType);
        } else if (_currentParent instanceof ModuleType) {
          ((ModuleType) _currentParent).getPackageOrResourceOrType().add(currentArtifactType);
        }
      }

      //
      break;
    }
    case Type: {

      //
      currentArtifactType = _objectFactory.createTypeType();
      currentArtifactType.setName(iArtifact.getQualifiedName());
      currentArtifactType.setId("123");

      //
      //
      if (_currentParent != null) {
        if (_currentParent instanceof PackageType) {
          ((PackageType) _currentParent).getPackageOrResourceOrType().add(currentArtifactType);
        } else if (_currentParent instanceof ModuleType) {
          ((ModuleType) _currentParent).getPackageOrResourceOrType().add(currentArtifactType);
        } else if (_currentParent instanceof ResourceType) {
          ((ResourceType) _currentParent).getType().add((TypeType)currentArtifactType);
        } 
      }

      //
      break;
    }
    default:
      break;
    }

    // _currentParent
    Object oldParent = _currentParent;
    _currentParent = currentArtifactType;

    //
    for (IArtifact artifact : sort(iArtifact.getChildren())) {
      addNode(artifact);
    }

    //
    _currentParent = oldParent;
  }

  /**
   * <p>
   * </p>
   * 
   * @param children
   * @return
   */
  private List<IArtifact> sort(Collection<IArtifact> children) {

    //
    List<IArtifact> sorted = new ArrayList<IArtifact>(children);

    //
    Collections.sort(sorted, new Comparator<IArtifact>() {
      @Override
      public int compare(IArtifact o1, IArtifact o2) {
        return o1.getQualifiedName().compareTo(o2.getQualifiedName());
      }
    });

    //
    return sorted;
  }
}

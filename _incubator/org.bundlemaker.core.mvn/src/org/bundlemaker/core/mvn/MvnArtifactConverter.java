package org.bundlemaker.core.mvn;

import java.util.Map;

import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.mvn.content.xml.MvnArtifactType;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MvnArtifactConverter {

  /** DELIMITER */
  private static final String DELIMITER                = ".";

  /** - */
  public static final String  ORIGINAL_MODULE_NAME     = "originalModuleModuleName";

  /** - */
  public static final String  ORIGINAL_MVN_ARTIFACT_ID = "originalMvnArtifactId";

  /** - */
  public static final String  ORIGINAL_MVN_GROUP_ID    = "originalMvnGroupId";

  /** - */
  public static final String  MVN_ARTIFACT_ID          = "mvnArtifactId";

  /** - */
  public static final String  MVN_GROUP_ID             = "mvnGroupId";

  public static final IModuleIdentifier toModuleIdentifier(MvnArtifactType mvnArtifactType) {

    //
    Assert.isNotNull(mvnArtifactType);

    //
    IModuleIdentifier moduleIdentifier = new ModuleIdentifier(mvnArtifactType.getGroupId() + DELIMITER
        + mvnArtifactType.getArtifactId(),
        mvnArtifactType.getVersion());

    //
    return moduleIdentifier;
  }

  /**
   * <p>
   * </p>
   * 
   * @param mvnArtifactDescriptor
   * @return
   */
  public static final IModuleIdentifier toModuleIdentifier(String groupId, String artifactId, String version) {

    //
    Assert.isNotNull(groupId);
    Assert.isNotNull(artifactId);
    Assert.isNotNull(version);

    //
    IModuleIdentifier moduleIdentifier = new ModuleIdentifier(groupId + DELIMITER
        + artifactId,
        version);

    //
    return moduleIdentifier;
  }

  /**
   * <p>
   * </p>
   * 
   * @param identifier
   * @return
   */
  public static final MvnArtifactType fromModule(IModule module) {

    //
    Assert.isNotNull(module);

    //
    Map<String, Object> attr = module.getUserAttributes();

    //
    if (attr.containsKey(MvnArtifactConverter.MVN_ARTIFACT_ID) &&
        attr.containsKey(MvnArtifactConverter.MVN_GROUP_ID)) {

      // TODO: check if modules has been renamed?
      MvnArtifactType mvnArtifactType = new MvnArtifactType();
      mvnArtifactType.setGroupId((String) attr.get(MvnArtifactConverter.MVN_GROUP_ID));
      mvnArtifactType.setArtifactId((String) attr.get(MvnArtifactConverter.MVN_ARTIFACT_ID));
      mvnArtifactType.setVersion(module.getModuleIdentifier().getVersion());

      //
      return mvnArtifactType;

    } else if (attr.containsKey(MvnArtifactConverter.MVN_GROUP_ID)
        && module.getModuleIdentifier().getName()
            .startsWith((String) attr.get(MvnArtifactConverter.MVN_GROUP_ID))) {

      //
      String groupId = (String) attr.get(MvnArtifactConverter.MVN_GROUP_ID);
      String moduleName = module.getModuleIdentifier().getName();

      // TODO: check if modules has been renamed?
      MvnArtifactType mvnArtifactType = new MvnArtifactType();
      mvnArtifactType.setGroupId(groupId);
      mvnArtifactType.setArtifactId(moduleName.substring(groupId.length() + 1));
      mvnArtifactType.setVersion(module.getModuleIdentifier().getVersion());

      //
      return mvnArtifactType;

    } else if (attr.containsKey(MvnArtifactConverter.ORIGINAL_MVN_GROUP_ID) &&
        attr.containsKey(MvnArtifactConverter.ORIGINAL_MVN_ARTIFACT_ID) &&
        attr.containsKey(MvnArtifactConverter.ORIGINAL_MODULE_NAME) &&
        attr.get(MvnArtifactConverter.ORIGINAL_MODULE_NAME).equals(module.getModuleIdentifier().getName())) {

      // TODO: check if modules has been renamed?
      MvnArtifactType mvnArtifactType = new MvnArtifactType();
      mvnArtifactType.setGroupId((String) attr.get(MvnArtifactConverter.ORIGINAL_MVN_GROUP_ID));
      mvnArtifactType.setArtifactId((String) attr.get(MvnArtifactConverter.ORIGINAL_MVN_ARTIFACT_ID));
      mvnArtifactType.setVersion(module.getModuleIdentifier().getVersion());

      //
      return mvnArtifactType;

    } else {

      String name = module.getModuleIdentifier().getName();

      // split the string
      String[] splittedString = new String[] { name, name };

      //
      int index = name.lastIndexOf('.');
      if (index != -1 && name.length() >= index + 1) {
        splittedString = new String[] { name.substring(0, index),
            name.substring(index + 1) };
      }

      //
      String groupId = splittedString[0];
      String artifactId = splittedString[1];
      String version = module.getModuleIdentifier().getVersion();

      //
      MvnArtifactType mvnArtifactType = new MvnArtifactType();
      mvnArtifactType.setGroupId(groupId);
      mvnArtifactType.setArtifactId(artifactId);
      mvnArtifactType.setVersion(version);

      //
      return mvnArtifactType;
    }
  }
}

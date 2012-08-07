package org.bundlemaker.core.util.gson;

import java.lang.reflect.Type;

import org.bundlemaker.core.analysis.ArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IArtifactTreeVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.Path;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * <p>
 * Implements a {@link JsonSerializer}/{@link JsonDeserializer} for {@link IBundleMakerArtifact IBundleMakerArtifacts}.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BundleMakerArtifactJsonAdapter implements JsonSerializer<IBundleMakerArtifact>,
    JsonDeserializer<IBundleMakerArtifact> {

  /** - */
  private IModularizedSystem _modularizedSystem;

  /**
   * <p>
   * Creates a new instance of type {@link BundleMakerArtifactJsonAdapter}.
   * </p>
   * 
   * @param modularizedSystem
   */
  public BundleMakerArtifactJsonAdapter(IModularizedSystem modularizedSystem) {
    _modularizedSystem = modularizedSystem;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonElement serialize(IBundleMakerArtifact bundleMakerArtifact, Type arg1, JsonSerializationContext context) {

    JsonObject result = new JsonObject();
    result.add("configuration", context.serialize(bundleMakerArtifact.getConfiguration()));
    result.addProperty("path", bundleMakerArtifact.getFullPath().toPortableString());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IBundleMakerArtifact deserialize(JsonElement jsonElement, Type arg1, JsonDeserializationContext context)
      throws JsonParseException {

    IArtifactModelConfiguration configuration = context.deserialize(jsonElement.getAsJsonObject()
        .get("configuration"), ArtifactModelConfiguration.class);

    Assert.isNotNull(configuration, "IArtifactModelConfiguration 'configuration' must not be null.");

    IRootArtifact rootArtifact = _modularizedSystem.getArtifactModel(configuration);

    String path = jsonElement.getAsJsonObject()
        .get("path").getAsString();

    if (".".equals(path)) {
      return rootArtifact;
    }

    //
    IBundleMakerArtifact result = rootArtifact.getChild(new Path(path));

    if (result == null) {

      _modularizedSystem.getArtifactModel(ArtifactModelConfiguration.BINARY_RESOURCES_CONFIGURATION).accept(
          new IArtifactTreeVisitor.Adapter() {

            @Override
            public boolean visit(IModuleArtifact moduleArtifact) {
              onVisit(moduleArtifact);
              return false;
            }

            @Override
            public boolean visit(IResourceArtifact artifact) {
              return false;
            }

            @Override
            public boolean onVisit(IBundleMakerArtifact artifact) {
              System.out.println(artifact.getFullPath().toOSString());
              return true;
            }
          });
    }

    //
    return result;
  }
}
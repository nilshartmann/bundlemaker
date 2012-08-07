package org.bundlemaker.core.util.gson;

import java.lang.reflect.Type;

import org.bundlemaker.core.analysis.IArtifactSelector;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * <p>
 * Implements a {@link JsonSerializer}/{@link JsonDeserializer} for {@link IArtifactSelector IArtifactSelectors}.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ArtifactSelectorJsonAdapter implements JsonSerializer<IArtifactSelector>,
    JsonDeserializer<IArtifactSelector> {

  /** the CLASSNAME attribute */
  private static final String CLASSNAME = "CLASSNAME";

  /** the INSTANCE attribute */
  private static final String INSTANCE  = "INSTANCE";

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonElement serialize(IArtifactSelector src, Type typeOfSrc,
      JsonSerializationContext context) {

    JsonObject retValue = new JsonObject();
    String className = src.getClass().getCanonicalName();
    retValue.addProperty(CLASSNAME, className);
    JsonElement elem = context.serialize(src);
    retValue.add(INSTANCE, elem);
    return retValue;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IArtifactSelector deserialize(JsonElement json, Type typeOfT,
      JsonDeserializationContext context) throws JsonParseException {

    JsonObject jsonObject = json.getAsJsonObject();
    JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASSNAME);
    String className = prim.getAsString();

    Class<?> clazz = null;
    try {
      clazz = Class.forName(className);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      throw new JsonParseException(e.getMessage());
    }

    // return the instance
    return context.deserialize(jsonObject.get(INSTANCE), clazz);
  }
}

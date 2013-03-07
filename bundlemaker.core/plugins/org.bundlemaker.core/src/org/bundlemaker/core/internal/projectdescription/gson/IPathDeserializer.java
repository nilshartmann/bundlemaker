package org.bundlemaker.core.internal.projectdescription.gson;

import java.lang.reflect.Type;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class IPathDeserializer implements JsonDeserializer<IPath>, JsonSerializer<IPath> {

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonElement serialize(IPath path, Type type, JsonSerializationContext context) {
    return new JsonPrimitive(path.toPortableString());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IPath deserialize(JsonElement element, Type type, JsonDeserializationContext context)
      throws JsonParseException {
    return new Path(element.getAsJsonPrimitive().getAsString());
  }
}
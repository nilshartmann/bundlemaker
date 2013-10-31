package org.bundlemaker.core.project.internal.gson;

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
    String portableString = path.toPortableString();
    return new JsonPrimitive(portableString);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IPath deserialize(JsonElement element, Type type, JsonDeserializationContext context)
      throws JsonParseException {
    JsonPrimitive asJsonPrimitive = element.getAsJsonPrimitive();
    String asString = asJsonPrimitive.getAsString();
    IPath path = Path.fromPortableString(asString);
    return path;
  }
}
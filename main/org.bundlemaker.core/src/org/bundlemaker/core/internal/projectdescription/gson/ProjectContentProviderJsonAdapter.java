package org.bundlemaker.core.internal.projectdescription.gson;

import java.lang.reflect.Type;

import org.bundlemaker.core.internal.projectdescription.contentprovider.ProjectContentProviderExtension;
import org.bundlemaker.core.projectdescription.IProjectContentProvider;
import org.eclipse.core.runtime.Assert;

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
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ProjectContentProviderJsonAdapter implements JsonSerializer<IProjectContentProvider>,
    JsonDeserializer<IProjectContentProvider> {

  /** the CLASSNAME attribute */
  private static final String                TYPE     = "provider-id";

  /** the INSTANCE attribute */
  private static final String                INSTANCE = "provider-config";

  /** - */
  private ContentProviderCompoundClassLoader _classLoader;

  /**
   * <p>
   * Creates a new instance of type {@link ProjectContentProviderJsonAdapter}.
   * </p>
   * 
   * @param classLoader
   */
  public ProjectContentProviderJsonAdapter(ContentProviderCompoundClassLoader classLoader) {
    Assert.isNotNull(classLoader);

    //
    _classLoader = classLoader;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonElement serialize(IProjectContentProvider src, Type typeOfSrc,
      JsonSerializationContext context) {

    String className = src.getClass().getCanonicalName();
    String id = _classLoader.getClassnameToIdMap().get(className);

    JsonObject retValue = new JsonObject();
    retValue.addProperty(TYPE, id);
    JsonElement elem = context.serialize(src);
    retValue.add(INSTANCE, elem);
    return retValue;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IProjectContentProvider deserialize(JsonElement json, Type typeOfT,
      JsonDeserializationContext context) throws JsonParseException {

    JsonObject jsonObject = json.getAsJsonObject();
    JsonPrimitive prim = (JsonPrimitive) jsonObject.get(TYPE);
    String id = prim.getAsString();
    ProjectContentProviderExtension extension = _classLoader.getIdToExtensionMap().get(id);

    Class<?> clazz = null;
    try {
      clazz = _classLoader.getCompoundClassLoader().loadClass(extension.getClassName());
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      throw new JsonParseException(e.getMessage());
    }

    // return the instance
    return context.deserialize(jsonObject.get(INSTANCE), clazz);
  }
}

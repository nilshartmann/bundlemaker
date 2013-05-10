package org.bundlemaker.core.util.prefs;

public interface IBundleMakerPreferences {

  void reload();

  boolean getBoolean(String key, boolean defaultValue);

  byte[] getByteArray(String key, byte[] defaultValue);

  double getDouble(String key, double defaultValue);

  float getFloat(String key, float defaultValue);

  int getInt(String key, int defaultValue);

  long getLong(String key, long defaultValue);

  String getString(String key, String defaultValue);
}

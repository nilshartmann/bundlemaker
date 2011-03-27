package org.bundlemaker.core.ui.editor.transformation;

public class NewModule {

  private final String _name;

  private final String _version;

  private String       _fromName;

  private String       _fromVersion;

  private String       _withResources;

  public NewModule(String name, String version) {
    super();
    _name = name;
    _version = version;
  }

  public NewModule from(String fromName, String fromVersion) {
    _fromName = fromName;
    _fromVersion = fromVersion;

    return this;
  }

  public NewModule including(String pattern) {
    _withResources = pattern;
    return this;
  }

  @Override
  public String toString() {
    return "NewModule [_name=" + _name + ", _version=" + _version + ", _fromName=" + _fromName + ", _fromVersion="
        + _fromVersion + ", _withResources=" + _withResources + "]";
  }

  public String getFromName() {
    return _fromName;
  }

  public void setFromName(String fromName) {
    _fromName = fromName;
  }

  public String getFromVersion() {
    return _fromVersion;
  }

  public void setFromVersion(String fromVersion) {
    _fromVersion = fromVersion;
  }

  public String[] getIncludes() {
    return _withResources.split(",");
  }

  public void setWithResources(String withResources) {
    _withResources = withResources;
  }

  public String getName() {
    return _name;
  }

  public String getVersion() {
    return _version;
  }

}

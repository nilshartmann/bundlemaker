/*******************************************************************************
 * Copyright (c) 2011 Gerd Wuetherich (gerd@gerd-wuetherich.de).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Gerd Wuetherich (gerd@gerd-wuetherich.de) - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.projectdescription.spi;

import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class FileBasedProjectContentInfo<T> {

  /** - */
  private String  _name;

  private String  _binaryName;

  /** - */
  private String  _version;

  /** - */
  private boolean _isSource = false;

  /** - */
  private T       _userObject;

  /**
   * <p>
   * Creates a new instance of type {@link FileBasedProjectContentInfo}.
   * </p>
   * 
   * @param name
   * @param version
   */
  public FileBasedProjectContentInfo(String name,
      String binaryName,
      String version, boolean isSource) {
    Assert.isNotNull(name);
    Assert.isNotNull(version);

    _name = name;
    _binaryName = binaryName;
    _version = version;
    _isSource = isSource;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public String getName() {
    return _name;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public String getVersion() {
    return _version;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public boolean isSource() {
    return _isSource;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public T getUserObject() {
    return _userObject;
  }

  /**
   * <p>
   * </p>
   * 
   * @param userObject
   */
  public void setUserObject(T userObject) {
    _userObject = userObject;
  }

  /**
   * @return the binaryName
   */
  public String getBinaryName() {
    return _binaryName;
  }

  @Override
  public String toString() {
    return "FileBasedProjectContentInfo [_name=" + _name + ", _version=" + _version + ", _isSource=" + _isSource
        + ", _userObject=" + _userObject + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (_isSource ? 1231 : 1237);
    result = prime * result + ((_name == null) ? 0 : _name.hashCode());
    result = prime * result + ((_version == null) ? 0 : _version.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    @SuppressWarnings("rawtypes")
    FileBasedProjectContentInfo other = (FileBasedProjectContentInfo) obj;
    if (_isSource != other._isSource)
      return false;
    if (_name == null) {
      if (other._name != null)
        return false;
    } else if (!_name.equals(other._name))
      return false;
    if (_version == null) {
      if (other._version != null)
        return false;
    } else if (!_version.equals(other._version))
      return false;
    return true;
  }
}

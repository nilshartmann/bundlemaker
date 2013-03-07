package org.bundlemaker.core.projectdescription.spi;

import org.bundlemaker.core.projectdescription.AnalyzeMode;
import org.bundlemaker.core.projectdescription.IProjectContentEntry;
import org.bundlemaker.core.projectdescription.IProjectDescription;
import org.bundlemaker.core.projectdescription.ProjectContentType;
import org.bundlemaker.core.projectdescription.VariablePath;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IModifiableProjectContentEntry extends IProjectContentEntry {

  void setId(String string);

  void setName(String contentName);

  void setVersion(String contentVersion);

  void setAnalyzeMode(AnalyzeMode analyzeMode);

  void addRootPath(VariablePath rootPath, ProjectContentType type);

  void removeRootPath(VariablePath rootPath, ProjectContentType type);

  void setBinaryPaths(String[] binaryRootPaths);

  void setSourcePaths(String[] sourceRootPaths);

  void initialize(IProjectDescription projectDescription) throws CoreException;
}

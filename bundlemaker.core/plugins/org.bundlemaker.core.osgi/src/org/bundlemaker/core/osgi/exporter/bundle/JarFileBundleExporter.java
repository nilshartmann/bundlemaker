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
package org.bundlemaker.core.osgi.exporter.bundle;

import static java.lang.String.format;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.exporter.ITemplateProvider;
import org.bundlemaker.core.exporter.util.ModuleExporterUtils;
import org.bundlemaker.core.osgi.exporter.AbstractManifestAwareExporter;
import org.bundlemaker.core.osgi.manifest.IBundleManifestCreator;
import org.bundlemaker.core.osgi.manifest.IManifestPreferences;
import org.bundlemaker.core.osgi.utils.JarFileManifestWriter;
import org.bundlemaker.core.osgi.utils.ManifestUtils;
import org.bundlemaker.core.resource.IModularizedSystem;
import org.bundlemaker.core.resource.IModule;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.resource.ResourceType;
import org.bundlemaker.core.util.JarFileUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.virgo.bundlor.ManifestWriter;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class JarFileBundleExporter extends AbstractManifestAwareExporter {

  /**
   * If set to <tt>true</tt> sources will be included in the resulting JAR (beneath OSGI-OPT/src subfolder)
   * 
   * <p>Defaults to <tt>false</tt>
   */
  private boolean _includeSources = false;
  
  private boolean _createEclipseSourceBundle = false;

  /**
   * <p>
   * Creates a new instance of type {@link JarFileBundleExporter}.
   * </p>
   */
  public JarFileBundleExporter() {
    super(null, null, null);
  }

  /**
   * <p>
   * Creates a new instance of type {@link JarFileBundleExporter}.
   * </p>
   * 
   * @param templateProvider
   */
  public JarFileBundleExporter(ITemplateProvider templateProvider, IBundleManifestCreator bundleManifestCreator,
      IManifestPreferences manifestPreferences) {
    super(templateProvider, bundleManifestCreator, manifestPreferences);
  }

  /**
   * {@inheritDoc}
   * 
   * @throws CoreException
   */
  @Override
  protected void doExport(IProgressMonitor progressMonitor) throws CoreException {

    // create new file if repackaging is required
    if (isIncludeSources() || !getTemplateProvider().getAdditionalResources(getCurrentModule(), getCurrentModularizedSystem(),
        getCurrentContext()).isEmpty()
        || ModuleExporterUtils.requiresRepackaging(getCurrentModule(), ResourceType.BINARY)) {

      // create new File
      createNewJarFile();
    }

    // copy (and patch) the original
    else {

      // get the root file
      File rootFile = ModuleExporterUtils.getRootFile(getCurrentModule(), ResourceType.BINARY);

      // get the manifest writer
      ManifestWriter manifestWriter = new JarFileManifestWriter(rootFile, getDestinationJarFile());

      //
      manifestWriter.write(getManifestContents());
    }
    
    if (isCreateEclipseSourceBundle()) {
      createEclipseSourceBundle();
    }
  }
  
  private void createEclipseSourceBundle() throws CoreException {
    
    File sourceFile = getDestinationSourceJarFile();
    
    OutputStream outputStream;
    try {
      outputStream = new FileOutputStream(sourceFile);
      
      Manifest manifest = createSourceManifest();
      
      JarFileUtils.createJarArchive(getCurrentModule().getResources(ResourceType.SOURCE), manifest, null, outputStream);
      outputStream.close();
    } catch (IOException e) {
      throw new CoreException(new Status(IStatus.ERROR, "", "Could not create Source Bundle " + sourceFile + ": " + e, e));
    }
  }

  /**
   * @return
   */
  private Manifest createSourceManifest() {
    
    IModule currentModule = getCurrentModule();
    String sourceBundleName = currentModule.getModuleIdentifier().getName()+".source";
    String bundleVersion = currentModule.getModuleIdentifier().getVersion();

    Manifest manifest = new Manifest();
    Attributes mainAttributes = manifest.getMainAttributes();
    
    mainAttributes.putValue("Bundle-ManifestVersion", "2");
    mainAttributes.putValue("Bundle-SymbolicName", sourceBundleName);
    mainAttributes.putValue("Bundle-Version", bundleVersion);
    
    String sourceBundleHeader = format("%s;version=\"%s\"", currentModule.getModuleIdentifier().getName(), 
        bundleVersion);
    
    mainAttributes.putValue("Eclipse-SourceBundle", sourceBundleHeader);
    
    return manifest;

  
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  private void createNewJarFile() throws CoreException {

    try {

      // create the output stream
      OutputStream outputStream = createOutputStream(getCurrentModularizedSystem(), getCurrentModule(),
          getCurrentContext());

      //
      Set<IResource> resourceKeys = getTemplateProvider().getAdditionalResources(getCurrentModule(),
          getCurrentModularizedSystem(), getCurrentContext());

      Set<IResource> additionalResources;

      if (isIncludeSources()) {
        additionalResources = new HashSet<IResource>();
        // add files from template provider
        additionalResources.addAll(resourceKeys);
        
        // add sources
        Set<? extends IModuleResource> sources = getCurrentModule().getResources(ResourceType.SOURCE);
        additionalResources.addAll(wrapSourceResources(sources));
      } else {
        
        // add only resources from template provider
        additionalResources = resourceKeys;
      }

      // export the jar archive
      JarFileUtils.createJarArchive(getCurrentModule().getResources(ResourceType.BINARY),
          ManifestUtils.toManifest(getManifestContents()), additionalResources, outputStream);

      // close the output stream
      outputStream.close();

    } catch (IOException e) {
      // TODO
      e.printStackTrace();
      throw new CoreException(new Status(IStatus.ERROR, "", ""));
    } catch (Exception e) {
      // TODO
      e.printStackTrace();
      throw new CoreException(new Status(IStatus.ERROR, "", ""));
    }
  }

  /**
   * "virtually" moves the specified resources to OSGI-OPT/src
   * @param sources
   * @return
   */
  private Collection<? extends IResource> wrapSourceResources(Set<? extends IModuleResource> sources) {
    
    Set<IResource> movedSources = new HashSet<IResource>();
    
    // wrap sources in new IReadableResource that has it's path pointing to OSGI-OPT/src
    for (final IResource source : sources) {
      final IResource movedSource = new IResource() {
        
        @Override
        public String getPath() {
          return "OSGI-OPT/src/" + source.getPath();
        }
        
        @Override
        public String getName() {
          return source.getName();
        }
        
        @Override
        public String getDirectory() {
          return "OSGI-OPT/src/" + getDirectory();
        }
        
        @Override
        public byte[] getContent() {
          return source.getContent();
        }
        
        @Override
        public long getCurrentTimestamp() {
          return source.getCurrentTimestamp();
        }
      };
      
      // add to result
      movedSources.add(movedSource);
    }
    
    // return
    return movedSources;
    
  }

  /**
   * <p>
   * </p>
   * 
   * @param modularizedSystem
   * @param module
   * @param context
   * @return
   * @throws Exception
   */
  protected OutputStream createOutputStream(IModularizedSystem modularizedSystem, IModule module,
      IModuleExporterContext context) throws Exception {

    File targetFile = getDestinationJarFile();

    // return a new file output stream
    return new FileOutputStream(targetFile);
  }
  
  protected File getDestinationFile(String name) {
    // create the target file
    File targetFile = new File(getCurrentContext().getDestinationDirectory(), name);

    // create the parent directories
    if (!targetFile.getParentFile().exists()) {
      targetFile.getParentFile().mkdirs();
    }
    return targetFile;

  }

  /**
   * <p>Returns the destination Jar File
   * </p>
   * 
   * @param modularizedSystem
   * @param module
   * @param context
   * @return
   */
  protected File getDestinationJarFile() {
    return getDestinationFile(computeJarFileName(getCurrentModule()));
  }
  
  protected File getDestinationSourceJarFile() {
    return getDestinationFile(computeSourceJarFileName(getCurrentModule()));
  }
  
  protected String computeSourceJarFileName(IModule module) {
    return module.getModuleIdentifier().getName() + ".source_" + module.getModuleIdentifier().getVersion() + ".jar";
  }

  /**
   * <p>
   * </p>
   * 
   * @param module
   * @return
   */
  protected String computeJarFileName(IModule module) {

    //
    return module.getModuleIdentifier().getName() + "_" + module.getModuleIdentifier().getVersion() + ".jar";
  }

  /**
   * @return the includeSources
   */
  public boolean isIncludeSources() {
    return _includeSources;
  }

  /**
   * Set to true to include sources in the jar file (in OSGI-OPT/src)
   * 
   * @param includeSources
   *          the includeSources to set
   */
  public void setIncludeSources(boolean includeSources) {
    _includeSources = includeSources;
  }
  
  /**
   * @param createEclipseSourceBundle the createEclipseSourceBundle to set
   */
  public void setCreateEclipseSourceBundle(boolean createEclipseSourceBundle) {
    _createEclipseSourceBundle = createEclipseSourceBundle;
  }
  
  /**
   * @return the createEclipseSourceBundle
   */
  public boolean isCreateEclipseSourceBundle() {
    return _createEclipseSourceBundle;
  }

}

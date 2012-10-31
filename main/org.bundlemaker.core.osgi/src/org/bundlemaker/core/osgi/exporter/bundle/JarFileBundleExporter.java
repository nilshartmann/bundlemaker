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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.exporter.ITemplateProvider;
import org.bundlemaker.core.exporter.util.ModuleExporterUtils;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.osgi.exporter.AbstractManifestAwareExporter;
import org.bundlemaker.core.osgi.manifest.IBundleManifestCreator;
import org.bundlemaker.core.osgi.manifest.IManifestPreferences;
import org.bundlemaker.core.osgi.utils.JarFileManifestWriter;
import org.bundlemaker.core.osgi.utils.ManifestUtils;
import org.bundlemaker.core.projectdescription.ProjectContentType;
import org.bundlemaker.core.resource.IReadableResource;
import org.bundlemaker.core.resource.IResource;
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
        || ModuleExporterUtils.requiresRepackaging(getCurrentModule(), ProjectContentType.BINARY)) {

      // create new File
      createNewJarFile();
    }

    // copy (and patch) the original
    else {

      // get the root file
      File rootFile = ModuleExporterUtils.getRootFile(getCurrentModule(), ProjectContentType.BINARY);

      // get the manifest writer
      ManifestWriter manifestWriter = new JarFileManifestWriter(rootFile, getDestinationFile());

      //
      manifestWriter.write(getManifestContents());
    }
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
      Set<IReadableResource> resourceKeys = getTemplateProvider().getAdditionalResources(getCurrentModule(),
          getCurrentModularizedSystem(), getCurrentContext());

      Set<IReadableResource> additionalResources;

      if (isIncludeSources()) {
        additionalResources = new HashSet<IReadableResource>();
        // add files from template provider
        additionalResources.addAll(resourceKeys);
        
        // add sources
        Set<IResource> sources = getCurrentModule().getResources(ProjectContentType.SOURCE);
        additionalResources.addAll(wrapSourceResources(sources));
      } else {
        
        // add only resources from template provider
        additionalResources = resourceKeys;
      }

      // export the jar archive
      JarFileUtils.createJarArchive(getCurrentModule().getResources(ProjectContentType.BINARY),
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
  private Collection<? extends IReadableResource> wrapSourceResources(Set<IResource> sources) {
    
    Set<IReadableResource> movedSources = new HashSet<IReadableResource>();
    
    // wrap sources in new IReadableResource that has it's path pointing to OSGI-OPT/src
    for (final IReadableResource source : sources) {
      final IReadableResource movedSource = new IReadableResource() {
        
        @Override
        public boolean isValidJavaPackage() {
          return source.isValidJavaPackage();
        }
        
        @Override
        public String getPath() {
          return "OSGI-OPT/src/" + source.getPath();
        }
        
        @Override
        public String getPackageName() {
          return source.getPackageName();
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
  protected OutputStream createOutputStream(IModularizedSystem modularizedSystem, IResourceModule module,
      IModuleExporterContext context) throws Exception {

    File targetFile = getDestinationFile();

    // return a new file output stream
    return new FileOutputStream(targetFile);
  }

  /**
   * <p>
   * </p>
   * 
   * @param modularizedSystem
   * @param module
   * @param context
   * @return
   */
  protected File getDestinationFile() {

    // create the target file
    File targetFile = new File(getCurrentContext().getDestinationDirectory(), computeJarFileName(getCurrentModule()));

    // create the parent directories
    if (!targetFile.getParentFile().exists()) {
      targetFile.getParentFile().mkdirs();
    }
    return targetFile;
  }

  /**
   * <p>
   * </p>
   * 
   * @param module
   * @return
   */
  protected String computeJarFileName(IResourceModule module) {

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

}

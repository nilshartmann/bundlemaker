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
package org.bundlemaker.core.osgi.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.osgi.manifest.IManifestConstants;
import org.bundlemaker.core.resource.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.virgo.bundlor.util.MatchUtils;
import org.eclipse.virgo.bundlor.util.SimpleManifestContents;
import org.eclipse.virgo.bundlor.util.SimpleParserLogger;
import org.eclipse.virgo.util.osgi.manifest.BundleManifest;
import org.eclipse.virgo.util.osgi.manifest.BundleManifestFactory;
import org.eclipse.virgo.util.osgi.manifest.parse.HeaderDeclaration;
import org.eclipse.virgo.util.osgi.manifest.parse.HeaderParserFactory;
import org.eclipse.virgo.util.parser.manifest.ManifestContents;
import org.eclipse.virgo.util.parser.manifest.RecoveringManifestParser;
import org.osgi.framework.Constants;
import org.osgi.framework.Version;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ManifestUtils {

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public static List<String> getHeaders(BundleManifest bundleManifest) {

    // create the result list
    List<String> result = new LinkedList<String>();

    // get the enumeration
    Enumeration<String> enumeration = bundleManifest.toDictionary().keys();

    // fill the result list
    while (enumeration.hasMoreElements()) {
      result.add((String) enumeration.nextElement());
    }

    // return the result
    return result;
  }

  public static boolean isHostForResourceModule(IModule exportingHostModule, IModule resourceModule) {
    return (ManifestUtils.isFragment(resourceModule) && ManifestUtils.getFragmentHost(resourceModule).equals(
        exportingHostModule));
  }
  
  /**
   * <p>
   * </p>
   * 
   * @param module
   * @return
   */
  public static boolean isFragment(IModule module) {

    //
    return !module.equals(getFragmentHost(module));
  }

  /**
   * <p>
   * </p>
   * 
   * @param module
   * @return
   */
  public static IModule getFragmentHost(IModule module) {

    //
    IModule hostModule = (IModule) module.getUserAttributes().get(IManifestConstants.OSGI_FRAGMENT_HOST);

    //
    return hostModule != null ? hostModule : module;
  }

  public static boolean isValidOSGiVersion(String version) {
    try {
      new Version(version);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param existingManifest
   * @return
   */
  public static boolean isBundleManifest(ManifestContents existingManifest) {

    //
    return existingManifest.getMainAttributes().containsKey(Constants.BUNDLE_SYMBOLICNAME);
  }

  /**
   * <p>
   * </p>
   * 
   * @param template
   * @return
   */
  public static List<HeaderDeclaration> parseManifestValue(String template) {

    if (template != null && !template.isEmpty()) {
      return HeaderParserFactory.newHeaderParser(new SimpleParserLogger()).parseHeader(template);
    } else {
      return new ArrayList<HeaderDeclaration>(0);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param declarations
   * @param packageName
   * @return
   */
  public static HeaderDeclaration findMostSpecificDeclaration(List<HeaderDeclaration> declarations, String packageName) {

    HeaderDeclaration match = null;
    int matchSpecificity = -1;

    for (HeaderDeclaration headerDeclaration : declarations) {
      for (String stem : headerDeclaration.getNames()) {
        int m = MatchUtils.rankedMatch(packageName, stem);
        if (m > matchSpecificity) {
          match = headerDeclaration;
          matchSpecificity = m;
        }
      }
    }
    return match;
  }

  public static ManifestContents readManifestContents(IResource manifestResource) throws IOException {

    ManifestContents originalManifestContents;

    if (manifestResource != null) {

      RecoveringManifestParser parser = new RecoveringManifestParser();
      originalManifestContents = parser.parse(new String(manifestResource.getContent()));

    } else {
      originalManifestContents = new SimpleManifestContents();
    }
    return originalManifestContents;
  }

  /**
   * <p>
   * </p>
   * 
   * @param file
   * @return
   */
  public static ManifestContents readManifestContents(File file) {

    //
    Assert.isNotNull(file);

    if (!file.exists()) {
      return null;
    }

    // read the bundleManifest
    try {

      //
      ManifestContents result = new RecoveringManifestParser().parse(new FileReader(file));

      //
      return result;

    } catch (Exception e) {
      System.out.println("Exception while reading " + file.getAbsolutePath());
      e.printStackTrace();
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param jarFile
   * @return
   * @throws IOException
   */
  public static ManifestContents readManifestContents(JarFile jarFile) throws IOException {

    ZipEntry zipEntry = jarFile.getEntry("META-INF/MANIFEST.MF");

    if (zipEntry != null) {

      InputStream inputStream = jarFile.getInputStream(zipEntry);
      RecoveringManifestParser recoveringManifestParser = new RecoveringManifestParser();
      recoveringManifestParser.parse(new InputStreamReader(inputStream));
      return recoveringManifestParser.getManifestContents();

    } else {
      return toManifestContents(BundleManifestFactory.createBundleManifest());
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param bundleManifest
   * @return
   */
  public static ManifestContents toManifestContents(BundleManifest bundleManifest) {

    // COPY!!

    Dictionary<String, String> headers = bundleManifest.toDictionary();
    ManifestContents manifest = new SimpleManifestContents(headers.get("Manifest-Version"));
    Map<String, String> attributes = manifest.getMainAttributes();

    Enumeration<String> headerNames = headers.keys();
    while (headerNames.hasMoreElements()) {
      String headerName = headerNames.nextElement();
      attributes.put(headerName, headers.get(headerName));
    }

    //
    return manifest;
  }

  /**
   * <p>
   * </p>
   * 
   * @param manifestContents
   * @return
   */
  public static Manifest toManifest(ManifestContents manifestContents) {

    Manifest manifest = new Manifest();

    for (Entry<String, String> entry : manifestContents.getMainAttributes().entrySet()) {

      //
      manifest.getMainAttributes().putValue(entry.getKey(), entry.getValue());
    }

    for (String sectionName : manifestContents.getSectionNames()) {

      Attributes attributes = new Attributes();

      for (Entry<String, String> entry : manifestContents.getAttributesForSection(sectionName).entrySet()) {

        //
        attributes.putValue(entry.getKey(), entry.getValue());
      }

      manifest.getEntries().put(sectionName, attributes);
    }

    //
    return manifest;
  }

  /**
   * <p>
   * </p>
   * 
   * @param base
   * @param add
   */
  public static void mergeManifests(ManifestContents base, ManifestContents add) {

    // COPY!!

    base.getMainAttributes().putAll(add.getMainAttributes());
    for (String sectionName : add.getSectionNames()) {
      base.getAttributesForSection(sectionName).putAll(add.getAttributesForSection(sectionName));
    }
  }

  @SuppressWarnings("rawtypes")
  public static Properties convertManifest(Manifest manifest) {
    Attributes attributes = manifest.getMainAttributes();
    Iterator iter = attributes.keySet().iterator();
    Properties result = new Properties();
    while (iter.hasNext()) {
      Attributes.Name key = (Attributes.Name) iter.next();
      result.put(key.toString(), attributes.get(key));
    }
    return result;
  }

}

/*
 * Copyright 2008-2009 SpringSource
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.springsource.bundlor.support.contributors;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.springsource.bundlor.support.ArtifactAnalyzer;
import com.springsource.bundlor.support.partialmanifest.PartialManifest;

/**
 * A helper class for analyzers that read XML Documents
 * <p />
 * 
 * <strong>Concurrent Semantics</strong><br />
 * 
 * Threadsafe
 * 
 * @author Ben Hale
 */
abstract class AbstractXmlDocumentArtefactAnalyser implements ArtifactAnalyzer {

    private final DocumentBuilder builder;

    AbstractXmlDocumentArtefactAnalyser(boolean namespaceAware) {
        this(namespaceAware, null);
    }
    
    AbstractXmlDocumentArtefactAnalyser(boolean namespaceAware, EntityResolver entityResolver) {
        try {
            DocumentBuilderFactory xmlFact = DocumentBuilderFactory.newInstance();
            xmlFact.setNamespaceAware(namespaceAware);
            
            DocumentBuilder builder = xmlFact.newDocumentBuilder();
            builder.setEntityResolver(entityResolver);
            
            this.builder = builder;
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public final void analyse(InputStream artefact, String artefactName, PartialManifest partialManifest) throws Exception {
        Document doc = parseDocument(artefact);
        analyse(doc, artefactName, partialManifest);
    }

    protected abstract void analyse(Document doc, String artefactName, PartialManifest partialManifest) throws Exception;

    private Document parseDocument(InputStream inputStream) {
        try {
            return builder.parse(new InputSource(inputStream));
        } catch (SAXException e) {
            return builder.newDocument();
        } catch (IOException e) {
            return builder.newDocument();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // nothing to do
                }
            }
        }
    }
}

/*
 * Copyright 2009 SpringSource
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

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.springsource.bundlor.support.partialmanifest.PartialManifest;
import com.springsource.bundlor.util.ClassNameUtils;

/**
 * An analyzer for a Log4J<code>log4j.xml</code> file. Analyzes the list of class names that are found in the
 * <code>appender</code> and <code>layout</code> tags.
 * <p />
 * 
 * <strong>Concurrent Semantics</strong><br />
 * 
 * Threadsafe.
 * 
 * @author Ben Hale
 */
public class Log4JXmlArtifactAnalyzer extends AbstractXmlDocumentArtefactAnalyser {
    
    private static final String LOG4J_DTD = "com/springsource/bundlor/support/contributors/log4j.dtd";

    private final XPathExpression expression;

    {
        try {
            XPathFactory newInstance = XPathFactory.newInstance();
            XPath xpath = newInstance.newXPath();
            this.expression = xpath.compile("//appender/@class | //layout/@class");
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }

    public Log4JXmlArtifactAnalyzer() {
        super(false, new Log4JEntityResolver());
    }

    @Override
    protected void analyse(Document doc, String artefactName, PartialManifest partialManifest) throws Exception {
        NodeList nodes = (NodeList) expression.evaluate(doc, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            Attr attribute = (Attr) nodes.item(i);
            String candidateType = attribute.getValue();
            if (ClassNameUtils.isValidFqn(candidateType)) {
                partialManifest.recordReferencedType(candidateType);
            }
        }
    }

    public boolean canAnalyse(String artefactName) {
        return "log4j.xml".equals(artefactName);
    }

    private static class Log4JEntityResolver implements EntityResolver {

        public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
            if (systemId.endsWith("log4j.dtd")) {
                ClassLoader classLoader = this.getClass().getClassLoader();
                return new InputSource(classLoader.getResourceAsStream(LOG4J_DTD));
            }
            return null;
        }

    }
}

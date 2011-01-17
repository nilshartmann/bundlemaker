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

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.springsource.bundlor.support.partialmanifest.PartialManifest;
import com.springsource.bundlor.util.ClassNameUtils;

/**
 * An analyzer for the <code>web.xml</code> file in a web application. Analyzers the list of package names that are
 * found in various places in the file.
 * <p />
 * 
 * <strong>Concurrent Semantics</strong><br />
 * 
 * Threadsafe
 * 
 * @author Ben Hale
 */
public final class WebApplicationArtifactAnalyzer extends AbstractXmlDocumentArtefactAnalyser {

    private static final String WEB_XML_LOCATION = "WEB-INF/web.xml";

    private static final String XPATH_EXPRESSION = //
    "//context-param/param-value | " //
        + "//filter/filter-class | " //
        + "//filter/init-param/param-value | " //
        + "//listener/listener-class | " //
        + "//servlet/servlet-class | " //
        + "//servlet/init-param/param-value | " //
        + "//error-page/exception-type | " //
        + "//env-entry/env-entry-type | " //
        + "//ejb-ref/home | " //
        + "//ejb-ref/remote | " //
        + "//ejb-local-ref/local-home | " //
        + "//ejb-local-ref/local | " //
        + "//service-ref/service-interface | " //
        + "//resource-ref/res-type | " //
        + "//resource-env-ref/resource-env-ref-type | " //
        + "//message-destination-ref/message-destination-type";

    private final XPathExpression expression;

    {
        try {
            XPathFactory newInstance = XPathFactory.newInstance();
            XPath xpath = newInstance.newXPath();
            this.expression = xpath.compile(XPATH_EXPRESSION);
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }

    public WebApplicationArtifactAnalyzer() {
        super(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void analyse(Document doc, String artefactName, PartialManifest partialManifest) throws Exception {
        NodeList nodes = (NodeList) expression.evaluate(doc, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);
            String candidateType = element.getTextContent().trim();
            if (ClassNameUtils.isValidFqn(candidateType)) {
                partialManifest.recordReferencedType(candidateType);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean canAnalyse(String artefactName) {
        return artefactName.endsWith(WEB_XML_LOCATION);
    }

}

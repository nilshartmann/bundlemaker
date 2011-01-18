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

import java.util.Iterator;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.springframework.util.StringUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.springsource.bundlor.support.partialmanifest.PartialManifest;
import com.springsource.bundlor.util.ClassNameUtils;

/**
 * An abstract class that encapsulates logic that is common to all XML-based configuration schemes.
 * 
 * <strong>Concurrent Semantics</strong><br />
 * 
 * Not threadsafe.
 * 
 * @author Ben Hale
 */
abstract class AbstractXmlConfigurationArtifactAnalyzer extends AbstractXmlDocumentArtefactAnalyser {

    public AbstractXmlConfigurationArtifactAnalyzer() {
        super(true);
    }

    protected abstract String getClassAttributesXPathExpression();

    protected abstract String getClassValueXPathExpression();

    protected abstract String getPackageXPathExpression();

    protected abstract Map<String, String> getNamespaceMapping();

    protected final void analyse(Document doc, String artefactName, PartialManifest partialManifest) throws Exception {
        analyzeClassAttributes(doc, partialManifest);
        analyzeClassValues(doc, partialManifest);
        analyzePackageAttributes(doc, partialManifest);
    }

    private void analyzeClassAttributes(Document doc, PartialManifest partialManifest) throws XPathExpressionException {
        String expressionString = getClassAttributesXPathExpression();
        if (expressionString != null) {
            NodeList nodes = (NodeList) getXPathExpression(expressionString).evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodes.getLength(); i++) {
                Attr classAttribute = (Attr) nodes.item(i);
                if (classAttribute.getValue().contains(",")) {
                    for (String clazz : StringUtils.tokenizeToStringArray(classAttribute.getValue(), ", ")) {
                        String candidateType = clazz.trim();
                        if (ClassNameUtils.isValidFqn(candidateType)) {
                            partialManifest.recordReferencedType(candidateType);
                        }
                    }
                } else {
                    String candidateType = classAttribute.getValue().trim();
                    if (ClassNameUtils.isValidFqn(candidateType)) {
                        partialManifest.recordReferencedType(candidateType);
                    }
                }
            }
        }
    }

    private void analyzeClassValues(Document doc, PartialManifest partialManifest) throws XPathExpressionException {
        String expressionString = getClassValueXPathExpression();
        if (expressionString != null) {
            NodeList nodes = (NodeList) getXPathExpression(expressionString).evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodes.getLength(); i++) {
                Node classNode = (Node) nodes.item(i);
                if (classNode.getTextContent().contains(",")) {
                    for (String clazz : StringUtils.tokenizeToStringArray(classNode.getTextContent(), ", ")) {
                        String candidateType = clazz.trim();
                        if (ClassNameUtils.isValidFqn(candidateType)) {
                            partialManifest.recordReferencedType(candidateType);
                        }
                    }
                } else {
                    String candidateType = classNode.getTextContent().trim();
                    if (ClassNameUtils.isValidFqn(candidateType)) {
                        partialManifest.recordReferencedType(candidateType);
                    }
                }
            }
        }
    }

    private void analyzePackageAttributes(Document doc, PartialManifest partialManifest) throws XPathExpressionException {
        String expressionString = getPackageXPathExpression();
        if (expressionString != null) {
            NodeList nodes = (NodeList) getXPathExpression(expressionString).evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodes.getLength(); i++) {
                Attr packageAttribute = (Attr) nodes.item(i);
                if (packageAttribute.getValue().contains(",")) {
                    for (String packageName : StringUtils.tokenizeToStringArray(packageAttribute.getValue(), ", ")) {
                        String candidatePackage = packageName.trim();
                        if (ClassNameUtils.isValidFqn(candidatePackage)) {
                            partialManifest.recordReferencedPackage(candidatePackage);
                        }
                    }
                } else {
                    String candidatePackage = packageAttribute.getValue().trim();
                    if (ClassNameUtils.isValidFqn(candidatePackage)) {
                        partialManifest.recordReferencedPackage(candidatePackage);
                    }
                }
            }
        }
    }

    private final XPathExpression getXPathExpression(String expressionString) throws XPathExpressionException {
        XPathFactory newInstance = XPathFactory.newInstance();
        XPath xpath = newInstance.newXPath();
        xpath.setNamespaceContext(getNamespaceContext());
        return xpath.compile(expressionString);
    }

    private final XmlConfigurationNamespaceContext getNamespaceContext() {
        return new XmlConfigurationNamespaceContext(getNamespaceMapping());
    }

    private static final class XmlConfigurationNamespaceContext implements NamespaceContext {

        private final Map<String, String> namespaceMapping;

        public XmlConfigurationNamespaceContext(Map<String, String> namespaceMapping) {
            this.namespaceMapping = namespaceMapping;
        }

        public String getNamespaceURI(String prefix) {
            if (namespaceMapping.containsKey(prefix)) {
                return namespaceMapping.get(prefix);
            }
            return XMLConstants.NULL_NS_URI;
        }

        public String getPrefix(String namespace) {
            if (namespaceMapping.containsValue(namespace)) {
                for (Map.Entry<String, String> entry : namespaceMapping.entrySet()) {
                    if (namespace.equals(entry.getValue())) {
                        return entry.getKey();
                    }
                }
            }
            return null;
        }

        @SuppressWarnings("unchecked")
        public Iterator getPrefixes(String namespace) {
            return null;
        }
    }
}

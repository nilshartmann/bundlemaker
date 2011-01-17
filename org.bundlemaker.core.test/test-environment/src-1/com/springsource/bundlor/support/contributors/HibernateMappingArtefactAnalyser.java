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

import java.util.Set;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.springsource.bundlor.support.partialmanifest.PartialManifest;
import com.springsource.bundlor.util.ClassNameUtils;
import com.springsource.util.math.Sets;

/**
 * An analyzer for a Hibernate Mapping file. Analyzes the list of package names that are found in the
 * <ul>
 * <li><code>class</code></li>
 * <li><code>id</code></li>
 * <li><code>generator</code></li>
 * <li><code>composite-id</code></li>
 * <li><code>discriminator</code></li>
 * <li><code>version</code></li>
 * <li><code>property</code></li>
 * <li><code>many-to-one</code></li>
 * <li><code>one-to-one</code></li>
 * <li><code>one-to-many</code></li>
 * <li><code>many-to-many</code></li>
 * <li><code>component</code></li>
 * <li><code>dynamic-component</code></li>
 * <li><code>subclass</code></li>
 * <li><code>joined-subclass</code></li>
 * <li><code>union-subclass</code></li>
 * <li><code>import</code></li>
 * </ul>
 * elements.
 * <p />
 * 
 * <strong>Concurrent Semantics</strong><br />
 * 
 * Threadsafe.
 * 
 * @author Ben Hale
 */
public final class HibernateMappingArtefactAnalyser extends AbstractXmlDocumentArtefactAnalyser {

    private static final Set<String> BASIC_HIBERNATE_TYPES = Sets.asSet(//
        "integer",//
        "long",//
        "short",//
        "float",//
        "double",//
        "character",//
        "byte",//
        "boolean",//
        "yes_no",//
        "true_false",//
        "string",//
        "date",//
        "time",//
        "timestamp",//
        "calendar",//
        "calendar_date",//        
        "big_decimal",//
        "big_integer",//
        "locale",//
        "timezone",//
        "currency",//
        "class",//
        "binary",//
        "text",//
        "serializable",//
        "clob",//
        "blob",//
        "imm_date",//
        "imm_time",//
        "imm_timestamp",//
        "imm_calendar",//
        "imm_calendar_date",//
        "imm_serializable",//
        "imm_binary");

    private static final Set<String> GENERATOR_TYPES = Sets.asSet(//
        "increment",//
        "identity",//
        "sequence",//
        "hilo",//
        "seqhilo",//
        "uuid",//
        "guid",//
        "native",//
        "assigned",//
        "select",//
        "foreign",//
        "sequence-identity");

    private final XPathExpression packageExpression;

    private final XPathExpression tagExpression;

    {
        try {
            XPathFactory newInstance = XPathFactory.newInstance();
            XPath xpath = newInstance.newXPath();
            this.packageExpression = xpath.compile("//hibernate-mapping/@package");
            this.tagExpression = xpath.compile(//
            "//class/@name | " + //
                "//id/@type | " + //
                "//generator/@class | " + //
                "//composite-id/@class | " + //
                "//discriminator/@type | " + //
                "//version/@type | " + //
                "//property/@type | " + //
                "//many-to-one/@class | " + //
                "//one-to-one/@class | " + //
                "//one-to-many/@class | " + //
                "//many-to-many/@class | " + //
                "//component/@class | " + //
                "//dynamic-component/@class | " + //
                "//subclass/@name | " + //
                "//joined-subclass/@name | " + //
                "//union-subclass/@name | " + //
                "//import/@class");
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }

    public HibernateMappingArtefactAnalyser() {
        super(false);
    }

    protected void analyse(Document doc, String artefactName, PartialManifest partialManifest) throws Exception {
        String packagePrefix = getPackagePrefix(doc);

        NodeList nodes = (NodeList) tagExpression.evaluate(doc, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            Attr attribute = (Attr) nodes.item(i);
            String value = attribute.getValue();

            if (!BASIC_HIBERNATE_TYPES.contains(value) && !GENERATOR_TYPES.contains(value)) {
                String candidateType;
                if (value.contains(".")) {
                    candidateType = value.trim();
                } else {
                    candidateType = packagePrefix + "." + value.trim();
                }
                if (ClassNameUtils.isValidFqn(candidateType)) {
                    partialManifest.recordReferencedType(candidateType);
                }
            }
        }
    }

    public boolean canAnalyse(String artefactName) {
        return artefactName.endsWith(".hbm");
    }

    private String getPackagePrefix(Document doc) {
        try {
            NodeList nodes = (NodeList) packageExpression.evaluate(doc, XPathConstants.NODESET);
            if (nodes.getLength() < 1) {
                return "";
            } else if (nodes.getLength() > 1) {
                throw new RuntimeException("Found more than one package attribute in file");
            }

            return ((Attr) nodes.item(0)).getValue();
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }

}

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

import java.util.HashMap;
import java.util.Map;

/**
 * An analyzer for an Application Context located in a JAR file. Analyzes the list of package names that are found in
 * bean declaration <code>class</code> attributes.
 * <p />
 * 
 * <strong>Concurrent Semantics</strong><br />
 * 
 * Not threadsafe.
 * 
 * @author Ben Hale
 */
public final class SpringApplicationContextArtefactAnalyser extends AbstractXmlConfigurationArtifactAnalyzer {

    private static final String CLASS_ATTRIBUTES = //
    "//beans:bean/@class | " + //
        "//aop:declare-parents/@implement-interface | " + //
        "//aop:declare-parents/@default-impl | " + //
        "//context:load-time-weaver/@weaver-class | " + //
        "//context:component-scan/@name-generator | " + //
        "//context:component-scan/@scope-resolver | " + //
        "//jee:jndi-lookup/@expected-type | " + //
        "//jee:jndi-lookup/@proxy-interface | " + //
        "//jee:remote-slsb/@home-interface | " + //
        "//jee:remote-slsb/@business-interface | " + //
        "//jee:local-slsb/@business-interface | " + //
        "//jms:listener-container/@container-class | " + //
        "//lang:jruby/@script-interfaces | " + //
        "//lang:bsh/@script-interfaces | " + //
        "//oxm:class-to-be-bound/@name | " + //
        "//oxm:jibx-marshaller/@target-class | " + //
        "//osgi:reference/@interface | " + //
        "//osgi:service/@interface | " + //
        "//util:list/@list-class | " + //
        "//util:map/@map-class | " + //
        "//util:set/@set-class | " + //
        "//webflow:flow-builder/@class | " + //
        "//webflow:attribute/@type";

    private static final String CLASS_VALUES = //
    "//osgi:service/osgi:interfaces/beans:value | " + //
        "//osgi:reference/osgi:interfaces/beans:value";

    private static final String PACKAGE_ATTRIBUTES = // 
    "//context:component-scan/@base-package";

    private static final Map<String, String> NAMESPACE_MAPPING;

    static {
        NAMESPACE_MAPPING = new HashMap<String, String>();
        NAMESPACE_MAPPING.put("beans", "http://www.springframework.org/schema/beans");
        NAMESPACE_MAPPING.put("aop", "http://www.springframework.org/schema/aop");
        NAMESPACE_MAPPING.put("context", "http://www.springframework.org/schema/context");
        NAMESPACE_MAPPING.put("jee", "http://www.springframework.org/schema/jee");
        NAMESPACE_MAPPING.put("jms", "http://www.springframework.org/schema/jms");
        NAMESPACE_MAPPING.put("lang", "http://www.springframework.org/schema/lang");
        NAMESPACE_MAPPING.put("jms", "http://www.springframework.org/schema/jms");
        NAMESPACE_MAPPING.put("util", "http://www.springframework.org/schema/util");
        NAMESPACE_MAPPING.put("oxm", "http://www.springframework.org/schema/oxm");

        NAMESPACE_MAPPING.put("osgi", "http://www.springframework.org/schema/osgi");

        NAMESPACE_MAPPING.put("webflow", "http://www.springframework.org/schema/webflow-config");
    }

    @Override
    protected String getClassAttributesXPathExpression() {
        return CLASS_ATTRIBUTES;
    }

    @Override
    protected String getClassValueXPathExpression() {
        return CLASS_VALUES;
    }

    @Override
    protected String getPackageXPathExpression() {
        return PACKAGE_ATTRIBUTES;
    }

    @Override
    protected Map<String, String> getNamespaceMapping() {
        return NAMESPACE_MAPPING;
    }

    public boolean canAnalyse(String artifactName) {
        return artifactName.endsWith(".xml");
    }

}
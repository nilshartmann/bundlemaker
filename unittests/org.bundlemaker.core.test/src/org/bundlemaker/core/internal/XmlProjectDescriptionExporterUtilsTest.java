package org.bundlemaker.core.internal;

import org.bundlemaker.core.model.internal.projectdescription.xml.XmlFileBasedContentType;
import org.bundlemaker.core.model.internal.projectdescription.xml.XmlProjectDescriptionType;
import org.junit.Test;

public class XmlProjectDescriptionExporterUtilsTest {

	@Test
	public void test_marshal() throws Exception {
		XmlProjectDescriptionType type = new XmlProjectDescriptionType();
		type.setCurrentId(1);
		type.setJre("jdk-16");
		type.setCurrentId(1);
		XmlFileBasedContentType xmlFileBasedContent= new XmlFileBasedContentType();
		xmlFileBasedContent.setAnalyze(true);
		xmlFileBasedContent.setId("content-1");
		xmlFileBasedContent.setName("name");
		xmlFileBasedContent.getBinaryPathNames().add("path-1");
		type.getFileBasedContent().add(xmlFileBasedContent);
		
		String marshal = XmlProjectDescriptionExporterUtils.marshal(type);
		System.out.println("marshal: " + marshal);
	}
	
}

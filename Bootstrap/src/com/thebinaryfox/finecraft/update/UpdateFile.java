package com.thebinaryfox.finecraft.update;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class UpdateFile {
	
	// Private
	private String name;
	private String description;
	private String[] authors;
	private String uniqueid;
	private String main;
	private RetentionType retention;
	private Update[] updates;
	
	// Public
	public void parse(InputStream input) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document xml = builder.parse(input);
		xml.getDocumentElement().normalize();
	}
	
	public void parse(byte[] input) {
		parse(new ByteArrayInputStream(input));
	}
	
}

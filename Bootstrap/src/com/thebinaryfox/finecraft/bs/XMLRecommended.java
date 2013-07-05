package com.thebinaryfox.finecraft.bs;

import java.io.IOException;
import java.net.URL;

import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XMLRecommended {

	// Methods: static public
	static public XMLRecommended fromURL(URL url) throws IOException {

	}

	// Methods: public
	private void parse(String data) {
		DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
		DocumentBuilder bdr = fac.newDocumentBuilder();
		Document doc = bdr.parse(data);
		doc.getDocumentElement().normalize();
	}

}

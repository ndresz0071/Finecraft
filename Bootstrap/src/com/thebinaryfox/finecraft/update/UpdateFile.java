package com.thebinaryfox.finecraft.update;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class UpdateFile {

	// Private
	private String name;
	private String description;
	private String[] authors;
	private String uniqueid;
	private RetentionType retention;
	private Update[] updates;

	// Public
	public void parse(byte[] input) throws IOException {
		parse(new ByteArrayInputStream(input));
	}

	public void parse(InputStream input) throws IOException {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document xml = builder.parse(input);
			xml.getDocumentElement().normalize();

			// Get branch info
			NodeList branchl = xml.getDocumentElement().getElementsByTagName("Branch");
			if (branchl.getLength() < 1)
				throw new IOException("; No branch information is specified.");
			if (branchl.getLength() > 1)
				throw new IOException("; More than one branch tag is contained.");

			Element branch = (Element) branchl.item(0);

			// Get branch info - name
			NodeList namel = branch.getElementsByTagName("Name");
			if (namel.getLength() < 1)
				throw new IOException("; No branch name is specified.");
			if (namel.getLength() > 1)
				throw new IOException("; More than one name tag is contained.");

			name = namel.item(0).getNodeValue().trim();
			namel = null;

			// Get branch info - authors
			NodeList authorl = branch.getElementsByTagName("Author");
			ArrayList<String> authora = new ArrayList<String>();
			for (int i = 0; i < authorl.getLength(); i++) {
				Element author = (Element) authorl.item(i);
				if (!author.hasAttribute("name"))
					throw new IOException("; An author tag does not have a name attribute.");

				authora.add(author.getAttribute("name").trim());
			}

			if (authora.size() == 0) {
				authors = new String[] { "Anonymous" };
			} else {
				authors = authora.toArray(new String[0]);
				authora.clear();
			}

			authora = null;
			authorl = null;

			// Get branch info - unique id
			NodeList ubidl = branch.getElementsByTagName("UBID");
			if (ubidl.getLength() < 1)
				throw new IOException("; No unique branch ID (UBID) is specified.");
			if (ubidl.getLength() > 1)
				throw new IOException("; More than one unique branch ID tag is contained.");

			uniqueid = ubidl.item(0).getNodeValue().trim();
			ubidl = null;

			// Get branch info - retention type (policy)
			NodeList rtpl = branch.getElementsByTagName("RTPL");
			if (rtpl.getLength() < 1) {
				retention = RetentionType.STORE;
			} else {
				if (rtpl.getLength() > 1)
					throw new IOException("; More than one retention policy tag is contained.");

				retention = RetentionType.get(rtpl.item(0).getNodeValue().trim());
			}

			rtpl = null;
			branch = null;
			branchl = null;
			
			// Get updates
			// TODO
		} catch (ParserConfigurationException e) {
			throw new IOException(e);
		} catch (SAXException e) {
			throw new IOException(e);
		}
	}

}

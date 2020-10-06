package org.lxml;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.lxml.node.Node;
import org.lxml.node.NodeList;
import org.lxml.parser.Parser;

public class LXMLDocument {
	
	private Parser parser;
	private Node   root;
	
	public LXMLDocument(String xmlFile) {
		if (xmlFile == null)
			throw new NullPointerException();
		
		if (xmlFile.isEmpty())
			throw new IllegalArgumentException();
		
		parser = new Parser(xmlFile);
	}
	
	public void setIgnoreWhiteSpace(boolean ignore) {
		parser.setIgnoreWhiteSpace(ignore);
	}
	
	public LXMLDocument parse() {
		root = parser.parse();
		return this;
	}
	
	public void save(String out) {
		if (out == null)
			throw new NullPointerException();
		
		if (out.isEmpty())
			throw new IllegalArgumentException();
		
		root.save(out);
	}
	
	public Node getRoot() {
		return root;
	}
	
	
	public static void main(String[] args) throws IOException {
		LXMLDocument doc = new LXMLDocument("F:\\JavaTest\\lxml\\python.xml");
		Node root = doc.parse().getRoot();
		NodeList list = root.getNodeList();
		Iterator<Node> iter = list.iterator();
		FileWriter out = new FileWriter("F:\\JavaTest\\lxml\\python.dat");
		while (iter.hasNext()) {
			out.append(iter.next().getAttrMap().getAttrValue("name") + "\n");
		}
		out.close();
		//System.out.println(root.getConent());
	}
}

package org.lxml.node;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class Node {
	
	private String tagName;
	
	private NodeList nodeList;
	private AttrMap  attrMap;
	
	private String content;
	
	public Node() {
		this.tagName  = "node";
		this.content  = "";
		this.nodeList = new NodeList();
		this.attrMap  = new AttrMap();
	}
	
	public Node(String tagName) {
		if (tagName == null)
			throw new NullPointerException();
		
		if (tagName.isEmpty())
			throw new IllegalArgumentException("参数为空串.");
		
		this.tagName  = tagName;
		this.content  = "";
		this.nodeList = new NodeList();
		this.attrMap  = new AttrMap();
	}
	
	public void setContent(String content) {
		if (content == null)
			throw new NullPointerException();
		
		this.content = content;		
	}
	
	public String getTagName() {
		return tagName;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setTagName(String tagName) {
		if (tagName == null)
			throw new NullPointerException();
		
		if (tagName.isEmpty())
			throw new IllegalArgumentException("参数为空串.");
		
		this.tagName  = tagName;
	}
	
	public NodeList getNodeList() {
		return nodeList;
	}
	
	public AttrMap getAttrMap() {
		return attrMap;
	}
	
	public void save(String out) {
		try {
			FileWriter fw = new FileWriter(out);
			level = 0; save0(this, fw); fw.close();
		} catch (Exception e) {
			throw new RuntimeException("文件: "+ out +" 保存失败.");
		}
	}
	
	private int level = 0;
	
	private void save0(Node parent, FileWriter out) throws IOException {
		for (int i=0; i<level; i++) {
			out.append("\t");
		}
		out.append("<");
		out.append(parent.getTagName());
		out.append(" ");
		Set<String> attrNames = parent.getAttrMap().getAttrNames();
		for (String name : attrNames) {
			out.append(name);
			out.append("=\"");
			out.append(parent.getAttrMap().getAttrValue(name));
			out.append("\" ");
		}
		if (parent.getNodeList().size()==0 && parent.getContent().isEmpty()) {
			out.append("/>\n");
		} else if (parent.getNodeList().size() != 0) {
			out.append(">\n");
			level++;
			var iter = parent.getNodeList().iterator();
			while (iter.hasNext()) {
				save0(iter.next(), out);
			}
			level--;
			for (int i=0; i<level; i++) {
				out.append("\t");
			}
			out.append("</");
			out.append(parent.getTagName());
			out.append(">\n");
		} else if (!parent.getContent().isEmpty()) {
			out.append(">\n");
			for (int i=0; i<level; i++) {
				out.append("\t");
			}
			out.append(parent.getContent());
			out.append("\n");
			for (int i=0; i<level; i++) {
				out.append("\t");
			}
			out.append("</");
			out.append(parent.getTagName());
			out.append(">\n");
		}
	}
	
	/*
	private void save0(int level, Node parent, FileWriter out) throws IOException {
		StringBuffer indent = new StringBuffer();
		for (int i=0; i<level; i++) {
			indent.append('\t');
		}
		
		out.write(indent + "<");
		out.write(parent.getTagName()+" ");
		
		AttrMap attrs = parent.getAttrMap();
		Set<String> attrNames = attrs.getAttrNames();
		
		for (String name : attrNames) {
			String value = attrs.getAttrValue(name);
			out.write(name+"="+"\""+value+"\" ");
		}
		
		out.write(">");
	
		out.write(parent.getConent());
	
		NodeList childs = parent.getNodeList();
		for (Node child : childs) {	
			save0(level+1, child, out);
		}
		
		indent = new StringBuffer();
		
		for (int i=0; i<level; i++) {
			indent.append('\t');
		}
		out.write(indent + "</"+parent.getTagName()+">\n");
	}*/
	
	/*
	public static void main(String[] args) {
		Node root = new Node("root");
		root.getNodeList().addNode(new Content("这是一个字符串."));
		root.getNodeList().addNodeByTag("open");
		root.getNodeList().getNodeByTag("open").getNodeList().addNode(new Content("open string."));
		root.getNodeList().addNode(new Content("This is another string."));
		root.save("F:\\JavaTest\\demo.xml");
		System.out.println(root.getNodeList().getNodeByTag("open").tagName);
	}
	*/
}

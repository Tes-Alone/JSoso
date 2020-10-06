package org.lxml.node;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class NodeList implements Iterable<Node> {
	
	private List<Node> nodeList;
	
	NodeList() {
		nodeList = new ArrayList<>();
	}
	
	public Iterator<Node> iterator() {
		return nodeList.iterator();
	}
	
	public Node getNodeByTag(String tagName) {
		if (tagName == null)
			throw new NullPointerException();
		
		if (tagName.isEmpty())
			throw new IllegalArgumentException("参数为空串.");
		
		for (Node node : nodeList) {
			if (node.getTagName().equals(tagName)) {
				return node;
			}
		}
		return null;
	}
	
	public Node addNodeByTag(String tagName) {
		if (tagName == null)
			throw new NullPointerException();
		
		if (tagName.isEmpty())
			throw new IllegalArgumentException("参数为空串.");
		
		Node node = new Node(tagName);
		nodeList.add(node);
		return node;
	}
	
	public void addNode(Node node) {
		if (node == null)
			throw new NullPointerException();
		
		if (node.getTagName()==null || node.getTagName().isEmpty())
			throw new IllegalArgumentException("节点标签名为空.");

		nodeList.add(node);
	}
	
	public Node getNodeByAttr(String attrName, String attrValue) {
		if (attrName == null || attrValue == null)
			throw new NullPointerException();
		
		if (attrName.isEmpty() || attrValue.isEmpty())
			throw new IllegalArgumentException("参数为空串.");
		
		for (Node node : nodeList) {
			AttrMap attrList = node.getAttrMap();
			Set<String> names = attrList.getAttrNames();
			for (String name : names) {
				if (attrList.getAttrValue(name).equals(attrValue)) {
					return node;
				}
			}
		}
		
		return null;
	}
	
	public Node getNodeById(String id) {
		if (id == null)
			throw new NullPointerException();
		
		if (id.isEmpty())
			throw new IllegalArgumentException("参数为空.");
		
		return getNodeByAttr("id", id);
	}
	
	public int size() {
		return nodeList.size();
	}
	
	public void removeNode(Node node) {
		if (node == null)
			throw new NullPointerException();
		
		nodeList.remove(node);
	}
	
	public void removeAll() {
		nodeList.clear();
	}
	
	public Node getNodeAt(int index) {
		if (index < 0 || index >= nodeList.size())
			throw new IllegalArgumentException();
		
		return nodeList.get(index);
	}
}

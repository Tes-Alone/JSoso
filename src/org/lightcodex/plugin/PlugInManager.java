package org.lightcodex.plugin;

import java.util.ArrayList;
import java.util.List;

import org.lightcodex.Constants;
import org.llogger.LLogger;
import org.lxml.LXMLDocument;
import org.lxml.node.Node;
import org.lxml.node.NodeList;

public class PlugInManager {
	
	private Node root;
	
	private static final String pluginsTag = "plugins";
	private static final String pluginTag = "plugin";
	private static final String nameAttr = "name";
	
	public PlugInManager() {
		String filePath = Constants.Homes.configHome + "plugin.xml";
		try {
			root = new LXMLDocument(filePath).parse().getRoot();
		} catch (RuntimeException e) {
			LLogger.getInstance().logWarning("PlugIns load failed!");
			root = new Node(pluginsTag);
		}
	}
	
	public PlugIn[] listPlugIns() {
		List<PlugIn> plugins = new ArrayList<PlugIn>();
		NodeList nodeList = root.getNodeList();
		for (int i=0; i<nodeList.size(); i++) {
			String name = nodeList.getNodeAt(i).getAttrMap().getAttrValue(nameAttr);
			PlugIn p = createPlugInByClassName(name);
			if (p != null) {
				plugins.add(p);
			}
		}
		PlugIn[] result = new PlugIn[plugins.size()];
		return plugins.toArray(result);
	}
	
	public void removePlugIn(PlugIn plugIn) {
		NodeList nodeList = root.getNodeList();
		Node node = nodeList.getNodeByAttr(nameAttr, plugIn.getName());
		if (node != null) {
			nodeList.removeNode(node);
		}
	}
	
	public void addPlugIn(String name) {
		NodeList nodeList = root.getNodeList();
		Node node = nodeList.getNodeByAttr(nameAttr, name);
		if (node == null) {
			node = nodeList.addNodeByTag(pluginTag);
			node.getAttrMap().addAttr(nameAttr, name);
		}
	}
	
	public void save() {
		String filePath = Constants.Homes.configHome + "plugin.xml";
		root.save(filePath);
	}
	
	private PlugIn createPlugInByClassName(String name) {
		try {
			return (PlugIn)Class.forName(name).getConstructors()[0].newInstance(name);
		} catch (Exception e) {
			LLogger.getInstance().logWarning("Plugin '%s' not found!", name);
		}
		return null;
	}
}

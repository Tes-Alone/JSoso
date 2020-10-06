package org.lightcodex.lang;

import java.util.ArrayList;
import java.util.List;

import org.lightcodex.Constants;
import org.llogger.LLogger;
import org.lxml.LXMLDocument;
import org.lxml.node.Node;
import org.lxml.node.NodeList;

public class LangManager {
	
	private Node root;
	
	public LangManager() {
		String path = Constants.Homes.configHome + "langs.xml";
		try {
			root = new LXMLDocument(path).parse().getRoot();
		} catch (RuntimeException e) {
			LLogger.getInstance().logWarning("Language info load failed, program use the build in info.");
			root = new Node(langsTag);
		}
		setupBuildInLangs();
	}
	
	private static final String langsTag = "langs";
	private static final String langTag = "lang";
	private static final String extTag = "ext";
	private static final String nameAttr = "name";
	private static final String editorClassAttr = "editor-class";

	private static Language[] buildInLangs = {
		Language.NORMAL, Language.INI, Language.ASSEMBLER,
		Language.CPP, Language.MAKEFILE, Language.JAVA,
		Language.D,   Language.PYTHON, 	 Language.HTML,
		Language.XML, Language.JAVA_SCRIPT, Language.CSS, 		     
		Language.JSP, Language.TEX
	};
	
	
	public Language[] listBuildIns() {
		return buildInLangs;
	}
	
	public Language findLanguage(String ext) {
		var langs = listLanguages();
		for (var lang : langs) {
			for (var i : lang.getExtensions()) {
				if (i.equals(ext)) {
					return lang;
				}
			}
		}
		return null;
	}
	
	public boolean isBuildIn(Language lang) {
		for (var l : buildInLangs) {
			if (l.equals(lang)) {
				return true;
			}
		}
		return false;
	}
	
	public List<Language> listLanguages() {
		List<Language> langs = new ArrayList<>();
		NodeList nodes = root.getNodeList();
		for (int i=0; i<nodes.size(); i++) {
			Node node = nodes.getNodeAt(i);
			NodeList extList = node.getNodeList();
			String[] exts = new String[extList.size()];
			for (int j=0; j<extList.size(); j++) {
				exts[j] = extList.getNodeAt(j).getAttrMap().getAttrValue(nameAttr);
			}
			langs.add(new Language(node.getAttrMap().getAttrValue(nameAttr), 
						node.getAttrMap().getAttrValue(editorClassAttr),exts));
		}
		return langs;
	}

	private void setupBuildInLangs() {
		for (Language lang : buildInLangs) {
			addLanguage(lang);
		}
	}
	
	public void save() {
		String path = Constants.Homes.configHome + "langs.xml";
		root.save(path);
	}
	
	public void addLanguage(Language lang) {
		Node node = root.getNodeList().getNodeByAttr(nameAttr, lang.getName());
		if (node == null) {
			node = root.getNodeList().addNodeByTag(langTag);
			String name = node.getAttrMap().getAttrValue(nameAttr);
			if (name == null) {
				node.getAttrMap().addAttr(nameAttr, lang.getName());
			}
			String editorClass = node.getAttrMap().getAttrValue(editorClassAttr);
			if (editorClass == null) {
				node.getAttrMap().addAttr(editorClassAttr, lang.getEditorClassName());
			}
		}
		for (String ext : lang.getExtensions()) {
			Node tmp = node.getNodeList().getNodeByAttr(nameAttr, ext);
			if (tmp == null) {
				tmp = node.getNodeList().addNodeByTag(extTag);
				tmp.getAttrMap().addAttr(nameAttr, ext);
			}
		}
	}
	
	/*
	public void removeLanguage(String name) {
		Node node = root.getNodeList().getNodeByAttr(nameAttr, name);
		if (node != null) {
			root.getNodeList().removeNode(node);
		}
	}
	
	public void removeExtension(String name, String ext) {
		Node node = root.getNodeList().getNodeByAttr(nameAttr, name);
		Node tmp = node.getNodeList().getNodeByAttr(nameAttr, ext);
		if (tmp != null) {
			node.getNodeList().removeNode(tmp);
		}
	}*/
}

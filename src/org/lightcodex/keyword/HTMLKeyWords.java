package org.lightcodex.keyword;

import org.llogger.LLogger;
import org.lxml.node.NodeList;

public class HTMLKeyWords extends KeyWordGetter {
	
	public HTMLKeyWords() {}
	
	private static String[] keyWords1 = {
		"html", "head", "title", "meta", "body", "h1", "h2",
		"h3", "h4", "h5", "h6", "p", "br", "div", "hr", "pre",
		"a", "img", "center", "b", "i", "script", "style", "link",
		"code", "canvas", "ol", "ul", "li"
	};
		
	private static String[] keyWords2 = {
		"id", "name", "style", "lang", "charset", "href", "src",
		"rel", "type"
	};
	
	public static String[] keyWord1List;
	public static String[] keyWord2List;
	
	
	static {
		try {
			initKeyWords();
		} catch (Exception e) {
			LLogger.getInstance().
				logWarning("Key words file read failed.");
			keyWord1List = keyWords1;
			keyWord2List = keyWords2;
		}
	}
	
	private static void initKeyWords() {
		NodeList keyWords = root.getNodeList().getNodeByTag("HTML").getNodeList(); 
		keyWord1List = keyWords.getNodeByTag("keyword1").getContent().split(":");
		keyWord2List = keyWords.getNodeByTag("keyword2").getContent().split(":");
	}
}

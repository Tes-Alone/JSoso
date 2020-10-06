package org.lightcodex.keyword;

import org.llogger.LLogger;
import org.lxml.node.NodeList;

public class JavaKeyWords extends KeyWordGetter {
	
	public JavaKeyWords() {}
	
	private final static String[] keyWords1 = {
		"void", "boolean", "byte", "short", "int",
		"long", "float", "double", "char",  "var",
		"String", "class", "enum", "interface", "super", 
		"this", "package", "import", "extends", "implements", 
		"throws", "abstract"
	};
	
	private final static String[] keyWords2 = {
		"if", "else", "for", "while", "do", "case", "switch",
		"default", "break", "continue", "try", "catch", "finally",
		"return", "public", "private", "protected", "static",
		"final", "synchronized", "native", "true", "false", "null",
		"goto", "new", "instanceof", "goto", "transient", "throw",
		"assert"
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
		NodeList keyWords = root.getNodeList().getNodeByTag("Java").getNodeList(); 
		keyWord1List = keyWords.getNodeByTag("keyword1").getContent().split(":");
		keyWord2List = keyWords.getNodeByTag("keyword2").getContent().split(":");
	}
}

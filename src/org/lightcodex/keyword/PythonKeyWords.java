package org.lightcodex.keyword;

import org.llogger.LLogger;
import org.lxml.node.NodeList;

public class PythonKeyWords extends KeyWordGetter {
	
	public PythonKeyWords() {}
	
	private static String[] keyWords1 = {
		"and", "break", "continue", "elif", "else",
		"except", "finally", "for", "if", "in", "is",
		"lambda", "not", "or", "pass", "raise", "return",
		"try", "while", "with", "yield"
	};
		
	private static String[] keyWords2 = {
		"False", "None", "True", "as", "assert",
		"async", "await", "class", "def", "del", "from",
		"global", "import", "nonlocal", "__annotations__",
		"__builtins__", "__doc__", "__loader__", "__name__",
		"__age__", "__spec__"
	};
	
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
	
	public static String[] keyWord1List;
	public static String[] keyWord2List;
	
	private static void initKeyWords() {
		NodeList keyWords = root.getNodeList().getNodeByTag("Python").getNodeList(); 
		keyWord1List = keyWords.getNodeByTag("keyword1").getContent().split(":");
		keyWord2List = keyWords.getNodeByTag("keyword2").getContent().split(":");
	}
}

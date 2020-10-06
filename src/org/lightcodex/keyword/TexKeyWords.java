package org.lightcodex.keyword;

import org.llogger.LLogger;
import org.lxml.node.NodeList;

public class TexKeyWords extends KeyWordGetter {

	public TexKeyWords() {}
	
	private static String[] keyWords1 = {
		"documentclass", "begin", "end",
		"emph", "usepackage", "lstset", 
		"frontmatter", "title", "author",
		"date", "input", "chapter", "section",
		"subsection", "paragraph", "subparagraph"
	};
		
	private static String[] keyWords2 = {
		"today", "em", "maketitle", "tableofcontents",
		"basicstyle", "breaklines", "framextopmargin",
		"frame", "document", "textrm", "textbf",
		"oneside", "a4paper", "book", "article"
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
		NodeList keyWords = root.getNodeList().getNodeByTag("Tex").getNodeList(); 
		keyWord1List = keyWords.getNodeByTag("keyword1").getContent().split(":");
		keyWord2List = keyWords.getNodeByTag("keyword2").getContent().split(":");
	}
}

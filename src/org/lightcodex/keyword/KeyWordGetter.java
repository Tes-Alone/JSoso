package org.lightcodex.keyword;

import org.lightcodex.Constants;
import org.lxml.LXMLDocument;
import org.lxml.node.Node;

public class KeyWordGetter {
	
	protected static Node root;
	
	public KeyWordGetter() {}
	
	static  {
		String keyWordsPath = Constants.Homes.configHome + "keywords.xml";
		try {
			root = new LXMLDocument(keyWordsPath).parse().getRoot();
		}catch (Exception e) {
			root = null;
		}
	}
}

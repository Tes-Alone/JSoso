package org.lightcodex.keyword;

import org.llogger.LLogger;
import org.lxml.node.NodeList;

public class DKeyWords extends KeyWordGetter{
	
	public DKeyWords() {}
	
	private final static String[] keyWords1 = {
		"void", "bool", "byte", "short", "int",
		"ubyte", "ushort", "uint", "ulong", "real",
		"long", "float", "double", "char",  "auto",
		"ulong", "cfloat", "cdouble", "wchar", "ireal",
		"ifloat", "idouble", "abstract", "creal", "dchar",
		"debug", "delegate", "deprecated", "cent", "ucent",
		"string", "class", "enum", "interface",
		"public", "private", "protected", "static",
		"final", "synchronized", "native",
		"alias", "align", "asm", "assert", "unittest",
		"version", "with", "size_t", "ptrdiff_t",
		"__gshared", "__traits", "__vector", "__parameters"
	};
	
	private final static String[] keyWords2 = {
		"if", "else", "for", "while", "do", "case", "switch",
		"default", "break", "continue", "try", "catch", "finally",
		"return", "true", "false", "null", "import", "throw",
		"module", "extern", "body", "cast", "extern", 
		"foreach", "foreach_reverse", "function", "goto", 
		"immutable", "in", "inout", "invariant", "is", 
		"lazy", "macro", "mixin", "nothrow", "new", "out", 
		"override", "package", "pragma", "pure", "ref", 
		"scope", "shared", "struct", "super", "this", 
		"template", "typeid", "typeof", "union", "const",
		"__FILE__", "__FILE_FULL_PATH__", "__MODULE__",
		"__LINE__", "__FUNCTION__", "__PRETTY_FUNCTION__"
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
		NodeList keyWords = root.getNodeList().getNodeByTag("D").getNodeList(); 
		keyWord1List = keyWords.getNodeByTag("keyword1").getContent().split(":");
		keyWord2List = keyWords.getNodeByTag("keyword2").getContent().split(":");
	}
}

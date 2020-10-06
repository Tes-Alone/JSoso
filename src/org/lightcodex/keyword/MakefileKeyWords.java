package org.lightcodex.keyword;

import org.llogger.LLogger;
import org.lxml.node.NodeList;

public class MakefileKeyWords extends KeyWordGetter {

	public MakefileKeyWords() {}
	
	private final static String[] keyWords1 = {
		"VPATH", "SHELL", "MAKESHELL", "MAKE", "MAKE_VERDION",
		"MAKE_HOST", "MAKELEVEL", "MAKEFLAGS", "GNUMAKEFLAGS",
		"MAKECMDGOALS", "CURDIR", "SUFFIXES", "LIBPATTERNS",
		"RECIPEPREFIX", "PHONY", "DEFAULT", "PRECIOUS", "INTERMEDIATE",
		"SECONDARY", "SECONDEXPANSION", "DELETE_ON_ERROR", "IGNORE",
		"LOW_RESOLUTION_TIME", "SILENT", "EXPORT_ALL_VARIABLES", 
		"NOTPARALLEL", "ONESHELL", "POSIX"
	};
		
	private final static String[] keyWords2 = {
		"ifeq", "ifneq", "endif", "else", "ifdef", "ifndef",
		"clean", "default", "define", "endef", "undefine",
		"include", "sinclude", "override", "export", "unexport",
		"private", "vpath", "subst", "patsubst", "strip",
		"findstring", "filter", "filter-out", "sort", "word",
		"words", "wordlist", "firstword", "lastword", "dir", 
		"notdir", "suffix", "basename", "addsuffix", "addprefix",
		"join", "wildcard", "realpath", "abspath", "error",
		"warnint", "shell", "origin", "flavor", "foreach",
		"if", "or", "and", "call", "eval", "file", "value"
		
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
		NodeList keyWords = root.getNodeList().getNodeByTag("Makefile").getNodeList(); 
		keyWord1List = keyWords.getNodeByTag("keyword1").getContent().split(":");
		keyWord2List = keyWords.getNodeByTag("keyword2").getContent().split(":");
	}
}

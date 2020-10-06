package org.lightcodex.lang;

import java.util.ArrayList;
import java.util.List;

public class Language {
	
	private static final String buildinPackage = "org.lightcodex.ui.editor.";
	
	public final static Language NORMAL = new Language("Normal text",
			buildinPackage + "TextEditor", new String[] {"txt", "log"}); 
	
	public final static Language INI = new Language("Initialization file",
			buildinPackage + "IniEditor", new String[] {"ini"}); 
	
	public final static Language ASSEMBLER = new Language("Assembler", 
			buildinPackage + "AsmTextEditor",new String[] {"asm", "nas"});
	
	public final static Language CPP = new Language("C/C++", 
								buildinPackage + "CTextEditor", 
											new String[] {"c", "C", "cc", "c++", "cxx", "cpp", "h", "hh", "H", "h++", "hxx", "hpp", "tcc", "rc"});
	
	public final static Language MAKEFILE = new Language("Makefile", 
								buildinPackage + "MakefileTextEditor",new String[] {"makefile", "mak"});
	
	public final static Language JAVA = new Language("Java", 
								buildinPackage + "JavaTextEditor",new String[] {"java"});
	
	public final static Language D = new Language("D", 
								buildinPackage + "DTextEditor",new String[] {"d", "dd", "di"});
	
	public final static Language PYTHON = new Language("Python",
								buildinPackage + "PythonTextEditor", new String[] {"py", "pyw"});
	
	public final static Language HTML = new Language("HTML", 
								buildinPackage + "HTMLTextEditor", new String[] {"html", "htm"});
	
	public final static Language XML = new Language("XML", 
								buildinPackage + "XMLTextEditor", new String[] {"xml"});
	
	public final static Language JAVA_SCRIPT = new Language("JavaScript", 
								buildinPackage + "JSTextEditor", new String[]{"js"});
	
	public final static Language JSP = new Language("JSP", 
								buildinPackage + "JSPTextEditor", new String[] {"jsp"});
	
	public final static Language CSS = new Language("CSS", 
								buildinPackage + "CSSTextEditor", new String[] {"css"});
	
	public final static Language TEX = new Language("Tex", 
								buildinPackage + "TexTextEditor", new String[] {"tex"});
	
	private String name;
	private String editorClassName;
	private List<String> exts;
	
	public Language(String name, String editorClassName, String[] exts) {
		this.name = name;
		this.editorClassName = editorClassName;
		this.exts = new ArrayList<>();
		
		for (String ext : exts) {
			if (ext!=null && !ext.isEmpty()) {
				this.exts.add(ext);
			}
		}
	}
	
	public Language(String name, String editorClassName) {
		this(name, editorClassName, new String[0]);
	}
	
	public void addExtension(String ext) {
		exts.add(ext);
	}
	
	public String getName() {
		return name;
	}
	
	public String getEditorClassName() {
		return editorClassName;
	}
	
	public void removeExtension(String ext) {
		exts.remove(ext);
	}
	
	public String[] getExtensions() {
		String[] result = new String[exts.size()];
		return exts.toArray(result);
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Language) {
			return obj.toString().equals(this.toString());
		} else {
			return false;
		}
	}
}

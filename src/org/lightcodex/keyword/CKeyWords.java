package org.lightcodex.keyword;

import org.llogger.LLogger;
import org.lxml.node.NodeList;

public class CKeyWords extends KeyWordGetter {
	
	public CKeyWords() {}
	
	private static final String[] keyWords1 = {
		"int", "char", "short", "long", "float", "double",
		"auto", "void", "char8_t", "char16_t", "char32_t",
		"wchar_t", "bool",
		
		"int8_t", "int16_t", "int32_t", "int64_t",
		"uint8_t", "uint16_t", "uint32_t", "uint64_t",
		"clock_t", "size_t", "FILE", 
		"jmp_buf", "time_t", "default",
		"delete", "this", "thread_local",
		"typename", "virtual", "xor", "xor_eq",
		
		"const", "inline", "register", "restrict", "typedef","volatile",
		"consteval", "constexpr", "constinit", "decltype", "explicit",
		"export", "using", "new", "neexception", "not", "not_eq",
		"operator", "or", "or_eq", "private", "public", "protected",
		"reinterpret_cast", "requires", "static_assert"
	};
	
	private static final String[] keyWords2 = {
		"if", "else", "for", "while", "continue", "break", 
		"goto", "switch", "case", "default", "do", "return",
		"catch", "try", "throw", "NULL", "EOF", "FALSE", "TRUE",
		
		"extern", "static", "unsigned", "signed", "sizeof",
		"alignas", "alignof", "asm", "template",
		
		"class", "enum", "struct", "union", "_Bool", "_Complex", "_Imaginary",
		"_Alignas", "and", "and_eq", "bitand", "bitor", "compl",
		"concept", "const_cast", "co_await", "co_return", "co_yield", "static_cast",
		"dynamic_cast", "false", "true", "nullptr", "friend", 
		"mutable", "namespace", "final", "override"
		
		//rc
		,"ACCELERATORS", "ALT", "AUTO3STATE", "AUTOCHECKBOX", 
		"AUTORADIOBUTTON", "BEGIN", "BITMAP", "BLOCK", 
		"BUTTON", "CAPTION", "CHARACTERISTICS", "CHECKBOX", 
		"CLASS",  "COMBOBOX", "CONTROL", "CTEXT", "CURSOR", 
		"DEFPUSHBUTTON", "DIALOG", "DIALOGEX", "DISCARDABLE", 
		"EDITTEXT", "END", "EXSTYLE", "FONT", "GROUPBOX", 
		"ICON", "LANGUAGE", "LISTBOX", "LTEXT", "MENU", 
		"MENUEX", "MENUITEM", "MESSAGETABLE", "POPUP", "PUSHBUTTON", 
		"RADIOBUTTON", "RCDATA", "RTEXT", "SCROLLBAR", "SEPARATOR", 
		"SHIFT", "STATE3", "STRINGTABLE", "STYLE", "TEXTINCLUDE", 
		"VALUE", "VERSION", "VERSIONINFO", "VIRTKEY" 
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
		NodeList keyWords = root.getNodeList().getNodeByTag("C").getNodeList(); 
		keyWord1List = keyWords.getNodeByTag("keyword1").getContent().split(":");
		keyWord2List = keyWords.getNodeByTag("keyword2").getContent().split(":");
	}
}

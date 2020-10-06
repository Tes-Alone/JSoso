package org.lxml.parser;

import org.lxml.lexer.Tag;
import org.lxml.lexer.Token;

public class ParserError {
	
	private ParserError() {}
	
	public static void error(String fileName, int line, 
								Token old, Token cur) {
		if (old==null && cur.tag!=Tag.openTag) {
			throw new RuntimeException("错误: " + fileName + ":" + line + ": 缺失开始标签.");
		}
		
		if (old.tag==Tag.attrName && cur.tag!=Tag.equ) {
			throw new RuntimeException("错误: " + fileName + ":" + line + ": 缺失'='.");
		}
		
		if (old.tag==Tag.equ && cur.tag!=Tag.attrValue) {
			throw new RuntimeException("错误: " + fileName + ":" + line + ": 缺失属性值." );
		}
		
		if (old.tag==Tag.closeTag && cur.tag!=Tag.big) {
			throw new RuntimeException("错误: " + fileName + ":" + line + ": 缺失'>'." );
		}
	}
}

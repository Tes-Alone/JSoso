package org.lxml.lexer;

public class Token {
	public Tag 		tag;
	public String	text;
	
	public Token(Tag tag, String text) {
		this.tag  = tag;
		this.text = text;
	}
	
	@Override
	public String toString() {
		return tag.toString() + " : " + text;
	}
}

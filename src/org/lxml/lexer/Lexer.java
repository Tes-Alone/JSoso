package org.lxml.lexer;

import java.io.FileReader;

public class Lexer {
	
	private int line;
	private int cur;
	private String text;
	private String fileName;
	
	public Lexer(String fileName) {
		if (fileName == null) {
			throw new NullPointerException();
		}
		
		this.fileName = fileName;
		this.text	  = read();
		this.line 	  = 1;
	}
	
	public Token nextToken() {
		filterWhiteSpace();
		
		if (!isEnd(1) && isOpenTagStart(getCur(), getCharUntil(1))) {
			return openTag(getOffset());
		} else if (!isEnd(2) && isCloseTagStart(getCur(), 
								getCharUntil(1), getCharUntil(2))) {
			return closeTagStart(getOffset());
		} else if (!isEnd(0) && isAttrNameStart(getCur())) {
			return attrName(getOffset());
		} else if (!isEnd(0) && isAttrValueStart(getCur(), '\'')) {
			return attrValue(getOffset(), '\'');
		} else if (!isEnd(0) && isAttrValueStart(getCur(), '"')) {
			return attrValue(getOffset(), '"');
		} else if (!isEnd(0) && getCur()=='>') {
			return big();
		} else if (!isEnd(1) && isSelfCloseSign(getCur(), getCharUntil(1))) {
			return selfCloseSign();
		} else if (!isEnd(0) && isContentStart(getCur())) {
			return content(getOffset());
		} else if (!isEnd(2) && isCommentStart(getCur(), 
								getCharUntil(1), getCharUntil(2), getCharUntil(3))) {
			return comment(getOffset());
		} else if (!isEnd(0) && getCur()=='=') {
			advance();
			return new Token(Tag.equ, "=");
		} else if (isEnd(0)) {
			return new Token(Tag.end, "");
		} else {
			throw new RuntimeException("错误: " + fileName + ":" + line + ": 无效字符 '" + getCur() + "'.");
		}
	}
	
	private Token comment(int offset) {
		advance(); advance(); advance(); advance();
		while (!isEnd(2) && !isCommentEnd(getCur(), 
							getCharUntil(1), getCharUntil(2))) {
			if (getCur() == '\n') incLine();
			advance();
		}
		
		if (isEnd(2)) {
			throw new RuntimeException("错误: " + fileName + ":" + line + ": 注释未关闭.");
		} else {
			advance(); advance(); advance();
		}
		return new Token(Tag.comment, "");
	}

	private boolean isCommentEnd(char c, char d, char e) {
		return c=='-' && d=='-' && e=='>';
	}

	private boolean isCommentStart(char c, char d, char e, char f) {
		return c=='<' && d=='!' && e=='-' && f=='-';
	}

	private Token content(int offset) {
		StringBuffer sb = new StringBuffer();
		while (!isEnd(0) && !isContentEnd(getCur())) {			
			if (!isEnd(0) && Character.isWhitespace(getCur())) {
				if (getCur() == '\n') {
					incLine();
				}
				if (!ignoreWhiteSpace) {
					sb.append(' ');
				}
				advance();
			}
			while (!isEnd(0) && !isContentEnd(getCur()) && 
									Character.isWhitespace(getCur())) {
				if (getCur()=='\n') incLine();
				advance();
			}
			
			//********************************
			// Content 中允许注释.
			if (!isEnd(3) && isCommentStart(getCur(), 
								getCharUntil(1), getCharUntil(2), getCharUntil(3))) {
				comment(getOffset());
			}
			//********************************
			// 这里的 else 不能省.
			else if (!isEnd(0) && !isContentEnd(getCur())) {
				sb.append(getCur());
				advance();
			}
		}
		return new Token(Tag.content, sb.toString().trim());
	}

	private boolean isContentEnd(char c) {
		return c == '<';
	}

	private boolean isContentStart(char c) {
		return startAttrName==false && c != '<';
	}

	private Token selfCloseSign() {
		advance(); advance();
		startAttrName = false;
		return new Token(Tag.selfCloseSign, "/>");
	}

	private Token big() {
		advance(); startAttrName = false;
		return new Token(Tag.big, ">");
	}

	private boolean isSelfCloseSign(char c, char d) {
		return c == '/' && d == '>';
	}

	private Token attrValue(int offset, char quot) {
		advance();
		while (!isEnd(0) && !isAttrValueEnd(getCur(), quot)) {
			if (getCur() == '\n') incLine();
			advance();
		}
		
		if (isEnd(0)) {
			throw new RuntimeException("错误: " + fileName + ":" + line + ": 属性值缺失右引号.");
		}
		
		//if (offset+1 == getOffset()) {
			//throw new RuntimeException("错误: " + fileName + ":" + line + ": 属性值空.");
		//}
		
		advance();
		return new Token(Tag.attrValue, subString(offset+1, getOffset()-1));
	}

	private boolean isAttrValueEnd(char c, char d) {
		return c == d;
	}

	private boolean isAttrValueStart(char c, char d) {
		return c == d;
	}

	private Token attrName(int offset) {
		advance();
		while (!isEnd(0) && isIdPart(getCur())) {
			advance();
		}
		return new Token(Tag.attrName, subString(offset, getOffset()));
	}

	private boolean isAttrNameStart(char c) {
		return startAttrName && 
				(Character.isLetterOrDigit(c) || c=='_');
	}

	private Token closeTagStart(int offset) {
		advance();
		advance();
		advance();
		while (!isEnd(0) && isIdPart(getCur())) {
			advance();
		}
		return new Token(Tag.closeTag, subString(offset+2, getOffset()));
	}

	private boolean isCloseTagStart(char c, char d, char e) {
		return c=='<' && d=='/' && 
						(Character.isLetterOrDigit(e) ||
									e=='_');
	}

	private boolean startAttrName;

	private Token openTag(int offset) {
		advance();
		advance();
		while (!isEnd(0) && isIdPart(getCur())) {
			advance();
		}
		
		startAttrName = true;
		return new Token(Tag.openTag, subString(offset+1, getOffset()));
	}
	
	private String subString(int start, int end) {
		return text.substring(start, end);
	}

	private boolean isIdPart(char c) {
		return Character.isLetterOrDigit(c) || c=='_' || c=='-';
	}

	private boolean isOpenTagStart(char c, char d) {
		return c=='<' && (Character.isLetterOrDigit(d) || d=='_');
	}

	private void filterWhiteSpace() {
		while (!isEnd(0)) {
			switch (getCur()) {
			case ' ': 
			case '\t':
				advance();
				break;
			case '\n': 
				incLine();
				advance();
				break;
			default:
				return;
			}
		}
	}
	
	private void incLine() {
		line++;
	}
	
	private int getOffset() {
		return cur;
	}
	
	private char getCur() {
		return text.charAt(cur);
	}
	
	private char getCharUntil(int until) {
		return text.charAt(cur + until);
	}
	
	private void advance() {
		cur++;
	}
	
	private boolean isEnd(int need) {
		return cur+need >= text.length();
	}

	private String read() {
		try {
			var in  = new FileReader(fileName);
			var out = new StringBuffer();
			var buf = new char[4096];
			var i   = in.read(buf);
			
			while (i == 4096) {
				out.append(buf, 0, i);
				i = in.read(buf);
			}
			
			if (i != -1)
				out.append(buf, 0, i);
			
			in.close();
			return out.toString().replaceAll("\r\n|\r|\n", "\n");
		} catch (Exception e) {
			throw new RuntimeException("文件: " + fileName + ", 读取失败.");
		}
	}
	
	public int getLine() {
		return line;
	}

	private boolean ignoreWhiteSpace = true;
	
	public void setIgnoreWhiteSpace(boolean ignore) {
		this.ignoreWhiteSpace = ignore;
	}
	
	/*
	public static void main(String[] args) {
		Lexer lex = new Lexer("F:\\JavaTest\\test.xml");
		Token tk = lex.nextToken();
		while (tk.tag != Tag.end) {
			System.out.println(tk);
			tk = lex.nextToken();
		}
	}*/
}

package org.lxml.parser;

import org.lxml.lexer.Lexer;
import org.lxml.lexer.Tag;
import org.lxml.lexer.Token;
import org.lxml.node.Node;

/*
   *  语法如下:
   *  doc -> node
   *  node -> OpenTag attrList Big content CloseTag Big
   *  node -> OpenTag attrList Big nodes CloseTag Big
   *  		| OpenTag attrList SelfCloseSign
   *  nodes -> node nodes | empty
   *  attrList -> attr attrList 
   *  		| empty
   *  attr -> AttrName Equ AttrValue
 * */

public class Parser {
	
	private String fileName;
	
	private Lexer  lexer;
	private Token  look;
	private Token  oldToken;
	
	private Node root;
	private Node top;
	
	private String topTag;
	
	public Parser(String fileName) {
		this.fileName = fileName;
		this.lexer    = new Lexer(fileName);
		move();
	}
	
	private boolean parsed;
	
	public Node parse() {
		if (!parsed) {
			if (match(Tag.end)) {
				root = new Node("empty");
			} else {
				document();
				if (!match(Tag.end)) {
					throw new RuntimeException("错误: " + fileName + ":" + lexer.getLine() 
								+ ": 根节点已关闭, 但还有文本.");
				}
			}
			parsed = true;
			return root;
		} else {
			return root;
		}
	}
	
	private void document() {
		node();
	}

	private void node() {
		String savedTag  = topTag; //***
		Node   savedNode = top;   //***
		
		topTag = look.text;			//***
		
		if (savedNode == null) {
			root = new Node(topTag); //***
			top  = root;  			//***
		} else {
			top = new Node(topTag);  //***
			savedNode.getNodeList().addNode(top); //***
		}
		
		matchAndMove(Tag.openTag);
		attrList();
		if (match(Tag.big)) {
			move();
			
			if (match(Tag.content))
				content();
			else
				nodes();
			checkPair(look.text);
			matchAndMove(Tag.closeTag);
			matchAndMove(Tag.big);
			topTag = savedTag; //***
			top    = savedNode;//***
		} else if (match(Tag.selfCloseSign)) {
			topTag = savedTag;//***
			top    = savedNode;//***
			move();
		} else {
			throw new RuntimeException("错误: " + fileName + ":" + lexer.getLine() + 
							": 缺失 '>' 或 '/>'");
		}
	}

	private void checkPair(String text) {
		if (!topTag.equals(text)) {
			throw new RuntimeException("错误: " + fileName + ":" + lexer.getLine() +
							": 标签 " + topTag + " 未关闭.");
		}
	}

	private void content() {
		top.setContent(look.text);
		move();
	}
	
	private void nodes() {
		while (true) {
			if (match(Tag.openTag)) {
				node();
			} else {
				break;
			}
		}
	}

	private void attrList() {
		while (true) {
			if (match(Tag.attrName)) {
				attr();
			} else {
				break;
			}
		} 
	}

	private void attr() {
		String name = look.text; //***
		move();
		matchAndMove(Tag.equ);
		
		String value = look.text; //***
		
		matchAndMove(Tag.attrValue);
		
		top.getAttrMap().addAttr(name, value); //***
	}

	private void matchAndMove(Tag tag) {
		if (match(tag)) {
			move();
		} else {
			//System.out.println(oldToken+"\n"+look);
			ParserError.error(fileName, lexer.getLine(), oldToken, look);
		}
	}
	
	private boolean match(Tag tag) {
		return look.tag == tag;
	}
	
	private void move() {
		oldToken = look;
		look = lexer.nextToken();
		filterComment();
	}
	
	private void filterComment() {
		while (look.tag == Tag.comment) {
			look = lexer.nextToken();
		}
	}
	
	/*
	public static void main(String[] args) {
		Parser parser = new Parser("F:\\JavaTest\\demo.xml");
		Node root = parser.parse();
		root.save("F:\\JavaTest\\copy.xml");
	}*/
	
	public void setIgnoreWhiteSpace(boolean ignore) {
		lexer.setIgnoreWhiteSpace(ignore);
	}
}

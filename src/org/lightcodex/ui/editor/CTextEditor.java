package org.lightcodex.ui.editor;

import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.lightcodex.Config;
import org.lightcodex.completeword.CPPCompleteWord;
import org.lightcodex.keyword.CKeyWords;
import org.lightcodex.project.SourceFile;
import org.lightcodex.theme.Theme;
import org.sigmai.SigmaI;
import org.sigmai.event.AutoIndentEvent;
import org.sigmai.event.AutoIndentListener;
import org.sigmai.event.PairEvent;
import org.sigmai.lexer.CPPLexer;
import org.sigmai.lexer.StylePalette;

public class CTextEditor extends TextEditor {

	static {
		StylePalette stylePalette = CPPLexer.getStylePaletteStatic();
		Theme theme = Config.getInstance().getTheme();
		for (int i=0; i<stylePalette.size(); i++) {
			RGB rgb = theme.getTokenColor(i);
			int style = theme.getTokenStyle(i);
			setLexemeStyle(stylePalette, i, rgb, style);
		}
	}
	
	public CTextEditor(Composite parent, SourceFile srcFile) {
		super(parent, srcFile);
		addListeners();
		commentSign = "//";
	}
	
	private int caretBackCount;
	
	private void addListeners() {
		checkWidget();
		sigmai.setAutoIndentListener(new AutoIndentListener(){
			
			private String oldIndentString;
			
			@Override
			public void indenting(AutoIndentEvent e) {
				oldIndentString = "";
				String tmp = e.lineBeforeCaret.trim(); 
				if (tmp.endsWith("{")) {
					oldIndentString = e.indentString;
					e.indentString  = e.indentString + "\t";
				} else if (tmp.endsWith(":")) {
					e.indentString += '\t';
				}
				if (e.lineAfterCaret.trim().startsWith("}")) {
					e.indentString += oldIndentString;
					caretBackCount = oldIndentString.length();
				}
			}
		});
		
		editor.addModifyListener(e->{
			if (caretBackCount != 0) {
				editor.setCaretOffset(editor.getCaretOffset()-caretBackCount);
				caretBackCount = 0;
			}
		});
		
		sigmai.setEnterCommentAreaListener(e->{
			if (!e.text.isEmpty()) {
				autoCompleteTag(e.text.charAt(0));
			}
		});
	}
	
	private void autoCompleteTag(char t) {
		checkWidget();
		if (Config.getInstance().isAutoCompleteTagPair() && t=='>') {
			int caretOffset = editor.getCaretOffset();
			int lineIndex   = editor.getLineAtOffset(caretOffset);
			int lineOffset  = caretOffset - editor.getOffsetAtLine(lineIndex) - 1;
			String line 	= editor.getLine(lineIndex);
			StringBuffer sb = new StringBuffer();
			boolean passLess = false;
			for (int i=lineOffset; i>=0; i--) {
				char c = line.charAt(i);
				if (i==lineOffset && c=='/') {
					return;
				} else if (i<lineOffset && c=='<' && 
								(line.charAt(i+1)=='/'||line.charAt(i+1)=='!'
									||line.charAt(i+1)=='%')) {
					return;
				} else if (c == '<') {
					passLess = true;
					break;
				} else if (!Character.isLetterOrDigit(c)&&c!=':'&&c!='_'&&c!='-') {
					sb = new StringBuffer();
				} else {
					sb.append(c);
				}
			}
			
			if (passLess && sb.length()!=0) {
				sb.reverse().insert(0, "</").append(">");
				sigmai.insert(sb.toString());
			}
		}
	}

	@Override
	protected void fixBraces(PairEvent e) {
		checkWidget();
		if (e.character == '<') {
			String line = editor.getLine(editor.getLineAtOffset(editor.getCaretOffset()));
			if (!line.trim().startsWith("#include")) {
				e.doit = false;
			}
		} else {
			char c = sigmai.rightCharOfCaret();
			if (c==0||Character.isWhitespace(c)||c=='{'||c=='}'||c=='('||c==')'||c=='['||c==']') {
				e.doit = true;
			} else {
				e.doit = false;
			}
		}
	}
	
	@Override
	protected void initLexer() {
		checkWidget();
		lexer = new CPPLexer();
		lexer.setKeyWord1(CKeyWords.keyWord1List);
		lexer.setKeyWord2(CKeyWords.keyWord2List);
	}

	@Override
	protected void fixQuot(PairEvent e, boolean isDouble) {
		checkWidget();
		char left = sigmai.leftCharOfCaret();
		char right = sigmai.rightCharOfCaret();
		char quot = isDouble ? '"' : '\'';
		if (left=='L' || left=='u' || left=='U' || left=='R') return;
		if (Character.isLetterOrDigit(right)||right=='_'||right==quot) {
			e.doit = false;
		} else if (Character.isLetterOrDigit(left)||left=='_'||left==quot||left=='\\') {
			e.doit = false;
		}
	}
	
	@Override
	protected void addBaseCompleteWord() {
		checkWidget();
		sigmai.addBaseCompleteWords(CKeyWords.keyWord1List);
		sigmai.addBaseCompleteWords(CKeyWords.keyWord2List);
		sigmai.addBaseCompleteWords(CPPCompleteWord.wordList);
	}
	
	@Override
	protected int getMatchBraceFlag() {
		checkWidget();
		return SigmaI.PAIR_BRAC | SigmaI.PAIR_PARE | SigmaI.PAIR_SQUA;
	}
}

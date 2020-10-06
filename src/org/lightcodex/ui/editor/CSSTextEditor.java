package org.lightcodex.ui.editor;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.lightcodex.Config;
import org.lightcodex.keyword.CSSKeyWords;
import org.lightcodex.project.SourceFile;
import org.lightcodex.theme.Theme;
import org.sigmai.SigmaI;
import org.sigmai.event.AutoIndentEvent;
import org.sigmai.event.AutoIndentListener;
import org.sigmai.event.PairEvent;
import org.sigmai.lexer.CSSLexer;
import org.sigmai.lexer.StylePalette;

public class CSSTextEditor extends TextEditor {

	static {
		StylePalette stylePalette = CSSLexer.getStylePaletteStatic();
		Theme theme = Config.getInstance().getTheme();
		for (int i=0; i<stylePalette.size(); i++) {
			RGB rgb = theme.getTokenColor(i);
			int style = theme.getTokenStyle(i);
			setLexemeStyle(stylePalette, i, rgb, style);
		}
	}
	
	public CSSTextEditor(Composite parent, SourceFile srcFile) {
		super(parent, srcFile);
		addListeners();
	}

	private int caretBackCount;
	
	private void addListeners() {
		checkWidget();
		sigmai.setAutoIndentListener(new AutoIndentListener(){
			
			private String oldIndentString;
			
			@Override
			public void indenting(AutoIndentEvent e) {
				oldIndentString = "";
				if (e.lineBeforeCaret.trim().endsWith("{")) {
					oldIndentString = e.indentString;
					e.indentString = e.indentString + "\t";
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
	}

	@Override
	protected void fixBraces(PairEvent e) {
		checkWidget();
		if (e.character != '{') {
			e.doit = false;
		}
			
	}
	
	@Override
	protected void initLexer() {
		checkWidget();
		lexer = new CSSLexer();
		lexer.setKeyWord1(CSSKeyWords.keyWord1List);
		// 使用 keyword1, 简化关键字提供.
		lexer.setKeyWord1(CSSKeyWords.keyWord2List);
	}

	@Override
	protected void fixQuot(PairEvent e, boolean isDouble) {
		checkWidget();
		char left = sigmai.leftCharOfCaret();
		char right = sigmai.rightCharOfCaret();
		char quot = isDouble ? '"' : '\'';
		if (Character.isLetterOrDigit(right)||right=='_'||right==quot) {
			e.doit = false;
		} else if (Character.isLetterOrDigit(left)||left=='_'||left==quot||left=='\\') {
			e.doit = false;
		}
	}
	
	@Override
	protected void addBaseCompleteWord() {
		checkWidget();
		sigmai.addBaseCompleteWords(CSSKeyWords.keyWord1List);
		sigmai.addBaseCompleteWords(CSSKeyWords.keyWord2List);
	}
	
	@Override
	protected int getMatchBraceFlag() {
		checkWidget();
		return SigmaI.PAIR_BRAC;
	}
	
	@Override
	public void addComment() {
		checkWidget();
		Point selection = editor.getSelectionRange();
		int startLine = editor.getLineAtOffset(selection.x);
		int endLine = editor.getLineAtOffset(selection.x+selection.y);
		StringBuffer sb = new StringBuffer();
		for (int i=startLine; i<=endLine; i++) {
			String line = editor.getLine(i);
			String insert = "";
			boolean startsWithComment = line.trim().startsWith("/*");
			boolean endsWithComment = line.trim().endsWith("*/");
			if (startsWithComment && !endsWithComment) {
				insert = line + "*/";
			} else if (!startsWithComment && endsWithComment) {
				insert = "/*" + line;
			} else if (!startsWithComment && !endsWithComment){
				insert = "/*" + line + "*/";
			} else {
				insert = line;
			}
			if (i != endLine)
				sb.append(insert).append(editor.getLineDelimiter());
			else
				sb.append(insert);
		}
		int start = editor.getOffsetAtLine(startLine);
		int end = editor.getOffsetAtLine(endLine) + 
						editor.getLine(endLine).length();
		editor.setSelection(start, end);
		sigmai.insert(sb.toString());
		start = editor.getOffsetAtLine(startLine);
		end = editor.getOffsetAtLine(endLine) + 
						editor.getLine(endLine).length();
		editor.setSelection(start, end);
	}
	
	@Override
	public void removeComment() {
		checkWidget();
		Point selection = editor.getSelectionRange();
		int startLine = editor.getLineAtOffset(selection.x);
		int endLine = editor.getLineAtOffset(selection.y+selection.x);
		StringBuffer sb = new StringBuffer(); 
		for (int i=startLine; i<=endLine; i++) {
			String line = editor.getLine(i);
			String tmp  = line.trim();
			if (tmp.startsWith("/*") && tmp.endsWith("*/")) {
				String insert = line.replace("/*", "").replace("*/", "");
				sb.append(insert);
				if (i != endLine)
					sb.append(editor.getLineDelimiter());
			} else {
				sb.append(line);
				if (i != endLine)
					sb.append(editor.getLineDelimiter());
			}
		}
		int start = editor.getOffsetAtLine(startLine);
		int end = editor.getOffsetAtLine(endLine)+ 
							editor.getLine(endLine).length();
		editor.setSelection(start, end);
		sigmai.insert(sb.toString());
		start = editor.getOffsetAtLine(startLine);
		end = editor.getOffsetAtLine(endLine)+ 
							editor.getLine(endLine).length();
		editor.setSelection(start, end);
	}
}

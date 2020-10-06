package org.lightcodex.ui.editor;

import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.lightcodex.Config;
import org.lightcodex.keyword.AsmKeyWords;
import org.lightcodex.project.SourceFile;
import org.lightcodex.theme.Theme;
import org.sigmai.SigmaI;
import org.sigmai.event.AutoIndentEvent;
import org.sigmai.event.AutoIndentListener;
import org.sigmai.event.PairEvent;
import org.sigmai.lexer.AsmLexer;
import org.sigmai.lexer.StylePalette;

public class AsmTextEditor extends TextEditor {

	static {
		StylePalette stylePalette = AsmLexer.getStylePaletteStatic();
		Theme theme = Config.getInstance().getTheme();
		for (int i=0; i<stylePalette.size(); i++) {
			RGB rgb = theme.getTokenColor(i);
			int style = theme.getTokenStyle(i);
			setLexemeStyle(stylePalette, i, rgb, style);
		}
	}
	
	public AsmTextEditor(Composite parent, SourceFile srcFile) {
		super(parent, srcFile);
		commentSign = ";";
		addListeners();
	}
	
	private void addListeners() {
		checkWidget();
		sigmai.setAutoIndentListener(new AutoIndentListener() {
			@Override
			public void indenting(AutoIndentEvent e) {
				if (e.lineBeforeCaret.trim().endsWith(":")) {
					e.indentString += '\t';
				}
			}
		});
	}

	@Override
	protected void initLexer() {
		checkWidget();
		lexer = new AsmLexer();
		((AsmLexer)lexer).setBaseInstrList(AsmKeyWords.baseInstrList);
		((AsmLexer)lexer).setSystemInstrList(AsmKeyWords.systemInstrList);
		((AsmLexer)lexer).setMediaInstrList(AsmKeyWords.mediaInstrList);
		((AsmLexer)lexer).setDirectiveList(AsmKeyWords.directiveList);
		((AsmLexer)lexer).setDirectiveList(AsmKeyWords.directiveList);
		((AsmLexer)lexer).setPseudoInstrList(AsmKeyWords.pseudoInstrList);
		((AsmLexer)lexer).setRegister1List(AsmKeyWords.register1List);
		((AsmLexer)lexer).setRegister2List(AsmKeyWords.register2List);
	}
	
	@Override
	protected void fixBraces(PairEvent e) {
		checkWidget();
		if (e.character=='<' || e.character=='{') {
			e.doit = false;
		} else {
			char c = sigmai.rightCharOfCaret();
			if (c==0||Character.isWhitespace(c)||c=='('||c==')'||c=='['||c==']') {
				e.doit = true;
			} else {
				e.doit = false;
			}
		}
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
		sigmai.addBaseCompleteWords(AsmKeyWords.baseInstrList);
		sigmai.addBaseCompleteWords(AsmKeyWords.directiveList);
		sigmai.addBaseCompleteWords(AsmKeyWords.mediaInstrList);
		sigmai.addBaseCompleteWords(AsmKeyWords.pseudoInstrList);
		sigmai.addBaseCompleteWords(AsmKeyWords.register1List);
		sigmai.addBaseCompleteWords(AsmKeyWords.register2List);
		sigmai.addBaseCompleteWords(AsmKeyWords.systemInstrList);
	}

	@Override
	protected int getMatchBraceFlag() {
		checkWidget();
		return SigmaI.PAIR_PARE | SigmaI.PAIR_SQUA;
	}
}

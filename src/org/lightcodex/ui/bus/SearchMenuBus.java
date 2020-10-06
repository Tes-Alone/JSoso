package org.lightcodex.ui.bus;

import java.util.regex.PatternSyntaxException;

import org.eclipse.swt.widgets.Display;
import org.lightcodex.Constants;
import org.lightcodex.info.FindFileInfo;
import org.lightcodex.info.FindInFolderInfo;
import org.lightcodex.info.FindInfo;
import org.lightcodex.info.RegexFindInfo;
import org.lightcodex.info.ReplaceInSelectionInfo;
import org.lightcodex.ui.EditorFolder;
import org.lightcodex.ui.FindAndReplaceDialog;
import org.lightcodex.ui.FindFileDialog;
import org.lightcodex.ui.FindInFolderDialog;
import org.lightcodex.ui.ProjectExplorer;
import org.lightcodex.ui.RegexDialog;
import org.lightcodex.ui.ReplaceInSelectionDialog;

public class SearchMenuBus {
	
	private SearchMenuBus() {
		oldFindInfo = new FindInfo();
		oldFindInfo.caseSensitive = false;
		oldFindInfo.forward = false;
		oldFindInfo.newWord = "";
		oldFindInfo.word = "";
		oldFindInfo.wholeWord = false;
		oldFindInfo.wrap = false;
		
		oldRegexFindInfo = new RegexFindInfo();
		oldRegexFindInfo.caseSensitive = false;
		oldRegexFindInfo.regex = "";
		oldRegexFindInfo.replacement = "";
		oldRegexFindInfo.wrap = false;
		oldRegexFindInfo.dotAll = false;
		
		oldFIFInfo = new FindInFolderInfo();
		oldFIFInfo.regex = "";
		oldFIFInfo.filters = "";
		
		oldFFInfo = new FindFileInfo();
		oldFFInfo.regex = "";
		
		oldReplaceInfo = new ReplaceInSelectionInfo();
		oldReplaceInfo.regex = "";
		oldReplaceInfo.replacement = "";
		oldReplaceInfo.dotAll = false;
		oldReplaceInfo.caseSensitive = false;
	}
	
	private static SearchMenuBus self;
	
	public static synchronized void setUpBus() {
		if (self == null) self = new SearchMenuBus();
	}

	public static SearchMenuBus getBus() {
		return self;
	}
	
	private EditorFolder editorFolder;
	
	public void setEditorFolder(EditorFolder editorFolder) {
		this.editorFolder = editorFolder;
	}
	
	private FindAndReplaceDialog fnrDialog; 
	private FindInfo oldFindInfo;
	
	public void openFindAndReplaceDialog() {
		if (fnrDialog == null) {
			fnrDialog = new FindAndReplaceDialog(editorFolder.getShell());
			fnrDialog.setOldFindInfo(oldFindInfo);
			fnrDialog.open();
			oldFindInfo = fnrDialog.getOldFindInfo();
			fnrDialog = null;
		}
	}
	
	public void closeFindAndReplaceDialog() {
		if (fnrDialog != null) {
			fnrDialog.close();
		}
	}
	
	public void findNext(FindInfo info) {
		if (!editorFolder.getSelection().findNext(info)) {
			fnrDialog.setResultInfo(Constants.Strings.noMoreResultInfo);
		} else {
			fnrDialog.setResultInfo("");
		}
	}
	
	public void replace(FindInfo info) {
		int flag = editorFolder.getSelection().replace(info); 
		if (flag == -1) {
			fnrDialog.setResultInfo(Constants.Strings.hasNoSelectionInfo);
			Display.getCurrent().beep();
		} else if (flag == -2) {
			fnrDialog.setResultInfo(Constants.Strings.noMoreResultInfo);
			Display.getCurrent().beep();
		} else {
			fnrDialog.setResultInfo("");
		}
	}
	
	public void replaceAll(FindInfo info) {
		int count = editorFolder.getSelection().replaceAll(info);
		fnrDialog.setResultInfo(String.format(Constants.Strings.replaceCountInfo, count));
	}
	
	private RegexDialog regexDialog;
	private RegexFindInfo oldRegexFindInfo;
	
	public void openRegexDialog() {
		if (regexDialog == null) {
			regexDialog = new RegexDialog(editorFolder.getShell());
			regexDialog.setOldRegexFindInfo(oldRegexFindInfo);
			regexDialog.open();
			oldRegexFindInfo = regexDialog.getOldRegexFindInfo();
			regexDialog = null;
		}
	}
	
	public void closeRegexDialog() {
		if (regexDialog != null) {
			regexDialog.close();
		}
	}
	
	public void findNext(RegexFindInfo info) {
		try {
			if (!editorFolder.getSelection().findNext(info)) {
				regexDialog.setResultInfo(Constants.Strings.noMoreResultInfo);
				Display.getCurrent().beep();
			} else {
				regexDialog.setResultInfo("");
			}
		} catch (PatternSyntaxException e) {
			regexDialog.setResultInfo(Constants.Strings.regexGrammarErrInfo);
			Display.getCurrent().beep();
		} catch (IllegalArgumentException e) {
			regexDialog.setResultInfo("");
		}
	}
	
	public void replace(RegexFindInfo info) {
		int flag = 0; 
		try {
			flag = editorFolder.getSelection().replace(info);
			if (flag == -1) {
				regexDialog.setResultInfo(Constants.Strings.hasNoSelectionInfo);
				Display.getCurrent().beep();
			} else if (flag == -2) {
				regexDialog.setResultInfo(Constants.Strings.noMoreResultInfo);
				Display.getCurrent().beep();
			} else {
				regexDialog.setResultInfo("");
			}
		} catch (PatternSyntaxException e) {
			regexDialog.setResultInfo(Constants.Strings.regexGrammarErrInfo);
			Display.getCurrent().beep();
		} catch (IndexOutOfBoundsException e) {
			regexDialog.setResultInfo(Constants.Strings.regexGroupTooMoreInfo);
			Display.getCurrent().beep();
		}
	}
	
	public void replaceAll(RegexFindInfo info) {
		try {
			editorFolder.getSelection().replaceAll(info);
			regexDialog.setResultInfo("");
		} catch (PatternSyntaxException e) {
			regexDialog.setResultInfo(Constants.Strings.regexGrammarErrInfo);
			Display.getCurrent().beep();
		} catch (IndexOutOfBoundsException e) {
			regexDialog.setResultInfo(Constants.Strings.regexGroupTooMoreInfo);
			Display.getCurrent().beep();
		}
	}
	
	private ProjectExplorer proExplorer;
	
	public void setProjectExplorer(ProjectExplorer proExplorer) {
		this.proExplorer = proExplorer;
	}
	
	private FindInFolderInfo oldFIFInfo;
	private FindInFolderDialog findInFolderDialog;
	
	public void openFindInFolderDialog() {
		if (findInFolderDialog == null) {
			findInFolderDialog = new FindInFolderDialog(editorFolder.getShell());
			findInFolderDialog.setOldFindInfo(oldFIFInfo);
			try {
				findInFolderDialog.setFolderPath(proExplorer.getCurrentFolder().getFullPath());
			} catch (NullPointerException e) {
				findInFolderDialog.setFolderPath("");
			}
			findInFolderDialog.open();
			oldFIFInfo = findInFolderDialog.getOldFindInfo();
			findInFolderDialog = null;
		}
	}
	
	private ReplaceInSelectionDialog replaceInSelectionDialog;
	private ReplaceInSelectionInfo oldReplaceInfo;
	
	public void openReplaceInSelectionDialog() {
		if (replaceInSelectionDialog == null) {
			replaceInSelectionDialog = new ReplaceInSelectionDialog(editorFolder.getShell());
			replaceInSelectionDialog.setOldReplaceInfo(oldReplaceInfo);
			replaceInSelectionDialog.open();
			oldReplaceInfo = replaceInSelectionDialog.getOldReplaceInfo();
			replaceInSelectionDialog = null;
		}
	}
	
	public void closeReplaceInSelectionDialog() {
		if (replaceInSelectionDialog != null) {
			replaceInSelectionDialog.close();
		}
	}
	
	public void replaceInSelection(ReplaceInSelectionInfo info) {
		try {
			if (editorFolder.getSelection().replaceInSelection(info) == -1) {
				replaceInSelectionDialog.setResultInfo(Constants.Strings.hasNoSelectionInfo);
				Display.getCurrent().beep();
			} else {
				replaceInSelectionDialog.setResultInfo("");
			}
		} catch (PatternSyntaxException e) {
			replaceInSelectionDialog.setResultInfo(Constants.Strings.regexGrammarErrInfo);
			Display.getCurrent().beep();
		} catch (IndexOutOfBoundsException e) {
			replaceInSelectionDialog.setResultInfo(Constants.Strings.regexGroupTooMoreInfo);
			Display.getCurrent().beep();
		}
	}

	private FindFileInfo oldFFInfo;
	private FindFileDialog findFileDialog;
	
	public void openFindFileDialog() {
		if (findFileDialog == null) {
			findFileDialog = new FindFileDialog(editorFolder.getShell());
			findFileDialog.setOldFindInfo(oldFFInfo);
			try {
				findFileDialog.setFolderPath(proExplorer.getCurrentFolder().getFullPath());
			} catch (NullPointerException e) {
				findFileDialog.setFolderPath("");
			}
			findFileDialog.open();
			oldFFInfo = findFileDialog.getOldFindInfo();
			findFileDialog = null;
		}
	}
}
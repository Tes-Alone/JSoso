package org.lightcodex.ui.bus;

import java.io.IOException;

import org.lightcodex.ui.DateTimeFormatDialog;
import org.lightcodex.ui.EditorFolder;
import org.lightcodex.ui.EncodeDialog;
import org.lightcodex.ui.GotoLineDialog;

public class EditMenuBus {
	
	private static EditMenuBus self;
	
	public static synchronized void setUpBus() { 
		if (self == null) {
			self = new EditMenuBus();
		}
	}
	
	public static EditMenuBus getBus() {
		return self;
	}
	
	private EditorFolder editorFolder;
	
	public void setEditorFolder(EditorFolder editorFolder) {
		this.editorFolder = editorFolder;
	}
	
	public void undo() {
		editorFolder.getSelection().undo();
	}
	
	public void redo() {
		editorFolder.getSelection().redo();
	}
	
	public void selectAll() {
		editorFolder.getSelection().selectAll();
	}
	
	public void dateTime() {
		DateTimeFormatDialog dialog = new DateTimeFormatDialog(editorFolder.getShell());
		String dateTime = dialog.open();
		if (dateTime != null) {
			editorFolder.getSelection().dateTime(dateTime);
		}
	}
	
	public void copyLine() {
		editorFolder.getSelection().copyLine();
	}
	
	public void sortLines() {
		editorFolder.getSelection().sortLines();
	}
	
	public void indent() {
		editorFolder.getSelection().indent();
	}
	
	public void unindent() {
		editorFolder.getSelection().unindent();
	}
	
	public void addComment() {
		editorFolder.getSelection().addComment();
	}
	
	public void removeComment() {
		editorFolder.getSelection().removeComment();
	}

	public void paste() {
		editorFolder.getSelection().paste();
	}

	public void copy() {
		editorFolder.getSelection().copy();
	}
	
	public void cut() {
		editorFolder.getSelection().cut();
	}
	
	public void delete() {
		editorFolder.getSelection().delete();
	}

	public void toLowerCase() {
		editorFolder.getSelection().toLowerCase();
	}
	
	public void toUpperCase() {
		editorFolder.getSelection().toUpperCase();
	}
	
	private EncodeDialog encodeDialog; 
	
	public void openEncodeDialog() {
		if (encodeDialog == null) {
			encodeDialog = new EncodeDialog(editorFolder.getShell());
			encodeDialog.open();
			encodeDialog = null;
		}
	}
	
	public void closeEncodeDialog() {
		if (encodeDialog != null) {
			encodeDialog.close();
		}
	}
	
	public void convertEncode(String charset) throws IOException {
		editorFolder.getSelection().convertEncode(charset);
	}
	
	private GotoLineDialog gotoLineDialog;
	
	public void openGotoLineDialog() {
		if (gotoLineDialog == null) {
			gotoLineDialog = new GotoLineDialog(editorFolder.getShell());
			gotoLineDialog.open();
			gotoLineDialog = null;
		}
	}
	
	public void closeGotoLineDialog() {
		if (gotoLineDialog != null) {
			gotoLineDialog.close();
		}
	}

	public void gotoLine(int lineIndex) {
		editorFolder.getSelection().gotoLine(lineIndex);
	}

	public void revolveLines() {
		editorFolder.getSelection().revolveLines();
	}

	public void splitLines() {
		editorFolder.getSelection().splitLines();
	}

	public void joinLines() {
		editorFolder.getSelection().joinLines();
	}

	public void trimLines() {
		editorFolder.getSelection().trimLines();
	}

	public void deleteLine() {
		editorFolder.getSelection().deleteLine();
	}

	public void chanageComment() {
		editorFolder.getSelection().changeComment();
	}
	
	public void toProperCase() {
		editorFolder.getSelection().toProperCase();
	}
	
	public void toSentenceCase() {
		editorFolder.getSelection().toSentenceCase();
	}
}
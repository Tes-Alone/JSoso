package org.lightcodex.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.lightcodex.Constants;
import org.lightcodex.ui.bus.EditMenuBus;

public class EditMenu extends AbstractMenu {
	
	public EditMenu(Menu menuBar) {
		super(menuBar);
		menuItem.setText(Constants.Strings.editMenu);
		
		createListener();
		createItems();
	}
	
	public void disableAll() {
		this.disableItem(Constants.Strings.undoMenu);
		this.disableItem(Constants.Strings.redoMenu);
		this.disableItem(Constants.Strings.copyLineMenu);
		this.disableItem(Constants.Strings.deleteLineMenu);
		this.disableItem(Constants.Strings.sortLinesMenu);
		this.disableItem(Constants.Strings.indentMenu);
		this.disableItem(Constants.Strings.trimMenu);
		this.disableItem(Constants.Strings.unindentMenu);
		this.disableItem(Constants.Strings.commentMenu);
		this.disableItem(Constants.Strings.changeCommentMenu);
		this.disableItem(Constants.Strings.uncommentMenu);
		this.disableItem(Constants.Strings.dateTimeMenu);
		this.disableItem(Constants.Strings.selectAllMenu);
		this.disableItem(Constants.Strings.copyMenu);
		this.disableItem(Constants.Strings.cutMenu);
		this.disableItem(Constants.Strings.pasteMenu);
		this.disableItem(Constants.Strings.deleteMenu);
		this.disableItem(Constants.Strings.convertToLowerCaseMenu);
		this.disableItem(Constants.Strings.convertToUpperCaseMenu);
		this.disableItem(Constants.Strings.encodingMenu);
		this.disableItem(Constants.Strings.gotoLineMenu);
		this.disableItem(Constants.Strings.revolveLinesMenu);
		this.disableItem(Constants.Strings.splitLinesMenu);
		this.disableItem(Constants.Strings.joinLinesMenu);
	}

	private void createListener() {
		listener = new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String text = ((MenuItem) e.widget).getText();
				if (text.equals(Constants.Strings.undoMenu)) {
					EditMenuBus.getBus().undo();
				} else if (text.equals(Constants.Strings.redoMenu)) {
					EditMenuBus.getBus().redo();
				} else if (text.equals(Constants.Strings.dateTimeMenu)) {
					EditMenuBus.getBus().dateTime();
				} else if (text.equals(Constants.Strings.selectAllMenu)) {
					EditMenuBus.getBus().selectAll();
				} else if (text.equals(Constants.Strings.copyLineMenu)) {
					EditMenuBus.getBus().copyLine();
				} else if (text.equals(Constants.Strings.sortLinesMenu)) {
					EditMenuBus.getBus().sortLines();
				} else if (text.equals(Constants.Strings.indentMenu)) {
					EditMenuBus.getBus().indent();
				} else if (text.equals(Constants.Strings.unindentMenu)) {
					EditMenuBus.getBus().unindent();
				} else if (text.equals(Constants.Strings.commentMenu)) {
					EditMenuBus.getBus().addComment();
				} else if (text.equals(Constants.Strings.uncommentMenu)) {
					EditMenuBus.getBus().removeComment();
				} else if (text.equals(Constants.Strings.pasteMenu)) {
					EditMenuBus.getBus().paste();
				} else if (text.equals(Constants.Strings.copyMenu)) {
					EditMenuBus.getBus().copy();
				} else if (text.equals(Constants.Strings.cutMenu)) {
					EditMenuBus.getBus().cut();
				} else if (text.equals(Constants.Strings.deleteMenu)) {
					EditMenuBus.getBus().delete();
				} else if (text.equals(Constants.Strings.convertToLowerCaseMenu)) {
					EditMenuBus.getBus().toLowerCase();
				} else if (text.equals(Constants.Strings.convertToUpperCaseMenu)) {
					EditMenuBus.getBus().toUpperCase();
				} else if (text.equals(Constants.Strings.encodingMenu)) {
					EditMenuBus.getBus().openEncodeDialog();
				} else if (text.equals(Constants.Strings.gotoLineMenu)) {
					EditMenuBus.getBus().openGotoLineDialog();
				} else if (text.equals(Constants.Strings.revolveLinesMenu)) {
					EditMenuBus.getBus().revolveLines();
				} else if (text.equals(Constants.Strings.splitLinesMenu)) {
					EditMenuBus.getBus().splitLines();
				} else if (text.equals(Constants.Strings.joinLinesMenu)) {
					EditMenuBus.getBus().joinLines();
				} else if (text.equals(Constants.Strings.trimMenu)) {
					EditMenuBus.getBus().trimLines();
				} else if (text.equals(Constants.Strings.deleteLineMenu)) {
					EditMenuBus.getBus().deleteLine();
				} else if (text.equals(Constants.Strings.changeCommentMenu)) {
					EditMenuBus.getBus().chanageComment();
				} else if (text.equals(Constants.Strings.convertToProperCaseMenu)) {
					EditMenuBus.getBus().toProperCase();
				} else if (text.equals(Constants.Strings.convertToSentenceCaseMenu)) {
					EditMenuBus.getBus().toSentenceCase();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		};
	}

	private void createItems() {
		this.createItem(Constants.Strings.undoMenu, Constants.Images.undo, SWT.CTRL | 'Z', false);
		this.createItem(Constants.Strings.redoMenu, Constants.Images.redo, SWT.CTRL | SWT.SHIFT | 'Z', false);
		new MenuItem(menu, SWT.SEPARATOR);
		addToTextSelectionGroup(this.createItem(Constants.Strings.copyMenu, Constants.Images.copy, SWT.CTRL|'C', false));
		addToTextSelectionGroup(this.createItem(Constants.Strings.cutMenu, Constants.Images.cut, SWT.CTRL|'X', false));
		addToEditorOpenCloseGroup(this.createItem(Constants.Strings.pasteMenu, Constants.Images.paste, SWT.CTRL|'V', false));
		
		//FXI for StyledText insert bug
		items.get(Constants.Strings.pasteMenu).setAccelerator(SWT.SHIFT|SWT.INSERT);
		
		addToTextSelectionGroup(this.createItem(Constants.Strings.deleteMenu, null, SWT.DEL, false));
		new MenuItem(menu, SWT.SEPARATOR);
		Menu lineSub = createSubMenu(Constants.Strings.lineHandleSubMenu);
		addToEditorOpenCloseGroup(this.createSubItem(lineSub, Constants.Strings.copyLineMenu, null, SWT.CTRL|'D', false));
		addToEditorOpenCloseGroup(this.createSubItem(lineSub, Constants.Strings.deleteLineMenu, null, SWT.CTRL|SWT.SHIFT|'D', false));
		addToTextSelectionGroup(this.createSubItem(lineSub, Constants.Strings.sortLinesMenu, null, SWT.CTRL|'T', false));
		addToTextSelectionGroup(this.createSubItem(lineSub, Constants.Strings.revolveLinesMenu, null, SWT.CTRL|SWT.SHIFT|'T', false));
		addToTextSelectionGroup(this.createSubItem(lineSub, Constants.Strings.splitLinesMenu, null, SWT.CTRL|SWT.ALT|'T', false));
		addToTextSelectionGroup(this.createSubItem(lineSub, Constants.Strings.joinLinesMenu, null, SWT.CTRL|'J', false));
		Menu caseSub = createSubMenu(Constants.Strings.caseConvertSubMenu);
		addToTextSelectionGroup(this.createSubItem(caseSub, Constants.Strings.convertToUpperCaseMenu, null, SWT.CTRL|'U', false));
		addToTextSelectionGroup(this.createSubItem(caseSub, Constants.Strings.convertToLowerCaseMenu, null, SWT.CTRL|'L', false));
		addToTextSelectionGroup(this.createSubItem(caseSub, Constants.Strings.convertToProperCaseMenu, null, SWT.CTRL|SWT.SHIFT|'U', false));
		addToTextSelectionGroup(this.createSubItem(caseSub, Constants.Strings.convertToSentenceCaseMenu, null, SWT.CTRL|SWT.ALT|'U', false));
		Menu indentSub = createSubMenu(Constants.Strings.indentSubMenu); 
		addToTextSelectionGroup(this.createSubItem(indentSub, Constants.Strings.indentMenu, null, SWT.TAB, false));
		addToTextSelectionGroup(this.createSubItem(indentSub, Constants.Strings.unindentMenu, null, SWT.SHIFT|SWT.TAB, false));
		addToTextSelectionGroup(this.createSubItem(indentSub, Constants.Strings.trimMenu, null, SWT.CTRL|'I', false));
		Menu commentSub = createSubMenu(Constants.Strings.commentSubMenu);
		addToEditorOpenCloseGroup(this.createSubItem(commentSub, Constants.Strings.commentMenu, null, SWT.CTRL|'Q', false));
		addToEditorOpenCloseGroup(this.createSubItem(commentSub, Constants.Strings.uncommentMenu, null, SWT.CTRL|SWT.SHIFT|'Q', false));
		addToEditorOpenCloseGroup(this.createSubItem(commentSub, Constants.Strings.changeCommentMenu, null, SWT.CTRL|SWT.ALT|'Q', false));
		new MenuItem(menu, SWT.SEPARATOR);
		addToEditorOpenCloseGroup(this.createItem(Constants.Strings.dateTimeMenu, null, SWT.F3, false));
		new MenuItem(menu, SWT.SEPARATOR);
		addToEditorOpenCloseGroup(this.createItem(Constants.Strings.selectAllMenu, null, SWT.CTRL|'A', false));
		new MenuItem(menu, SWT.SEPARATOR);
		addToEditorOpenCloseGroup(this.createItem(Constants.Strings.encodingMenu, null, SWT.CTRL|'E', false));
		new MenuItem(menu, SWT.SEPARATOR);
		addToEditorOpenCloseGroup(this.createItem(Constants.Strings.gotoLineMenu, null, SWT.CTRL|'G', false));
	}
	
	public MenuItem createSubItem(Menu parent, String text, Image image, int acc, boolean enable) {
		var item = new MenuItem(parent, SWT.PUSH);
		item.setText(text);
		item.setAccelerator(acc);
		item.setEnabled(enable);
		if (image!=null)
			item.setImage(image);
		item.addSelectionListener(listener);
		items.put(text, item);
		return item;
	}
	
	public Menu createSubMenu(String text) {
		var parentMenu = new Menu(menu.getShell(), SWT.DROP_DOWN);
		var item = new MenuItem(menu, SWT.CASCADE);
		item.setMenu(parentMenu);
		item.setText(text);
		return parentMenu;
	}
}

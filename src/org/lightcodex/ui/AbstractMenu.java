package org.lightcodex.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public abstract class AbstractMenu {
	
	protected HashMap<String, MenuItem> items;
	protected Menu menu;
	protected MenuItem menuItem;
	protected SelectionListener listener;
	
	private List<MenuItem> changeOnTextSelectionGroup;
	private List<MenuItem> changeOnEditorOpenCloseGroup;
	
	public AbstractMenu(Menu menuBar) {
		menu     = new Menu(menuBar.getShell(), SWT.DROP_DOWN);
		menuItem = new MenuItem(menuBar, SWT.CASCADE);
		items = new HashMap<>();
		
		changeOnTextSelectionGroup   = new ArrayList<>();
		changeOnEditorOpenCloseGroup = new ArrayList<>();
		
		menuItem.setMenu(menu);
	}
	
	protected void addToTextSelectionGroup(MenuItem item) {
		changeOnTextSelectionGroup.add(item);
	}
	
	protected void addToEditorOpenCloseGroup(MenuItem item) {
		changeOnEditorOpenCloseGroup.add(item);
	}
	
	public void enableTextSelectionGroup(boolean enable) {
		for (var item : changeOnTextSelectionGroup) {
			item.setEnabled(enable);
		}
	}
	
	public void enableEditorOpenCloseGroup(boolean enable) {
		for (var item : changeOnEditorOpenCloseGroup) {
			item.setEnabled(enable);
		}
	}
	
	public void enableItem(String itemText) {
		MenuItem item = items.get(itemText);
		if (!item.isEnabled()) {
			item.setEnabled(true);
		}
	}
	
	public void disableItem(String itemText) {
		MenuItem item = items.get(itemText);
		if (item.isEnabled()) {
			item.setEnabled(false);
		}
	}
	
	protected MenuItem createItem(String text, Image image, int accel, boolean enable) {
		var item = new MenuItem(menu, SWT.PUSH);
		item.setText(text);
		item.setAccelerator(accel);
		
		if (image != null)
			item.setImage(image);
		
		item.addSelectionListener(listener);
		item.setEnabled(enable);
		
		items.put(item.getText(), item);
		return item;
	}
}

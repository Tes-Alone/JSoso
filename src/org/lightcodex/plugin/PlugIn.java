package org.lightcodex.plugin;

import org.lightcodex.ui.PlugInMenu;

public abstract class PlugIn {
	
	private String name;
	
	public PlugIn(String name) {
		if (name == null) {
			throw new NullPointerException();
		}
		if (name.isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.name = name;
	}
	
	public abstract void onAppCreate(PlugInMenu plugInMenu);
	public abstract void onAppDispose();
	public abstract String getPlugInInfo();
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return getPlugInInfo();
	}
	
	
}

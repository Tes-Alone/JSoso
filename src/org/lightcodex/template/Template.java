package org.lightcodex.template;

import java.io.File;

public class Template {
	private File template;
	
	public Template(File file) {
		template = file;
	}
	
	public String toString() {
		return template.getName();
	}
	
	public File getFile() {
		return template;
	}

	public String getExtension() {
		String text = template.getName(); 
		return text.substring(text.lastIndexOf("."));
	}
}

package org.lightcodex.template;

import java.io.File;
import java.util.Locale;

import org.lightcodex.Constants;
import org.llogger.LLogger;

public class TemplateManager {
	
	private Template[] templates;
	
	public TemplateManager(Locale locale) {
		File[] files = null;
		if (locale == Locale.CHINESE) {
			files = new File(Constants.Homes.templateHome+"zh"+File.separator).listFiles();
		} else {
			files = new File(Constants.Homes.templateHome+"en"+File.separator).listFiles(); 
		}
		if (files == null) {
			files = new File[0];
			LLogger.getInstance().logWarning("Template info load failed!");
		} else {
			templates = new Template[files.length];
			for (int i=0; i<files.length; i++) {
				templates[i] = new Template(files[i]);
			}
		}
	}
	
	public Template[] listTemplates() {
		return templates;
	}
}

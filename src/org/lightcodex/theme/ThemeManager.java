package org.lightcodex.theme;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.lightcodex.Constants;

public class ThemeManager {
	
	private static List<Theme> allTheme;
	
	private ThemeManager() {}
	

	public static List<Theme> listAll() {
		allTheme = new ArrayList<Theme>();
		allTheme.add(new DefaultTheme());
		File themeHome = new File(Constants.Homes.themeHome);
		File[] files = themeHome.listFiles();
		for (File file : files) {
			String fileName = file.getName(); 
			if (fileName.endsWith(".xml")) {
				allTheme.add(new CustomTheme(
						fileName.substring(0, fileName.lastIndexOf("."))));
			}
		}
		return allTheme;
	}
	
	public static int indexOf(Theme theme) {
		return allTheme.indexOf(theme);
	}
	
	public static void remove(String themeName) {
		String themePath = Constants.Homes.themeHome + themeName + ".xml";
		new File(themePath).delete();
	}
}

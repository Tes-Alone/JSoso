package org.lightcodex.theme;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.lightcodex.Constants;
import org.llogger.LLogger;
import org.lxml.LXMLDocument;
import org.lxml.node.Node;

public class CustomTheme implements Theme {
	
	private Node root;
	private String themeName;
	
	public CustomTheme(String themeName) {
		init(themeName);
	}
	
	public CustomTheme() {
		root = new Node("theme");
	}
	
	private void init(String themeName) {
		defaultTheme     = new DefaultTheme();
		this.themeName   = themeName;
		String themePath = Constants.Homes.themeHome + this.themeName + ".xml";
		root = new LXMLDocument(themePath).parse().getRoot();
	}
	
	private static final String backAttr = "background";
	private static final String foreAttr = "foreground";
	private static final String selBackAttr = "sel-background";
	private static final String selForeAttr = "sel-foreground";
	private static final String fontStyleAttr = "font-style";
	private static final String colorAttr = "color";
	private static final String nameAttr = "name";
	
	private static final String editorFontTag = "editor-font";
	private static final String highLightLineTag = "highlight-line";
	private static final String highLightWordTag = "highlight-word";
	private static final String editAreaTag = "editarea";
	private static final String lineMarginTag = "line-margin";
	private static final String wordFixMarginTag = "wordfix-margin";
	private static final String toolbarTag = "toolbar";
	private static final String statusbarTag = "statusbar";
	private static final String proExplorerTag = "project-explorer";
	private static final String editorFolderTag = "editor-folder";
	private static final String assistPanelTag = "assist-panel";
	private static final String buttonTag = "button";
	private static final String defaultButtonTag = "default-button";
	private static final String inputTextTag = "input-text";
	private static final String comboTag = "combo";
	private static final String listTag = "list";
	private static final String labelTag = "label";
	private static final String compositeTag = "composite";
	private static final String tabFolderTag = "tab-folder";
	private static final String groupTag = "group";
	private static final String treeTag = "tree";
	
	private static final String tokenTag = "token";
	private static final String styleTag = "style";
	
	private FontData getFontData(String tag, String attrName) {
		try {
			String value = root.getNodeList().getNodeByTag(tag).getAttrMap().getAttrValue(attrName); 
			return parseFontData(value);
		} catch (Exception e) {
			LLogger.getInstance().
				logWarning("Custom theme info load failed, program use the default theme.");
			return null;
		}
	}
	
	private FontData parseFontData(String value) throws Exception {
		String[] tmp = value.split(":");
		if (tmp.length != 3) throw new Exception();
		for (int i=0; i<tmp.length; i++) {
			tmp[i] = tmp[i].trim();
		}
		int style = parseFontStyle(tmp[2]); 
		return new FontData(tmp[0], Integer.parseInt(tmp[1]), style);
	}
	
	private RGB getRGB(String tag, String attrName) {
		try {
			String value = root.getNodeList().getNodeByTag(tag).getAttrMap().getAttrValue(attrName); 
			return parseRGB(value);
		} catch (Exception e) {
			LLogger.getInstance().
				logWarning("Custom theme info load failed, program use the default theme.");
			return null;
		}
	}
	
	private RGB getTokenRGB(int index) {
		try {
			String value = root.getNodeList().getNodeByTag(tokenTag)
								.getNodeList().getNodeByTag(styleTag+index).getAttrMap()
									.getAttrValue(colorAttr);
			return parseRGB(value);
		} catch (Exception e) {
			//e.printStackTrace();
			LLogger.getInstance().
				logWarning("Custom theme info load failed, program use the default theme.");
			return null;
		}
	}

	private RGB parseRGB(String value) throws Exception {
		String[] tmp = value.split(":");
		if (tmp.length != 3) throw new Exception();
		for (int i=0; i<tmp.length; i++) {
			tmp[i] = tmp[i].trim();
			if (tmp[i].isEmpty()) throw new Exception();
		}
		return new RGB(Integer.parseInt(tmp[0]), 
				Integer.parseInt(tmp[1]), 
					Integer.parseInt(tmp[2]));
	}

	private DefaultTheme defaultTheme;
	
	@Override
	public FontData getEditorFont() {
		FontData data = getFontData(editorFontTag, nameAttr);
		if (data == null) return defaultTheme.getEditorFont();
		return data;
	}
	
	private String fontDataToString(FontData data) {
		if (data == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(data.getName() + ":");
		sb.append(data.getHeight() + ":");
		sb.append(fontStyleToString(data.getStyle()));
		return sb.toString();
	}
	
	private String rgbToString(RGB rgb) {
		if (rgb == null) return null;
		return rgb.red + ":" + rgb.green + ":" + rgb.blue;
	}
	
	private String fontStyleToString(int style) {
		char bold   = ((style&SWT.BOLD)!=0) ? '1' : '0';
		char italic = ((style&SWT.ITALIC)!=0) ? '1' : '0';
		return bold + "" + italic;
	}
	
	private void setCustomThemeInfo(String tag, String attrName, String attrValue) {
		try {
			Node node = root.getNodeList().getNodeByTag(tag);
			if (node == null) {
				node = root.getNodeList().addNodeByTag(tag); 
			}
			node.getAttrMap().addAttr(attrName, attrValue);
		} catch (Exception e) {
			LLogger.getInstance().logWarning("Custom theme info set failed.");
		}
	}
	
	private void setTokenRGB(int index, String rgb) {
		try {
			Node node = root.getNodeList().getNodeByTag(tokenTag);
			if (node == null) {
				node = root.getNodeList().addNodeByTag(tokenTag);
			}
			Node style = node.getNodeList().getNodeByTag(styleTag+index);
			if (style == null) {
				style = node.getNodeList().addNodeByTag(styleTag + index);
			}
			style.getAttrMap().addAttr(colorAttr, rgb);
		} catch (Exception e) {
			LLogger.getInstance().logWarning("Custom theme info set failed.");
		}
	}
	
	private void setTokenStyle(int index, String fontStyle) {
		try {
			Node node = root.getNodeList().getNodeByTag(tokenTag);
			if (node == null) {
				node = root.getNodeList().addNodeByTag(tokenTag);
			}
			Node style = node.getNodeList().getNodeByTag(styleTag + index);
			if (style == null) {
				style = node.getNodeList().addNodeByTag(styleTag + index);
			}
			style.getAttrMap().addAttr(fontStyleAttr, fontStyle);
		} catch (Exception e) {
			LLogger.getInstance().logWarning("Custom theme info set failed.");
		}
	}
	
	public void setEditorFont(FontData data) {
		setCustomThemeInfo(editorFontTag, nameAttr, fontDataToString(data));
	}
	
	@Override
	public RGB getHighLightLineColor() {
		RGB rgb = getRGB(highLightLineTag, colorAttr);
		if (rgb == null) return defaultTheme.getHighLightLineColor(); 
		return rgb;
	}
	
	public void setHighLightLineColor(RGB rgb) {
		setCustomThemeInfo(highLightLineTag, colorAttr, rgbToString(rgb));
	}
	
	@Override
	public RGB getHighLightWordColor() {
		RGB rgb = getRGB(highLightWordTag, colorAttr);
		if (rgb == null) return defaultTheme.getHighLightWordColor(); 
		return rgb;
	}
	
	public void setHighLightWordColor(RGB rgb) {
		setCustomThemeInfo(highLightWordTag, colorAttr, rgbToString(rgb));
	}
	
	@Override
	public RGB getEditAreaBackground() {
		RGB rgb = getRGB(editAreaTag, backAttr);
		if (rgb == null) return defaultTheme.getEditAreaBackground(); 
		return rgb;
	}
	
	public void setEditAreaBackground(RGB rgb) {
		setCustomThemeInfo(editAreaTag, backAttr, rgbToString(rgb));
	}

	@Override
	public RGB getEditAreaForeground() {
		RGB rgb = getRGB(editAreaTag, foreAttr);
		if (rgb == null) return defaultTheme.getEditAreaForeground(); 
		return rgb;
	}

	public void setEditAreaForeground(RGB rgb) {
		setCustomThemeInfo(editAreaTag, foreAttr, rgbToString(rgb));
	}
	
	@Override
	public RGB getLineMarginBackground() {
		RGB rgb = getRGB(lineMarginTag, backAttr);
		if (rgb == null) return defaultTheme.getLineMarginBackground(); 
		return rgb;
	}
	
	public void setLineMarginBackground(RGB rgb) {
		setCustomThemeInfo(lineMarginTag, backAttr, rgbToString(rgb));
	}

	@Override
	public RGB getLineMarginForeground() {
		RGB rgb = getRGB(lineMarginTag, foreAttr);
		if (rgb == null) return defaultTheme.getLineMarginForeground(); 
		return rgb;
	}
	
	public void setLineMarginForeground(RGB rgb) {
		setCustomThemeInfo(lineMarginTag, foreAttr, rgbToString(rgb));
	}
	
	@Override
	public RGB getWordFixMarginBackground() {
		RGB rgb = getRGB(wordFixMarginTag, backAttr);
		if (rgb == null) return defaultTheme.getWordFixMarginBackground(); 
		return rgb;
	}
	
	public void setWordFixMarginBackground(RGB rgb) {
		setCustomThemeInfo(wordFixMarginTag, backAttr, rgbToString(rgb));
	}


	@Override
	public RGB getWordFixMarginForeground() {
		RGB rgb = getRGB(wordFixMarginTag, foreAttr);
		if (rgb == null) return defaultTheme.getWordFixMarginForeground(); 
		return rgb;
	}
	
	public void setWordFixMarginForeground(RGB rgb) {
		setCustomThemeInfo(wordFixMarginTag, foreAttr, rgbToString(rgb));
	}
	
	@Override
	public RGB getToolBarBackground() {
		RGB rgb = getRGB(toolbarTag, backAttr);
		if (rgb == null) return defaultTheme.getToolBarBackground(); 
		return rgb;
	}

	public void setToolBarBackground(RGB rgb) {
		setCustomThemeInfo(toolbarTag, backAttr, rgbToString(rgb));
	}

	@Override
	public RGB getStatusBarBackground() {
		RGB rgb = getRGB(statusbarTag, backAttr);
		if (rgb == null) return defaultTheme.getStatusBarBackground(); 
		return rgb;
	}
	
	public void setStatusBarBackground(RGB rgb) {
		setCustomThemeInfo(statusbarTag, backAttr, rgbToString(rgb));
	}

	@Override
	public RGB getStatusBarForeground() {
		RGB rgb = getRGB(statusbarTag, foreAttr);
		if (rgb == null) return defaultTheme.getStatusBarForeground(); 
		return rgb;
	}
	
	public void setStatusBarForeground(RGB rgb) {
		setCustomThemeInfo(statusbarTag, foreAttr, rgbToString(rgb));
	}

	@Override
	public RGB getProjectExplorerBackground() {
		RGB rgb = getRGB(proExplorerTag, backAttr);
		if (rgb == null) return defaultTheme.getProjectExplorerBackground(); 
		return rgb;
	}
	
	public void setProjectExplorerBackground(RGB rgb) {
		setCustomThemeInfo(proExplorerTag, backAttr, rgbToString(rgb));
	}
	
	@Override
	public RGB getProjectExplorerForeground() {
		RGB rgb = getRGB(proExplorerTag, foreAttr);
		if (rgb == null) return defaultTheme.getProjectExplorerForeground(); 
		return rgb;
	}
	
	public void setProjectExplorerForeground(RGB rgb) {
		setCustomThemeInfo(proExplorerTag, foreAttr, rgbToString(rgb));
	}
	
	@Override
	public RGB getEditorFolderBackground() {
		RGB rgb = getRGB(editorFolderTag, backAttr);
		if (rgb == null) return defaultTheme.getEditorFolderBackground(); 
		return rgb;
	}

	public void setEditorFolderBackground(RGB rgb) {
		setCustomThemeInfo(editorFolderTag, backAttr, rgbToString(rgb));
	}

	@Override
	public RGB getEditorFolderForeground() {
		RGB rgb = getRGB(editorFolderTag, foreAttr);
		if (rgb == null) return defaultTheme.getEditorFolderForeground(); 
		return rgb;
	}

	public void setEditorFolderForeground(RGB rgb) {
		setCustomThemeInfo(editorFolderTag, foreAttr, rgbToString(rgb));
	}

	@Override
	public RGB getEditorFolderSelectionBackground() {
		RGB rgb = getRGB(editorFolderTag, selBackAttr);
		if (rgb == null) return defaultTheme.getEditorFolderSelectionBackground(); 
		return rgb;
	}

	public void setEditorFolderSelectionBackground(RGB rgb) {
		setCustomThemeInfo(editorFolderTag, selBackAttr, rgbToString(rgb));
	}

	@Override
	public RGB getEditorFolderSelectionForeground() {
		RGB rgb = getRGB(editorFolderTag, selForeAttr);
		if (rgb == null) return defaultTheme.getEditorFolderSelectionForeground(); 
		return rgb;
	}

	public void setEditorFolderSelectionForeground(RGB rgb) {
		setCustomThemeInfo(editorFolderTag, selForeAttr, rgbToString(rgb));
	}
	
	@Override
	public RGB getButtonBackground() {
		RGB rgb = getRGB(buttonTag, backAttr);
		if (rgb == null) return defaultTheme.getButtonBackground(); 
		return rgb;
	}
	
	public void setButtonBackground(RGB rgb) {
		setCustomThemeInfo(buttonTag, backAttr, rgbToString(rgb));
	}

	@Override
	public RGB getButtonForeground() {
		RGB rgb = getRGB(buttonTag, foreAttr);
		if (rgb == null) return defaultTheme.getButtonForeground(); 
		return rgb;
	}
	
	public void setButtonForeground(RGB rgb) {
		setCustomThemeInfo(buttonTag, foreAttr, rgbToString(rgb));
	}

	@Override
	public RGB getDefaultButtonBackground() {
		RGB rgb = getRGB(defaultButtonTag, backAttr);
		if (rgb == null) return defaultTheme.getDefaultButtonBackground(); 
		return rgb;
	}
	
	public void setDefaultButtonBackground(RGB rgb) {
		setCustomThemeInfo(defaultButtonTag, backAttr, rgbToString(rgb));
	}

	@Override
	public RGB getDefaultButtonForeground() {
		RGB rgb = getRGB(defaultButtonTag, foreAttr);
		if (rgb == null) return defaultTheme.getDefaultButtonForeground(); 
		return rgb;
	}
	
	public void setDefaultButtonForeground(RGB rgb) {
		setCustomThemeInfo(defaultButtonTag, foreAttr, rgbToString(rgb));
	}

	@Override
	public RGB getInputTextBackground() {
		RGB rgb = getRGB(inputTextTag, backAttr);
		if (rgb == null) return defaultTheme.getInputTextBackground(); 
		return rgb;
	}
	
	public void setInputTextBackground(RGB rgb) {
		setCustomThemeInfo(inputTextTag, backAttr, rgbToString(rgb));
	}
	
	@Override
	public RGB getInputTextForeground() {
		RGB rgb = getRGB(inputTextTag, foreAttr);
		if (rgb == null) return defaultTheme.getInputTextForeground(); 
		return rgb;
	}

	public void setInputTextForeground(RGB rgb) {
		setCustomThemeInfo(inputTextTag, foreAttr, rgbToString(rgb));
	}
	
	@Override
	public RGB getComboBackground() {
		RGB rgb = getRGB(comboTag, backAttr);
		if (rgb == null) return defaultTheme.getComboBackground(); 
		return rgb;
	}

	public void setComboBackground(RGB rgb) {
		setCustomThemeInfo(comboTag, backAttr, rgbToString(rgb));
	}

	@Override
	public RGB getComboForeground() {
		RGB rgb = getRGB(comboTag, foreAttr);
		if (rgb == null) return defaultTheme.getComboForeground(); 
		return rgb;
	}

	public void setComboForeground(RGB rgb) {
		setCustomThemeInfo(comboTag, foreAttr, rgbToString(rgb));
	}

	@Override
	public RGB getListBackground() {
		RGB rgb = getRGB(listTag, backAttr);
		if (rgb == null) return defaultTheme.getListBackground(); 
		return rgb;
	}
	
	public void setListBackground(RGB rgb) {
		setCustomThemeInfo(listTag, backAttr, rgbToString(rgb));
	}


	@Override
	public RGB getListForeground() {
		RGB rgb = getRGB(listTag, foreAttr);
		if (rgb == null) return defaultTheme.getListForeground(); 
		return rgb;
	}

	public void setListForeground(RGB rgb) {
		setCustomThemeInfo(listTag, foreAttr, rgbToString(rgb));
	}

	@Override
	public RGB getLabelBackground() {
		RGB rgb = getRGB(labelTag, backAttr);
		if (rgb == null) return defaultTheme.getLabelBackground(); 
		return rgb;
	}
	
	public void setLabelBackground(RGB rgb) {
		setCustomThemeInfo(labelTag, backAttr, rgbToString(rgb));
	}


	@Override
	public RGB getLabelForeground() {
		RGB rgb = getRGB(labelTag, foreAttr);
		if (rgb == null) return defaultTheme.getLabelForeground(); 
		return rgb;
	}
	
	public void setLabelForeground(RGB rgb) {
		setCustomThemeInfo(labelTag, foreAttr, rgbToString(rgb));
	}


	@Override
	public RGB getCompositeBackground() {
		RGB rgb = getRGB(compositeTag, backAttr);
		if (rgb == null) return defaultTheme.getCompositeBackground(); 
		return rgb;
	}
	
	public void setCompositeBackground(RGB rgb) {
		setCustomThemeInfo(compositeTag, backAttr, rgbToString(rgb));
	}

	@Override
	public RGB getTabFolderBackground() {
		RGB rgb = getRGB(tabFolderTag, backAttr);
		if (rgb == null) return defaultTheme.getTabFolderBackground(); 
		return rgb;
	}

	public void setTabFolderBackground(RGB rgb) {
		setCustomThemeInfo(tabFolderTag, backAttr, rgbToString(rgb));
	}

	@Override
	public RGB getTabFolderForeground() {
		RGB rgb = getRGB(tabFolderTag, foreAttr);
		if (rgb == null) return defaultTheme.getTabFolderForeground(); 
		return rgb;
	}
	
	public void setTabFolderForeground(RGB rgb) {
		setCustomThemeInfo(tabFolderTag, foreAttr, rgbToString(rgb));
	}


	@Override
	public RGB getTabFolderSelectionBackground() {
		RGB rgb = getRGB(tabFolderTag, selBackAttr);
		if (rgb == null) return defaultTheme.getTabFolderSelectionBackground(); 
		return rgb;
	}
	
	public void setTabFolderSelectionBackground(RGB rgb) {
		setCustomThemeInfo(tabFolderTag, selBackAttr, rgbToString(rgb));
	}


	@Override
	public RGB getTabFolderSelectionForeground() {
		RGB rgb = getRGB(tabFolderTag, selForeAttr);
		if (rgb == null) return defaultTheme.getTabFolderSelectionForeground(); 
		return rgb;
	}

	public void setTabFolderSelectionForeground(RGB rgb) {
		setCustomThemeInfo(tabFolderTag, selForeAttr, rgbToString(rgb));
	}

	@Override
	public RGB getGroupBackground() {
		RGB rgb = getRGB(groupTag, backAttr);
		if (rgb == null) return defaultTheme.getGroupBackground(); 
		return rgb;
	}

	public void setGroupBackground(RGB rgb) {
		setCustomThemeInfo(groupTag, backAttr, rgbToString(rgb));
	}

	@Override
	public RGB getGroupForeground() {
		RGB rgb = getRGB(groupTag, foreAttr);
		if (rgb == null) return defaultTheme.getGroupForeground(); 
		return rgb;
	}
	
	public void setGroupForeground(RGB rgb) {
		setCustomThemeInfo(groupTag, foreAttr, rgbToString(rgb));
	}
	
	@Override
	public RGB getTreeForeground() {
		RGB rgb = getRGB(treeTag, foreAttr);
		if (rgb == null) return defaultTheme.getTreeForeground(); 
		return rgb;
	}
	
	public void setTreeForeground(RGB rgb) {
		setCustomThemeInfo(treeTag, foreAttr, rgbToString(rgb));
	}

	@Override
	public RGB getTreeBackground() {
		RGB rgb = getRGB(treeTag, backAttr);
		if (rgb == null) return defaultTheme.getTreeBackground(); 
		return rgb;
	}
	
	public void setTreeBackground(RGB rgb) {
		setCustomThemeInfo(treeTag, backAttr, rgbToString(rgb));
	}

	private int getTokenFontStyle(int index) {
		try {
			String value = root.getNodeList().getNodeByTag(tokenTag)
								.getNodeList().getNodeByTag(styleTag+index).getAttrMap()
									.getAttrValue(fontStyleAttr);
			return parseFontStyle(value.trim());
		} catch (Exception e) {
			//e.printStackTrace();
			LLogger.getInstance().
				logWarning("Custom theme info load failed, program use the default theme.");
			return -1;
		}
	}

	private int parseFontStyle(String value) throws Exception {
		if (value.isEmpty() || !value.matches("[01][01]")) throw new Exception();
		return (value.charAt(0)=='0'?SWT.NORMAL:SWT.BOLD) | (value.charAt(1)=='0'?0:SWT.ITALIC);
	}
	
	@Override
	public String getThemeName() {
		return themeName;
	}

	@Override
	public boolean equals(Object obj) {
		Theme other = (Theme) obj;
		return other.getThemeName().equals(getThemeName());
	}
	
	public void saveAs(String themeName) {
		String themePath = Constants.Homes.themeHome + themeName + ".xml";
		if (new File(themePath).exists()) {
			throw new RuntimeException();
		}
		root.save(themePath);
	}

	@Override
	public RGB getTokenColor(int index) {
		if (index<0 || index>TOKEN_STYLE_MAX) {
			throw new IndexOutOfBoundsException();
		}
		RGB rgb = getTokenRGB(index); 
		return rgb==null?defaultTheme.getTokenColor(index):rgb;
	}
	
	public void addTokenColor(int index, RGB rgb) {
		if (index<0 || index>TOKEN_STYLE_MAX) {
			throw new IndexOutOfBoundsException();
		}
		setTokenRGB(index, rgbToString(rgb));
	}
	
	public void addTokenStyle(int index, int style) {
		setTokenStyle(index, fontStyleToString(style));
	}
	
	public void removeTokenStyle() {
		Node node = root.getNodeList().getNodeByTag(tokenTag);
		if (node != null) {
			root.getNodeList().removeNode(node);
		}
	}

	@Override
	public int getTokenStyle(int index) {
		if (index<0 || index>TOKEN_STYLE_MAX) {
			throw new IndexOutOfBoundsException();
		}
		int style = getTokenFontStyle(index); 
		return style==-1 ? defaultTheme.getTokenStyle(index) : style;
	}

	@Override
	public RGB getAssistPanelBackground() {
		RGB rgb = getRGB(assistPanelTag, backAttr);
		if (rgb == null) return defaultTheme.getAssistPanelBackground(); 
		return rgb;
	}
	
	public void setAssistPanelBackground(RGB rgb) {
		setCustomThemeInfo(assistPanelTag, backAttr, rgbToString(rgb));
	}

	@Override
	public RGB getAssistPanelForeground() {
		RGB rgb = getRGB(assistPanelTag, foreAttr);
		if (rgb == null) return defaultTheme.getAssistPanelForeground(); 
		return rgb;
	}
	
	public void setAssistPanelForeground(RGB rgb) {
		setCustomThemeInfo(assistPanelTag, foreAttr, rgbToString(rgb));
	}

	@Override
	public RGB getAssistPanelSelectionBackground() {
		RGB rgb = getRGB(assistPanelTag, selBackAttr);
		if (rgb == null) return defaultTheme.getAssistPanelSelectionBackground(); 
		return rgb;
	}
	
	public void setAssistPanelSelectionBackground(RGB rgb) {
		setCustomThemeInfo(assistPanelTag, selBackAttr, rgbToString(rgb));
	}

	@Override
	public RGB getAssistPanelSelectionForeground() {
		RGB rgb = getRGB(assistPanelTag, selForeAttr);
		if (rgb == null) return defaultTheme.getAssistPanelSelectionForeground(); 
		return rgb;
	}
	
	public void setAssistPanelSelectionForeground(RGB rgb) {
		setCustomThemeInfo(assistPanelTag, selForeAttr, rgbToString(rgb));
	}
}

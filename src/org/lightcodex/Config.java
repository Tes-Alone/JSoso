package org.lightcodex;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

import org.lightcodex.theme.CustomTheme;
import org.lightcodex.theme.DefaultTheme;
import org.lightcodex.theme.Theme;
import org.llogger.LLogger;
import org.lxml.LXMLDocument;
import org.lxml.node.Node;

/**
 * 此包负责 LightCodeX 的一般配置管理.
 * 
 * */

public class Config {
	
	private static final String cfgDefaultText = "<lightcodex-cfg>\r\n" + 
			"\r\n" + 
			"<theme name=\"default\"/>\r\n" + 
			"<locale value=\"english\"/>\r\n" + 
			"\r\n" + 
			"<tab-width value=\"4\"/>\r\n" + 
			"<auto-indent value=\"false\"/>\r\n" + 
			"<auto-complete-word value=\"false\"/>\r\n" + 
			"<show-vertical-edge value=\"false\"/>\r\n" + 
			"<auto-complete-pair value=\"false\"/>\r\n" +
			"<auto-complete-tag-pair value=\"false\"/>\r\n" +
			"<balance-pair value=\"false\"/>" + 
			"<highlight-line value=\"false\"/>\r\n" + 
			"<highlight-word value=\"false\"/>\r\n" + 
			"\r\n" + 
			"</lightcodex-cfg>";
	
	private static final String cfgRootTag = "lightcodex-cfg";
	private static final String autoIndentTag = "auto-indent";
	private static final String tabWidthTag = "tab-width";
	private static final String showVerticalEdgeTag = "show-vertical-edge";
	private static final String autoCompletePairTag = "auto-complete-pair";
	private static final String autoCompleteTagPairTag = "auto-complete-tag-pair";
	private static final String braceMatchTag = "match-brace";
	private static final String highLightLineTag = "highlight-line";
	private static final String highLightWordTag = "highlight-word";
	private static final String completeWordTag = "auto-complete-word";
	private static final String localeTag = "locale";
	private static final String themeTag = "theme";
	
	private boolean autoIndent;
	private boolean showVerticalEdge;
	private boolean autoCompletePair;
	private boolean autoCompleteTagPair;
	private boolean braceMatch;
	private boolean highLightLine;
	private boolean highLightWord;
	private boolean completeWord;
	
	private int tabWidth;
	
	private Theme  theme;
	private Locale locale;
	
	private Node root;
	
	private static Config self;
	
	private Config() {
		checkConfigFile();
		parse();
		loadTheme();
		setOthers();
	}
	
	private void setOthers() {
		try {
			String value = getSetting(tabWidthTag);
			tabWidth = getTabWidth(value);
			value = getSetting(autoIndentTag);
			autoIndent = getBoolean(value);
			value = getSetting(showVerticalEdgeTag);
			showVerticalEdge = getBoolean(value);
			value = getSetting(autoCompletePairTag);
			autoCompletePair = getBoolean(value);
			value = getSetting(autoCompleteTagPairTag);
			autoCompleteTagPair = getBoolean(value);
			value = getSetting(braceMatchTag);
			braceMatch = getBoolean(value);
			value = getSetting(highLightLineTag);
			highLightLine = getBoolean(value);
			value = getSetting(highLightWordTag);
			highLightWord = getBoolean(value);
			value = getSetting(completeWordTag);
			completeWord = getBoolean(value);
			value = getSetting(localeTag);
			locale = getLocale(value);
		} catch (Exception e) {
			resetOthers();
			LLogger.getInstance().
				logWarning("Configure info load failed, program use default configure.");
			
		}
	}
	
	private int getTabWidth(String value) {
		int width = Integer.parseInt(value);
		return width<1 ? 1 : width;
	}

	private Locale getLocale(String value) {
		if (value.equalsIgnoreCase("English") || value.equalsIgnoreCase("英文")) {
			return Locale.ENGLISH;
		} else if (value.equalsIgnoreCase("Chinese") || value.equalsIgnoreCase("中文")) {
			return Locale.CHINESE;
		} else {
			return Locale.ENGLISH;
		}
	}
	
	private boolean getBoolean(String value) {
		return Boolean.parseBoolean(value);
	}

	private String getSetting(String setting) {
		Node node = root.getNodeList().getNodeByTag(setting);
		return node.getAttrMap().getAttrValue("value");
	}
	
	/**
	 * 重置除 主题 之外的所有设置.
	 * */
	public void resetOthers() {
		this.autoIndent = false;
		this.tabWidth   = 4;
		this.showVerticalEdge = false;
		this.autoCompletePair = false;
		this.autoCompleteTagPair = false;
		this.braceMatch = false;
		this.highLightLine = false;
		this.highLightWord = false;
		this.completeWord = false;
		this.locale = Locale.ENGLISH;
	}
	
	/**
	 * 设置默认主题.
	 * */
	public void setDefaultTheme() {
		theme = new DefaultTheme();
	}

	private void parse() {
		String filePath = Constants.Homes.configHome + "config.xml";
		try {
			root = new LXMLDocument(filePath).parse().getRoot();
		} catch (RuntimeException e) {
			root = new Node(cfgRootTag);
			LLogger.getInstance().logWarning("Config info load failed!");
		}
	}

	private void loadTheme() {
		try {
			Node themeNode = root.getNodeList().getNodeByTag(themeTag);
			String themeName = themeNode.getAttrMap().getAttrValue("name");
			if (themeName.equalsIgnoreCase("Default")) {
				theme = new DefaultTheme();
			} else {
				theme = new CustomTheme(themeName);
			}
		} catch (RuntimeException e) {
			theme = new DefaultTheme();
			LLogger.getInstance().
				logWarning("Theme file load failed, program use the default theme.");
		}
	}
	
	/**
	 * 获取设定的主题
	 * */
	public Theme getTheme() {
		return theme;
	}
	
	/**
	 * 设定主题.
	 * 
	 * @param themeName 主题名.
	 * */
	public void setTheme(String themeName) {
		try {
			Node node = root.getNodeList().getNodeByTag(themeTag);
			node.getAttrMap().addAttr("name", themeName);
		} catch (Exception e) {
			Node node = root.getNodeList().addNodeByTag(themeTag);
			node.getAttrMap().addAttr("name", themeName);
		}
	}
	
	/**
	 * 设定本地信息.
	 * 
	 * 目前只支持两种本地信息, 一种为 English/英文, 一种为 Chinese/中文.
	 * 
	 * 如果 locale 不为上述值, 设定为 English.
	 * 
	 * @param locale 本地信息.
	 * */
	public void setLocale(String locale) {
		setSetting(localeTag, locale);
	}
	
	/**
	 * 设定是否自动缩进.
	 * 
	 * @param b 布尔值
	 * */
	public void setAutoIndent(boolean b) {
		setSetting(autoIndentTag, Boolean.toString(b));
	}
	
	/**
	 * 设定制表符宽度.
	 * 
	 * 如果设定值为 0 或负值, 函数不会做任何处理, 但在从配置文件中读取设定时, 会进行调整.
	 * 
	 * @param width 宽度
	 * */
	public void setTabWidth(int width) {
		setSetting(tabWidthTag, Integer.toString(width));
	}
	
	/**
	 * 设定是否显示垂直边.
	 * 
	 * @param b 布尔值
	 * 
	 * */
	public void setShowVerticalEdge(boolean b) {
		setSetting(showVerticalEdgeTag, Boolean.toString(b));
	}
	
	/**
	 * 设定是否完成括号.
	 * 
	 * @param b 布尔值
	 * */
	public void setAutoCompletePair(boolean b) {
		setSetting(autoCompletePairTag, Boolean.toString(b));
	}
	
	public void setAutoCompleteTagPair(boolean b) {
		setSetting(autoCompleteTagPairTag, Boolean.toString(b));
	}
	
	/**
	 * 设定是否匹配括号.
	 * 
	 * @param b 布尔值
	 * */
	public void setBraceMatch(boolean b) {
		setSetting(braceMatchTag, Boolean.toString(b));
	}
	
	/**
	 * 设定是否高亮当前行.
	 * 
	 * @param b 布尔值
	 * 
	 * */
	public void setHighLightLine(boolean b) {
		setSetting(highLightLineTag, Boolean.toString(b));
	}
	
	/**
	 * 设定是否高亮单词.
	 * 
	 * @param b 布尔值
	 * */
	public void setHighLightWord(boolean b) {
		setSetting(highLightWordTag, Boolean.toString(b));	
	}
	
	/**
	 * 设定是否开启随笔提示.
	 * 
	 * @param b 布尔值
	 * 
	 * */
	public void setAutoCompleteWord(boolean b) {
		setSetting(completeWordTag, Boolean.toString(b));
	}
	
	private void setSetting(String tag, String setting) {
		try {
			Node node = root.getNodeList().getNodeByTag(tag);
			node.getAttrMap().addAttr("value", setting);
		} catch (Exception e) {
			Node node = root.getNodeList().addNodeByTag(tag);
			node.getAttrMap().addAttr("value", setting);
		}
	}

	/**
	 * 获取本地信息.
	 * 
	 * 目前只支持 Locale.ENGLISH 和 Locale.CHINESE.
	 * 
	 * 默认为 Locale.ENGLISH.
	 * 
	 * @return 本地信息
	 * */
	public Locale getLocale() {
		return locale;
	}
	
	/**
	 * 获取制表符宽度.
	 * 
	 * @return 制表符宽度. 
	 * */
	public int getTabWidth() {
		return tabWidth;
	}
	
	/**
	 * 获取是否自动缩进信息.
	 * 
	 * @return 自动缩进配置.
	 * */
	public boolean isAutoIndent() {
		return autoIndent;
	}
	
	/**
	 * 获取是否开启随笔提示信息.
	 * 
	 * @return 随笔提示配置.
	 * */
	public boolean isAutoCompleteWord() {
		return completeWord;
	}
	
	/**
	 * 获取是否显示垂直边配置.
	 * 
	 * @return 垂直变配置.
	 * */
	public boolean isShowVerticalEdge() {
		return showVerticalEdge;
	}
	
	public boolean isAutoCompletePair() {
		return autoCompletePair;
	}
	
	public boolean isAutoCompleteTagPair() {
		return autoCompleteTagPair;
	}
	
	public boolean isBraceMatch() {
		return braceMatch;
	}
	
	public boolean isHighLightLine() {
		return highLightLine;
	}
	
	public boolean isHighLightWords() {
		return highLightWord;
	}
	
	public void save() {
		String filePath = Constants.Homes.configHome + "config.xml";
		root.save(filePath);
	}
	
	public static synchronized Config getInstance() {
		if (self == null) {
			self = new Config();
		}
		return self;
	}

	private void checkConfigFile() {
		String filePath = Constants.Homes.configHome + "config.xml";
		File file = new File(filePath);
		if (!file.exists()) {
			LLogger.getInstance().
				logWarning("Configure file not exists, program will create a new configure file.");
			try {
				FileWriter out = new FileWriter(file);
				out.write(cfgDefaultText);
				out.close();
			} catch (IOException e) {
				setDefaultTheme();
				resetOthers();
				LLogger.getInstance().
					logWarning("New configure file create failed, program use the default configure.");
			}
		}
	}
}

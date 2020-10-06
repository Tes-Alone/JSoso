package org.lightcodex;

import java.io.File;
import java.util.Locale;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class Constants {
	
	public static class Homes {
		//public static final String imagesHome = "F:\\JavaTest\\LightCodeX\\images\\";
		public static final String imagesHome = "images" + File.separator;
		//public static final String configHome = "F:\\JavaTest\\LightCodeX\\config\\";
		public static final String configHome = "config" + File.separator;
		//public static final String themeHome = "F:\\JavaTest\\LightCodeX\\config\\theme\\";
		public static final String themeHome = "config" + File.separator + "theme" + File.separator;
		//public static final String templateHome = "F:\\JavaTest\\LightCodeX\\config\\template\\";
		public static final String templateHome = "config" + File.separator + "template" + File.separator;
		//public static final String pluginHome = "F:\\JavaTest\\LightCodeX\\plugin\\";
		public static final String pluginHome = "plugin" + File.separator;
		
		public static final String completeWordHome = "config" + File.separator + "complete_word" + File.separator;
	}
	
	public static class Images {
		public static final Image newSource = new Image(Display.getCurrent(), Homes.imagesHome + "newSource.png");
		public static final Image open = new Image(Display.getCurrent(),Homes.imagesHome + "open.png");
		public static final Image save = new Image(Display.getCurrent(),Homes.imagesHome + "save.png");
		public static final Image saveAs = new Image(Display.getCurrent(),Homes.imagesHome + "saveAs.png");
		public static final Image close = new Image(Display.getCurrent(),Homes.imagesHome + "close.png");
		public static final Image config = new Image(Display.getCurrent(),Homes.imagesHome + "config.png");
		public static final Image exit = new Image(Display.getCurrent(),Homes.imagesHome + "exit.png");
		
		public static final Image explorer = new Image(Display.getCurrent(),Homes.imagesHome + "explorer.gif");
		public static final Image project = new Image(Display.getCurrent(),Homes.imagesHome + "project.png");
		public static final Image folder = new Image(Display.getCurrent(),Homes.imagesHome + "folder.png");
		public static final Image source = new Image(Display.getCurrent(),Homes.imagesHome + "source.png"); 
		
		public static final Image saved = new Image(Display.getCurrent(),Homes.imagesHome + "saved.png");
		public static final Image unsaved = new Image(Display.getCurrent(),Homes.imagesHome + "changed.png");
		
		public static final Image undo = new Image(Display.getCurrent(),Homes.imagesHome + "undo.png");
		public static final Image redo = new Image(Display.getCurrent(),Homes.imagesHome + "redo.png");
		public static final Image copy = new Image(Display.getCurrent(),Homes.imagesHome + "copy.png");
		public static final Image cut = new Image(Display.getCurrent(),Homes.imagesHome + "cut.png");
		public static final Image paste = new Image(Display.getCurrent(),Homes.imagesHome + "paste.png");
		
		public static final Image find = new Image(Display.getCurrent(), Homes.imagesHome + "find.png");
		
		public static final Image terminal = new Image(Display.getCurrent(), Homes.imagesHome + "terminal.png");
		public static final Image logo = new Image(Display.getCurrent(), Homes.imagesHome + "logo.png");
		public static final Image splash = new Image(Display.getCurrent(), Homes.imagesHome + "splash.png");
		public static final Image about = new Image(Display.getCurrent(), Homes.imagesHome + "about.png");
		
		public static void disposeAll() {
			newSource.dispose();
			open.dispose();
			save.dispose();
			saveAs.dispose();
			close.dispose();
			config.dispose();
			exit.dispose();
			explorer.dispose();
			project.dispose();
			folder.dispose();
			source.dispose();
			saved.dispose();
			unsaved.dispose();
			undo.dispose();
			redo.dispose();
			copy.dispose();
			cut.dispose();
			paste.dispose();
			find.dispose();
			terminal.dispose();
			logo.dispose();
			splash.dispose();
			about.dispose();
		}
	}
	
	public static class Strings {		
		public static final String assistPrefix = "siyv";
		
		public static  String fileMenu = "&File";
		public static  String newMenu  = "&New";
		public static  String newProjectMenu = "&New Project...\tCtrl+N";
		public static  String newFolderMenu = "&New Folder...\tCtrl+Alt+N";
		public static  String newSourceMenu = "&New Source...\tCtrl+Shift+N";
		public static  String openMenu = "&Open...\tCtrl+O";
		public static  String copyFromFolderMenu = "&Copy From Folder...\tCtrl+Shift+O";
		public static  String saveMenu = "&Save\tCtrl+S";
		public static  String saveAsMenu = "&Save As...\tCtrl+Shift+S";
		public static  String closeMenu = "&Close\tCtrl+W";
		public static  String closeAllMenu = "&Close All\tCtrl+Shift+W";
		public static  String remvoeProjectMenu = "&Remove Project";
		public static  String configMenu = "&Configure...";
		public static  String exitMenu = "E&xit\tAlt+X";
		
		public static  String editMenu = "&Edit";
		public static  String undoMenu = "&Undo\tCtrl+Z";
		public static  String redoMenu = "&Redo\tCtrl+Shift+Z";
		public static  String copyMenu = "&Copy\tCtrl+C";
		public static  String cutMenu = "&Cut\tCtrl+X";
		public static  String pasteMenu = "&Paste\tCtrl+V";
		public static  String deleteMenu = "&Delete\tDel";
		public static  String lineHandleSubMenu = "&Line Handle";
		public static  String copyLineMenu = "&Copy Line\tCtrl+D";
		public static  String deleteLineMenu = "&Delete Line\tCtrl+Shift+D";
		public static  String sortLinesMenu = "&Sort Lines\tCtrl+T";
		public static  String revolveLinesMenu = "&Revolve Lines\tCtrl+Shift+T";
		public static  String splitLinesMenu = "&Split Lines\tCtrl+Alt+T";
		public static  String joinLinesMenu = "&Join Lines\tCtrl+J";
		public static  String caseConvertSubMenu = "&Convert Case To";
		public static  String convertToUpperCaseMenu  = "&UPPERCASE\tCtrl+U";
		public static  String convertToLowerCaseMenu  = "&lowercase\tCtrl+L";
		public static  String convertToProperCaseMenu = "&Proper case\tCtrl+Shift+U";
		public static  String convertToSentenceCaseMenu = "&Sentence case\tCtrl+Alt+U";
		public static  String indentSubMenu = "&Indent";
		public static  String indentMenu = "&Indent\tTab";
		public static  String unindentMenu = "&Unindent\tShift+Tab";
		public static  String trimMenu = "&Trim\tCtrl+I";
		public static  String commentSubMenu = "&Comment";
		public static  String commentMenu = "&Comment\tCtrl+Q";
		public static  String uncommentMenu = "&Uncomment\tCtrl+Shift+Q";
		public static  String changeCommentMenu = "&Change Comment\tCtrl+Alt+Q";
		public static  String dateTimeMenu = "&Date/Time...\tF3";
		public static  String encodingMenu = "&Encoding...\tCtrl+E";
		public static  String selectAllMenu = "&Select All\tCtrl+A";
		public static  String gotoLineMenu = "&Goto Line...\tCtrl+G";
		
		public static  String searchMenu = "&Search";
		public static  String findReplaceMenu = "&Find and Replace...\tCtrl+F";
		public static  String regexMenu = "&Regex...\tCtrl+R";
		public static  String replaceInSelectionMenu = "&Replace in Selection...\tCtrl+Alt+F";
		public static  String findInFolderMenu = "&Find in Folder...\tCtrl+Shift+F";
		public static  String findFileMenu = "&Find File...";
		
		public static  String terminalMenu = "&Terminal";
		public static  String openTerminalMenu = "&Open Terminal...\tCtrl+Alt+T";
		public static  String openExplorerMenu = "&Open Explorer...";
		public static  String openConfigFolderMenu = "&Open Config Folder...";
		public static  String openPlugInFolderMenu = "&Open PlugIn Folder..."; 
		
		public static String langMenu = "&Language";
		public static String langManage = "&Language Manager...";
		
		public static String plugInMenu = "&PlugIn";
		public static String plugInManMenu = "&PlugIn Manager...";
		
		public static String helpMenu  = "&Help";
		public static String aboutMenu = "&About...";
		
		public static String aboutDialog = "About:";
		
		public static  String projectExplorer = "Project Explorer";
		
		public static  String openFolderMenu = "Open Folder...";
		
		public static  String newFolderDialog = "New Folder:";
		public static  String nfdFolderNameLabel = "Folder Name:";
		public static  String nfdFolderPathLabel = "Folder Path:";
		public static  String nfdNameMatchTip = "The name needs to match ([\\u4e00-\\u9fff0-9a-zA-Z_]|-|\\+|\\.)+";
		
		public static  String copyFromFolderDialog = "Copy From Folder:";
		public static  String srcFolderLabel = "Source Folder:";
		public static  String targetFolderLabel = "Target Folder:";
		public static  String copyFromFolderTip0 = "Source folder is parent of target folder.";
		public static  String copyFromFolderTip1 = "Target folder is parent of source folder.";
		
		public static  String plugInManDialog  = "PlugIn Manager:";
		public static  String plugInFilePathGroup = "JAR File Path"; 
		public static  String removePlugInButton = "&Remove PlugIn";
		public static  String addPlugInButton = "&Add PlugIn";
		
		public static  String editorSelectDialog = "Editor Select:";
		public static  String editorSelectTip = "The file type '*.%s' not support now, please select a temporary editor.";
		
		public static  String findFileDialog = "Find File:";
		
		public static  String Ok = "&OK";
		public static  String error = "Error:";
		public static  String warning  = "Warning:";
		public static  String reset = "&Reset";
		public static  String select = "&Select...";
		
		public static  String errFolderCreateFailed = "Folder '%s' create failed!";
		public static  String errFileCreateFailed = "File '%s' create failed!";
		public static  String errFileOpenFailed = "File '%s' open failed!";
		public static  String errFileSaveFailed = "File '%s' save failed!";
		public static  String errTerminalOpenFailed = "Terminal Open Failed!";
		public static  String errExplorerOpenFailed = "Explorer Open Failed!";
		public static  String errFileRenameFailed = "File '%s' rename failed, may be the file already exists or the file was opened!";
		public static  String warnFileUnSaved = "File '%s' not saved, save it?";
		public static  String warnConfigSaveFailed = "Configure save failed!";
		public static  String warnThemeCannotGoBack = "The deleted theme may not be go back, do you sure?";
		public static  String warnDefaultThemeCannotBeDeleted = "the 'Default' theme can not be deleted!";
		public static  String warnFilesCannotGoBack = "The deleted file/folder '%s' may not be go back, do you sure?";
		public static  String warnSomeFilesBeNotDeleted = "Deletion failed, some files may not be deleted!";
		public static  String warnFileNotExists = "File '%s' not exists, keep it?";
		
		public static  String dateTimeFormatDialog = "Pick Date/Time Format:";
		public static  String configDialog = "Config:";
		public static  String themeDialog = "Theme Editor: - ";
		
		public static  String generalConfig = "General";
		public static  String editGroupConfig = "Edit";
		public static  String autoIndentConfig = "Auto Indent";
		public static  String autoCompleteBraceConfig = "Auto Complete Braces";
		public static  String autoCompleteWordConfig = "Auto Complete Words";
		public static  String autoCompleteTagConfig = "Auto Complete HTML/XML Tag Pair";
		public static  String decorationGroupConfig = "Decoration";
		public static  String braceMatchConfig = "Macth Braces";
		public static  String tabWidthConfig = "Tab Width";
		public static  String highLightLineConfig = "High Light Current Line";
		public static  String highLightWordConfig = "High Light Words";
		public static  String showVerticalEdgeConfig = "Show Vertical Edge";
		public static  String cfgDialogRestartTip = "Applying configure need restart, restart NOW?";
		
		public static  String themeConfig = "Theme";
		public static  String themeLabelConfig = "Theme:";
		public static  String editThemeConfig = "&Edit Theme...";
		public static  String removeThemeConfig = "&Remove Theme";
		public static  String localeConfig = "Localization";
		public static  String localeCH = "Chinese";
		public static  String localeEN = "English";
		
		public static  String backConfig = " Background";
		public static  String foreConfig = " Foreground";
		
		public static  String basicUIConfig = "Basic UI";
		public static  String buttonConfig  = "Button";
		public static  String defButtonConfig = "Default Button";
		public static  String inputTextConfig = "Input Text";
		public static  String labelConfig = "Label";
		public static  String comboConfig = "Combo";
		public static  String listConfig = "List";
		public static  String groupConfig = "Group";
		public static  String tabFolderConfig = "TabFolder";
		public static  String tabFolderSelConfig = "TabFolder Selection";
		public static  String treeConfig = "Tree";
		public static  String compositeConfig = "Composite";
		
		/*
		public static  String mainWindowUI = "MainWindow UI";
		public static  String toolBarConfig = "ToolBar";
		public static  String proExplorerConfig = "ProjectExplorer";
		public static  String editorFolderConfig = "EditorFolder";
		public static  String editorFolderSelConfig = "EditorFolder Selection";
		public static  String statusBarConfig = "StatusBar";
		public static  String assistPanelConfig = "AssistPanel";
		public static  String assistPanelSelConfig = "AssistPanel Selection";
		*/
		
		public static  String editorUI = "Editor"; 
		public static  String editorFontConfig = "Editor Font";
		public static  String editAreaConfig = "EditArea";
		public static  String lineMarginConfig = "Line Margin";
		public static  String wordFixMarginConfig = "WordFix Margin";
		public static  String highLightWordColor = "HighLight Word Background";
		public static  String highLightLineColor = "HighLight Line Background";
		
		public static  String tokenColor = "Token Color";
		public static  String tokenStyle = "Token Style";
		
		public static String boldStyle = "Bold";
		public static String italicStyle = "Italic";
		
		public static String themeVisualInfoTip = "Click mouse to select color.";
		public static String themeSaveAsConfig = "&Save Theme";
		public static String themeSaveAsGroupConfig = "Theme Save";
		public static String themeNameTip = "Theme name can just contain letters and digits, and must start with letters.";
		public static String themeSaveAsWarning = "Theme '%s' Saved Failed, may be the theme exists!";
		
		public static String findAndReplaceDialog = "Find and Replace:";
		public static String findLabel = "Find:";
		public static String replaceLabel = "Replace With:";
		public static String directionGroup = "Direction";
		public static String forwardRadio = "Forward";
		public static String backwardRadio = "Backward";
		public static String scopeGroup = "Scope";
		public static String allRadio = "All";
		public static String selectionRadio = "Selection";
		public static String optionsGroup = "Options";
		public static String caseSensitiveCheck = "Case Sensitive";
		public static String wholeWordCheck = "Whole Word";
		public static String wrapCheck = "Wrap Search";
		public static String findNextButton = "&Find Next";
		public static String replaceButton = "&Replace";
		public static String replaceAllButton = "&Replace All";
		public static String noMoreResultInfo = "No more results.";
		public static String hasNoSelectionInfo = "Has no selection.";
		public static String replaceCountInfo = "Total %d replacements.";
		
		public static String regexDialog = "Regex:";
		public static String regexGrammarErrInfo = "Regex has syntax error!";
		public static String regexGroupTooMoreInfo = "Group too more!";
		public static String dotAllCheck = "Dot All";
		
		public static String replaceInSelectionDialog = "Replace In Selection:";
		
		public static String findInFolderDialog = "Find In Folder:";
		public static String regexLabel = "Regex:";
		public static String folderLabel = "Folder:";
		public static String filterLabel = "Filter:";
		public static String findButton = "&Find";
		public static String explorerButton = "&Explorer...";
		
		public static String folderFindInfo = "line %d, hit: %s";
		public static String hasNoHit = "Has no hit.";
		public static String extSplitTip = "Extensions need split by semicolon ';'.";
		
		public static String renameFileOrFolderMenu = "Rename File/Folder";
		public static String deleteFileOrFolderMenu = "Delete File/Folder";
		public static String flushFolderMenu = "Flush Folder";
		public static String flushProjectTreeMenu = "Flush All";
		
		public static String fileRenameDialog = "File/Folder Rename:";
		public static String newNameLabel = "New Name:";
		
		public static String totalCharCountStatus = "|Total %d lines, %d chars";
		public static String currentRowColStatus = "|Row %d, Col %d";
		public static String offsetStatus = "|Caret at %d";
		public static String insertInputModeStatus = "|Insert Mode";
		public static String overwriteInputModeStatus = "|Overwrite Mode";
		
		public static String newSourceDialog = "New Source:";
		public static String templateGroup = "Template";
		public static String fileNameLabel = "File Name:";
		public static String filePathLabel = "File Path:";
		public static String useTemplateCheck = "Not Use Template";
		
		public static String encodeDialog = "Encode Convert:";
		public static String encodeGroup = "Source Encode";
		public static String encodeConvertButton = "Convert To JVM Encode";
		public static String errEncodeConvertFailed = "Encode convert failed!";
		public static  String encodeTip = "If you know the source encoding, select the right one. if you not,"
				+ "you can take a trying. But when your trying failed, you need do a undo, or you lose you data.";
		
		public static String gotoLineDialog = "Goto Line:";
		public static String lineIndexLabel = "Line Index:";
		public static String gotoButton = "&Goto";
		
		public static String assistPanel = "Assist Panel";
		public static String assistTmp = "Temp ";
		public static String clipboard = "Clipboard ";
		public static String drop = "Drop ";
		public static String nameLabel = "Type a Name";
		public static String newButton = "&New";
		public static String copyButton = "&Copy";
		public static String importFromFileButton = "&Import From File...";
		public static String importFromClipboardButton = "&Import From Clipboard";
		public static String assistNote = "In this assist panel, you can create NEW assist area,\n"
				+ "COPY the content to clipboard,\nset the content from CLIPBOARD or FILE.\n"
				+ "It even supports	DRAG-DROP.\n"
				+ "Feel free to use this panel, wish it can make some sense for you.";
		
		public static String langManageDialog = "Language Manager:";
		//public static String langGroup = "Language";
		//public static String extGroup = "Extension";
		public static String langNameLabel = "Language Name:";
		public static String langLibLabel = "Lib Path:";
		public static String editorClassLabel = "Editor Class:";
		public static String addLangButton = "&Add Language";
		//public static String removeLangButton = "&Remove Language";
		public static String extLabel    = "Extension:";
		public static String addExtButton = "&Add Extension";
		public static String errIllegalJarFile = "Illegal jar file!";
		//public static String removeExtButton = "&Remove Extension";
		
		public static String errPlugInInfoSavedFaile = "PlugIn Info saved failed!";
		
		static {
			if (Config.getInstance().getLocale()==Locale.CHINESE) {
				fileMenu = "文件(&F)";
				newMenu  = "新建(&N)";
				newProjectMenu = "新建工程(&N)...\tCtrl+N";
				newFolderMenu = "新建文件夹(&N)...\tCtrl+Alt+N";
				newSourceMenu = "新建源文件(&N)...\tCtrl+Shift+N";
				openMenu = "打开(&O)...\tCtrl+O";
				copyFromFolderMenu = "从文件夹复制(&C)...\tCtrl+Shift+O";
				saveMenu = "保存(&S)\tCtrl+S";
				saveAsMenu = "另存为(&S)...\tCtrl+Shift+S";
				closeMenu = "关闭(&C)\tCtrl+W";
				closeAllMenu = "关闭全部(&C)\tCtrl+Shift+W";
				remvoeProjectMenu = "移除工程(&R)";
				configMenu = "配置(&C)...";
				exitMenu = "退出(&X)\tAlt+X";
				
				editMenu = "编辑(&E)";
				undoMenu = "撤销(&U)\tCtrl+Z";
				redoMenu = "重做(&R)\tCtrl+Shift+Z";
				copyMenu = "复制(&C)\tCtrl+C";
				cutMenu = "剪切(&C)\tCtrl+X";
				pasteMenu = "粘贴(&P)\tCtrl+V";
				deleteMenu = "删除(&D)\tDel";
				lineHandleSubMenu = "行处理(&L)";
				copyLineMenu = "复制当前行(&C)\tCtrl+D";
				deleteLineMenu = "删除当前行(&D)\tCtrl+Shift+D";
				sortLinesMenu = "排序行(&S)\tCtrl+T";
				revolveLinesMenu = "旋转行(&R)\tCtrl+Shift+T";
				splitLinesMenu = "分割行(&S)\tCtrl+Alt+T";
				joinLinesMenu = "连接行(&J)\tCtrl+J";
				caseConvertSubMenu = "转到(&C)";
				convertToUpperCaseMenu = "大写(&U)\tCtrl+U";
				convertToLowerCaseMenu = "小写(&l)\tCtrl+L";
				convertToProperCaseMenu = "适当(&S)\tCtrl+Shift+U";
				convertToSentenceCaseMenu = "句子(&P)\tCtrl+Alt+U";
				indentSubMenu = "缩进(&I)";
				indentMenu = "缩进(&I)\tTab";
				unindentMenu = "删除缩进(&U)\tShift+Tab";
				trimMenu = "去除首尾空白符(&T)\tCtrl+I";
				commentSubMenu = "注释(&C)";
				commentMenu = "添加注释(&C)\tCtrl+Q";
				uncommentMenu = "删除注释(&U)\tCtrl+Shift+Q";
				changeCommentMenu = "改变注释状态(&C)\tCtrl+Alt+Q";
				dateTimeMenu = "日期/时间(&D)...\tF3";
				encodingMenu = "编码(&E)...\tCtrl+E";
				selectAllMenu = "全选(&S)\tCtrl+A";
				gotoLineMenu = "转到行(&G)...\tCtrl+G";
				
				encodeTip = "如果你知道源文件编码, 选择对应的编码进行转换. 如果你不知道, 你可以进行尝试."
						+ "但在尝试失败后, 你需要执行撤销操作, 否则会丢失数据.";
				
				searchMenu = "搜索(&S)";
				findReplaceMenu = "查找和替换(&F)...\tCtrl+F";
				regexMenu = "正则表达式(&R)...\tCtrl+R";
				replaceInSelectionMenu = "选择文本中替换(&R)...\tCtrl+Alt+F";
				findInFolderMenu = "文件夹中查找(&F)...\tCtrl+Shift+F";
				findFileMenu = "查找文件(&F)...";
				
				terminalMenu = "终端(&T)";
				openTerminalMenu = "打开终端(&O)...\tCtrl+Alt+T";
				openExplorerMenu = "打开文档浏览器(&O)...";
				openConfigFolderMenu = "打开配置文件目录(&O)...";
				openPlugInFolderMenu = "打开插件目录(&O)...";
				
				langMenu = "语言(&L)";
				langManage = "语言管理(&L)...";
				
				plugInMenu = "插件(&P)";
				plugInManMenu = "插件管理(&P)...";
				helpMenu = "帮助(&H)";
				aboutMenu = "关于(&A)...";
				
				aboutDialog = "关于:";
				
				projectExplorer = "工程浏览";
				
				openFolderMenu = "打开目录...";
				
				newFolderDialog = "新建文件夹:";
				nfdFolderNameLabel = "文件夹名:";
				nfdFolderPathLabel = "文件夹路径:";
				nfdNameMatchTip = "文件(夹)名需匹配 ([\\u4e00-\\u9fff0-9a-zA-Z_]|-|\\+|\\.)+";
				
				copyFromFolderDialog = "从文件夹复制:";
				srcFolderLabel = "源目录:";
				targetFolderLabel = "目标目录:";
				copyFromFolderTip0 = "源目录是目标目录的父目录.";
				copyFromFolderTip1 = "目标目录是源目录的父目录.";
				
				plugInManDialog = "插件管理:";
				plugInFilePathGroup = "JAR 文件路径";
				removePlugInButton = "移除插件(&R)";
				addPlugInButton = "添加插件(&A)";
				
				editorSelectDialog = "编辑器选择:";
				editorSelectTip = "目前不支持文件类型 '*.%s', 请选择一个临时编辑器.";
				
				findFileDialog = "查找文件:";
				
				Ok = "确定(&O)";
				reset = "重置(&R)";
				error = "错误:";
				warning  = "警告:";
				select = "选择(&S)..."; 
				
				errFolderCreateFailed = "文件夹 '%s' 建立失败!";
				errFileCreateFailed = "文件 '%s' 建立失败!";
				errFileOpenFailed = "文件 '%s' 打开失败!";
				errFileSaveFailed = "文件 '%s' 保存失败!";
				errTerminalOpenFailed = "终端打开失败!";
				errExplorerOpenFailed = "文档浏览器打开失败!";
				errFileRenameFailed = "文件 '%s' 重命名失败, 可能是文件已存在或文件正被使用!";
				warnFileUnSaved = "文件 '%s' 未保存, 是否保存?";
				warnConfigSaveFailed = "配置信息保存失败!";
				warnThemeCannotGoBack = "被删除主题可能无法恢复, 你确定要删除吗?";
				warnFilesCannotGoBack = "被删除的文件(夹) '%s' 可能无法恢复, 你确定要删除吗?";
				warnDefaultThemeCannotBeDeleted = "主题 'Default' 不能被删除!";
				warnSomeFilesBeNotDeleted = "文件删除遇到故障, 有些文件可能未被删除!";
				warnFileNotExists = "文件 '%s' 被删除, 是否将其保持在编辑面板?";
				
				dateTimeFormatDialog = "选择日期时间格式:";
				configDialog = "配置:";
				themeDialog = "主题编辑器: - ";
				generalConfig = "一般";
				editGroupConfig = "编辑";
				autoIndentConfig = "自动缩进";
				autoCompleteBraceConfig = "自动完成括号";
				autoCompleteWordConfig = "自动完成单词";
				autoCompleteTagConfig  = "自动完成　HTML/XML 标签对";
				decorationGroupConfig = "装饰";
				braceMatchConfig = "匹配括号";
				tabWidthConfig = "制表符宽度";
				highLightLineConfig = "高亮当前行";
				highLightWordConfig = "高亮单词";
				showVerticalEdgeConfig = "显示垂直线";
				cfgDialogRestartTip = "设定重启后生效, 是否现在重启?";
				
				themeConfig = "主题";
				themeLabelConfig = "主题:";
				editThemeConfig = "编辑主题(&E)...";
				removeThemeConfig = "删除主题(&R)";
				
				localeConfig = "本地化";
				localeCH = "中文";
				localeEN = "英文";
				
				basicUIConfig = "基本用户接口";
				buttonConfig = "按钮";
				defButtonConfig = "默认按钮";
				inputTextConfig = "输入框";
				labelConfig = "标签";
				comboConfig = "下拉列表框";
				listConfig = "列表框";
				compositeConfig = "父面板";
				groupConfig = "组";
				tabFolderConfig = "选择夹";
				tabFolderSelConfig = "选择夹当前";
				treeConfig = "树组件";
				
				/*
				mainWindowUI = "主窗口用户接口";
				toolBarConfig = "工具条";
				proExplorerConfig = "工程浏览";
				editorFolderConfig = "编辑器选择夹";
				editorFolderSelConfig = "编辑器选择夹当前";
				statusBarConfig = "状态栏";
				assistPanelConfig = "辅助面板";
				assistPanelSelConfig = "辅助面板当前";
				*/
				
				editorUI = "编辑器";
				editorFontConfig = "编辑器字体";
				editAreaConfig = "编辑区域";
				lineMarginConfig = "代码行旁注栏";
				wordFixMarginConfig = "单词定位旁注栏";
				highLightLineColor = "高亮行背景色";
				highLightWordColor = "高亮单词背景色";
				
				backConfig = "背景色";
				foreConfig = "前景色";
				
				tokenColor = "Token 颜色";				
				tokenStyle = "Token 风格";
				
				boldStyle = "黑体";
				italicStyle = "斜体";
				
				themeVisualInfoTip = "点击鼠标选择颜色.";
				themeNameTip = "主题名只能包括字母和数字, 并以字母开头.";
				
				themeSaveAsConfig = "保存主题(&S)";
				themeSaveAsGroupConfig = "主题保存";
				themeSaveAsWarning = "主题 '%s' 保存失败, 可能是主题已存在!";
				
				findAndReplaceDialog = "查找和替换:";
				findLabel = "查找:";
				replaceLabel = "替换:";
				directionGroup = "方向";
				forwardRadio = "向前查找";
				backwardRadio = "向后查找";
				scopeGroup = "范围";
				allRadio = "全部";
				selectionRadio = "被选择文本";
				optionsGroup = "选项";
				caseSensitiveCheck = "区分大小写";
				wholeWordCheck = "做为整体";
				wrapCheck = "回卷查找";
				findNextButton = "查找下一个(&F)";
				replaceButton = "替换(&R)";
				replaceAllButton = "全部替换(&R)";
				noMoreResultInfo = "无更多结果.";
				hasNoSelectionInfo = "无选择文本.";
				replaceCountInfo = "共替换 %d 次.";
				
				regexDialog = "正则表达式:";
				regexGrammarErrInfo = "正则表达式有语法错误!";
				regexGroupTooMoreInfo = "组指定过多!";
				dotAllCheck = ". 匹配所有";
				
				replaceInSelectionDialog = "选择文本中替换:";
				
				findInFolderDialog = "文件夹中查找:";
				regexLabel = "正则表达式:";
				filterLabel = "过滤:";
				findButton = "查找(&F)";
				folderLabel = "文件夹:";
				explorerButton = "浏览(&E)...";
				
				folderFindInfo = "在第 %d 行, 命中: %s";
				hasNoHit = "无匹配信息.";
				extSplitTip = "后缀名以分号 ';' 分隔.";
				
				renameFileOrFolderMenu = "重命名文件(夹)";
				deleteFileOrFolderMenu = "删除文件(夹)";
				flushFolderMenu = "刷新文件夹";
				flushProjectTreeMenu = "刷新全部";
				
				fileRenameDialog = "文件(夹)重命名:";
				newNameLabel = "新文件(夹)名:";
				
				totalCharCountStatus = "|共 %d 行, %d 个字符";
				currentRowColStatus = "|行 %d, 列 %d";
				offsetStatus = "|偏移为 %d";
				insertInputModeStatus = "|插入模式";
				overwriteInputModeStatus = "|复写模式";
				
				newSourceDialog = "新建源文件:";
				templateGroup = "模板";
				fileNameLabel = "文件名:";
				filePathLabel = "文件路径:";
				useTemplateCheck = "不使用模板";
				
				encodeDialog = "编码转换:";
				encodeGroup = "源编码";
				encodeConvertButton = "转换到 JVM 编码";
				errEncodeConvertFailed = "编码转换失败!";
				
				gotoLineDialog = "转到行:";
				lineIndexLabel = "行号:";
				gotoButton = "转到(&G)";
				
				assistPanel = "辅助面板";
				assistTmp = "临时 ";
				nameLabel = "请输入名字";
				clipboard = "剪贴簿 ";
				drop = "拖放 ";
				newButton = "新建(&N)";
				copyButton = "复制(&C)";
				importFromFileButton = "从文件导入(&I)...";
				importFromClipboardButton = "从剪贴簿导入(&I)";
				assistNote = "在这个辅助面板, 你可以  '新建' 辅助区域, '复制' 文本到剪贴簿,\n从'剪贴簿' 导入文本,从'文件' 导入文本. "
						+ "它甚至支持拖放.\n" +
						"你可以适时地使用它, 希望它对你有用.";
				
				langManageDialog = "语言管理:";
				//langGroup = "语言";
				//extGroup = "扩展名";
				langNameLabel = "语言名:";
				langLibLabel = "类库路径:";
				editorClassLabel = "编辑器类名:";
				extLabel = "扩展名:";
				addLangButton = "添加语言(&A)";
				//removeLangButton = "删除语言(&R)";
				addExtButton = "添加扩展名(&A)";
				errIllegalJarFile = "无效 jar 文件!";
				//removeExtButton = "删除扩展名(&R)";
				
				errPlugInInfoSavedFaile = "插件信息存储失败!";
			}
		}
	}
	
	public static class Ints {
		public static int scrollBarWidth = 17;
	}
}

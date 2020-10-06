package org.lightcodex.ui;

import java.util.List;
import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.lightcodex.Config;
import org.lightcodex.Constants;
import org.lightcodex.LightCodeX;
import org.lightcodex.theme.Theme;
import org.lightcodex.theme.ThemeManager;
import org.lightcodex.ui.util.MessageBoxEx;
import org.lightcodex.ui.util.UIStyler;

public class ConfigDialog extends Dialog {

	private Shell shell;
	private Text tabWidthT;
	
	private Combo themeCombo;
	
	public ConfigDialog(Shell parent) {
		super(parent, SWT.NONE);
		setText(Constants.Strings.configDialog);
	}

	public void open() {
		createContents();
		UIStyler.centre(shell);
		shell.open();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
		shell.setText(getText());
		shell.setSize(574, 437);
		shell.setLayout(null);
		
		Theme theme = Config.getInstance().getTheme();
		UIStyler.setCompositeStyle(theme, shell);
		
		CTabFolder tabFolder = new CTabFolder(shell, SWT.BORDER);
		tabFolder.setBounds(0, 0, 568, 349);
		UIStyler.setTabFolderStyle(theme, tabFolder);
		
		CTabItem item = new CTabItem(tabFolder, SWT.NONE);
		item.setText(Constants.Strings.generalConfig);
		
		var genCfgPane = new Composite(tabFolder, SWT.NONE);
		item.setControl(genCfgPane);
		UIStyler.setCompositeStyle(theme, genCfgPane);
		
		Group editG = new Group(genCfgPane, SWT.NONE);
		editG.setBounds(10, 78, 532, 99);
		editG.setText(Constants.Strings.editGroupConfig);
		UIStyler.setGroupStyle(theme, editG);
		
		Button autoIndentCB = new Button(editG, SWT.CHECK);
		autoIndentCB.setBounds(10, 26, 257, 17);
		autoIndentCB.setText(Constants.Strings.autoIndentConfig);
		autoIndentCB.setSelection(Config.getInstance().isAutoIndent());
		UIStyler.setButtonStyle(theme, autoIndentCB);
		
		Button autoComPairCB = new Button(editG, SWT.CHECK);
		autoComPairCB.setBounds(10, 49, 257, 17);
		autoComPairCB.setText(Constants.Strings.autoCompleteBraceConfig);
		autoComPairCB.setSelection(Config.getInstance().isAutoCompletePair());
		UIStyler.setButtonStyle(theme, autoComPairCB);
		
		Button completeWordCB = new Button(editG, SWT.CHECK);
		completeWordCB.setBounds(273, 26, 249, 17);
		completeWordCB.setText(Constants.Strings.autoCompleteWordConfig);
		completeWordCB.setSelection(Config.getInstance().isAutoCompleteWord());
		UIStyler.setButtonStyle(theme, completeWordCB);
		
		Button completeTagCB = new Button(editG, SWT.CHECK);
		completeTagCB.setBounds(273, 49, 249, 17);
		completeTagCB.setText(Constants.Strings.autoCompleteTagConfig);
		completeTagCB.setSelection(Config.getInstance().isAutoCompleteTagPair());
		UIStyler.setButtonStyle(theme, completeTagCB);
		
		Group decorationG = new Group(genCfgPane, SWT.NONE);
		decorationG.setBounds(10, 183, 532, 131);
		decorationG.setText(Constants.Strings.decorationGroupConfig);
		UIStyler.setGroupStyle(theme, decorationG);
		
		Button verticalEdgeCB = new Button(decorationG, SWT.CHECK);
		verticalEdgeCB.setBounds(10, 29, 512, 17);
		verticalEdgeCB.setText(Constants.Strings.showVerticalEdgeConfig);
		verticalEdgeCB.setSelection(Config.getInstance().isShowVerticalEdge());
		UIStyler.setButtonStyle(theme, verticalEdgeCB);
		
		Button braceMatchCB = new Button(decorationG, SWT.CHECK);
		braceMatchCB.setBounds(10, 52, 512, 17);
		braceMatchCB.setText(Constants.Strings.braceMatchConfig);
		braceMatchCB.setSelection(Config.getInstance().isBraceMatch());
		UIStyler.setButtonStyle(theme, braceMatchCB);
		
		Button highLightLineCB = new Button(decorationG, SWT.CHECK);
		highLightLineCB.setBounds(10, 75, 512, 17);
		highLightLineCB.setText(Constants.Strings.highLightLineConfig);
		highLightLineCB.setSelection(Config.getInstance().isHighLightLine());
		UIStyler.setButtonStyle(theme, highLightLineCB);
		
		Button highLightWordCB = new Button(decorationG, SWT.CHECK);
		highLightWordCB.setBounds(10, 98, 512, 17);
		highLightWordCB.setText(Constants.Strings.highLightWordConfig);
		highLightWordCB.setSelection(Config.getInstance().isHighLightWords());
		UIStyler.setButtonStyle(theme, highLightWordCB);
		
		Group tabWidthG = new Group(genCfgPane, SWT.NONE);
		tabWidthG.setBounds(287, 10, 255, 62);
		tabWidthG.setText(Constants.Strings.tabWidthConfig);
		UIStyler.setGroupStyle(theme, tabWidthG);
		
		Button resetBut = new Button(shell, SWT.NONE);
		resetBut.setText(Constants.Strings.reset);
		resetBut.setBounds(382, 362, 80, 27);
		UIStyler.setButtonStyle(theme, resetBut);
		
		Button okBut = new Button(shell, SWT.NONE);
		okBut.setText(Constants.Strings.Ok);
		okBut.setEnabled(false);
		okBut.setBounds(468, 362, 80, 27);
		UIStyler.setDefaultButtonStyle(theme, okBut);
		shell.setDefaultButton(okBut);
		
		tabWidthT = new Text(tabWidthG, SWT.BORDER);
		tabWidthT.setBounds(10, 29, 235, 23);
		UIStyler.setInputTextStyle(theme, tabWidthT);
		
		tabWidthT.addModifyListener(e->{
			String text = tabWidthT.getText();
			if (text.matches("[1-9][0-9]*")) {
				okBut.setEnabled(true);
			} else {
				okBut.setEnabled(false);
			}
		});
		
		tabWidthT.setText(Integer.toString(Config.getInstance().getTabWidth()));
		
		Group localeG = new Group(genCfgPane, SWT.NONE);
		localeG.setBounds(10, 10, 255, 62);
		localeG.setText(Constants.Strings.localeConfig);
		UIStyler.setGroupStyle(theme, localeG);
		
		Combo localeCombo = new Combo(localeG, SWT.READ_ONLY);
		localeCombo.setBounds(10, 27, 235, 25);
		localeCombo.add(Constants.Strings.localeEN);
		localeCombo.add(Constants.Strings.localeCH);
		localeCombo.select(Config.getInstance().getLocale()==Locale.ENGLISH?0:1);
		UIStyler.setComboStyle(theme, localeCombo);
		
		item = new CTabItem(tabFolder, SWT.NONE);
		item.setText(Constants.Strings.themeConfig);
		
		var themePane = new Composite(tabFolder, SWT.NONE);
		item.setControl(themePane);
		UIStyler.setCompositeStyle(theme, themePane);
		
		Label themeL = new Label(themePane, SWT.NONE);
		themeL.setBounds(10, 67, 532, 17);
		themeL.setText(Constants.Strings.themeLabelConfig);
		UIStyler.setLabelStyle(theme, themeL);
		
		Button editThemeBut = new Button(themePane, SWT.NONE);
		editThemeBut.setBounds(283, 172, 253, 27);
		editThemeBut.setText(Constants.Strings.editThemeConfig);
		UIStyler.setButtonStyle(theme, editThemeBut);
		editThemeBut.addListener(SWT.Selection, e->{
			ThemeDialog themeDialog = new ThemeDialog(shell);
			themeDialog.setConfigDialog(this);
			themeDialog.open();
		});
		
		themeCombo = new Combo(themePane, SWT.READ_ONLY);
		themeCombo.setBounds(10, 113, 532, 21);
		setUpThemeCombo();
		UIStyler.setComboStyle(theme, themeCombo);
		
		Button removeThemeBut = new Button(themePane, SWT.NONE);
		removeThemeBut.setBounds(15, 172, 253, 27);
		removeThemeBut.setText(Constants.Strings.removeThemeConfig);
		removeThemeBut.addListener(SWT.Selection, e->{
			String themeName = themeCombo.getItem(themeCombo.getSelectionIndex());
			
			if (themeName.equalsIgnoreCase("Default")) {
				MessageBoxEx.warning(shell, Constants.Strings.warnDefaultThemeCannotBeDeleted);
				return;
			}
			
			int flag = MessageBoxEx.questionWithOutCancel(shell, Constants.Strings.warnThemeCannotGoBack);
			if (flag == SWT.YES) {
				ThemeManager.remove(themeCombo.getItem(themeCombo.getSelectionIndex()));
				this.setUpThemeCombo();
			}
		});
		UIStyler.setButtonStyle(theme,removeThemeBut);
		
		resetBut.addListener(SWT.Selection, e->{
			Config.getInstance().setDefaultTheme();
			Config.getInstance().resetOthers();
			autoIndentCB.setSelection(Config.getInstance().isAutoIndent());
			autoComPairCB.setSelection(Config.getInstance().isAutoCompletePair());
			completeWordCB.setSelection(Config.getInstance().isAutoCompleteWord());
			completeTagCB.setSelection(Config.getInstance().isAutoCompleteTagPair());
			verticalEdgeCB.setSelection(Config.getInstance().isShowVerticalEdge());
			highLightLineCB.setSelection(Config.getInstance().isHighLightLine());
			highLightWordCB.setSelection(Config.getInstance().isHighLightWords());
			braceMatchCB.setSelection(Config.getInstance().isBraceMatch());
			tabWidthT.setText(Integer.toString(Config.getInstance().getTabWidth()));
			localeCombo.select(Config.getInstance().getLocale()==Locale.ENGLISH?0:1);
			themeCombo.select(0);
		});
		
		okBut.addListener(SWT.Selection, e->{
			Config.getInstance().setAutoCompletePair(autoComPairCB.getSelection());
			Config.getInstance().setAutoIndent(autoIndentCB.getSelection());
			Config.getInstance().setAutoCompleteWord(completeWordCB.getSelection());
			Config.getInstance().setAutoCompleteTagPair(completeTagCB.getSelection());
			Config.getInstance().setBraceMatch(braceMatchCB.getSelection());
			Config.getInstance().setShowVerticalEdge(verticalEdgeCB.getSelection());
			Config.getInstance().setHighLightLine(highLightLineCB.getSelection());
			Config.getInstance().setHighLightWord(highLightWordCB.getSelection());
			Config.getInstance().setTabWidth(Integer.parseInt(tabWidthT.getText()));
			Config.getInstance().setLocale(localeCombo.getItem(localeCombo.getSelectionIndex()));
			Config.getInstance().setTheme(themeCombo.getItem(themeCombo.getSelectionIndex()));
			
			try {
				Config.getInstance().save();
				
				int flag = MessageBoxEx.questionWithOutCancel(shell, Constants.Strings.cfgDialogRestartTip); 				
				shell.dispose();
				if (flag == SWT.YES) {
					LightCodeX.exitValue = LightCodeX.REBOOT_ID;
					MainWindow.getInstance().close();
				}
			} catch (RuntimeException ex) {
				MessageBoxEx.error(shell, Constants.Strings.warnConfigSaveFailed);
			}
		});
	}

	void setUpThemeCombo() {
		themeCombo.removeAll();
		List<Theme> themeList = ThemeManager.listAll();
		for (Theme theme : themeList) {
			themeCombo.add(theme.getThemeName());
		}
		Theme current = Config.getInstance().getTheme();
		int index = ThemeManager.indexOf(current);
		if (index == -1) {index = 0;}
		themeCombo.select(index);
	}
}

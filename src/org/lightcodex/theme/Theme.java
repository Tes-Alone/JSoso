package org.lightcodex.theme;

import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;

public interface Theme {
	
	static final int TOKEN_STYLE_MAX = 22;
	
	@Override
	boolean equals(Object obj);
	
	String getThemeName();
	RGB getEditAreaBackground();
	RGB getEditAreaForeground();
	RGB getLineMarginBackground();
	RGB getLineMarginForeground();
	RGB getWordFixMarginBackground();
	RGB getWordFixMarginForeground();
	RGB getHighLightLineColor();
	RGB getHighLightWordColor();
	
	FontData getEditorFont();
	
	@Deprecated
	RGB getToolBarBackground();
	@Deprecated
	RGB getStatusBarBackground();
	@Deprecated
	RGB getStatusBarForeground();
	@Deprecated
	RGB getProjectExplorerBackground();
	@Deprecated
	RGB getProjectExplorerForeground();
	@Deprecated
	RGB getEditorFolderBackground();
	@Deprecated
	RGB getEditorFolderForeground();
	@Deprecated
	RGB getEditorFolderSelectionBackground();
	@Deprecated
	RGB getEditorFolderSelectionForeground();
	@Deprecated
	RGB getAssistPanelBackground();
	@Deprecated
	RGB getAssistPanelForeground();
	@Deprecated
	RGB getAssistPanelSelectionBackground();
	@Deprecated
	RGB getAssistPanelSelectionForeground();
	
	RGB getTabFolderBackground();
	RGB getTabFolderForeground();
	RGB getTabFolderSelectionBackground();
	RGB getTabFolderSelectionForeground();
	RGB getButtonBackground();
	RGB getButtonForeground();
	RGB getDefaultButtonBackground();
	RGB getDefaultButtonForeground();
	RGB getInputTextBackground();
	RGB getInputTextForeground();
	RGB getComboBackground();
	RGB getComboForeground();
	RGB getListBackground();
	RGB getListForeground();
	RGB getLabelBackground();
	RGB getLabelForeground();
	RGB getGroupBackground();
	RGB getGroupForeground();
	RGB getCompositeBackground();
	RGB getTreeForeground();
	RGB getTreeBackground();
	
	RGB getTokenColor(int index);
	int getTokenStyle(int index);
}
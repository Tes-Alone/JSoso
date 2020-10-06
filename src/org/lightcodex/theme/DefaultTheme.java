package org.lightcodex.theme;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;

public class DefaultTheme implements Theme {

	@Override
	public RGB getEditAreaBackground() {
		return new RGB(0xff,0xff,0xff);
	}

	@Override
	public RGB getEditAreaForeground() {
		return new RGB(0, 0, 0);
	}

	@Override
	public RGB getLineMarginBackground() {
		return new RGB(0xee,0xee,0xee);
	}

	@Override
	public RGB getLineMarginForeground() {
		return new RGB(0,0,0);
	}

	@Override
	public FontData getEditorFont() {
		return new FontData("Microsoft Yahei", 10, SWT.NORMAL);
	}

	@Override
	public RGB getProjectExplorerBackground() {
		return null;
	}
	
	@Override
	public RGB getProjectExplorerForeground() {
		return null;
	}

	@Override
	public RGB getButtonBackground() {
		return null;
	}

	@Override
	public RGB getButtonForeground() {
		return null;
	}

	@Override
	public RGB getDefaultButtonBackground() {
		return null;
	}

	@Override
	public RGB getDefaultButtonForeground() {
		return null;
	}

	@Override
	public RGB getInputTextBackground() {
		return null;
	}

	@Override
	public RGB getInputTextForeground() {
		return null;
	}

	@Override
	public RGB getCompositeBackground() {
		return null;
	}	
	
	@Override
	public RGB getHighLightLineColor() {
		return new RGB(0xe7, 0xe7, 0xe7);
	}

	@Override
	public RGB getHighLightWordColor() {
		return new RGB(0,0xff,0);
	}

	@Override
	public RGB getWordFixMarginBackground() {
		return new RGB(0xff,0xff,0xff);
	}

	@Override
	public RGB getWordFixMarginForeground() {
		return new RGB(0,0,0);
	}

	@Override
	public RGB getComboBackground() {
		return null;
	}

	@Override
	public RGB getComboForeground() {
		return null;
	}

	@Override
	public RGB getListBackground() {
		return null;
	}

	@Override
	public RGB getListForeground() {
		return null;
	}

	@Override
	public RGB getLabelBackground() {
		return null;
	}

	@Override
	public RGB getLabelForeground() {
		return null;
	}

	@Override
	public RGB getTabFolderBackground() {
		return null;
	}

	@Override
	public RGB getTabFolderForeground() {
		return null;
	}

	@Override
	public RGB getTabFolderSelectionBackground() {
		return null;
	}

	@Override
	public RGB getTabFolderSelectionForeground() {
		return null;
	}

	@Override
	public RGB getGroupBackground() {
		return null;
	}

	@Override
	public RGB getGroupForeground() {
		return null;
	}

	@Override
	public String getThemeName() {
		return "Default";
	}
	
	@Override
	public boolean equals(Object obj) {
		Theme other = (Theme) obj;
		return other.getThemeName().equals(getThemeName());
	}

	@Override
	public RGB getToolBarBackground() {
		return null;
	}

	@Override
	public RGB getStatusBarBackground() {
		return null;
	}

	@Override
	public RGB getStatusBarForeground() {
		return null;
	}

	@Override
	public RGB getEditorFolderBackground() {
		return null;
	}

	@Override
	public RGB getEditorFolderForeground() {
		return null;
	}

	@Override
	public RGB getEditorFolderSelectionBackground() {
		return null;
	}

	@Override
	public RGB getEditorFolderSelectionForeground() {
		return null;
	}

	@Override
	public RGB getTreeForeground() {
		return null;
	}

	@Override
	public RGB getTreeBackground() {
		return null;
	}

	private static RGB[] tokenColors = {
		new RGB(0,0,0x7f),
		new RGB(0x22,0x80,0xc3),
		new RGB(0,0x80,0x40),
		new RGB(0x47,0x5c,0x21),
		new RGB(0x7e,0x48,0x28),
		new RGB(128,0,0),
		new RGB(0x70,0x7f,0x7f),
		new RGB(0xdd,0x8b,0x92),
		new RGB(0xdd,0x8b,0x92),
		new RGB(0,0x7f,0),
		new RGB(0,0,0),
		new RGB(0xdd,0xb5,0x3b),
		new RGB(0,0,0x7f),
		new RGB(67,98,100),
		new RGB(99,39,105),
		new RGB(56,67,89),
		new RGB(66,200,65),
		new RGB(77,87,56),
		new RGB(100,100,100),
		new RGB(200,200,200),
		new RGB(0,0,0),
		new RGB(0,0x8f,0) // 22 TOKEN_STYLE_MAX
	};
	
	@Override
	public RGB getTokenColor(int index) {
		if (index<0 || index>TOKEN_STYLE_MAX) {
			throw new IndexOutOfBoundsException();
		}
		return tokenColors[index];
	}
	
	@Override
	public int getTokenStyle(int index) {
		if (index<0 || index>TOKEN_STYLE_MAX) {
			throw new IndexOutOfBoundsException();
		}
		return index<8 ? SWT.NORMAL : SWT.BOLD;
	}

	@Override
	public RGB getAssistPanelBackground() {
		return null;
	}

	@Override
	public RGB getAssistPanelForeground() {
		return null;
	}

	@Override
	public RGB getAssistPanelSelectionBackground() {
		return null;
	}

	@Override
	public RGB getAssistPanelSelectionForeground() {
		return null;
	}
}

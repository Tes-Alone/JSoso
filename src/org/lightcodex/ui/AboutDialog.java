package org.lightcodex.ui;

import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.lightcodex.Config;
import org.lightcodex.Constants;
import org.lightcodex.theme.Theme;
import org.lightcodex.ui.util.UIStyler;

public class AboutDialog extends Dialog {

	private Shell shell;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public AboutDialog(Shell parent) {
		super(parent, SWT.NONE);
		setText(Constants.Strings.aboutDialog);
	}

	public void open() {
		createContents();
		UIStyler.centre(shell);
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private static String author  = "@Tes Alone";
	private static String version = "Version 2.0";
	private static String description = "JSoso: A just so-so code editor.";
	
	static {
		if (Config.getInstance().getLocale()==Locale.CHINESE) {
			author  = "Tes Alone 制作";
			version = "版本 2.0";
			description = "JSoso: 一个 Just So-So 代码编辑器.";
		}
	}
	
	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), SWT.APPLICATION_MODAL|SWT.DIALOG_TRIM);
		shell.setSize(349, 335);
		shell.setText(getText());
		shell.setLayout(null);
		
		Theme theme = Config.getInstance().getTheme();
		UIStyler.setCompositeStyle(theme, shell);
		
		Label imageLabel = new Label(shell, SWT.NONE);
		imageLabel.setBounds(0, 0, 348, 210);
		imageLabel.setImage(Constants.Images.about);
		
		Group group = new Group(shell, SWT.NONE);
		group.setBounds(10, 222, 313, 70);
		UIStyler.setGroupStyle(theme, group);
		
		Label descLabel = new Label(group, SWT.CENTER);
		descLabel.setBounds(10, 10, 293, 17);
		descLabel.setText(description);
		UIStyler.setLabelStyle(theme, descLabel);
		
		Label versionLabel = new Label(group, SWT.CENTER);
		versionLabel.setBounds(10, 30, 293, 17);
		versionLabel.setText(version);
		UIStyler.setLabelStyle(theme, versionLabel);
		
		Label authorLabel = new Label(group, SWT.CENTER);
		authorLabel.setBounds(10, 50, 293, 17);
		authorLabel.setText(author);
		UIStyler.setLabelStyle(theme, authorLabel);
	}
}

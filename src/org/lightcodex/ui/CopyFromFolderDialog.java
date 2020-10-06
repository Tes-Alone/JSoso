package org.lightcodex.ui;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.lightcodex.Config;
import org.lightcodex.Constants;
import org.lightcodex.theme.Theme;
import org.lightcodex.ui.util.UIStyler;
import org.lightcodex.util.Utility;

public class CopyFromFolderDialog extends Dialog {

	private String result;
	private Shell shell;
	private Text srcText;
	private Text targetText;

	public CopyFromFolderDialog(Shell parent) {
		super(parent, SWT.NONE);
		setText(Constants.Strings.copyFromFolderDialog);
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public String open() {
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
		return result;
	}

	private String targetFolder;
	private Text tipText;
	
	public void setTargetFolder(String targetFolder) {
		this.targetFolder = targetFolder;
	}
	
	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), SWT.APPLICATION_MODAL|SWT.DIALOG_TRIM);
		shell.setSize(454, 321);
		shell.setText(getText());
		shell.setLayout(null);
		
		Theme theme = Config.getInstance().getTheme();
		UIStyler.setCompositeStyle(theme, shell);
		
		Label srcLabel = new Label(shell, SWT.NONE);
		srcLabel.setBounds(10, 26, 418, 17);
		srcLabel.setText(Constants.Strings.srcFolderLabel);
		UIStyler.setLabelStyle(theme, srcLabel);
		
		srcText = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		srcText.setBounds(10, 49, 324, 23);
		UIStyler.setInputTextStyle(theme, srcText);
		
		Button expButton = new Button(shell, SWT.NONE);
		expButton.setBounds(348, 47, 80, 27);
		expButton.setText(Constants.Strings.explorerButton);
		UIStyler.setButtonStyle(theme, expButton);
		
		Label targetLabel = new Label(shell, SWT.NONE);
		targetLabel.setBounds(10, 133, 418, 17);
		targetLabel.setText(Constants.Strings.targetFolderLabel);
		UIStyler.setLabelStyle(theme, targetLabel);
		
		targetText = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		targetText.setBounds(10, 156, 418, 23);
		UIStyler.setInputTextStyle(theme, targetText);
		
		Button okButton = new Button(shell, SWT.NONE);
		okButton.setBounds(348, 212, 80, 27);
		okButton.setText(Constants.Strings.Ok);
		okButton.setEnabled(false);
		UIStyler.setDefaultButtonStyle(theme, okButton);
		shell.setDefaultButton(okButton);
		
		tipText = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		tipText.setBounds(10, 250, 418, 23);
		UIStyler.setInputTextStyle(theme, tipText);
		
		targetText.setText(targetFolder);
		targetText.setSelection(targetText.getCharCount());
		
		srcText.addModifyListener(e->{
			File parent = new File(targetText.getText());
			File child  = new File(srcText.getText());
			if (Utility.isParent(child, parent)) {
				tipText.setText(Constants.Strings.copyFromFolderTip0);
				okButton.setEnabled(false);
			} else if (Utility.isParent(parent, child)) {
				tipText.setText(Constants.Strings.copyFromFolderTip1);
				okButton.setEnabled(false);
			} else {
				tipText.setText("");
				targetText.setText(targetFolder + File.separator 
							+ new File(srcText.getText()).getName());
				okButton.setEnabled(true);
			}
			srcText.setSelection(srcText.getCharCount());
		});
		
		okButton.addListener(SWT.Selection, e->{
			result = srcText.getText();
			shell.dispose();
		});
		
		expButton.addListener(SWT.Selection, e->{
			DirectoryDialog dialog = new DirectoryDialog(shell);
			String path = dialog.open();
			if (path != null) {
				srcText.setText(path);
			}
		});
	}
}

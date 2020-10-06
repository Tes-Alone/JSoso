package org.lightcodex.ui;

import java.time.LocalDateTime;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.lightcodex.Config;
import org.lightcodex.Constants;
import org.lightcodex.theme.Theme;
import org.lightcodex.ui.util.UIStyler;
import org.lightcodex.util.Utility;

public class DateTimeFormatDialog extends Dialog {

	private String result;
	private Shell shell;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public DateTimeFormatDialog(Shell parent) {
		super(parent, SWT.NONE);
		setText(Constants.Strings.dateTimeFormatDialog);
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

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setSize(261, 300);
		shell.setText(getText());
		shell.setLayout(null);
		
		Theme theme = Config.getInstance().getTheme();
		UIStyler.setCompositeStyle(theme, shell);
		
		List formatList = new List(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		formatList.setBounds(0, 0, 255, 211);
		UIStyler.setListStyle(theme, formatList);
		fillFormatList(formatList);
		
		Button okBut = new Button(shell, SWT.NONE);
		okBut.setBounds(82, 225, 80, 27);
		okBut.setText(Constants.Strings.Ok);
		UIStyler.setDefaultButtonStyle(theme, okBut);
		okBut.addListener(SWT.Selection, e->{
			result = formatList.getSelection()[0];
			shell.dispose();
		});
		
		shell.setDefaultButton(okBut);
	}

	private void fillFormatList(List formatList) {
		LocalDateTime dateTime = LocalDateTime.now();
		java.util.List<String> dateTimeList = 
					Utility.dateTimeFormat(dateTime, Config.getInstance().getLocale());
		for (String str : dateTimeList) {
			formatList.add(str);
		}
		formatList.setSelection(0);
	}
}

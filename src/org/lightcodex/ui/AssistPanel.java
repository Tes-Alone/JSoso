package org.lightcodex.ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;
import org.lightcodex.Config;
import org.lightcodex.Constants;
import org.lightcodex.theme.Theme;
import org.lightcodex.ui.util.MessageBoxEx;
import org.lightcodex.ui.util.SWTResourceManager;
import org.lightcodex.ui.util.UIStyler;
import org.lightcodex.util.OnClosedAssistPanelKeeper;
import org.lightcodex.util.Utility;
import org.sigmai.SigmaI;
import org.sigmai.lexer.Lexer;
import org.sigmai.lexer.StylePalette;
import org.sigmai.lexer.Token;

public class AssistPanel extends Composite {
	
	private class AssistLexer extends Lexer {
		
		@Override
		public StylePalette getStylePalette() {
			return null;
		}

		@Override
		protected void scan() {
			if (!isEnd() 
					&& (Character.isLetterOrDigit(current())||current()=='_')) {
				addWord(offset());
			} else {
				advance();
			}
		}
		
		private void addWord(int offset) {
			advance();
			while (!isEnd() && (Character.isLetterOrDigit(current())||current()=='_')) {
				advance();
			}
			String lexeme = subString(offset, offset());
			if (isHighLightWord(lexeme)) {
				Token token = new Token();
				token.isHighLightWord = true;
				token.type   = 0;
				token.start  = offset;
				token.length = offset() - offset;
				addToken(token);
			}
		}
	}
	
	private class AssistArea extends Composite {

		private SigmaI sigmai;
		
		private AssistArea(Composite parent) {
			super(parent, SWT.NONE);
			this.setLayout(new FillLayout());
			sigmai = new SigmaI(this, SigmaI.LINE_MARGIN | SigmaI.WIN7);
			Theme theme = Config.getInstance().getTheme();
			sigmai.setLexer(new AssistLexer());
			sigmai.setLineMarginBackground(SWTResourceManager.getColor(theme.getLineMarginBackground()));
			sigmai.setLineMarginForeground(SWTResourceManager.getColor(theme.getLineMarginForeground()));
			sigmai.setWordFixMarginBackground(SWTResourceManager.getColor(theme.getWordFixMarginBackground()));
			sigmai.setWordFixMarginForeground(SWTResourceManager.getColor(theme.getWordFixMarginForeground()));
			sigmai.setEditAreaBakcground(SWTResourceManager.getColor(theme.getEditAreaBackground()));
			sigmai.setEditAreaForeground(SWTResourceManager.getColor(theme.getEditAreaForeground()));
			sigmai.setHighLightWord(true);
			sigmai.setHighLightWordColor(SWTResourceManager.getColor(theme.getHighLightWordColor()));
			sigmai.getStyledText().setEditable(false);
			sigmai.setFont(SWTResourceManager.getFont("Courier New", 16, SWT.NORMAL));
			addDND(sigmai.getStyledText());
		}
		
		private void copy() {
			checkWidget();
			if (sigmai.getStyledText().getSelectionCount() == 0) {
				sigmai.getStyledText().selectAll();
				sigmai.getStyledText().copy();
				sigmai.getStyledText().setCaretOffset(0);
			} else {
				sigmai.getStyledText().copy();
			}
		}
		
		private void setText(String text) {
			checkWidget();
			sigmai.setText(text);
		}
		
		private void paste() {
			checkWidget();
			sigmai.getStyledText().setEditable(true);
			sigmai.paste();
			sigmai.getStyledText().setEditable(false);
		}
		
		private void focus() {
			checkWidget();
			sigmai.setFocus();
		}
		
		private boolean isEmpty() {
			checkWidget();
			return sigmai.getStyledText().getCharCount() == 0;
		}
		
		private String getText() {
			checkWidget();
			return sigmai.getStyledText().getText();
		}
		
		private void addDND(StyledText editor) {
			checkWidget();
			DragSource source = new DragSource(editor, DND.DROP_MOVE);
	        source.setTransfer(TextTransfer.getInstance());
	        source.addDragListener(new DragSourceListener() {
	        		private String data;
	        		
	                public void dragStart(DragSourceEvent event) {
	                	if (editor.getSelectionCount() == 0) {
	                		event.doit = false;
	                	} else {
	                		data = editor.getSelectionText();
	                	}
	                }
	                
	                public void dragSetData(DragSourceEvent event) {
	                	event.data = data;
	                	//System.out.println(event.data);
	                }
	                
	                public void dragFinished(DragSourceEvent event) {
	                }
	        });
		}
	}
	
	private CTabFolder tabFolder;
	private Text nameText;
	private Button newButton;
	private Button copyButton;
	private Button importFromFileButton;
	private Button importFromClipboardButton;
	
	public AssistPanel(Composite parent) {
		super(parent, SWT.BORDER);
		this.setLayout(new FormLayout());
		init();
		
		//setUpNoteHelpArea();
		this.reloadAssistArea();
		addDNDTo(tabFolder);
		this.getShell().addDisposeListener(e->{
			this.saveOpendAssistArea();
		});
	}

	/*
	private void setUpNoteHelpArea() {
		checkWidget();
		addAssistArea(Constants.Strings.assistPanel);
		AssistArea area = (AssistArea)tabFolder.getSelection().getControl();
		area.setText(Constants.Strings.assistNote);
	}*/
	
	private void addDNDTo(Control c) {
		checkWidget();
		DropTarget drop = new DropTarget(c, DND.DROP_COPY |DND.DROP_MOVE);
		drop.setTransfer(new Transfer[] {FileTransfer.getInstance(), TextTransfer.getInstance()});
		drop.addDropListener(new DropTargetAdapter() {
			public void drop(DropTargetEvent event) {
				// A drop has occurred, copy over the data
                if (event.data == null) { // no data to copy, indicate failure in event.detail
                        event.detail = DND.DROP_NONE;
                        return;
                }
                
                if (FileTransfer.getInstance().isSupportedType(event.currentDataType)) {
	                String[] data = (String[]) event.data;
	                List<File> files = new ArrayList<>();
	                
	                for (String str : data) {
	                	File file = new  File(str);
	                	if (file.isFile()) {
	                		files.add(file);
	                	}
	                }
	                addFiles(files);
                } else if (TextTransfer.getInstance().isSupportedType(event.currentDataType)) {
        			if (tabFolder.getItemCount() == 0) {
        				addAssistArea(Constants.Strings.drop + dropCount++);
        				AssistArea area = (AssistArea)tabFolder.getSelection().getControl();
        				area.setText((String)event.data);
        			} else {
        				AssistArea area = (AssistArea)tabFolder.getSelection().getControl();
        				if (area.isEmpty()) {
        					area.setText((String)event.data);
        				} else {
        					addAssistArea(Constants.Strings.drop + dropCount++);
        					area = (AssistArea)tabFolder.getSelection().getControl();
        					area.setText((String)event.data);
        				}
        			}
                }
 			}
		});
	}
	
	private static int clipCount = 1;
	private static int dropCount = 1; 

	private void init() {
		checkWidget();
		
		Theme theme = Config.getInstance().getTheme();
		UIStyler.setCompositeStyle(theme, this);
		
		tabFolder = new CTabFolder(this, SWT.TOP|SWT.CLOSE);
		FormData data = new FormData();
		data.top = new FormAttachment(0);
		data.left = new FormAttachment(0);
		data.right = new FormAttachment(100);
		data.bottom = new FormAttachment(100, -30);
		tabFolder.setLayoutData(data);
		//UIStyler.setAssistPanelStyle(theme, tabFolder);
		UIStyler.setTabFolderStyle(theme, tabFolder);
		
		Composite buttonComposite = new Composite(this, SWT.NONE);
		data = new FormData();
		data.top = new FormAttachment(tabFolder, 4);
		data.left = new FormAttachment(0);
		data.bottom = new FormAttachment(100);
		data.right = new FormAttachment(100);
		buttonComposite.setLayoutData(data);
		buttonComposite.setLayout(new FillLayout());
		UIStyler.setCompositeStyle(theme, buttonComposite);
		
		/*
		nameLabel = new Label(buttonComposite, SWT.LEFT);
		nameLabel.setText(Constants.Strings.nameLabel);
		UIStyler.setLabelStyle(theme, nameLabel);
		*/
		nameText = new Text(buttonComposite, SWT.SINGLE | SWT.BORDER);
		nameText.setText(Constants.Strings.nameLabel);
		UIStyler.setInputTextStyle(theme, nameText);
		
		newButton = new Button(buttonComposite, SWT.PUSH);
		newButton.setText(Constants.Strings.newButton);
		newButton.setEnabled(false);
		UIStyler.setButtonStyle(theme, newButton);
		
		copyButton = new Button(buttonComposite, SWT.PUSH);
		copyButton.setText(Constants.Strings.copyButton);
		copyButton.setEnabled(false);
		copyButton.addListener(SWT.Selection, e->{
			AssistArea area = (AssistArea)tabFolder.getSelection().getControl();
			area.copy();
		});
		UIStyler.setButtonStyle(theme, copyButton);
		
		/*
		for (int i=0; i<2; i++) {
			Label l = new Label(buttonComposite, SWT.NONE);
			UIStyler.setLabelStyle(theme, l);
		}*/
		
		importFromClipboardButton = new Button(buttonComposite, SWT.PUSH);
		importFromClipboardButton.setText(Constants.Strings.importFromClipboardButton);
		UIStyler.setButtonStyle(theme, importFromClipboardButton);
		
		importFromClipboardButton.addListener(SWT.Selection, e->{
			if (tabFolder.getItemCount() == 0) {
				addAssistArea(Constants.Strings.clipboard + clipCount++);
				AssistArea area = (AssistArea)tabFolder.getSelection().getControl();
				area.paste();
			} else {
				AssistArea area = (AssistArea)tabFolder.getSelection().getControl();
				if (area.isEmpty()) {
					area.paste();
				} else {
					addAssistArea(Constants.Strings.clipboard + clipCount++);
					area = (AssistArea)tabFolder.getSelection().getControl();
					area.paste();
				}
			}
			
		});
		
		importFromFileButton = new Button(buttonComposite, SWT.PUSH);
		importFromFileButton.setText(Constants.Strings.importFromFileButton);
		UIStyler.setButtonStyle(theme, importFromFileButton);
		importFromFileButton.addListener(SWT.Selection, e->{
			FileDialog dialog = new FileDialog(getShell(), SWT.OPEN|SWT.MULTI);
			dialog.open();
			String[] names = dialog.getFileNames();
			List<File> files = new ArrayList<>();
			for (String name : names) {
				String path = dialog.getFilterPath() + File.separator + name;
				files.add(new File(path));
			}
			addFiles(files);
		});
		
		newButton.addListener(SWT.Selection, e->{
			addAssistArea(nameText.getText());
		});
		
		nameText.addMouseListener(new MouseListener() {

			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}

			@Override
			public void mouseDown(MouseEvent e) {
				nameText.selectAll();
			}

			@Override
			public void mouseUp(MouseEvent e) {
			}
		});
		
		nameText.addModifyListener(e->{
			String text = nameText.getText();
			if (text.matches("[a-zA-Z0-9_\u4e00-\u9fff]+")) {
				newButton.setEnabled(true);
			} else {
				newButton.setEnabled(false);
			}
		});
		
		nameText.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				if (newButton.getEnabled()) {
					addAssistArea(nameText.getText());
				}
			}
			
		});
		
		itemDisposeListener = new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				if (tabFolder.getItemCount() == 0) {
					copyButton.setEnabled(false);
				}
			}
		};
	}
	
	private void saveOpendAssistArea() {
		checkWidget();
		OnClosedAssistPanelKeeper keeper = new OnClosedAssistPanelKeeper();
		keeper.clean();
		int itemCount = tabFolder.getItemCount();
		for (int i=0; i<itemCount; i++) {
			CTabItem item = tabFolder.getItem(i);
			String name = item.getText();
			String text = ((AssistArea)item.getControl()).getText();
			if (item == tabFolder.getSelection()) {
				name = name + Constants.Strings.assistPrefix;
			}
			keeper.addAssist(name, text);
		}
	}
	
	private void reloadAssistArea() {
		checkWidget();
		OnClosedAssistPanelKeeper keeper = new OnClosedAssistPanelKeeper();
		var assistList = keeper.listAssists();
		this.addFiles(assistList);
		if (onClosedItem != null) {
			tabFolder.setSelection(onClosedItem);
		}
	}
	
	private void addFiles(List<File> files) {
		checkWidget();
		for (File file : files) {
			addAssistArea(file.getName());
			AssistArea area = (AssistArea)tabFolder.getSelection().getControl();
			try {
				area.setText(Utility.read(file.getAbsolutePath()));
			} catch (IOException e) {
				MessageBoxEx.error(getShell(), 
						String.format(Constants.Strings.errFileOpenFailed, file.getAbsolutePath()));
			}
		}
	}
	
	private DisposeListener itemDisposeListener;
	private CTabItem onClosedItem;
	
	private void addAssistArea(String name) {
		checkWidget();
		CTabItem item = new CTabItem(tabFolder, SWT.NONE);
		if (name.endsWith(Constants.Strings.assistPrefix)) {
			name = name.substring(0, name.lastIndexOf(Constants.Strings.assistPrefix));
			onClosedItem = item;
		}
		item.setText(name);
		item.addDisposeListener(itemDisposeListener);
		AssistArea area = new AssistArea(tabFolder);
		item.setControl(area);
		tabFolder.setSelection(item);
		area.focus();
		
		if (!copyButton.getEnabled()) {
			copyButton.setEnabled(true);
		}
	}
}

package org.lightcodex;

import java.io.File;

import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.lightcodex.plugin.PlugIn;
import org.lightcodex.plugin.PlugInManager;
import org.lightcodex.project.Project;
import org.lightcodex.project.SourceFile;
import org.lightcodex.ui.EditMenu;
import org.lightcodex.ui.EditorFolder;
import org.lightcodex.ui.FileMenu;
import org.lightcodex.ui.MainWindow;
import org.lightcodex.ui.MenuBar;
import org.lightcodex.ui.PlugInMenu;
import org.lightcodex.ui.ProjectExplorer;
import org.lightcodex.ui.SearchMenu;
import org.lightcodex.ui.Splash;
import org.lightcodex.ui.StatusBar;
import org.lightcodex.ui.ToolBarEx;
import org.lightcodex.ui.bus.EditMenuBus;
import org.lightcodex.ui.bus.FileMenuBus;
import org.lightcodex.ui.bus.SearchMenuBus;
import org.lightcodex.ui.util.SWTResourceManager;
import org.llogger.LLogger;

public class LightCodeX {
	
	public static final String APP_NAME    = "JSoso";
	public static final String APP_VERSION = "2.0";
	
	public static int exitValue;
	
	public static final int REBOOT_ID = 111;
	
	public static void main(String[] args) {
		exitValue = 0;
		
		try {
			LLogger.setup();
			
			Splash splash = new Splash();
			splash.show();
			
			MainWindow win = MainWindow.getInstance();
			win.setSplash(splash);
			
			FileMenuBus.setUpBus();
			EditMenuBus.setUpBus();
			SearchMenuBus.setUpBus();
			
			MenuBar menuBar = win.getMenuBar();
			FileMenu fileMenu = menuBar.getFileMenu();
			EditMenu editMenu = menuBar.getEditMenu();
			SearchMenu searchMenu = menuBar.getSearchMenu();
			PlugInMenu plugInMenu = menuBar.getPlugInMenu();
			
			PlugInManager plugInMan = new PlugInManager();
			PlugIn[] plugins = plugInMan.listPlugIns();
			for (var p : plugins) {
				p.onAppCreate(plugInMenu);
			}
			
			ProjectExplorer proExplorer = win.getProjectExplorer();	
			EditorFolder  editorFolder = win.getEditorFolder();
			StatusBar statusBar = win.getStatusBar();
			ToolBarEx toolBarEx = win.getToolBarEx();
			
			FileMenuBus.getBus().setProjectExplorer(proExplorer);
			FileMenuBus.getBus().setEditorFolder(editorFolder);
			
			EditMenuBus.getBus().setEditorFolder(editorFolder);
			
			SearchMenuBus.getBus().setEditorFolder(editorFolder);
			SearchMenuBus.getBus().setProjectExplorer(proExplorer);
			
			proExplorer.addClickFileListener(e->{
				if (e.file instanceof SourceFile && e.doubleClick) {
					editorFolder.addEditor((SourceFile)e.file);
				} 
				if (!e.doubleClick){
					statusBar.setSelectionPath(e.file.getFullPath());
				}
			});
			
			proExplorer.addAddProjectListener(e->{
				fileMenu.enableItem(Constants.Strings.newFolderMenu);
				fileMenu.enableItem(Constants.Strings.newSourceMenu);
				fileMenu.enableItem(Constants.Strings.remvoeProjectMenu);
				fileMenu.enableItem(Constants.Strings.copyFromFolderMenu);
				
				toolBarEx.enableItem(Constants.Images.newSource);
			});
			
			proExplorer.addFileSelectionListener(e->{
				if (e.file instanceof Project) {
					fileMenu.enableItem(Constants.Strings.remvoeProjectMenu);
				} else {
					fileMenu.disableItem(Constants.Strings.remvoeProjectMenu);
				}
			});
			
			editorFolder.addEditorCloseListener(e->{
				if (e.remainedCount == 0) {
					fileMenu.disableItem(Constants.Strings.saveAsMenu);
					fileMenu.disableItem(Constants.Strings.saveMenu);
					fileMenu.disableItem(Constants.Strings.closeMenu);
					fileMenu.disableItem(Constants.Strings.closeAllMenu);
					
					editMenu.disableAll();	
					searchMenu.enableEditorOpenCloseGroup(false);
					
					SearchMenuBus.getBus().closeFindAndReplaceDialog();
					SearchMenuBus.getBus().closeRegexDialog();
					SearchMenuBus.getBus().closeReplaceInSelectionDialog();
					EditMenuBus.getBus().closeEncodeDialog();
					EditMenuBus.getBus().closeGotoLineDialog();
					
					win.setTitle(APP_NAME);
					statusBar.cleanAll();
					
					toolBarEx.disableItem(Constants.Images.save);
					toolBarEx.disableItem(Constants.Images.saveAs);
					toolBarEx.disableItem(Constants.Images.find);
					toolBarEx.disableItem(Constants.Images.undo);
					toolBarEx.disableItem(Constants.Images.redo);
					toolBarEx.disableItem(Constants.Images.copy);
					toolBarEx.disableItem(Constants.Images.cut);
					toolBarEx.disableItem(Constants.Images.paste);
				}
			});
			
			editorFolder.addEditorSelectListener(e->{
				if (e.editor.isSaved()) {
					fileMenu.disableItem(Constants.Strings.saveMenu);
					toolBarEx.disableItem(Constants.Images.save);
				} else {
					fileMenu.enableItem(Constants.Strings.saveMenu);
					toolBarEx.enableItem(Constants.Images.save);
				}
				
				if (e.editor.canRedo()) {
					editMenu.enableItem(Constants.Strings.redoMenu);
					toolBarEx.enableItem(Constants.Images.redo);
				} else {
					editMenu.disableItem(Constants.Strings.redoMenu);
					toolBarEx.disableItem(Constants.Images.redo);
				}
				
				if (e.editor.canUndo()) {
					editMenu.enableItem(Constants.Strings.undoMenu);
					toolBarEx.enableItem(Constants.Images.undo);
				} else {
					editMenu.disableItem(Constants.Strings.undoMenu);
					toolBarEx.disableItem(Constants.Images.undo);
				}
				
				win.setTitle(APP_NAME + " - " + e.editor.getSourceFile().getFullPath());
			});
			
			editorFolder.addEditorOpenListener(e->{
				fileMenu.enableItem(Constants.Strings.saveAsMenu);
				fileMenu.enableItem(Constants.Strings.closeMenu);
				fileMenu.enableItem(Constants.Strings.closeAllMenu);
				
				editMenu.enableEditorOpenCloseGroup(true);
				searchMenu.enableEditorOpenCloseGroup(true);
				
				toolBarEx.enableItem(Constants.Images.saveAs);
				toolBarEx.enableItem(Constants.Images.find);
				toolBarEx.enableItem(Constants.Images.paste);
				
				win.setTitle(APP_NAME + " - " + e.file.getFullPath());
			});
			
			proExplorer.addFileRenameListener(e->{
				editorFolder.retitleEditor(e.oldFile, e.newFile);
			});
			
			proExplorer.addDeleteFileListener(e->{
				editorFolder.removeEditor(e.deletedFile);
				if (e.remainedProjectCount == 0) {
					fileMenu.disableItem(Constants.Strings.newFolderMenu);
					fileMenu.disableItem(Constants.Strings.newSourceMenu);
					fileMenu.disableItem(Constants.Strings.remvoeProjectMenu);
					fileMenu.disableItem(Constants.Strings.copyFromFolderMenu);
					
					toolBarEx.disableItem(Constants.Images.newSource);
					statusBar.setSelectionPath("");
				}
			});
			
			proExplorer.addFlushProjectsListener(e->{
				if (e.remainedProjectCount == 0) {
					fileMenu.disableItem(Constants.Strings.newFolderMenu);
					fileMenu.disableItem(Constants.Strings.newSourceMenu);
					fileMenu.disableItem(Constants.Strings.remvoeProjectMenu);
					fileMenu.disableItem(Constants.Strings.copyFromFolderMenu);
					
					toolBarEx.disableItem(Constants.Images.newSource);
					statusBar.setSelectionPath("");
				}
			});
			
			proExplorer.addFocusListener(new FocusListener() {

				@Override
				public void focusGained(FocusEvent e) {
				}

				@Override
				public void focusLost(FocusEvent e) {
					statusBar.setSelectionPath("");
				}
			});
			
			// 接受拖放参数
			if (args.length >= 2) {
				for (int i=1; i<args.length; i++) {
					File check = new File(args[i]);
					if (check.exists() && check.isFile()) {
						editorFolder.addEditor(new SourceFile(check.getAbsolutePath()));
					}
				}
			}
			
			win.enterLoop();
			
			for (var p : plugins) {
				p.onAppDispose();
			}
		} catch (Exception e) {
			/*StackTraceElement[] stacks = e.getStackTrace();
			for (var s : stacks) {
				System.err.println(s);
			}*/
			e.printStackTrace();
		} finally {
			Constants.Images.disposeAll();
			SWTResourceManager.dispose();
			LLogger.getInstance().finalReport();
		}
	}
}

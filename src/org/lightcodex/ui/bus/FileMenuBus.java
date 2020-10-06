package org.lightcodex.ui.bus;

import java.io.File;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.lightcodex.Config;
import org.lightcodex.Constants;
import org.lightcodex.info.NewSourceInfo;
import org.lightcodex.project.Folder;
import org.lightcodex.project.Project;
import org.lightcodex.project.SourceFile;
import org.lightcodex.ui.ConfigDialog;
import org.lightcodex.ui.CopyFromFolderDialog;
import org.lightcodex.ui.EditorFolder;
import org.lightcodex.ui.NewFolderDialog;
import org.lightcodex.ui.NewSourceDialog;
import org.lightcodex.ui.ProjectExplorer;
import org.lightcodex.ui.util.MessageBoxEx;
import org.lightcodex.util.Utility;

public class FileMenuBus {
	
	private FileMenuBus() {}
	
	private static FileMenuBus self;
	
	public static synchronized void setUpBus() {
		if (self == null) self = new FileMenuBus();
	}

	public static FileMenuBus getBus() {
		return self;
	}

	private ProjectExplorer proExplorer;
	
	public void setProjectExplorer(ProjectExplorer proExplorer) {
		this.proExplorer = proExplorer;
	}
	
	private EditorFolder editorFolder;
	
	public void setEditorFolder(EditorFolder editorFolder) {
		this.editorFolder = editorFolder;
	}
	
	public void newProject() {
		DirectoryDialog dia = new DirectoryDialog(proExplorer.getShell());
		String directory = dia.open();
		if (directory != null) {
			Project project = new Project(directory);
			project.flush();
			proExplorer.addProject(project);
		}
	}
	
	public void newFolder() {
		Folder parent = proExplorer.getCurrentFolder();
		NewFolderDialog dialog = new NewFolderDialog(proExplorer.getShell(), SWT.NONE);
		dialog.setParentPath(parent.getFullPath());
		String folderPath= dialog.open();
		if (folderPath != null) {
			Folder folder = new Folder(folderPath);
			if (!folder.exists()) {
				if (folder.create()) {
					proExplorer.addAbstractFile(parent, folder);
				} else {
					MessageBoxEx.error(proExplorer.getShell(), 
									String.format(Constants.Strings.errFolderCreateFailed, 
											folder.getFullPath()));
				}
			}
		}
	}
	
	public void newSource() {
		Folder parent = proExplorer.getCurrentFolder();
		NewSourceDialog dialog = new NewSourceDialog(proExplorer.getShell());
		dialog.setParentPath(parent.getFullPath());
		NewSourceInfo info = dialog.open();
		if (info != null) {
			SourceFile file = new SourceFile(info.filePath);
			if (file.exists()) {
				MessageBoxEx.error(proExplorer.getShell(), String.format(Constants.Strings.errFileCreateFailed, file.getFullPath()));
				return;
			}
			try {
				Utility.createSrcFromTemplate(info.filePath, info.template, Config.getInstance().getLocale());
				proExplorer.addAbstractFile(parent, file);
			} catch (IOException e) {
				MessageBoxEx.error(proExplorer.getShell(), String.format(Constants.Strings.errFileCreateFailed, file.getFullPath()));
			}
		}
	}

	public void openFile() {
		FileDialog dialog = new FileDialog(proExplorer.getShell(), SWT.OPEN | SWT.MULTI);
		dialog.open();
		String[] files = dialog.getFileNames();
		for (String file : files) {
			SourceFile src = new SourceFile(dialog.getFilterPath()+File.separator+file);
			if (src.exists()) {
				editorFolder.addEditor(src);
			} else {
				MessageBoxEx.error(proExplorer.getShell(), 
						String.format(Constants.Strings.errFileOpenFailed, src.getFullPath()));
			}
		}
	}
	
	public void saveAs() {
		FileDialog dialog = new FileDialog(proExplorer.getShell(), SWT.SAVE);
		dialog.setOverwrite(true);
		String file = dialog.open();
		if (file != null) {
			editorFolder.getSelection().saveAs(file);
		}
	}
	
	public void save() {
		editorFolder.saveCurrent();
	}
	
	public void close() {
		editorFolder.closeCurrent();
	}
	
	public void closeAll() {
		editorFolder.closeAll();
	}
	
	public void config() {
		ConfigDialog dialog = new ConfigDialog(editorFolder.getShell());
		dialog.open();
	}
	
	public void removeProject() {
		proExplorer.removeCurrentProject();
	}
	
	public void openTerminal() {
		Folder folder = proExplorer.getCurrentFolder();
		String home = ".";
		if (folder != null) {
			home = folder.getFullPath();
		}
		String[] cmd = {"cmd", "/C", "start"};
		ProcessBuilder proBuilder = new ProcessBuilder(cmd);
		try {
			proBuilder.directory(new File(home));
			proBuilder.start();
		} catch (Exception e) {
			MessageBoxEx.error(proExplorer.getShell(), 
						Constants.Strings.errTerminalOpenFailed);
		}
	}

	public void openExplorer() {
		Folder folder = proExplorer.getCurrentFolder();
		String home = ".";
		if (folder != null) {
			home = folder.getFullPath();
		}
		String[] cmd = {"explorer", home};
		ProcessBuilder proBuilder = new ProcessBuilder(cmd);
		try {
			proBuilder.start();
		} catch (Exception e) {
			MessageBoxEx.error(proExplorer.getShell(), 
						Constants.Strings.errExplorerOpenFailed);
		}
	}

	public void openConfigFolder() {
		File configFolder = new File(Constants.Homes.configHome);
		String[] cmd = {"explorer", configFolder.getAbsolutePath()};
		ProcessBuilder proBuilder = new ProcessBuilder(cmd);
		try {
			proBuilder.start();
		} catch (Exception e) {
			MessageBoxEx.error(proExplorer.getShell(), 
						Constants.Strings.errExplorerOpenFailed);
		}
	}
	
	public void openPlugInFolder() {
		File folder = new File(Constants.Homes.pluginHome);
		String[] cmd = {"explorer", folder.getAbsolutePath()};
		ProcessBuilder proBuilder = new ProcessBuilder(cmd);
		try {
			proBuilder.start();
		} catch (Exception e) {
			MessageBoxEx.error(proExplorer.getShell(), 
						Constants.Strings.errExplorerOpenFailed);
		}
	}
	
	public void copyFromFolder() {
		CopyFromFolderDialog dialog = new CopyFromFolderDialog(proExplorer.getShell());
		Folder parent = proExplorer.getCurrentFolder();
		dialog.setTargetFolder(parent.getFullPath());
		String result = dialog.open();
		if (result != null) {
			proExplorer.copyFolder(parent, result);
		}
	}
}

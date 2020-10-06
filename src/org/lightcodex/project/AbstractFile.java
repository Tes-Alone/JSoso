package org.lightcodex.project;

import java.io.File;

public abstract class AbstractFile implements Comparable<AbstractFile> {
	
	protected File file;
	private String filePath;
	
	public AbstractFile(String filePath) {
		if (filePath == null)
			throw new NullPointerException();
		
		if (filePath.isEmpty())
			throw new IllegalArgumentException();
		
		this.filePath = filePath;
		this.file = new File(filePath);
	}
	
	public String getFullPath() {
		if (!file.exists()) return filePath;
		return file.getAbsolutePath();
	}
	
	public boolean exists() {
		return file.exists();
	}
	
	public String getName() {
		return file.getName();
	}
	
	public String getParentPath() {
		return file.getParent();
	}
	
	public boolean rename(String newName) {
		File newFile = new File(file.getParent()+File.separator+newName);
		if (newFile.exists()) return false;
		boolean result = file.renameTo(newFile);
		if (result) {
			file = newFile;
		}
		return result;
	}
	
	public File getFile() {
		return file;
	}
	
	public long lastModifyTime() {
		return file.lastModified();
	}
	
	public abstract boolean create();
	public abstract AbstractFile copy();
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (! (obj instanceof AbstractFile)) return false;
		return ((AbstractFile)obj).getFullPath().equals(this.getFullPath());
	}

	@Override
	public int compareTo(AbstractFile file) {
		if ((this instanceof Folder && file instanceof Folder) || 
				(this instanceof SourceFile && file instanceof SourceFile)) {
			return this.file.compareTo(file.file);
		}
		
		return (this instanceof Folder) ? -1 : 1;
	}
}

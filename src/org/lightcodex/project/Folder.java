package org.lightcodex.project;

import java.io.File;
import java.util.Set;
import java.util.TreeSet;

public class Folder extends AbstractFile {

	private Set<AbstractFile> children;
	
	public Folder(String filePath) {
		super(filePath);
		
		if (file.exists() && !file.isDirectory()) {
			throw new IllegalArgumentException();
		}
		
		this.children = new TreeSet<>();
	}
	
	public Folder(Folder folder) {
		super(folder.getFullPath());
	}
	
	public void addChild(AbstractFile file) {
		if (file == null)
			throw new NullPointerException();
		children.add(file);
	}
	
	public AbstractFile[] getChildren() {
		return children.toArray(new AbstractFile[children.size()]);
	}
	
	public void cleanChildren() {
		children.clear();
	}

	private void addChildren(Folder parent, File file) {
		File[] children = file.listFiles();
		for (File f : children) {
			if (f.isDirectory()) {
				Folder tmp = new Folder(f.getAbsolutePath());
				parent.addChild(tmp);
				addChildren(tmp, f);
			} else {
				parent.addChild(new SourceFile(f.getAbsolutePath()));
			}
		}
	}
	
	public void flush() {
		cleanChildren();
		addChildren(this, file);
	}
	
	@Override
	public boolean create() {
		return file.mkdirs();
	}

	@Override
	public AbstractFile copy() {
		return new Folder(this);
	}
}

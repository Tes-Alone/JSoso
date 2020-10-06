package org.lightcodex.project;

import java.io.File;
import java.io.IOException;

public class SourceFile extends AbstractFile {

	public SourceFile(String filePath) {
		super(filePath);
		
		if (file.exists() && !file.isFile()) {
			throw new IllegalArgumentException();
		}
	}
	
	public SourceFile(SourceFile srcFile) {
		super(srcFile.getFullPath()); 
	}

	public String getExtensionName() {
		return getName().toLowerCase().contains("makefile") ? "makefile" : 
					getName().substring(getName().lastIndexOf(".")+1);
	}

	@Override
	public boolean create() {
		try {
			boolean result = false;
			File parent = file.getParentFile();
			if (!parent.exists()) {
				result = parent.mkdirs();
			}
			if (result) {
				return file.createNewFile();
			}
			return false;
		} catch (IOException e) {
			return false;
		}
	}

	@Override
	public AbstractFile copy() {
		return new SourceFile(this);
	}
}

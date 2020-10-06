package org.lightcodex.event;

import org.lightcodex.project.AbstractFile;

public class DeleteFileEvent {
	public AbstractFile deletedFile;
	public int remainedProjectCount;
}

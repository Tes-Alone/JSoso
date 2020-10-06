package org.lightcodex.event;

import org.eclipse.swt.widgets.Event;
import org.lightcodex.project.AbstractFile;

public class ClickFileEvent extends Event {
	
	public AbstractFile file;
	public boolean doubleClick;
}

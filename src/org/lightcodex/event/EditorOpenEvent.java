package org.lightcodex.event;

import org.eclipse.swt.widgets.Event;
import org.lightcodex.project.AbstractFile;
import org.lightcodex.ui.editor.Editor;

public class EditorOpenEvent extends Event {
	public Editor editor;
	public AbstractFile file;
}

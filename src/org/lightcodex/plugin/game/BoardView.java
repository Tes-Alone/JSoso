package org.lightcodex.plugin.game;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public abstract class BoardView extends Canvas {
	
	public BoardView(Composite parent) {
		super(parent, SWT.NO_BACKGROUND);
	}
}

package org.lightcodex.plugin.game;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.lightcodex.ui.util.SWTResourceManager;

public class ScoreView extends Canvas {

	private int score;
	
	public ScoreView(Composite parent) {
		super(parent, SWT.NONE);
		this.setFont(SWTResourceManager.getFont("Courier New", 20, SWT.BOLD));
		this.addPaintListener(e->{
			e.gc.drawString(TinyGame.score + score, 10, 10);
		});
	}
	
	public void addScore(int score) {
		checkWidget();
		this.score += score;
		redraw();
	}
	
	public void clean() {
		checkWidget();
		this.score = 0;
		redraw();
	}
}

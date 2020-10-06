package org.lightcodex.plugin.game;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.lightcodex.Config;
import org.lightcodex.project.SourceFile;
import org.lightcodex.theme.Theme;
import org.lightcodex.ui.editor.EditorAdapter;
import org.lightcodex.ui.util.UIStyler;

public abstract class Game extends EditorAdapter {

	protected BoardView board;
	protected Composite composite;
	protected ScoreView scoreView;
	protected static TimeCounterView counter;
	
	public Game(Composite parent, SourceFile srcFile) {
		super(parent, srcFile);
		this.setLayout(new FillLayout());
		composite   = new Composite(this, SWT.NONE);
		Theme theme = Config.getInstance().getTheme();
		UIStyler.setCompositeStyle(theme, composite);
		composite.setLayout(null);
		counter = new TimeCounterView(composite);
		counter.setBounds(0, 0, 280, 40);
		counter.setBackground(composite.getBackground());
		scoreView = new ScoreView(composite);
		scoreView.setBounds(0, 50, 280, 40);
		scoreView.setBackground(composite.getBackground());
		setBoardView();
		composite.addPaintListener(e->{
			Point size = composite.getSize();
			Point s = board.getSize();
			Point center = new Point((size.x-s.x)/2, (size.y-s.y)/2);
			board.setLocation(center);
		});
	}
	
	@Override
	public boolean setFocus() {
		checkWidget();
		return board.setFocus();
	}
	
	protected abstract void setBoardView();
}

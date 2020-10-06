package org.lightcodex.plugin.game;

import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.lightcodex.project.SourceFile;

public class RandomNumber extends Game {

	private class NumberBox {

		int digit;
		int xPixel;
		
		public NumberBox(int offset, int gridWidth) {
			digit = 8;
			xPixel = 1 + offset*gridWidth;
		}
		
		public void randomize() {
			digit = Math.abs(new Random().nextInt()) % 10;
		}
	}
	
	private class RBoardView extends BoardView {
		BoardModel<NumberBox> bm;
		final int boardWidth  = 3;
		final int boardHeight = 1;
		Font numberFont;
		Color backColor;
		Color numberColor;
		Color eightColor;
		Color borderColor;
		int gridWidth;
		boolean stopRandomize = true;
		public RBoardView(Composite parent, int gridWidth) {
			super(parent);
			this.gridWidth = gridWidth;
			bm = new BoardModel<>(gridWidth, boardWidth, boardHeight);
			bm.setDataAt(0, 0, new NumberBox(0, gridWidth));
			bm.setDataAt(1, 0, new NumberBox(1, gridWidth));
			bm.setDataAt(2, 0, new NumberBox(2, gridWidth));
			numberFont = new Font(getDisplay(), "Microsoft Yahei", 90, SWT.NORMAL);
			backColor = getDisplay().getSystemColor(SWT.COLOR_BLACK);
			numberColor = getDisplay().getSystemColor(SWT.COLOR_GREEN);
			eightColor = getDisplay().getSystemColor(SWT.COLOR_RED);
			borderColor = getDisplay().getSystemColor(SWT.COLOR_WHITE);
			setSize(bm.getHorizontalPixel(), bm.getVerticalPixel());
			addPaintListener(e->{
				drawWorld(e.gc);
			});
			addDisposeListener(e->{
				numberFont.dispose();
			});
			addKeyListener(new KeyListener() {

				@Override
				public void keyPressed(KeyEvent e) {
					if (e.keyCode == SWT.CR) {
						if (stopRandomize) {
							stopRandomize = false;
						} else {
							stopRandomize = true;
							int eightCount = 0;
							for (int i=0; i<3; i++) {
								if (bm.getDataAt(i, 0).digit == 8) {
									eightCount++;
								}
							}
							scoreView.addScore(eightCount*30);
						}
					}
				}

				@Override
				public void keyReleased(KeyEvent e) {
				}
			});
			
			new Runnable() {

				@Override
				public void run() {
					if (!isDisposed()) {
						if (!stopRandomize) {
							for (int i=0; i<3; i++) {
								bm.getDataAt(i, 0).randomize();
								redraw();
							}
						}
						Display.getCurrent().timerExec(10, this);
					}
				}
			}.run();
		}

		private void drawWorld(GC gc) {
			gc.setFont(numberFont);
			gc.setBackground(backColor);
			gc.fillRectangle(this.getClientArea());
			
			gc.setForeground(borderColor);
			gc.drawRectangle(bm.getDataAt(0, 1).xPixel, 1, gridWidth-2, gridWidth-2);
			gc.drawRectangle(bm.getDataAt(1, 1).xPixel, 1, gridWidth-2, gridWidth-2);
			gc.drawRectangle(bm.getDataAt(2, 1).xPixel, 1, gridWidth-2, gridWidth-2);
			
			for (int i=0; i<3; i++) {
				int number = bm.getDataAt(i, 1).digit;
				if (number == 8) {
					gc.setForeground(eightColor);
				} else {
					gc.setForeground(numberColor);
				}
				Point strSize = gc.textExtent(""+number);
				Point center = new Point((gridWidth-strSize.x)/2, (gridWidth-strSize.y)/2);
				gc.drawString(""+number, center.x+bm.getDataAt(i, 0).xPixel, center.y);
			}
		}
	}
	
	public RandomNumber(Composite parent, SourceFile src) {
		super(parent, src);
	}

	@Override
	protected void setBoardView() {
		board = new RBoardView(composite, 300); 
	}
}

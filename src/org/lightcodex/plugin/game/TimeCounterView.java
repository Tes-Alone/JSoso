package org.lightcodex.plugin.game;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.lightcodex.ui.util.SWTResourceManager;

public class TimeCounterView extends Canvas {

	private static TimeCounter counter = new TimeCounter();
	private static Timer timer = null;
	
	public TimeCounterView(Composite parent) {
		super(parent, SWT.NONE);
		counter.incUseCount();
		setFont(SWTResourceManager.getFont("Courier New", 20, SWT.BOLD));
		this.addPaintListener(e->{
			int m = counter.getMinute(); 
			if (m!=0 && (m%5==0)) {
				e.gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_RED));
			}
			e.gc.drawString(TinyGame.time + counter, 10, 10);
		});
		this.addDisposeListener(e->{
			counter.decUseCount();
		});
		new Runnable () {
			public void run() {
				if (!isDisposed()) {
					redraw();
					Display.getDefault()
						.timerExec(1000, this);
				}
			}
		}.run();
		
		if (timer == null) {
			timer = new Timer();
			TimerTask task = new TimerTask() {
				public void run() {
					if (counter.getUseCount()!=0) {
						counter.incOneSecond();
					} else {
						timer.cancel();
						timer = null;
					}
				}
			};
			timer.schedule(task, 0, 1000);
		}
	}
	
	@Override
	public void setBackground(Color back) {
		checkWidget();
		if (back!=null && !back.isDisposed()) {
			super.setBackground(back);
		}
	}
	
}

package org.lightcodex.plugin.game;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.lightcodex.project.SourceFile;

public class Snake extends Game {

	enum Direction {LEFT, RIGHT, TOP, BOTTOM}
	
	private class Snakez {
		private LinkedList<Point> body;
		private int bw;
		private int bh;
		private Direction d;
		
		Snakez(int bw, int bh) {
			this.bw = bw;
			this.bh = bh;
			reset();
		}
		
		void reset() {
			body = new LinkedList<>();
			int x = (bw - 5) / 2;
			int y = (bh - 1) / 2;
			body.add(new Point(x+4, y));
			body.add(new Point(x+3, y));
			body.add(new Point(x+2, y));
			body.add(new Point(x+1, y));
			body.add(new Point(x, y));
			d = Direction.RIGHT;
		}
		
		void setDirection(Direction d) {
			this.d = d;
		}
		
		void growUp() {
			Point tail = body.get(body.size()-1);
			Point beforeTail = body.get(body.size()-2);
			if (tail.x == beforeTail.x-1) {
				body.add(new Point(tail.x+1, tail.y));
			} else if (tail.x == beforeTail.x+1) {
				body.add(new Point(tail.x-1, tail.y));
			} else if (tail.y == beforeTail.y-1) {
				body.add(new Point(tail.x, tail.y+1));
			} else if (tail.y == beforeTail.y+1) {
				body.add(new Point(tail.x, tail.y-1));
			}
		}
		
		Point getHead() {
			return body.getFirst();
		}
		
		Point getTail() {
			return body.getLast();
		}
		
		boolean tryMove() {
			switch (d) {
			case LEFT:
				return moveToLeft();
			case RIGHT:
				return moveToRight();
			case TOP:
				return moveToTop();
			case BOTTOM:
				return moveToBottom();
			}
			return true; // never arrive here
		}
		
		boolean moveToLeft() {
			Point afterHead = body.get(1);
			Point head = body.get(0);
			if (afterHead.x == head.x-1) { 
				return moveToRight();
			}
			Point check = new Point(head.x-1, head.y);
			if (check.x==-1 || body.contains(check)) {
				return false;
			}
			body.removeLast();
			body.addFirst(new Point(head.x-1, head.y));
			return true;
		}
		
		boolean moveToRight() {
			Point afterHead = body.get(1);
			Point head = body.get(0);
			if (afterHead.x == head.x+1) { 
				return moveToLeft();
			}
			Point check = new Point(head.x+1, head.y);
			if (check.x==bw || body.contains(check)) {
				return false;
			}
			body.removeLast();
			body.addFirst(new Point(head.x+1, head.y));
			return true;
		}
		
		boolean moveToTop() {
			Point afterHead = body.get(1);
			Point head = body.get(0);
			if (afterHead.y == head.y-1) { 
				return moveToBottom();
			}
			Point check = new Point(head.x, head.y-1);
			if (check.y==-1 || body.contains(check)) {
				return false;
			}
			body.removeLast();
			body.addFirst(new Point(head.x, head.y-1));
			return true;
		}
		
		boolean moveToBottom() {
			Point afterHead = body.get(1);
			Point head = body.get(0);
			if (afterHead.y == head.y+1) { 
				return moveToTop();
			}
			Point check = new Point(head.x, head.y+1);
			if (check.y==bh || body.contains(check)) {
				return false;
			}
			body.removeLast();
			body.addFirst(new Point(head.x, head.y+1));
			return true;
		}
	}
	
	private boolean gamePaused;
	private boolean isStarted;
	
	private class SBoardView extends BoardView {
		final int boardWidth  = 50;
		final int boardHeight = 30;
		Snakez snake;
		Color backColor;
		Color snBodyColor;
		Color snHeadColor;
		Color foodColor;
		int gridWidth;
		Point nextFood;
		LinkedList<Point> ateFood;
		
		public SBoardView(Composite parent, int gridWidth) {
			super(parent);
			ateFood = new LinkedList<>();
			snake = new Snakez(boardWidth, boardHeight);
			this.gridWidth = gridWidth;
			backColor = getDisplay().getSystemColor(SWT.COLOR_BLACK);
			snHeadColor = getDisplay().getSystemColor(SWT.COLOR_YELLOW);
			snBodyColor = getDisplay().getSystemColor(SWT.COLOR_GREEN);
			foodColor = getDisplay().getSystemColor(SWT.COLOR_MAGENTA);
			nextFood = new Point(0,0);
			gamePaused = true;
			nextFood();
			this.setSize(boardWidth*gridWidth, boardHeight*gridWidth);
			this.addPaintListener(e->{
				drawWorld(e.gc);
			});
			this.addKeyListener(new KeyListener() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (!isStarted) {
						if (e.keyCode == SWT.CR) {
							start();
							return;
						}
						return;
					}
					
					if (e.character=='p'||e.character=='P') {
						gamePaused = !gamePaused;
					}
					
					if (gamePaused) {
						return;
					}
					
					switch (e.keyCode) {
					case SWT.ARROW_LEFT:
						snake.setDirection(Direction.LEFT);
						break;
					case SWT.ARROW_RIGHT:
						snake.setDirection(Direction.RIGHT);
						break;
					case SWT.ARROW_UP:
						snake.setDirection(Direction.TOP);
						break;
					case SWT.ARROW_DOWN:
						snake.setDirection(Direction.BOTTOM);
						break;
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
						if (!gamePaused) {
					        if (!snake.tryMove()) {
					        	gamePaused = true;
					        	isStarted  = false;
					        	Display.getCurrent().beep();
					        } else {
					        	eat();
					        	growUp();
					        	redraw();
					        }
						}
						Display.getDefault().timerExec(170, this);
					}
				}
			}.run();
		}
		
		void growUp() {
			checkWidget();
			if (ateFood.isEmpty()) return;
			Point firstAteFood = ateFood.getLast();
			if (firstAteFood.equals(snake.getTail())) {
				snake.growUp();
				ateFood.removeLast();
			}
		}

		void nextFood() {
			checkWidget();
			Random rand = new Random();
			do { 
				nextFood.x = Math.abs(rand.nextInt()) % boardWidth;
				nextFood.y = Math.abs(rand.nextInt()) % boardHeight;
			} while(snake.body.contains(nextFood));
		}
		
		void eat() {
			checkWidget();
			Point head = snake.getHead();
			if (head.equals(nextFood)) {
				ateFood.addFirst(new Point(nextFood.x, nextFood.y));
				nextFood();
				scoreView.addScore(30);
			}
		}
		
		void start() {
			checkWidget();
			setFocus();
			snake.reset();
			ateFood.clear();
			gamePaused = false;
			isStarted  = true;
		}
		
		private void drawWorld(GC gc) {
			checkWidget();
			gc.setBackground(backColor);
			gc.fillRectangle(this.getClientArea());
			
			drawSnake(gc);
			drawFood(gc);
			drawAteFood(gc);
		}
		private void drawAteFood(GC gc) {
			checkWidget();
			Iterator<Point> iter = ateFood.iterator();
			while (iter.hasNext()) {
				Point b = iter.next();
				if (b.equals(snake.getHead())) {
					gc.setBackground(snHeadColor);
				} else {
					gc.setBackground(snBodyColor);
				}
				gc.fillRoundRectangle(b.x*gridWidth-3, b.y*gridWidth-3, gridWidth+6, gridWidth+6, 4, 4);
			}
		}

		private void drawFood(GC gc) {
			checkWidget();
			gc.setBackground(foodColor);
			gc.fillRoundRectangle(nextFood.x*gridWidth, nextFood.y*gridWidth,
									gridWidth, gridWidth, 4, 4);
		}

		private void drawSnake(GC gc) {
			checkWidget();
			Point h = snake.body.getFirst();
			gc.setBackground(snHeadColor);
			drawSquare(gc, h.x, h.y);
			gc.setBackground(snBodyColor);
			Iterator<Point> iter = snake.body.iterator();
			iter.next();
			while (iter.hasNext()) {
				Point b = iter.next();
				drawSquare(gc, b.x, b.y);
			}
		}
		private void drawSquare(GC gc, int x, int y) {
			checkWidget();
			gc.fillRoundRectangle(x*gridWidth, y*gridWidth, gridWidth, gridWidth, 4, 4);
		}
	}
	
	public Snake(Composite parent, SourceFile srcFile) {
		super(parent, srcFile);
	}

	@Override
	protected void setBoardView() {
		board = new SBoardView(composite, 20);
	}

	
}

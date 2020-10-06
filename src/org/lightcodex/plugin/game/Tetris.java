package org.lightcodex.plugin.game;

import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.lightcodex.project.SourceFile;

public class Tetris extends Game {
	
	private enum Tetrominoes {
		NoShape, ZShape, SShape, LineShape, TShape, SquareShape, LShape, MirroredLShape
	}
	
	private static int[][][] coordsTable = {
	     { { 0, 0 },   { 0, 0 },   { 0, 0 },   { 0, 0 } },
	     { { 0, -1 },  { 0, 0 },   { -1, 0 },  { -1, 1 } },
	     { { 0, -1 },  { 0, 0 },   { 1, 0 },   { 1, 1 } },
	     { { 0, -1 },  { 0, 0 },   { 0, 1 },   { 0, 2 } },
	     { { -1, 0 },  { 0, 0 },   { 1, 0 },   { 0, 1 } },
	     { { 0, 0 },   { 1, 0 },   { 0, 1 },   { 1, 1 } },
	     { { -1, -1 }, { 0, -1 },  { 0, 0 },   { 0, 1 } },
	     { { 1, -1 },  { 0, -1 },  { 0, 0 },   { 0, 1 } }
	};
	
	private Color[] colorTable = {
		new Color(getDisplay(), 0,   0,   0),
		new Color(getDisplay(), 204, 102, 102), new Color(getDisplay(), 102, 204, 102),
		new Color(getDisplay(), 102, 102, 204), new Color(getDisplay(), 204, 204, 102),
		new Color(getDisplay(), 204, 102, 204), new Color(getDisplay(), 102, 204, 204),
		new Color(getDisplay(), 218, 170, 0)
	};
	
	private class Shape {
		int[][] coords;
		Tetrominoes pieceShape;
		Shape() {
			coords = new int[4][2];
			setShape(Tetrominoes.NoShape);
		}
		
		void setShape(Tetrominoes shape) {			
			for (int i = 0; i < 4 ; i++) {
			    for (int j = 0; j < 2; ++j) {
			        coords[i][j] = coordsTable[shape.ordinal()][i][j];
			    }
			}
			this.pieceShape = shape;
		}
		
		private void setX(int index, int x) { coords[index][0] = x; }
		private void setY(int index, int y) { coords[index][1] = y; }
		int x(int index) { return coords[index][0]; }
		int y(int index) { return coords[index][1]; }
		Tetrominoes getShape()  { return pieceShape; }
		
		void setRandomShape() {
			Random r = new Random();
			int x = Math.abs(r.nextInt()) % 7 + 1;
			Tetrominoes[] values = Tetrominoes.values(); 
			setShape(values[x]);
		}
		
		/*
		int minX() {
			int m = coords[0][0];
			
			for (int i=0; i < 4; i++) {
			   m = Math.min(m, coords[i][0]);
			}
			
			return m;
		}*/
		
		
		int minY() {
			int m = coords[0][1];
			
			for (int i=0; i < 4; i++) {
			   m = Math.min(m, coords[i][1]);
			}
			
			return m;
		}

		Shape rotateLeft() { 
			if (pieceShape == Tetrominoes.SquareShape)
			    return this;
			
			Shape result = new Shape();
			result.pieceShape = pieceShape;
			
			for (int i = 0; i < 4; ++i) {    
			    result.setX(i, y(i));
			    result.setY(i, -x(i));
			}
			
			return result;
		}

		Shape rotateRight() {
			if (pieceShape == Tetrominoes.SquareShape)
			    return this;
			
			Shape result = new Shape();
			result.pieceShape = pieceShape;
			
			for (int i = 0; i < 4; ++i) {
			    result.setX(i, -y(i));
			    result.setY(i, x(i));
			}
			 
			return result;
		}
	}
	
	private class TBoardView extends BoardView {
	    private final int BoardWidth = 10;
	    private final int BoardHeight = 22;
	    
		private BoardModel<Tetrominoes> bm; 
		private int curX = 0;
		private int curY = 0;
	    private boolean isFallingFinished = false;
	    private boolean isStarted = false;
	    private boolean isPaused  = true;
	    private Shape curPiece;
	    private Color blackColor;
	    private Color whiteColor;
	    
	    private Shape nextPiece;
		
		TBoardView(Composite parent, int gridWidth) {
			super(parent);
			setFocus();
			blackColor = getDisplay().getSystemColor(SWT.COLOR_BLACK);
			whiteColor = getDisplay().getSystemColor(SWT.COLOR_WHITE);
		    
			curPiece = new Shape();
			
		    nextPiece = new Shape();
		    nextPiece.setRandomShape();
		    
		    addKeyListener(new TAdapter());
			bm = new BoardModel<Tetrominoes>(gridWidth, BoardWidth, BoardHeight);
			this.setSize(bm.getHorizontalPixel() + (gridWidth * 5), bm.getVerticalPixel());
		    clearBoard();
		    
			this.addPaintListener(e->{
				doDrawing(e.gc);
			});
			new Runnable() {
				@Override
				public void run() {
					if (!isDisposed()) {
						if (!isPaused) {
					        if (isFallingFinished) {
					            isFallingFinished = false;
					            newPiece();
					        } else {   
					            oneLineDown();
					        }
						}
						Display.getDefault().timerExec(400, this);
					}
				}
			}.run();
		}

		
	    private int squareWidth() { return (int) bm.getHorizontalPixel() / BoardWidth; }
	    private int squareHeight() { return (int) bm.getVerticalPixel() / BoardHeight; }
	    private Tetrominoes shapeAt(int x, int y) { return bm.getDataAt(x, y);}
		
		private void drawSquare(GC g, int x, int y, Tetrominoes shape) {
			g.setBackground(colorTable[shape.ordinal()]);
		    g.fillRectangle(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);

	        g.setForeground(blackColor);
	        g.drawLine(x, y + squareHeight() - 1, x, y);
	        g.drawLine(x, y, x + squareWidth() - 1, y);

	        g.setForeground(whiteColor);
	        g.drawLine(x + 1, y + squareHeight() - 1,
	                         x + squareWidth() - 1, y + squareHeight() - 1);
	        g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1,
	                         x + squareWidth() - 1, y + 1);
		}

		
		private void doDrawing(GC gc) {
			Point size = getSize();
			Image buffer = new Image(getDisplay(), size.x, size.y);
			GC g = new GC(buffer);
			
			// fill background
			g.setBackground(blackColor);
			g.fillRectangle(0, 0, size.x, size.y);
	        
	        // draw bounds
			g.setForeground(whiteColor);
			g.drawLine(bm.getHorizontalPixel(), 0, bm.getHorizontalPixel(), size.y);
	        
	        int boardTop = (int) size.y - BoardHeight * squareHeight();
	        
	        // draw remainder square left on screen.
	        for (int i = 0; i < BoardHeight; ++i) {    
	            for (int j = 0; j < BoardWidth; ++j) {
	                Tetrominoes shape = shapeAt(j, BoardHeight - i - 1);
	                if (shape != Tetrominoes.NoShape)
	                    drawSquare(g, 0 + j * squareWidth(),
	                               boardTop + i * squareHeight(), shape);
	            }
	        }
	        
	        //In the second step, we paint the actual falling piece.
	        if (curPiece.getShape() != Tetrominoes.NoShape) {
	            
	            for (int i = 0; i < 4; ++i) {
	                
	                int x = curX + curPiece.x(i);
	                int y = curY - curPiece.y(i);
	                drawSquare(g, 0 + x * squareWidth(),
	                           boardTop + (BoardHeight - y - 1) * squareHeight(),
	                           curPiece.getShape());
	            }
	        }
	        
	        // draw next piece
			for (int i = 0; i < 4; ++i) {
                int x = bm.getWidth() + 2 + nextPiece.x(i);
                int y = bm.getHeight() - 4 - nextPiece.y(i);
                drawSquare(g, 0 + x * squareWidth(),
                           boardTop + (BoardHeight - y - 1) * squareHeight(),
                           nextPiece.getShape());
            }
	        
	        gc.drawImage(buffer, 0, 0);
	        buffer.dispose();
		}
		
	    void start()  {
	        setFocus();
	        isStarted = true;
	        isPaused  = false;
	        isFallingFinished = false;
	        clearBoard();

	        newPiece();
	        scoreView.clean();
	    }

	    private void pause()  {
	        isPaused = !isPaused;
	    }
		
		 private void removeFullLines() {		        
	        int numFullLines = 0;

	        for (int i = BoardHeight - 1; i >= 0; --i) {
	            boolean lineIsFull = true;

	            for (int j = 0; j < BoardWidth; ++j) {
	                if (shapeAt(j, i) == Tetrominoes.NoShape) {
	                    lineIsFull = false;
	                    break;
	                }
	            }

	            if (lineIsFull) {
	                ++numFullLines;
	                for (int k = i; k < BoardHeight - 1; ++k) {
	                    for (int j = 0; j < BoardWidth; ++j) {
	                    	bm.setDataAt(j, k, shapeAt(j, k+1));
	                    }
	                }
	            }
	        }

	        if (numFullLines > 0) {
	        	int score = numFullLines * 30;
	        	if (numFullLines == 4) {
	        		score += 100;
	        	}
	        	scoreView.addScore(score);
	            isFallingFinished = true;
	            curPiece.setShape(Tetrominoes.NoShape);
	            redraw();
	        }
	     }
		 
		 private boolean tryMove(Shape newPiece, int newX, int newY) {
	        for (int i = 0; i < 4; ++i) {
	            
	            int x = newX + newPiece.x(i);
	            int y = newY - newPiece.y(i);
	            
	            if (x < 0 || x >= BoardWidth || y < 0 || y >= BoardHeight)
	                return false;
	            
	            if (shapeAt(x, y) != Tetrominoes.NoShape)
	                return false;
	        }

	        curPiece = newPiece;
	        curX = newX;
	        curY = newY;

	        redraw();

	        return true;
	    }
		 
	    private void newPiece()  {	        
        	curPiece = nextPiece;
        	nextPiece = new Shape();
        	nextPiece.setRandomShape();
        	
	        curX = BoardWidth / 2 + 1;
	        curY = BoardHeight - 1 + curPiece.minY();

	        if (!tryMove(curPiece, curX, curY)) {
	            curPiece.setShape(Tetrominoes.NoShape);
	            isStarted = false;
	            isPaused  = true;
	            Display.getCurrent().beep();
	        }
	    }
	    
	    private void dropDown() {
	        int newY = curY;
	        
	        while (newY > 0) {
	            
	            if (!tryMove(curPiece, curX, newY - 1))
	                break;
	            --newY;
	        }
	        
	        pieceDropped();
	    }

	    private void oneLineDown()  {
	        if (!tryMove(curPiece, curX, curY - 1))
	            pieceDropped();
	    }
	    
	    private void clearBoard() {
	        for (int i=0; i<BoardWidth; ++i) {
		    	for (int j=0; j<BoardHeight; j++) {
		    		bm.setDataAt(i, j, Tetrominoes.NoShape);
		    	}
	        }
	    }
	    
	    private void pieceDropped() { 
	        for (int i = 0; i < 4; ++i) {
	            
	            int x = curX + curPiece.x(i);
	            int y = curY - curPiece.y(i);
	            bm.setDataAt(x, y, curPiece.getShape());
	        }

	        removeFullLines();

	        if (!isFallingFinished)
	            newPiece();
	    }
		
		class TAdapter extends KeyAdapter {
	        
	        @Override
	        public void keyPressed(KeyEvent e) {
	            if (e.keyCode == SWT.CR) {
	            	if (!isStarted) {
	            		start();
	            	}
	                return;
	            }
	        	
	            if (!isStarted || curPiece.getShape() == Tetrominoes.NoShape) {  
	                return;
	            }
	            
	            if (e.character=='p' || e.character=='P') {
	            	pause();
	            }

	            if (isPaused)
	                return;
	            int keycode = e.keyCode;
	            switch (keycode) {
	                
	            case SWT.ARROW_LEFT:
	                tryMove(curPiece, curX - 1, curY);
	                break;
	                
	            case SWT.ARROW_RIGHT:
	                tryMove(curPiece, curX + 1, curY);
	                break;
	                
	            case SWT.ARROW_DOWN:
	                tryMove(curPiece.rotateRight(), curX, curY);
	                break;
	                
	            case SWT.ARROW_UP:
	                tryMove(curPiece.rotateLeft(), curX, curY);
	                break;
	                
	            case ' ':
	                dropDown();
	                break;
	                
	            case 'd':
	                oneLineDown();
	                break;
	                
	            case 'D':
	                oneLineDown();
	                break;
	            }
	        }
	    }
	}
	
	public Tetris(Composite parent, SourceFile srcFile) {
		super(parent, srcFile);		
		this.addDisposeListener(e->{
			for (var c : colorTable) {
				c.dispose();
			}
		});
	}

	@Override
	protected void setBoardView() {
		checkWidget();
		board = new TBoardView(composite, 30);
	}
}

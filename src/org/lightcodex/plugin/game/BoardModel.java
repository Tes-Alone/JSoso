package org.lightcodex.plugin.game;

import org.eclipse.swt.graphics.Point;

public class BoardModel<E> {
	
	private int gridWidth;
	private int width;
	private int height;
	
	private Object[][] dataTable;
	
	public BoardModel(int gridWidth, int width, int height) {
		if (gridWidth < 1) gridWidth = 1;
		if (width < 1) width = 1;
		if (height < 1) height = 1;
		this.gridWidth = gridWidth;
		this.width  = width;
		this.height = height;
		dataTable = new Object[width][height];
	}
	
	public void fillData(E data) {
		for (int i=0; i<width; i++) {
			for (int j=0; j<height; j++) {
				dataTable[i][j] = data;
			}
		}
	}
	
	public void setDataAt(int x, int y, E data) {
		if (x < 0) x = 0;
		if (y < 0) y = 0;
		if (x >= width)  x = width-1;
		if (y >= height) y = height-1;
		dataTable[x][y] = data;
	}
	
	@SuppressWarnings("unchecked")
	public E getDataAt(int x, int y) {
		if (x < 0) x = 0;
		if (y < 0) y = 0;
		if (x >= width)  x = width-1;
		if (y >= height) y = height-1;
		return (E)dataTable[x][y];
	}
	
	@SuppressWarnings("unchecked")
	public E[][] getAll() {
		return (E[][]) dataTable;
	}
	
	public Point getPixelAt(int x, int y) {
		if (x < 0) x = 0;
		if (y < 0) y = 0;
		if (x >= width)  x = width-1;
		if (y >= height) y = height-1;
		return new Point(x*gridWidth, y*gridWidth);
	}
	
	public int getGridWidth() {
		return gridWidth;
	}
	
	public int getHorizontalPixel() {
		return width * gridWidth;
	}
	
	public int getVerticalPixel() {
		return height * gridWidth;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
}

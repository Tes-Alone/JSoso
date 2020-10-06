package org.lightcodex.plugin.game;

import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.lightcodex.Config;
import org.lightcodex.plugin.PlugIn;
import org.lightcodex.project.SourceFile;
import org.lightcodex.ui.EditorFolder;
import org.lightcodex.ui.MainWindow;
import org.lightcodex.ui.PlugInMenu;

public class TinyGame extends PlugIn {

	private static String plugInInfo = "Tiny Games, v1.0, by Tes Alone.";
	private static String tinyGameMenu = "&Tiny Games";
	private static String randomNumMenu = "&Random Number";
	private static String tetrisMenu = "&Tetris";
	private static String snakeMenu = "&Snake";
	private static String rTitle = "Random Number";
	private static String sTitle = "Snake";
	private static String tTitle = "Tetris";
	
	static String pressEnterStart = "Press Enter To Run";
	static String pressEnterStop  = "Press Enter To Stop";
	static String score = "Score: ";
	static String time = "Time: ";
	
	static String gameOver = "Game Over";
	
	static {
		if (Config.getInstance().getLocale()==Locale.CHINESE) {
			tinyGameMenu  = "小游戏(&T)";
			randomNumMenu = "随机数(&R)";
			snakeMenu = "贪吃蛇(&S)";
			tetrisMenu = "俄罗斯方块(&T)";
			rTitle = "随机数";
			sTitle = "贪吃蛇";
			tTitle = "俄罗斯方块";
			plugInInfo = "小游戏插件, v1.0, Tes Alone 制作.";
			
			pressEnterStart = "按回车键开始";
			pressEnterStop = "按回车键停止";
			score = "分数: ";
			time  = "时间: ";
			
			gameOver = "游戏结束";
		}
	}
	
	public TinyGame(String name) {
		super(name);
	}
	
	private RandomNumber rand   = null;
	private Tetris 	 	 tetris = null;
	private Snake 		 snake  = null;

	@Override
	public void onAppCreate(PlugInMenu plugInMenu) {
		Menu menu = plugInMenu.getMenu();
		Menu gameMenu = new Menu(menu.getShell(), SWT.DROP_DOWN);
		MenuItem gameItem = new MenuItem(menu, SWT.CASCADE);
		gameItem.setMenu(gameMenu);
		gameItem.setText(tinyGameMenu);
		
		MenuItem item = new MenuItem(gameMenu, SWT.PUSH);
		item.setText(randomNumMenu);
		item.addListener(SWT.Selection, e->{
			EditorFolder editorFolder = MainWindow.getInstance().getEditorFolder();
			if (rand == null) {
				rand = new RandomNumber(editorFolder.getTabFolder(),
								new SourceFile(rTitle));
				rand.addDisposeListener(ev->{rand=null;});
			}
			editorFolder.addEditor(rand);
		});
		
		item = new MenuItem(gameMenu, SWT.PUSH);
		item.setText(snakeMenu);
		item.addListener(SWT.Selection, e->{
			EditorFolder editorFolder = MainWindow.getInstance().getEditorFolder();
			if (snake == null) {
				snake = new Snake(editorFolder.getTabFolder(),
								new SourceFile(sTitle));
				snake.addDisposeListener(ev->{snake=null;});
			}
			editorFolder.addEditor(snake);
		});
		
		item = new MenuItem(gameMenu, SWT.PUSH);
		item.setText(tetrisMenu);
		item.addListener(SWT.Selection, e->{
			EditorFolder editorFolder = MainWindow.getInstance().getEditorFolder();
			if (tetris == null) {
				tetris = new Tetris(editorFolder.getTabFolder(),
								new SourceFile(tTitle));
				tetris.addDisposeListener(ev->{tetris=null;});
			}
			editorFolder.addEditor(tetris);
		});
	}

	@Override
	public void onAppDispose() {
	}

	@Override
	public String getPlugInInfo() {
		return plugInInfo;
	}

}

package org.lightcodex.completeword;

import java.io.IOException;

import org.lightcodex.Constants;
import org.lightcodex.util.Utility;
import org.llogger.LLogger;

public class PythonCompleteWord {
	
	private PythonCompleteWord() {}
	
	public static String[] wordList;
	
	static {
		String path = Constants.Homes.completeWordHome + "python.dat";
		try {
			wordList = Utility.read(path).replaceAll("\r\n|\r|\n", "\n").split("\n");
		} catch (IOException e) {
			LLogger.getInstance().logWarning("Complete words load failed!");
			wordList = new String[0];
		}
	}
}

package org.llogger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LLogger {

	private static final String setUpInfo = 
		"Little Logger setup done, start logging...";
	private static final String logIllegalFormat = 
		"ERROR: ===> Logger has been feed some illegal argumetns";
	private static final String logNull = 
		"ERROR: ===> Logger has been feed a null pointer!";
	private static final String logEmpty = 
		"ERROR: ===> Logger has been feed a empty log!";
	
	private static final DateTimeFormatter formatter = DateTimeFormatter.
			ofPattern("YYYY/dd/MM HH:mm:ss E", Locale.US);
	
	private LLogger() {
		logHeader();
	}
	
	private int errCount = 0;
	private int warningCount = 0;
	
	public void logOut(String log, Object... args) {
		System.out.println(currentDateTimeInfo());
		if (log == null) {
			errCount++;
			System.err.println(logNull);
			return;
		} 
		if (log.isEmpty()) {
			errCount++;
			System.err.println(logEmpty);
			return;
		}
		try {
			System.out.println("GOOD: --> " + fixCase(String.format(log, args)));
		} catch(RuntimeException e) {
			errCount++;
			System.err.println(logIllegalFormat);
		}
	}
	
	/**
	 * 单引号括起来的部分不改变大小写, 连同引号一起输出;
	 * 大括号括起来的部分不改变大小写, 但括号被忽略;
	 * 四个转义字符, \, ', {, }; 转义字符以 \ 开始.
	 * 
	 * log 首字母大写, 其它没有被引号括起来的部分, 会变为小写.
	 * */
	private String fixCase(String log) {
		StringBuilder sb = new StringBuilder();
		char[] chs = log.toCharArray();
		for (int i=0; i<chs.length; i++) {
			if (i == 0) {
				sb.append(Character.toUpperCase(chs[i]));
			} else if (chs[i] == '\'') {
				sb.append(chs[i]);
				i++;
				while (i<chs.length && chs[i]!='\'') {
					sb.append(chs[i]);
					i++;
				}
				if (i<chs.length) {
					sb.append(chs[i]);
				} else {
					throw new RuntimeException();
				}
			} else if (chs[i] == '{') {
				i++;
				while (i<chs.length && chs[i]!='}') {
					sb.append(chs[i]);
					i++;
				}
				if (i >= chs.length) {
					throw new RuntimeException();
				}
			} else if (chs[i]=='\\') {
				i++;
				if (i<chs.length) {
					switch(chs[i]) {
					case '\\':
					case '{':
					case '}':
					case '\'':
						sb.append(chs[i]);
						break;
					default:
						throw new RuntimeException();
					}
				} else {
					throw new RuntimeException();
				}
			} else {
				sb.append(Character.toLowerCase(chs[i]));
			}
		}
		return sb.toString();
	}
	
	public void logErr(String log, Object... args) {
		errCount++;
		System.err.println(currentDateTimeInfo());
		if (log == null) {
			System.err.println(logNull);
			return;
		} 
		if (log.isEmpty()) {
			System.err.println(logEmpty);
			return;
		}
		try {
			System.err.println("ERROR: ===> " + fixCase(String.format(log, args)));
		} catch (RuntimeException e) {
			System.err.println(logIllegalFormat);
		}
	}
	
	public void logWarning(String log, Object... args) {
		warningCount++;
		System.err.println(currentDateTimeInfo());
		if (log == null) {
			errCount++;
			System.err.println(logNull);
			return;
		} 
		if (log.isEmpty()) {
			errCount++;
			System.err.println(logEmpty);
			return;
		}
		try {
			System.err.println("WARNING: ==> " + fixCase(String.format(log, args)));
		} catch (RuntimeException e) {
			errCount++;
			System.err.println(logIllegalFormat);
		}
	}
	
	private static LLogger self;
	
	public static synchronized void setup() {
		if (self == null) self = new LLogger();
	}
	
	public static LLogger getInstance() {
		return self;
	}
	
	public void finalReport() {
		System.out.println(currentDateTimeInfo());
		System.out.print("Logging over, ");
		if (errCount!=0 || warningCount!=0) {
			System.out.println("Total " + 
						warningCount + " Warning, " +
							errCount + " Error.");
		} else {
			System.out.println("nothing wrong!");
		}
	}
	
	private void logHeader() {
		System.out.println(currentDateTimeInfo());
		System.out.println(setUpInfo);
	}
	
	private String currentDateTimeInfo() {
		LocalDateTime date = LocalDateTime.now();
		return "At " + date.format(formatter)  + ":";
	}

	
	public static void main(String[] args) {
		LLogger loger = new LLogger();
		loger.logOut("the quick fox jmp over the {LAZY DOG}!");
		loger.logErr("this is \\'A\" \"ERR\" log");
		loger.finalReport();
	}
}

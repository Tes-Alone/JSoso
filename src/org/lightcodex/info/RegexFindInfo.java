package org.lightcodex.info;

public class RegexFindInfo {
	public String regex;
	public String replacement;
	public boolean caseSensitive;
	public boolean wrap;
	public boolean dotAll;
	
	public RegexFindInfo copy() {
		RegexFindInfo copy = new RegexFindInfo();
		copy.regex = regex;
		copy.replacement = replacement;
		copy.caseSensitive = caseSensitive;
		copy.wrap = wrap;
		return copy;
	}
}

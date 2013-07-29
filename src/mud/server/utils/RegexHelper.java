package mud.server.utils;

import java.util.regex.Pattern;

public class RegexHelper {
	private static final String STRIP_NON_CHARS = "[^A-Za-z0-9]";
	
	
	public static String StripNonCharacters(String input) {
		Pattern pattern = Pattern.compile(STRIP_NON_CHARS);
		
		
		return input;
	}
	/**
	 * EOF
	 */
}

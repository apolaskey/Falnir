package mud.server.color;

public class AnsiCodes {
	
	/**
	 * ANSI Escape Character.
	 */
	public static final char ESCAPE = '\033';
	
	/**
	 * ANSI End of Line placer, this moves the cursor up one and adds a space
	 */
	public static final String MoveToEOL = " " + ESCAPE + "[1A";
	
	public static final String CRETURN = ESCAPE + "[13m" + ESCAPE + "[10m";
	
	/**
	 * Resets all color codes to the default set by user.
	 */
	public static final String DEFAULT = ESCAPE + "[0m";
	

	public static final String BOLD = ESCAPE + "[1m";
	
	/**
	 * Not supported in Telnet but works in Putty
	 */
	public static final String UNDERLINE = ESCAPE + "[4m";
	
	/**
	 * Not supported in most clients.
	 */
	public static final String BLINK = ESCAPE + "[5m";
	
	public static final String INVERSE = ESCAPE + "[7m";
	
	public static final String BLACK = ESCAPE + "[0;30m";
	public static final String GRAY = ESCAPE + "[1;30m";
	
	public static final String DARK_RED = ESCAPE + "[0;31m";
	public static final String BRIGHT_RED = ESCAPE + "[1;31m";
	
	public static final String DARK_GREEN = ESCAPE + "[0;32m";
	public static final String BRIGHT_GREEN = ESCAPE + "[1;32m";
	
	public static final String DARK_YELLOW = ESCAPE + "[0;33m";
	public static final String BRIGHT_YELLOW = ESCAPE + "[1;33m";
	
	public static final String DARK_BLUE = ESCAPE + "[0;34m";
	public static final String BRIGHT_BLUE = ESCAPE + "[1;34m";
	
	public static final String DARK_MAGENTA = ESCAPE + "[0;35m";
	public static final String BRIGHT_MAGENTA = ESCAPE + "[1;35m";
	
	public static final String DARK_CYAN = ESCAPE + "[0;36m";
	public static final String BRIGHT_CYAN = ESCAPE + "[1;36m";
	
	/**
	 * Dont use to reset color please.
	 */
	public static final String DARK_WHITE = ESCAPE + "[0;37m";
	/**
	 * Dont use to reset color please.
	 */
	public static final String BRIGHT_WHITE = ESCAPE + "[1;37m";
	
}

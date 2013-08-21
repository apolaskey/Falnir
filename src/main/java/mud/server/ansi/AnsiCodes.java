package mud.server.ansi;

/**
 * Contains all of the basic Telnet ANSI Codes
 * @author Andrew
 */
public class AnsiCodes {
	
	/**
	 * ANSI Escape Character.
	 */
	public static final char ESCAPE = '\033';
	
	/*=======================================================*/
	// CURSOR PLACEMENT
	/*=======================================================*/
	
	/**
	 * Resets all color codes to the default set by user.
	 */
	public static final String DEFAULT = ESCAPE + "[0m";
	
	/**
	 * ANSI End of Line placer, this moves the cursor up one and adds a space
	 */
	public static final String END_LINE = " " + ESCAPE + "[1A";
	
	/**
	 * New Line / Carriage Return
	 */
	public static final String NEWLINE = "\r\n";
	
	public static String ShiftRow(int row) {
		return ESCAPE + "[" + Integer.toString(row) + ";0H";
	}
	
	
	/*=======================================================*/
	// TEXT FORMATTING
	/*=======================================================*/
	
	/**
	 * I don't think it works? None of my test clients do anything
	 */
	public static final String BOLD = ESCAPE + "[1m";
	
	/**
	 * Not supported in Telnet but works in Putty
	 */
	public static final String UNDERLINE = ESCAPE + "[4m";
	
	/**
	 * Umm...dosn't blink but I have confirmed this is the code.
	 */
	public static final String BLINK = ESCAPE + "[5m";
	
	/**
	 * Inverts the background with the foreground.
	 */
	public static final String INVERSE = ESCAPE + "[7m";
	
	/*=======================================================*/
	// COLORS
	// I believe the below are self-explanatory
	/*=======================================================*/
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
	
	public static final String DARK_WHITE = ESCAPE + "[0;37m";
	public static final String BRIGHT_WHITE = ESCAPE + "[1;37m";
	
	/**
	 * EOF
	 */
}

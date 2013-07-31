package mud.server.core;

import mud.server.color.AnsiCodes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionStrings {
	private static final Logger logger = LoggerFactory.getLogger(ConnectionStrings.class);
	public final static String WelcomeArt = loadWelcomeArt();
	
	public final static String AuthUserName = "Who art thou?";
	public final static String AuthUserPass = "Ah! What is thy secret phrase?";
	
	public final static String AuthNewUser = "Hmmmm this one does not regonize thee. Is this really thou name?";
	public final static String AuthNewPass = "So that we may become bound; what is thy secret phrase?";
	
	// This is a by-law requirement for the US
	// It's not super important atm but I don't want to forget
	// If the user answers no we need to disconnect them asap
	// Possibly add the result to our DB for proof of record?
	public final static String AuthNewUserCOPPA = "Is thy over the age of thirteen?";
	
	/**
	 * Quick little helper to load in a raw text file and display it's contents.
	 * @return (String)
	 */
	private static String loadWelcomeArt() {
		String artString = "";
		try {
			logger.info("Loading the welcome screen art.");
			artString = new Scanner( new File("./Configs/WelcomeScreen.txt"), "US-ASCII" ).useDelimiter("\\A").next();
		} catch (FileNotFoundException e) {
			logger.warn("File not found for the Welcome Screen Art!");
		}
		return artString;
	}
}

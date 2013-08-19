package mud.commands;

import mud.server.core.PlayerSession;

/**
 * Command.java
 * 
 * Abstract base class for in-game commands. 
 *  
 * @author mcmillhj
 */

public abstract class Command {
	protected String name;
    protected String[] args; 
    
	/**
	 * Default constructor
	 * 
	 * Empty command with no parameters
	 */
	public Command() {
		this("");
	}
	
	/**
	 * Explicit constructor
	 * 
	 * @param name Name of the command
	 * @param params List of parameters that this command accepts
	 */
	public Command(String name) {
		this(name, new String[]{});
	}
	
	public Command(String name, String[] args) {
		this.name = name;
		this.args = args;
	}

	public String getName() {
		return this.name;
	}

	public String[] getArgs() {
		return this.args;
	}
	
	public abstract String help();
	public abstract String failureMessage();
	public abstract String unableToProcessMessage();
	public abstract String successMessage();
	public abstract boolean execute(PlayerSession session);
	
	public String toString() {
		return this.name;
	}
}

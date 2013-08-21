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

	public String getName() {
		return this.name;
	}

	public String[] getArgs() {
		return this.args;
	}
	
	public abstract String usageHelp();
	public abstract String failureMessage();
	public abstract String successMessage();
	public abstract boolean execute(PlayerSession session, String[] args);
	
	public String toString() {
		return this.name;
	}
}

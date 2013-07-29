package mud.commands;

import mud.commands.Result;
import mud.entities.player.Player;

import java.util.List;
import java.util.ArrayList;

/**
 * Command.java
 * 
 * Abstract base class for in-game commands. 
 *  
 * @author mcmillhj
 */

public abstract class Command {
	private String name;

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
		this.name = name;
	}

	/**
	 * All Commands must be able to execute 
	 */
	public abstract Result execute(Player player, List<String> params); 
	
	public String getName() {
		return this.name;
	}
	
	
	public String toString() {
		return this.name;
	}
}

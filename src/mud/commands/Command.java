package mud.commands;

import mud.commands.Result;

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
	private List<String> params;

	/**
	 * Default constructor
	 * 
	 * Empty command with no parameters
	 */
	public Command() {
		this("", new ArrayList<String>());
	}
	
	/**
	 * Explicit constructor
	 * 
	 * @param name Name of the command
	 * @param params List of parameters that this command accepts
	 */
	public Command(String name, List<String> params) {
		this.name   = name;
		this.params = params;
	}

	/**
	 * All Commands must be able to execute 
	 */
	public abstract Result execute(); 
	
	public String getName() {
		return this.name;
	}
	
	public List<String> getParameters() {
		return this.params;
	}
	
	public String toString() {
		return this.name + "\t" + params.toString();
	}
}

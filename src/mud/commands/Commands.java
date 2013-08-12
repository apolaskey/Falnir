package mud.commands;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import java.util.List;

import mud.entities.player.Player;

public final class Commands {
	
	/**
	 * Dispatch method for command execution
	 * 
	 * Uses reflection to 
	 * @param command
	 * @param p
	 * @param params
	 * @return
	 */
	public static String execute(String command, Player p, List<String> params) {
		Class<? extends Command> commandClass = commandMap.get(command);
		
		if(commandClass == null) {
			// throw new IllegalArgumentException("Invalid command " + command);
			return null; // for now
		}
	
		try {
			// grab the execute method for some subclass of Command
			Method m = commandClass.getMethod("execute", Player.class, Object[].class);
			
//			return (Result)m.invoke(commandClass.newInstance(), p, params);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	public static Map<String, Class<? extends Command>> commandMap = 
			ImmutableMap.of(
					"who", Who.class,
					"whois", WhoIs.class,
					"login", Login.class
			);
}

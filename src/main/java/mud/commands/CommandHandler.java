package mud.commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.StringUtils;

import mud.entities.player.Player;
import mud.server.core.PlayerSession;

public class CommandHandler {
	private static final Logger logger = LoggerFactory.getLogger(CommandHandler.class);
	private static Map<String, Command> commands = loadCommands();
	private PlayerSession playerSession;
	
	
	
	public CommandHandler(PlayerSession session) {
		playerSession = session;
	}
	
	/**
	 * Retrieve a Map of all the commands.
	 * @return (Map<String, Command>) Commands
	 */
	public static Map<String, Command> getCommands() {
		return commands;
	}
	
	/**
	 * Retrieve a command by name
	 * @param commandName
	 * @return (Command+) A Command object but can be casted to a higher type
	 */
	public Command lookUpCommand(String commandName) {
		// entire command
		String[] commandParts = commandName.split(" ");
		
		// isolate name and arguments
		String command     = commandParts[0];
		String[] arguments = Arrays.copyOfRange(commandParts, 1, commandParts.length);
		
		Command cmd = commands.get(command);
		
		if(cmd == null) {
			logger.warn("Attempted to grab an unknown command: {}", commandName);
		}
		else {
			cmd = commands.get("errorprompt");
		}
		
		return cmd;
	}
	

	
	/**
	 * Uses the Reflections lib. to retrieve all of our types
	 */
	private static Map<String, Command> loadCommands() {
		// Prevent Async issues
		if(commands == null) {
			logger.info("Auto-Loading all available commands...");
			Reflections reflections = new Reflections("mud.commands.input");
			Set<Class<? extends Command>> commandTypes = reflections.getSubTypesOf(Command.class);
			Map<String, Command> commandsLoaded = new HashMap<String, Command>();
			
			for(Class<? extends Command> cmd : commandTypes) {
				Command temp;
				
				try {
					temp = cmd.newInstance();
					logger.info("Loading command {} with name {}", temp.getClass().getSimpleName(), temp.getClass().getSimpleName().toLowerCase());
					commandsLoaded.put(temp.getClass().getSimpleName().toLowerCase(), temp);
				} catch (InstantiationException e) {
					logger.error("Failed to instantiate command!", e);
				} catch (IllegalAccessException e) {
					logger.error("Illegal Access on command!", e);
				}
				
			}
			
			logger.info("Auto-Loaded {} commands out of {} commands.", commandsLoaded.size(), commandTypes.size());
			return commandsLoaded;
		}
		return commands;
	}
	
	
}

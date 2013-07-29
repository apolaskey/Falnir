package mud.commands;

import java.util.List;

import mud.entities.player.Player;

public class CommandHandler {
	private Player player;
	private String commandName;
	private List<String> params;
	
	public CommandHandler(Player player, String commandName, List<String> params) {
		this.player      = player;
		this.commandName = commandName;
		this.params      = params;
	}
	
	/**
	 * Execute Command commandName for Player player
	 * 
	 * @return The result of execution a command in the context of a Player
	 */
	public Result execute() {
		return Commands.execute(commandName, player, params);
	}
}

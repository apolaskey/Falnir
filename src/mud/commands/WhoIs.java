package mud.commands;

import java.util.List;

import mud.entities.player.Player;
import mud.server.core.BaseMudGameServer;
import mud.server.core.ConnectionHandler;

public class WhoIs extends Command {
	public WhoIs(String playerName) {
		super("whois"); 
	}

	@Override
	public Result execute(Player player, List<String> params) {
		Result result = new Result();
		
		// grab connections in context of a Player 
		// filter by room? 
		//List<ConnectionHandler> connections = getConnectionsByPlayer(player);
		
		return result;
	}
}

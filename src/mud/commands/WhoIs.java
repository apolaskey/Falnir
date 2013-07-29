package mud.commands;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mud.entities.player.Player;
import mud.server.core.BaseMudGameServer;
import mud.server.core.ConnectionHandler;

public class WhoIs extends Command {

	public WhoIs(String playerName) {
		super("whois"); 
	}
	
	@Override
	public Result execute(Player p, List<String> params) {
		Result result = new Result();
		String playerName = params.get(0);
		
		// get connections in context of this player
		//List<ConnectionHandler> connections = getConnections(p);
		//String name = findPlayerByName(playerName, connections);
		
		return result;
	}

}

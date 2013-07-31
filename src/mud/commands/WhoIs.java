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
	
//		String playerName = params.get(0);
//		Player otherPlayer = UTILITY?.playerFromName(playerName);
//		result.addResult( otherPlayer.getSession().getIpAddress().toString() );
		
		return result;
	}
}

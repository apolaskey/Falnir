package mud.commands;

import java.util.List;

import mud.entities.player.Player;
import mud.server.core.ConnectionHandler;

public class Who extends Command {
	public Who(String command) {
		super("who");
	}
	
	@Override
	public Result execute(Player player, List<String> params) {
		Result result = new Result(); 

		List<ConnectionHandler> connections = player.getSession()
				                                    .getServer()
				                                    .getConnections();
		
		for(ConnectionHandler ch : connections) {
			result.addResult(
					ch.getSession()
					  .getRemoteAddress()
					  .toString()
			);
		}
		
		return result;
	}

}
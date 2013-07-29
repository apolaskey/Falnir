package mud.commands;

import java.util.ArrayList;
import java.util.List;

import mud.server.core.BaseMudGameServer;
import mud.server.core.ConnectionHandler;

public class Who extends Command {
	
	public Who() {
		super("who", new ArrayList<String>());
	}
	
	@Override
	public Result execute() {
		Result result = new Result();
		
		List<ConnectionHandler> connections = BaseMudGameServer.GetConnections();

		// Can't reach connections
		
		
		
		return result;
	}

}

package mud.server.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handle a soft shutdown
 * @author Andrew
 */
public class MudShutdownHook implements Runnable {
	
	private static final Logger logger = LoggerFactory.getLogger(MudShutdownHook.class);
	
	BaseMudGameServer server;
	
	MudShutdownHook(BaseMudGameServer server) {
		this.server = server;
	}
	
	@Override
	public void run() {
		logger.info("Closing server...");
		server.shutdown();
		Runtime.getRuntime().exit(0);
		logger.info("Server stopped successfully.");
	}

}

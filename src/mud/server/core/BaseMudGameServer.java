package mud.server.core;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import mud.server.authentication.AuthenticationDriver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public abstract class BaseMudGameServer {
	
	private static final Logger logger = LoggerFactory.getLogger(BaseMudGameServer.class);
	private IoAcceptor acceptor = new NioSocketAcceptor();
	private MudGameDriver gameLoop;
	
	// Use the Java.Util list not the Java.awt (Sillyness)
	private List<ConnectionHandler> connections = new ArrayList<ConnectionHandler>();	

	/**
	 * Apache Mina's IoAcceptor
	 * @return (IoAcceptor as NioSocketAcceptor)
	 */
	public IoAcceptor GetAcceptor() {
		return acceptor;
	}
	
	/**
	 * Game related Logic
	 * @return (MudGameDriver)
	 */
	public MudGameDriver GetGame() {
		return gameLoop;
	}
	
	/**
	 * Active Connections to the Server
	 * @return
	 */
	public List<ConnectionHandler> GetConnections() {
		return connections; // Should create our own list to add events
	}
	
	
	/**
	 * Base type for a Mud Game Server, will setup and prepare from configs.
	 */
	public BaseMudGameServer() {
		logger.info("Setting Configurations.");
		
		// Bind slf4j logging to Apache Mina Lib
		acceptor.getFilterChain().addLast("logger", MudConfig.Logging.GetFilter());
		acceptor.getFilterChain().addLast("codec", MudConfig.TextOutput.GetNewlineOutput());
		
		// Add our Connection Handler to Apache Mina's NIO
		acceptor.setHandler(new ConnectionHandler(this));
		
		// Set the maximum buffer input to take in
		acceptor.getSessionConfig().setReadBufferSize(2048);
		
		// Connection Handler's idle loop timing
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, MudConfig.SERVER_TICK);
        
        // Start it up!
        try {
            logger.info("Starting Server on port {}", Integer.toString(MudConfig.PORT));
        	gameLoop = new MudGameDriver(this); // Bind NIO Server to Game Loop
            acceptor.bind(new InetSocketAddress(MudConfig.PORT));
		} catch (IOException e) {
			e.printStackTrace();
			logger.trace("Port already in use!");
		}
	}
	/**
	 * EOF
	 */
}

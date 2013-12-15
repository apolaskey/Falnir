package falnir.server.core;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import falnir.server.ansi.AnsiCodes;
import falnir.server.commands.CommandDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineEncoder;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * Handles all of the information related to connecting and environment info.
 * @author Andrew
 */
public abstract class BaseMudGameServer {

	public static final int CPU_CORES = Runtime.getRuntime().availableProcessors();
	
	private static final Logger logger = LoggerFactory.getLogger(BaseMudGameServer.class);
	
	private IoAcceptor acceptor;
	private GameDriver gameLoop;
	private MudShutdownHook shutdownThread;
	
	/**
	 * Base type for a Mud Game Server, will setup and prepare from configs.
	 */
	public BaseMudGameServer() {
		logger.info("Setting Configurations.");
		
		// Add exit hook to ensure the server gets unbound
		shutdownThread = new MudShutdownHook(this);
		Runtime.getRuntime().addShutdownHook(new Thread(shutdownThread, shutdownThread.getClass().getName()));
		
		acceptor = new NioSocketAcceptor(CPU_CORES);
		logger.info("Using {} CPU's for processing.", CPU_CORES);

		// utilize different cpus for some tasks
		acceptor.getFilterChain().addLast("executor", new ExecutorFilter( Executors.newCachedThreadPool()));
		// Bind slf4j logging to Apache Mina Lib
		acceptor.getFilterChain().addLast("logger", MudConfig.Logging.getFilter());
//		acceptor.getFilterChain().addLast("codec",  MudConfig.TextOutput.getNewlineOutput());
		
		// decode falnir.mud.commands sent to server
		acceptor.getFilterChain().addLast("codec", 
				new ProtocolCodecFilter( 
						new TextLineEncoder(), new CommandDecoder()
				)
		);
		
		// Add our Connection Handler to Apache Mina's NIO
		acceptor.setHandler(new ConnectionHandler());
		
		// Set the maximum buffer falnir.mud.commands.input to take in
		acceptor.getSessionConfig().setReadBufferSize(2048);
		
		// Connection Handler's idle loop timing
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, MudConfig.SERVER_TICK);
        
        // Start it up!
        try {
            logger.info("Starting Server on port {}", Integer.toString(MudConfig.PORT));
        	gameLoop = new GameDriver(this); // Bind NIO Server to Game Loop
            acceptor.bind(new InetSocketAddress(MudConfig.PORT));
		} catch (IOException e) {
			logger.trace(e.getMessage());
		}
	}
	
	/**
	 * Send a message to all connected sessions.
	 * @param (String) message
	 */
	public void broadcastMessage(String message) {
		acceptor.broadcast(message + AnsiCodes.END_LINE);
	}
	
	public void shutdown() {
		acceptor.unbind();
	}
	
	/**
	 * Retrieves the current connection count.
	 * @return (int) Current Active IoSessions
	 */
	public int getCurrentConnections()
	{
		return acceptor.getManagedSessionCount();
	}
	
	/**
	 * Game related Logic
	 * @return (MudGameDriver) Active Game Loop
	 */
	public GameDriver getGame() {
		return gameLoop;
	}
}

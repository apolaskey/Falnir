package mud.server.core;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mud.auth.AuthenticationHandler;
import mud.auth.command.CommandDecoder;
import mud.server.authentication.AuthenticationDriver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineEncoder;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.statemachine.StateMachine;
import org.apache.mina.statemachine.StateMachineFactory;
import org.apache.mina.statemachine.StateMachineProxyBuilder;
import org.apache.mina.statemachine.annotation.IoFilterTransition;
import org.apache.mina.statemachine.context.IoSessionStateContextLookup;
import org.apache.mina.statemachine.context.StateContext;
import org.apache.mina.statemachine.context.StateContextFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public abstract class BaseMudGameServer {
	
	private static final Logger logger = LoggerFactory.getLogger(BaseMudGameServer.class);
	private IoAcceptor acceptor; // = new NioSocketAcceptor();
	private MudGameDriver gameLoop;
	
	private List<ConnectionHandler> connections = new ArrayList<ConnectionHandler>();	
	
	/**
	 * Base type for a Mud Game Server, will setup and prepare from configs.
	 */
	public BaseMudGameServer() {
		logger.info("Setting Configurations.");
		
		acceptor = new NioSocketAcceptor( Runtime.getRuntime().availableProcessors() );

		// utilize different cpus for some tasks
		acceptor.getFilterChain().addLast("executor", new ExecutorFilter( Executors.newCachedThreadPool()));
		// Bind slf4j logging to Apache Mina Lib
		acceptor.getFilterChain().addLast("logger", MudConfig.Logging.getFilter());
//		acceptor.getFilterChain().addLast("codec",  MudConfig.TextOutput.getNewlineOutput());
		
		// decode commands sent to server
		acceptor.getFilterChain().addLast("codec", 
				new ProtocolCodecFilter( 
						new TextLineEncoder(), new CommandDecoder()
				)
		);
		
		// handle auth
		acceptor.getFilterChain().addLast("authentication", createAuthenticationFilter());

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
	 * Apache Mina's IoAcceptor
	 * @return (IoAcceptor as NioSocketAcceptor)
	 */
	public IoAcceptor getAcceptor() {
		return acceptor;
	}
	
	/**
	 * Game related Logic
	 * @return (MudGameDriver)
	 */
	public MudGameDriver getGame() {
		return gameLoop;
	}
	
	/**
	 * Active Connections to the Server
	 * @return
	 */
	public List<ConnectionHandler> getConnections() {
		return connections; // Should create our own list to add events
	}
	
	public IoFilter createAuthenticationFilter() {
		StateMachine sm = StateMachineFactory.getInstance( 
					IoFilterTransition.class ).create(
							AuthenticationHandler.START, new AuthenticationHandler()
					);
		
		return new StateMachineProxyBuilder().setStateContextLookup(
				new IoSessionStateContextLookup(new StateContextFactory() {
					public StateContext create() {
						return new AuthenticationHandler.AuthenticationContext();
					}
				}, "authContext"))//.setIgnoreUnhandledEvents(true)
								  .setIgnoreStateContextLookupFailure(true)
								  .create(IoFilter.class, sm);
	}
	
	/**
	 * EOF
	 */
}

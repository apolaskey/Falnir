package mud.server.core;

import java.util.Date;
import java.util.Set;

import mud.server.color.AnsiCodes;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Defines the basic connection workflow
 * @author Andrew
 *
 */
public class ConnectionHandler extends IoHandlerAdapter {
	private static Logger logger = LoggerFactory.getLogger(ConnectionHandler.class);
	protected BaseMudGameServer server;
	protected IoSession currentSession;
	
	public IoSession GetSession() {
		return currentSession;
	}
	
	public ConnectionHandler(BaseMudGameServer server) {
		// So we can reference the server from each connection
		this.server = server;
	}
	
	/**
	 * Good place to grab user details, client, address, port, etc...
	 */
	@Override
	public void sessionCreated(IoSession session) {
		logger.info("Incoming connection on address {}", session.getRemoteAddress());
		server.GetConnections().add(this);
		logger.info("Current connections is now at {}", server.GetConnections().size());
	}
	
	/**
	 * User Authentication or relogin
	 */
	@Override
	public void sessionOpened(IoSession session) {
		logger.info("Anonymous user has established a connection.");
		session.write(ConnectionStrings.WelcomeArt);
		session.write(AnsiCodes.ESCAPE + "[9;0H"); // Fuck-it; force home row
		session.write(ConnectionStrings.AuthUserName + AnsiCodes.END_LINE);
	}
	
	
	@Override
    public void exceptionCaught(IoSession session, Throwable cause ) throws Exception {
        logger.trace(cause.getMessage());
    }		

	/**
	 * Handle input from the user.
	 */
    @Override
    public void messageReceived(IoSession session, Object message ) throws Exception {
    	// Parse command through IOC method?
        String str = message.toString();
        
        if(str.trim().equalsIgnoreCase("quit") || str.trim().equalsIgnoreCase("exit")) {
            session.close(true);
            return;
        }
        logger.info("Address {} sent {} to server.", session.getRemoteAddress(), str);
        str = AnsiCodes.DARK_RED + "Echo: " + AnsiCodes.BRIGHT_RED + message.toString() + AnsiCodes.DEFAULT;
        session.write(str + AnsiCodes.END_LINE);
    }

    /**
     * User's Game Loop essentially.
     */
    @Override
    public void sessionIdle(IoSession session, IdleStatus status ) throws Exception {
    	logger.info("Idle on address {}", session.getRemoteAddress());
    }
    
    /**
     * Invoked when the user disconnects.
     */
    @Override
    public void sessionClosed(IoSession session) {
    	logger.info("Address {} keep-alive state disconnected.", session.getRemoteAddress());
    	
    	while(!session.getCloseFuture().isClosed()) {
    		//No-op loop the session until it's closed.
    	}
    	logger.info("Address {} session has closed.", session.getRemoteAddress());
    	

    	// Before removing the player from the world we might want to get their status to prevent cheating
    	server.GetConnections().remove(this);
    	logger.info("Current connections is now at {}", server.GetConnections().size());
    }
}

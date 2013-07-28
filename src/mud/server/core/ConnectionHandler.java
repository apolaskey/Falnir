package mud.server.core;

import java.util.Date;
import java.util.Set;

import mud.server.color.AnsiColor;

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
		
	}
	
	/**
	 * TODO: Authenticate user here?
	 */
	@Override
	public void sessionOpened(IoSession session) {
		logger.info("Anonymous user has established a connection.");
		session.write(ConnectionStrings.Welcome);
	}
	
	
	@Override
    public void exceptionCaught(IoSession session, Throwable cause ) throws Exception {
        logger.trace(cause.getMessage());
    }		

	/**
	 * Incoming command from user.
	 * TODO: More stuff lol, right now just echoing out the inputted command
	 */
    @Override
    public void messageReceived(IoSession session, Object message ) throws Exception {
    	// Parse command through IOC method?
        String str = message.toString();
        
        if(str.trim().equalsIgnoreCase("quit") || str.trim().equalsIgnoreCase("exit")) {
        	logger.info("Anonymous user has disconnected");
            session.close(true);
            return;
        }
        logger.info("Address {} sent {} to server.", session.getRemoteAddress(), str);
        session.write(AnsiColor.DARK_RED + "Echo: " + AnsiColor.BLINK + AnsiColor.BRIGHT_RED + message.toString());
    }

    /**
     * User's Game Loop essentially.
     */
    @Override
    public void sessionIdle(IoSession session, IdleStatus status ) throws Exception {
    	
    }
    
    /**
     * TODO: Clean-up used threads? Unsure if Mina will do this for us...
     */
    @Override
    public void sessionClosed(IoSession session) {
    	
    }
}

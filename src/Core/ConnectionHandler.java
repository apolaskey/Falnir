package Core;

import java.util.Date;

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
	
	/**
	 * Good place to grab user details, client, address, port, etc...
	 */
	@Override
	public void sessionCreated(IoSession session)
	{
		logger.info("Incoming connection on address {}", session.getLocalAddress());
		
	}
	
	/**
	 * TODO: Authenticate user here?
	 */
	@Override
	public void sessionOpened(IoSession session)
	{
		logger.info("Anonymous user has established a connection.");
		session.write(ConnectionStrings.Welcome);
	}
	
	
	@Override
    public void exceptionCaught( IoSession session, Throwable cause ) throws Exception
    {
        logger.trace(cause.getMessage());
    }		

	/**
	 * Incoming command from user.
	 * TODO: More stuff lol, right now just echoing out the inputted command
	 */
    @Override
    public void messageReceived( IoSession session, Object message ) throws Exception
    {
    	// Parse command through IOC method?
        String str = message.toString();
        
        if(str.trim().equalsIgnoreCase("quit") || str.trim().equalsIgnoreCase("exit")) {
        	logger.info("Anonymous user has disconnected");
            session.close(true);
            return;
        }
        
        session.write("Echo: " + message.toString());
    }

    /**
     * User's Game Loop essentially.
     */
    @Override
    public void sessionIdle( IoSession session, IdleStatus status ) throws Exception
    {
    	
    }
    
    /**
     * TODO: Clean-up used threads? Unsure if Mina will do this for us...
     */
    @Override
    public void sessionClosed(IoSession session)
    {
    	
    }
}

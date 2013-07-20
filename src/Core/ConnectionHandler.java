package Core;

import java.util.Date;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	 * Authenticate user here?
	 */
	@Override
	public void sessionOpened(IoSession session)
	{
		logger.info("Anon connected.");
		session.write("Welcome to Mina!");
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
        String str = message.toString();
        if(str.trim().equalsIgnoreCase("quit") || str.trim().equalsIgnoreCase("exit")) {
        	logger.info("Anon Disconnecting.");
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
     * Clean-up used threads? Unsure if Mina will do this for us...
     */
    @Override
    public void sessionClosed(IoSession session)
    {
    	
    }
}

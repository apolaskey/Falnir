package falnir.server.core;

import java.util.HashMap;
import java.util.Map;

import falnir.server.ansi.AnsiCodes;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Defines the basic connection workflow
 * @author Andrew
 *
 */
public class ConnectionHandler extends IoHandlerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(ConnectionHandler.class);
	
	private static Map<Long, PlayerSession> playerSessions = new HashMap<Long, PlayerSession>();

	/**
	 * Good place to grab user details, client, address, port, etc...
	 */
	@Override
	public void sessionCreated(IoSession session) {
		logger.info("Incoming connection on address {} using session {}", session.getRemoteAddress(), session.getId());
		
		logger.info("Retreiving connection details...");
		
		/*for(Map.Entry<Long, PlayerSession> pSession : playerSessions.entrySet())
		{
			logger.info("Scanning for previous connection...");
			String oldIp = pSession.getValue().getRemoteAddress().toString().split(":")[0];
			String newIp = session.getRemoteAddress().toString().split(":")[0];
			if(oldIp.equalsIgnoreCase(newIp))
			{
				logger.info("Reconnecting Player to Session {}", session.getId());
				break;
			}
		}*/
		
		logger.info("Creating a PlayerSession instance");
		playerSessions.put(session.getId(), new PlayerSession(session));
		session.setAttribute("state", MessageIoState.WAIT);
		
//		Session sessionH = HibernateDriver.OpenSession();
//		sessionH.beginTransaction();
		//java.util.List result = sessionH.createQuery("from PLAYER_DATA").list(); // Dosn't work...investigate
//		java.util.List result = sessionH.createSQLQuery("SELECT * FROM PLAYER_DATA").list(); // Does work -.-
//		logger.info("Players found: {}", result.size());
//		sessionH.close();
		
//		Player a = new Player();
		
	}
	
	/**
	 * User Authentication or re-login
	 */
	@Override
	public void sessionOpened(IoSession session) {
		logger.info("Displaying Welcome screen and asking for credentials.");
        session.write(ConnectionStrings.WELCOME_ART);
        session.write(AnsiCodes.ShiftRow(9)); // home row
        session.write(ConnectionStrings.AUTH_USER_NAME + AnsiCodes.END_LINE);
	}
	
	
	@Override
    public void exceptionCaught(IoSession session, Throwable cause ) throws Exception {
        logger.error("Exception: closing session...\n", cause);
        session.close(true);
    }		

	/**
	 * Handle falnir.mud.commands.input from the user.
	 */
    @Override
    public void messageReceived(IoSession session, Object message ) throws Exception {
       logger.info("Address {} sent {} to server.", session.getRemoteAddress(), message.toString());
       playerSessions.get(session.getId()).parseCommand((String)message);
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
    	logger.info("Session {} at {} keep-alive state disconnected.", session.getId(), playerSessions.get(session.getId()).getRemoteAddress());

    	while(!session.getCloseFuture().isClosed()) {
    		logger.info("Attempting to close session {} on {}", session.getId(), playerSessions.get(session.getId()).getRemoteAddress());
    	}
    	
    	logger.info("Address {} session has closed.", playerSessions.get(session.getId()).getRemoteAddress());
    	playerSessions.remove(session.getId());
    }
}

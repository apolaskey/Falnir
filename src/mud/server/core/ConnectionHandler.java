package mud.server.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import mud.commands.*;

import mud.entities.player.Player;
import mud.server.authentication.AuthenticationDriver;
import mud.server.color.AnsiCodes;
import mud.server.database.HibernateDriver;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;


import org.hibernate.Session;
import org.hibernate.mapping.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Defines the basic connection workflow
 * @author Andrew
 *
 */
public class ConnectionHandler extends IoHandlerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(ConnectionHandler.class);
	private static BaseMudGameServer server;

	private final Set<IoSession> sessions = 
			Collections.synchronizedSet(new HashSet<IoSession>());

	private final Set<String> users = 
			Collections.synchronizedSet(new HashSet<String>());
	
	public ConnectionHandler(BaseMudGameServer server) {
		// So we can reference the server from each connection
		ConnectionHandler.server = server;
	}

	/**
	 * Good place to grab user details, client, address, port, etc...
	 */
	@Override
	public void sessionCreated(IoSession session) {
		logger.info("Incoming connection on address {}", session.getRemoteAddress());

		
//		logger.info("Current connections is now at {}", server.getConnections().size());
		
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
        session.write(ConnectionStrings.WELCOME_ART);
        session.write(AnsiCodes.ESCAPE + "[9;0H"); // home row		
	}
	
	
	@Override
    public void exceptionCaught(IoSession session, Throwable cause ) throws Exception {
        logger.trace(cause.getMessage());
        logger.info("Exception: closing session...\n" + cause.getMessage());
        cause.printStackTrace();
        session.close(true);
    }		

	/**
	 * Handle input from the user.
	 */
    @Override
    public void messageReceived(IoSession session, Object message ) throws Exception {
       logger.info("Address {} sent {} to server.", session.getRemoteAddress(), message.toString());
       
       try {
    	   Command command = (Command)message; 
    	   String[] args   = command.getArgs();
    	   String user     = (String)session.getAttribute("user");
    	   String password; 
    	   
    	   if( command instanceof Login ) {
    		   if( user != null ) {
    			   session.write("User " + user + " already logged into this session.");
    			   return;
    		   }
    		  
    		   if( args.length == 2 ) {
        		   user     = args[0];
        		   password = args[1];
        		   
        		   // validate password
    		   }
        	   else {
    			   session.write("Invalid login command: " + command.usage());
    			   return;
    		   }

    		   if( users.contains(user) ) {
    			   session.write("The name " + user + " is already in use.");
    		   }
     		   
    		   sessions.add(session);
    		   session.setAttribute("user", user);
    		   
    		   users.add(user);
    		   logger.info("User " + user + " just connected with password " + password);
    		   session.write("User " + user + " just connected with password " + password);
    	   }
    	   else if( command instanceof Who ) {
    		   // Nothing for now
    	   }
    	   else if( command instanceof WhoIs ){
    		   // Nothing for now
    	   }
       }
       catch(Exception e) {
    	   logger.info("Exception: " + e.getMessage());
       }
       
//        if(str.trim().equalsIgnoreCase("quit") || str.trim().equalsIgnoreCase("exit")) {
//            session.close(true);
//            return;
//        }
        
//        str = AnsiCodes.DARK_RED + "Echo: " + AnsiCodes.BRIGHT_RED + message.toString() + AnsiCodes.DEFAULT;
//        session.write(str + AnsiCodes.END_LINE);
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
    	sessions.remove(session);

    	// Before removing the player from the world we might want to get their status to prevent cheating
    	server.getConnections().remove(this);
    	logger.info("Current connections is now at {}", server.getConnections().size());
    }
}

package falnir.server.core;

import java.net.SocketAddress;

import falnir.server.ansi.AnsiCodes;
import falnir.server.authentication.AuthenticationHandler;
import falnir.server.authentication.AuthenticationState;
import falnir.server.commands.Command;
import falnir.server.commands.CommandHandler;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerSession {
	private static final Logger logger = LoggerFactory.getLogger(PlayerSession.class);
	private IoSession ioSession;
	private SocketAddress remoteAddress;
	private CommandHandler commandHandler;
	private AuthenticationHandler authHandler;
	private Command waitCommand;
	private MessageIoState currentState;
	private boolean inGame;
	
	public PlayerSession(IoSession session) {
		ioSession = session;
		remoteAddress = ioSession.getRemoteAddress();
		commandHandler = new CommandHandler(this);
		authHandler = new AuthenticationHandler(this);
		currentState = MessageIoState.BLOCKED;
		inGame = false;
	}
	
	/**
	 * Retrieve the IoSession ID
	 * @return (long) IoSession ID
	 */
	public long getId() {
		return this.ioSession.getId();
	}
	
	/**
	 * Grabs the Connections IP Adress and port 
	 * @return (SocketAddress)
	 */
	public SocketAddress getRemoteAddress() {
		return this.remoteAddress;
	}
	
	/**
	 * Retrieves the current Messaging state.
	 * @return (MessageIoState) State
	 */
	public MessageIoState getState() {
		return this.currentState;
	}
	
	public void setStateToWait(Command command) {
		this.currentState = MessageIoState.WAIT;
		this.waitCommand = command;
	}
	
	public void setStateToBlocked() {
		this.currentState = MessageIoState.BLOCKED;
	}
	
	public void setStateToAsync() {
		this.currentState = MessageIoState.ASYNC;
		this.waitCommand = null;
	}
	
	public void setState(MessageIoState newState) {
		this.ioSession.setAttribute("state", newState);
	}
	
	public boolean inGame() {
		return this.inGame;
	}
	
	/**
	 * TODO: Create a buffered write to do a very large prompt; this will cutdown on tcp calls
	 * @param message
	 */
	public void write(String message) {
		this.ioSession.write(message);
	}
	
	/**
	 * Parse Messages coming from a player connection
	 * @param (String) command text from player
	 */
	public void parseCommand(String command) {
		
		// If the User is authorized process messages else do auth processing
		if(authHandler.getState() == AuthenticationState.SECURE) {
			// Begin Normal Message Processing
			switch(currentState) {
				case ASYNC:
					// Going wild and doing anything that your heart desires
					this.commandHandler.lookUpCommand(command).execute(this, command.split(" "));
					break;
				case BLOCKED:
					// Preventing user falnir.mud.commands.input
					this.ioSession.write("You can not do that right now." + AnsiCodes.END_LINE);
					break;
				case WAIT:
					// Waiting on command specific falnir.mud.commands.input
					this.waitCommand.execute(this, command.split(" "));
					break;
				default:
					// No-op
					break;
			}
		}
		else {
			this.authHandler.verifySession(command);
		}
	}
	

}

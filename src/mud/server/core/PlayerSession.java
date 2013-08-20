package mud.server.core;

import java.net.SocketAddress;
import java.util.Arrays;

import mud.commands.Command;
import mud.commands.CommandHandler;
import mud.server.ansi.AnsiCodes;
import mud.server.authentication.AuthenticationHandler;
import mud.server.authentication.AuthenticationState;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.StringUtils;

public class PlayerSession {
	private static final Logger logger = LoggerFactory.getLogger(PlayerSession.class);
	private IoSession ioSession;
	private SocketAddress remoteAddress;
	private CommandHandler commandHandler;
	private AuthenticationHandler authHandler;
	private Command waitCommand;
	private MessageIoState currentState;
	
	public PlayerSession(IoSession session) {
		ioSession = session;
		remoteAddress = ioSession.getRemoteAddress();
		commandHandler = new CommandHandler(this);
		authHandler = new AuthenticationHandler(this);
		currentState = MessageIoState.BLOCKED;
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
		
	}
	
	public void setStateToAsync() {
		
	}
	
	public void setState(MessageIoState newState) {
		this.ioSession.setAttribute("state", newState);
	}

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
					if(!StringUtils.isNullOrEmpty(command)) {
						this.commandHandler.lookUpCommand(command).execute(this, command.split(" "));
					}
					else {
						// TODO: Do "prompt" (basically reports out player stats)
					}
					break;
				case BLOCKED:
					// Preventing user input
					this.ioSession.write("You can not do that right now." + AnsiCodes.END_LINE);
					break;
				case WAIT:
					// Waiting on command specific input
					this.waitCommand.execute(this, command.split(" "));
					break;
				default:
					// No-op
					break;
			}
		}
		else {
			this.authHandler.verifySession();
		}
	}
	

}

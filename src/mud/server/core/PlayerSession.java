package mud.server.core;

import java.net.SocketAddress;
import java.util.Arrays;

import mud.commands.Command;

import org.apache.mina.core.session.IoSession;

public class PlayerSession {
	private IoSession ioSession;
	private SocketAddress remoteAddress;
	private Command command;
	
	public PlayerSession(IoSession session) {
		ioSession = session;
		remoteAddress = ioSession.getRemoteAddress();
	}
	
	/**
	 * Retrieve the IoSession ID
	 * @return (long) IoSession ID
	 */
	public long getId() {
		return ioSession.getId();
	}
	
	/**
	 * Grabs the Connections IP Adress and port 
	 * @return (SocketAddress)
	 */
	public SocketAddress getRemoteAddress() {
		return remoteAddress;
	}
	
	public State getState() {
		return (State)this.ioSession.getAttribute("state");
	}
	
	public void setState(State newState) {
		this.ioSession.setAttribute("state", newState);
	}

	public void write(String message) {
		this.ioSession.write(message);
	}
	
	public void parseCommand(Command command) { 
		write("Received command " + command + " with args " + Arrays.toString(command.getArgs()));
		switch( getState() ) {
			case ASYNC:	
				break;
			case BLOCKED:
				break;
			case WAIT:
				if(this.command == null) { this.command = command; }
				
				boolean r = command.execute(this);
				
				write("Login was " + (r ? "successful" : "not successful") );
				if( r ) {
					setState(State.ASYNC);
					command = null;
				}
				break;
			default:
				break;
		}
	}
	

}

package mud.server.core;

import java.net.SocketAddress;

import org.apache.mina.core.session.IoSession;

public class PlayerSession {
	private IoSession ioSession;
	private SocketAddress remoteAddress;
	
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
	
	public PlayerSession(IoSession session) {
		ioSession = session;
		remoteAddress = ioSession.getRemoteAddress();
	}
}

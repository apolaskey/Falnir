package mud.server.core;

import java.net.SocketAddress;

import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionHandler {
	private static final Logger logger = LoggerFactory.getLogger(SessionHandler.class);
	protected BaseMudGameServer server;
	protected ConnectionHandler connectionHandler;
	protected IoSession session;
	
	private IoSession GetIoSession() {
		return session;  //TODO: Ask Jolt...very confused as to why this is possible
	}
	/**
	 * Gets the User's remote address.
	 * @return (SocketAddress)
	 */
	public SocketAddress GetIpAddress() {
		return GetIoSession().getRemoteAddress();
	}
	
	public SessionHandler(BaseMudGameServer server, ConnectionHandler connectionHandler) {
		this.server = server;
		this.connectionHandler = connectionHandler;
		this.session = connectionHandler.currentSession;
	}
	
	/**
	 * Write out to the client asap.
	 * Because Mina is fully threaded things might not happen instantly.
	 * @param (String) Output text
	 */
	public void WriteOutput(String output) {
		
		GetIoSession().write(output);
	}
	
	/**
	 * Yeah this is confusing to all hell...almost opting for a state machine
	 */
	public String ReadInput() throws InterruptedException {
		String message = "";
		
		session.getConfig().setUseReadOperation(true);
		
		ReadFuture readFuture = session.read();

		
		message = readFuture.getMessage().toString();
		
		session.getConfig().setUseReadOperation(false);
		
		return message;
	}
	/**
	 * EOF
	 */
}

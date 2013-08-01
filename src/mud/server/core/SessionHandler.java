package mud.server.core;

import java.net.SocketAddress;

import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.buffer.IoBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionHandler {
	private static final Logger logger = LoggerFactory.getLogger(SessionHandler.class);
	private BaseMudGameServer server;
	private ConnectionHandler connectionHandler;
	private IoSession session;
	
	public SessionHandler(BaseMudGameServer server, ConnectionHandler connectionHandler, IoSession session) {
		this.server = server;
		this.connectionHandler = connectionHandler;
		this.session = session;
	}
	
	public BaseMudGameServer getServer() {
		return this.server;
	}
	
	public IoSession getIoSession() {
		return session;  //TODO: Ask Jolt...very confused as to why this is possible
	}
	
	/**
	 * Gets the User's remote address.
	 * @return (SocketAddress)
	 */
	public SocketAddress getIpAddress() {
		return this.session.getRemoteAddress();
	}
	
	/**
	 * Write out to the client asap.
	 * Because Mina is fully threaded things might not happen instantly.
	 * @param (String) Output text
	 */
	public void writeOutput(String output) {
		this.session.write(output);
	}
	
	/**
	 * Yeah this is confusing to all hell...almost opting for a state machine
	 */
	public String readInput() throws InterruptedException {
		Object object = null;
		
		session.getConfig().setUseReadOperation(true);
		
		ReadFuture readFuture = session.read();
		readFuture.await();
		
		try {
			object = readFuture.getMessage();
			logger.info("Future: " + object);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
//		session.write( ((IoBuffer)object).duplicate());
//		message = readFuture.getMessage().toString();
		
		session.getConfig().setUseReadOperation(false);
		
		return String.valueOf(object);
	}
	/**
	 * EOF
	 */
}

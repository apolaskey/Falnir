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
		return session; 
	}
	
	/**
	 * Gets the User's remote address.
	 * @return (SocketAddress)
	 */
	public SocketAddress getIpAddress() {
		return this.session.getRemoteAddress();
	}
	
	public void write(String output) {
		this.session.write(output);
	}
	
	public void setUserReadOperation(boolean value) {
		session.getConfig().setUseReadOperation(value);
	}
	
	/**
	 * Write out to the client asap.
	 * Because Mina is fully threaded things might not happen instantly.
	 * @param (String) Output text
	 */
	public void writeAsync(String output) {
		WriteFuture writeFuture = session.write(output);
		try {
			writeFuture = writeFuture.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Yeah this is confusing to all hell...almost opting for a state machine
	 */
	public String readAsync() throws InterruptedException {
		String message = "";
		
		ReadFuture readFuture = session.read();
		readFuture.awaitUninterruptibly();
		
		Object object = readFuture.getMessage();
		logger.info("Future: " + object);
		
		return String.valueOf(object);
	}
	/**
	 * EOF
	 */
}

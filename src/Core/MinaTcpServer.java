package Core;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LogLevel;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO: Create Config file to bind this nonsense
 * @author Andrew
 */
public class MinaTcpServer {

	private static final Logger logger = LoggerFactory.getLogger(MinaTcpServer.class);
	private static IoAcceptor server = new NioSocketAcceptor();
	
	/**
	 * Mina TCP Server.
	 * @return (IoAcceptor)
	 */
	public static IoAcceptor GetServer()
	{
		return server;
	}
	
	/**
	 * Main Loop baby.
	 */
	public static void main(String[] args) {
		logger.info("Setting Configurations.");
		server.getFilterChain().addLast( "logger", MudConfig.Logging.GetFilter());
		server.getFilterChain().addLast( "codec", MudConfig.TextOutput.GetNewlineOutput());
		
		// Set how we handle the connections
		server.setHandler(new ConnectionHandler());
		
		server.getSessionConfig().setReadBufferSize(2048);
		server.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, MudConfig.GAME_TICK);
        
        // Start it up!
        try {
            logger.info("Starting Server on port {}", Integer.toString(MudConfig.PORT));
            server.bind(new InetSocketAddress(MudConfig.PORT));
		} catch (IOException e) {
			e.printStackTrace();
			logger.trace("Port already in use!");
		}
	}
	// EOF
}
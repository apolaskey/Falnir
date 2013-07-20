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
 * Create Config file to bind this nonsense
 * @author Andrew
 *
 */
public class MinaMud {

	private static final int PORT = 9123;
	private static final int TICK = 16;
	private static final Logger logger = LoggerFactory.getLogger(MinaMud.class);
	public static IoAcceptor Server = new NioSocketAcceptor();
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) {
		Server.getFilterChain().addLast( "logger", MudConfig.Logging.GetFilter());
		Server.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new TextLineCodecFactory(Charset.forName("UTF-8"), LineDelimiter.CRLF, LineDelimiter.AUTO)));
		
		// Set how we handle the connections
		Server.setHandler(  new ConnectionHandler() );
		
		Server.getSessionConfig().setReadBufferSize( 2048 );
		Server.getSessionConfig().setIdleTime( IdleStatus.BOTH_IDLE, TICK );
        
        // Start it up!
        try {
            logger.info("Starting Server on port {}", Integer.toString(PORT));
			Server.bind( new InetSocketAddress(PORT) );
		} catch (IOException e) {
			e.printStackTrace();
			logger.trace("Port already in use!");
		}
	}

}

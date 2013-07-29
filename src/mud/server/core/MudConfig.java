package mud.server.core;

import java.nio.charset.Charset;
import java.util.SortedMap;

import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LogLevel;
import org.apache.mina.filter.logging.LoggingFilter;

/**
 * This should really be read from a file
 * @author Andrew
 *
 */
public class MudConfig {
	public final static int PORT = 9123;
	public final static int SERVER_TICK = 16;
	public final static int GAME_TICK = 1600;
	/**
	 * Logging Settings for Mina
	 * @author Andrew
	 */
	public static class Logging
	{
		public static LoggingFilter GetFilter()
		{
			LoggingFilter filter = new LoggingFilter();
			filter.setSessionCreatedLogLevel(LogLevel.NONE);
			filter.setSessionOpenedLogLevel(LogLevel.NONE);
			filter.setMessageReceivedLogLevel(LogLevel.NONE);
			filter.setSessionIdleLogLevel(LogLevel.NONE);
			filter.setSessionClosedLogLevel(LogLevel.NONE);
			filter.setMessageSentLogLevel(LogLevel.NONE);
			return filter;
		}
	}
	
	/**
	 * Console Stream Output Settings
	 * @author Andrew
	 */
	public static class TextOutput
	{
		public static ProtocolCodecFilter GetNewlineOutput()
		{
			ProtocolCodecFilter filter;
			// TextLineCodecFactory textFilter = new TextLineCodecFactory(Charset.forName("US-ASCII"), LineDelimiter.DEFAULT, LineDelimiter.DEFAULT);
			
			TextLineCodecFactory textFilter = new TextLineCodecFactory(Charset.forName("US-ASCII"));
			filter = new ProtocolCodecFilter(textFilter);
			return filter;
		}
	}
	//EOF
}

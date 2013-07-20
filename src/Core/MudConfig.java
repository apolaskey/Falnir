package Core;

import java.nio.charset.Charset;

import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LogLevel;
import org.apache.mina.filter.logging.LoggingFilter;

public class MudConfig {
	public final static int PORT = 9123;
	public final static int GAME_TICK = 16;
	/**
	 * Logging Settings for Mina
	 * @author Andrew
	 */
	public static class Logging
	{
		public static LoggingFilter GetFilter()
		{
			LoggingFilter filter = new LoggingFilter();
			filter.setMessageReceivedLogLevel(LogLevel.NONE);
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
			TextLineCodecFactory textFilter = new TextLineCodecFactory(Charset.forName("UTF-8"), LineDelimiter.CRLF, LineDelimiter.AUTO);
			filter = new ProtocolCodecFilter(textFilter);
			return filter;
		}
	}
	//EOF
}

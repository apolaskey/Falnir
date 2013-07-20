package Core;

import java.nio.charset.Charset;

import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LogLevel;
import org.apache.mina.filter.logging.LoggingFilter;

public class MudConfig {
	
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
	
	public static class Output
	{
		public static ProtocolCodecFilter GetOutputCodec()
		{
			ProtocolCodecFilter filter;
			TextLineCodecFactory textFilter = new TextLineCodecFactory(Charset.forName("UTF-8"), LineDelimiter.AUTO, LineDelimiter.AUTO);
			return null;
		}
	}
	
}

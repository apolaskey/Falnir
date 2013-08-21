package mud.commands;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import mud.commands.input.Commands;
import mud.server.ansi.AnsiCodes;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.filterchain.IoFilter.NextFilter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.textline.TextLineDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO: Instead of using this to try and parse our game commands we should let it act as a filter
 */
public class CommandDecoder extends TextLineDecoder {
	private static final Logger logger = LoggerFactory.getLogger(CommandDecoder.class);
	private List<String> lines = new LinkedList<String>();
	private static final String regexAllowedChars = "[^a-zA-Z0-9!<>.@\"'&]$";
	//private static final String regexStripAll = "[^A-Za-z 0-9 \\.,\\?'\"\"!@#\\$%\\^&\\*\\(\\)-_=\\+;:<>\\/\\\\|\\}\\{\\[\\]`~]*";
	private static final String regexStripAscii = "[^\\x20-\\x7E]";
	
	/*private static final ProtocolDecoderOutput decoder = new ProtocolDecoderOutput() {
		public void write(Object message) {
			lines.add((String)message);
		}
		public void flush(NextFilter nextFilter, IoSession session) {}
	};*/
	
	public CommandDecoder() {
		super(Charset.forName("US-ASCII"));
	}
	
	private String parseCommand(String line) throws Exception {
		// Return cleaned line
		return line.replaceAll(regexAllowedChars, "").replaceAll(regexStripAscii, "");
	}
	
	@Override
	public void decode(IoSession session, IoBuffer in, 
			           final ProtocolDecoderOutput out) {
		
		try {
			super.decode(session, in, new ProtocolDecoderOutput() {
				public void write(Object message) {
					lines.add((String)message);
				}
				public void flush(NextFilter nextFilter, IoSession session) {}
			});
		} catch (Exception e) {
			logger.error("Error encountered decoding command!", e);
		}
		
		for(String s : lines) {
			logger.info("Parsing command " + s);
			
			try {
				out.write(parseCommand(s));
			} catch (Exception e) {
				logger.info("Unexpected command entered.");
				out.write("error");
				return;
			}
		}
		
		lines.clear();
	}
	/**
	 * EOC
	 */
}

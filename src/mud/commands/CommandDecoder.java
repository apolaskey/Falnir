package mud.commands;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
	
	public CommandDecoder() {
		super( Charset.forName("ASCII") );
	}
	
	private Object parseCommand(String line) throws Exception {
		// entire command
		String[] commandParts = line.split(" ");
		
		// isolate name and arguments
		String command     = commandParts[0];
		String[] arguments = Arrays.copyOfRange(commandParts, 1, commandParts.length);
		
		// get command class for this command
		Class<? extends Command> c = Commands.commandMap.get(command);
	
		// return an instance of the class passing the args
		return Class.forName(c.getName())
				    .getConstructor( String[].class )
				    .newInstance( new Object[] { arguments });
	}
	
	@Override
	public void decode(IoSession session, IoBuffer in, 
			           final ProtocolDecoderOutput out) {
		final List<String> lines = new LinkedList<String>();
		
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
				out.write("Huh?");
				return;
			}
			
		}
	}
}

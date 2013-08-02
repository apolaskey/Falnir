package mud.auth.command;

import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.filterchain.IoFilter.NextFilter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.textline.TextLineDecoder;

public class CommandDecoder extends TextLineDecoder {
	
	public CommandDecoder() {
		super(Charset.forName("US-ASCII"));
	}
	
	private Object parseCommand(String line) throws Exception {
		String[] commandParts = line.split(" ");
		String commandName    = commandParts[0].toLowerCase();
		String arg            = commandParts.length > 0 ? commandParts[1] : null;
		
		if ( UserCommand.NAME.equals(commandName) ) {
			if ( arg == null ) {
				throw new Exception("No username specified");
			}
			
			return new UserCommand(arg);
		}
		else if ( PasswordCommand.NAME.equals(commandName) ) {
			if ( arg == null ) {
				throw new Exception("No password specified");
			}
			
			return new PasswordCommand(arg);			
		}
		else if ( QuitCommand.NAME.equals(commandName) ) {
			return new QuitCommand();
		}
		else {
			throw new Exception("Unknown command " + commandName);
		}
	}
	
	@Override
	public void decode(IoSession session, IoBuffer in, final ProtocolDecoderOutput out) 
			throws Exception {
		
		final List<String> lines = new LinkedList<String>();
		super.decode(session, in, new ProtocolDecoderOutput() {
			public void write(Object message) {
				lines.add((String)message);
			}
			public void flush(NextFilter nextFilter, IoSession session) {}
		});
		
		for ( String line : lines ) {
			out.write( parseCommand(line) );
		}
	}
}

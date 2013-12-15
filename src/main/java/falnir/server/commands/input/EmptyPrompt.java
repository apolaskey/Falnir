package falnir.server.commands.input;

import falnir.server.ansi.AnsiCodes;
import falnir.server.commands.Command;
import falnir.server.core.PlayerSession;

/**
 * Dummy command to use for the user sending nothing
 * @author Andrew
 */
public final class EmptyPrompt extends Command {

	public EmptyPrompt() {
		super();
		super.name = "";
	}
	
	@Override
	public String usageHelp() {
		return "help <command>" + AnsiCodes.END_LINE;
	}

	@Override
	public String failureMessage() {
		return successMessage();
	}

	@Override
	public String successMessage() {
		return "";
	}

	@Override
	public boolean execute(PlayerSession session, String[] args) {
		return true;
	}

}

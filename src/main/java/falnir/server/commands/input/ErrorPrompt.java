package falnir.server.commands.input;

import falnir.server.ansi.AnsiCodes;
import falnir.server.commands.Command;
import falnir.server.core.PlayerSession;

/**
 * Dummy command to use for errors.
 * @author Andrew
 */
public final class ErrorPrompt extends Command {

	@Override
	public String usageHelp() {
		return "";
	}

	@Override
	public String failureMessage() {
		return successMessage();
	}

	@Override
	public String successMessage() {
		return "Huh?" + AnsiCodes.END_LINE;
	}

	@Override
	public boolean execute(PlayerSession session, String[] args) {
		return true;
	}

}

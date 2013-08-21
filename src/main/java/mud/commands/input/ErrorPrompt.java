package mud.commands.input;

import mud.commands.Command;
import mud.server.ansi.AnsiCodes;
import mud.server.core.PlayerSession;

/**
 * Dummy command to use for errors.
 * @author Andrew
 */
public class ErrorPrompt extends Command {

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

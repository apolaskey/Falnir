package mud.commands.input;

import mud.commands.Command;
import mud.server.core.GameDriver;
import mud.server.core.PlayerSession;

public final class Who extends Command {

	@Override
	public String usageHelp() {
		return null;
	}

	@Override
	public String failureMessage() {
		return null;
	}

	@Override
	public String successMessage() {
		return null;
	}

	@Override
	public boolean execute(PlayerSession session, String[] args) {
		session.write("There are currently " + GameDriver.getServer().getCurrentConnections() + " users connected.");
		return true;
	}
}
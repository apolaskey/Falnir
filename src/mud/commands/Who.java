package mud.commands;

import mud.server.core.PlayerSession;

public class Who extends Command {
	public Who(String[] args) {
		super("who", args);
	}

	@Override
	public String help() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String failureMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String unableToProcessMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String successMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean execute(PlayerSession session) {
		// TODO Auto-generated method stub
		return false;
	}
}
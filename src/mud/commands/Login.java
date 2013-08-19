package mud.commands;

import mud.server.core.PlayerSession;

public class Login extends Command {

	public Login(String[] args) {
		super("login", args);
	}
	
	@Override
	public String help() {
		return "\n\tlogin <username> <password>\n";
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
		
		if( args.length != 2 ) { 
			session.write(unableToProcessMessage());
			return false;
		}
		
		String username = args[0];
		String password = args[1];
		
		return username.equals( new StringBuffer(password).reverse().toString() );
	}
}

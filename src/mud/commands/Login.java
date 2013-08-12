package mud.commands;

public class Login extends Command {

	public Login(String[] args) {
		super("login", args);
	}
	
	public String usage() {
		return "\n\tlogin <username> <password>\n";
	}
}

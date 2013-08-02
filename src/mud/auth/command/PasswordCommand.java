package mud.auth.command;

public class PasswordCommand extends Command {
	public static final String NAME = "password";
	private final String password; 
	
	public PasswordCommand(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String getName() {
		return NAME;
	}
}

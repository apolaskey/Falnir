package mud.auth.command;

public class UserCommand extends Command {
	public static final String NAME = "user";
	private final String username; 
	
	public UserCommand(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getName() {
		return NAME;
	}
}

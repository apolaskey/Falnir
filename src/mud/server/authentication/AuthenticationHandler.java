package mud.server.authentication;

import mud.server.core.BaseMudGameServer;

import com.google.inject.Inject;

public class AuthenticationHandler {
	protected AuthenticationState currentState;
	
	@Inject
	public AuthenticationHandler(BaseMudGameServer server) {
		
	}
	
	public boolean Authenticate(String userName, String password) {
		
		return false;
	}
}

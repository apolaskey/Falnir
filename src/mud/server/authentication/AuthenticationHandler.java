package mud.server.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mud.server.core.PlayerSession;

public class AuthenticationHandler {
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationHandler.class);
	private AuthenticationState currentState;
	private PlayerSession playerSession;
	
	public AuthenticationHandler(PlayerSession session) {
		playerSession = session;
		currentState = AuthenticationState.UNSECURE;
	}
	
	
	
	public AuthenticationState getState() {
		return currentState;
	}
	
	public void verifySession() {
		switch(currentState) {
			case SECURE:
				logger.trace("PlayerSession {} is secured; allowing message execution.", playerSession.getId());
				break;
			case TIMED_OUT:
				logger.info("PlayerSession {} has timed out; prompting to re-auth.", playerSession.getId());
				break;
			case UNSECURE:
				logger.info("PlayerSession {} has not been verified prompting for username and password.", playerSession.getId());
				break;
			default:
				// No-op
				break;
		}
	}
}

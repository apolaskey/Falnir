package falnir.server.authentication;

import falnir.server.ansi.AnsiCodes;
import falnir.server.core.ConnectionStrings;
import falnir.server.core.PlayerSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	public void verifySession(String command) {
		switch(currentState) {
			case SECURE:
				logger.trace("PlayerSession {} is secured; allowing message execution.", playerSession.getId());
				break;
			case TIMED_OUT:
				logger.info("PlayerSession {} has timed out; prompting to re-auth.", playerSession.getId());
				break;
			case UNSECURE:
				if(playerSession.inGame()) {
					playerSession.write("[AFK] " + ConnectionStrings.AUTH_NEW_PASS + AnsiCodes.END_LINE);
					logger.info("PlayerSession {} has not been verified prompting for username and password.", playerSession.getId());
				}
				else {
					playerSession.write("Echo: " + command + AnsiCodes.END_LINE);
					logger.info("PlayerSession {} has not been verified.", playerSession.getId());
				}
				break;
			default:
				// No-op
				break;
		}
	}
}

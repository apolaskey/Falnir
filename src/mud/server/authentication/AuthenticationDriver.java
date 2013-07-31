package mud.server.authentication;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;
import org.hibernate.Session;
import org.hibernate.mapping.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mud.entities.player.Player;
import mud.server.color.AnsiCodes;
import mud.server.core.BaseMudGameServer;
import mud.server.core.ConnectionHandler;
import mud.server.core.ConnectionStrings;
import mud.server.core.SessionHandler;
import mud.server.database.HibernateDriver;

import com.google.common.base.Stopwatch;
import com.google.inject.Inject;

/**
 * Responsible for handling Player Authentication and loading.
 * @author Andrew
 */
public class AuthenticationDriver {
	protected static final Logger logger = LoggerFactory.getLogger(AuthenticationDriver.class);
	protected static final long AUTH_TIMEOUT = 900000; // 15 minutes
	protected AuthenticationState currentState;
	protected BaseMudGameServer server;
	protected SessionHandler session;
	protected Stopwatch stopwatch;
	
	public AuthenticationDriver(BaseMudGameServer server, SessionHandler session) {
		currentState = AuthenticationState.UNSECURE;
		this.server = server;
		this.session = session;
		stopwatch = new Stopwatch();
	}
	
	public Player AuthenticateUser(String userName, String password) {
		Session session = HibernateDriver.OpenSession();
		session.beginTransaction();
		List result = (List)session.createQuery("from Player").list();
		session.close();
		return null;
	}
	
	public boolean RefreshAuthStatus() {
		
		return true;
	}
	
	public boolean DoWelcomeLogin() {
		logger.info("Prompting for session login.");
		session.WriteOutput(ConnectionStrings.AuthUserName + AnsiCodes.END_LINE);
		String input;
		
		try {
			input = session.ReadInput();
			logger.info("Authentication Async Read: {}", input);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	
}

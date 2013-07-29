package mud.server.authentication;

import org.hibernate.Session;

import mud.entities.player.Player;
import mud.server.core.BaseMudGameServer;
import mud.server.database.HibernateDriver;

import com.google.inject.Inject;

public class AuthenticationDriver {
	protected AuthenticationState currentState;
	@Inject
	public AuthenticationDriver(BaseMudGameServer server) {
		
	}
	
	public Player AuthenticateUser(String userName, String password) {
		Session temp = HibernateDriver.OpenSession();
		temp.close();
		return null;
	}
	
	
}

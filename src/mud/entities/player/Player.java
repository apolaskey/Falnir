package mud.entities.player;

import java.util.Date;

import mud.entities.EntityStats;
import mud.entities.IGameEntity;
import mud.server.core.ConnectionHandler;

// Java note - implements is for interfaces and extends is for classes -.-; why can't it just be : geez
// ^^^^
// this ain't no C# !
public class Player implements IGameEntity {
	
	protected int id;
	
	protected String firstName;
	protected String lastName;
	
	protected String shortDescription;
	protected String longDescription;
	
	protected EntityStats stats;
	
	// Server Information
	protected int lastIpAddress;
	protected boolean isBanned;
	protected boolean isSuspended;
	protected Date suspensionStartDate;
	protected Date suspensionEndDate;
	protected ConnectionHandler connection;
	
	public Player(int id, ConnectionHandler connection) {
		this.connection = connection;
		connection.GetSession().getRemoteAddress();
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getFirstName() {
		return firstName;
	}

	@Override
	public String getLastName() {
		return lastName;
	}
    

	@Override
	public String getShortDescription() {
		return shortDescription;
	}

	@Override
	public String getLongDescription() {
		return longDescription;
	}

	@Override
	public EntityStats getStats() {
		return stats;
	}
	
	public ConnectionHandler getConnection() {
		return this.connection;
	}

	@Override
	public String setFirstName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setLastName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setShortDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setLongDescription() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * EOF
	 */
}

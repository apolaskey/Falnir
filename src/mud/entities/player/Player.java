package mud.entities.player;

import java.util.Date;

import mud.entities.EntityStats;
import mud.entities.IGameEntity;
import mud.server.core.ConnectionHandler;
import mud.server.core.SessionHandler;

// Java note - implements is for interfaces and extends is for classes -.-; why can't it just be : geez
// ^^^^
// this ain't no C# ! <-- Who spaces their punctuation like that?
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
	protected SessionHandler session;
	
	public Player(int id, SessionHandler session) {
		this.session = session;
	}
	
	public SessionHandler getSession() {
		return this.session;
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

	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	@Override
	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}
	
	/**
	 * EOF
	 */
}

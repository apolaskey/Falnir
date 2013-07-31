package mud.entities.player;

import java.util.Date;

import mud.entities.EntityStats;
import mud.entities.IGameEntity;
import mud.server.core.SessionHandler;

// Java note - implements is for interfaces and extends is for classes -.-; why can't it just be : geez
// ^^^^
// this ain't no C# ! <-- Who spaces their punctuation like that?
// I didn't to have two punctuation marks next to one another #!
public class Player implements IGameEntity {
	
	private int id;
	
	private String firstName;
	private String lastName;
	
	private String shortDescription;
	private String longDescription;
	
	private EntityStats stats;
	
	// Server Information
	private int lastIpAddress;
	private boolean isBanned;
	private boolean isSuspended;
	private Date suspensionStartDate;
	private Date suspensionEndDate;
	private SessionHandler session;
	
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

package mud.entities.player;

import java.util.Date;

import mud.entities.EntityStats;
import mud.entities.IGameEntity;

// Java note - implements is for interfaces and extends is for classes -.-; why can't it just be : geez
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
	
	public Player(String firstName, String password) {
		
	}

	@Override
	public int GetId() {
		return id;
	}

	@Override
	public String GetFirstName() {
		return firstName;
	}

	@Override
	public String GetLastName() {
		return lastName;
	}

	@Override
	public String GetShortDescription() {
		return shortDescription;
	}

	@Override
	public String GetLongDescription() {
		return longDescription;
	}

	@Override
	public EntityStats GetStats() {
		return stats;
	}
	
	/**
	 * EOF
	 */
}

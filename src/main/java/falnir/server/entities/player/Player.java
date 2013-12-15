package falnir.server.entities.player;

import falnir.server.entities.EntityStats;
import falnir.server.entities.IGameEntity;

import java.util.Date;

/**
 * Data Structure for a Player
 * @author Andrew
 */
public class Player implements IGameEntity {
	
	private int id;
	
	private String firstName;
	private String lastName;
	
	private String password;
	
	private String shortDescription;
	private String longDescription;
	
	private EntityStats stats;
	
	// Server Information
	private int lastIpAddress;
	private boolean isBanned;
	private boolean isSuspended;
	private Date suspensionStartDate;
	private Date suspensionEndDate;
	
	public static Player loadPlayer(String username) {
		Player player = new Player();
		
		return player;
	}
	
	public static Player loadPlayer(String username, String password) {
		Player player = new Player();
		
		return player;
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
	public void setId(int id) {
		this.id = id;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * EOF
	 */
}

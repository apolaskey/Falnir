package Player;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import Authentication.AuthenticationHandler;

@Entity(name="PLAYER")
public class Player {
	protected AuthenticationHandler authHandler;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	int id;
	
	// Username and in-game name
	@Column(name="FIRST_NAME")
	String firstName;
	@Column(name="LAST_NAME")
	String lastName;
	// Unidentified description
	@Column(name="SHORT_DESC")
	String shortDescription;
	// Detailed Description
	@Column(name="LONG_DESC")
	String longDescription;
}

package mud.entities.player;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import mud.server.authentication.AuthenticationHandler;

@Entity(name="PLAYER")
public class Player {
	protected AuthenticationHandler authHandler;
	protected int id;
	String firstName;
	String lastName;
	String shortDescription;
	String longDescription;
}

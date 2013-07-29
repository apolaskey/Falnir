package mud.entities;

public interface IGameEntity {
	public int getId();
	public String getFirstName();
	public String getLastName();
	public String getShortDescription();
	public String getLongDescription();
	public EntityStats getStats();
}

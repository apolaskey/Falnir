package mud.entities;

public interface IGameEntity {
	public int getId();
	public String setFirstName();
	public String getFirstName();
	public String setLastName();
	public String getLastName();
	public String setShortDescription();
	public String getShortDescription();
	public String setLongDescription();
	public String getLongDescription();
	public EntityStats getStats();
}

package mud.entities;

public interface IGameEntity {
	public int GetId();
	public String GetFirstName();
	public String GetLastName();
	public String GetShortDescription();
	public String GetLongDescription();
	public EntityStats GetStats();
}

package falnir.server.entities;

public interface IGameEntity {
	public void setId(int id);
	public int getId();
	
	public void setFirstName(String firstName);
	public String getFirstName();
	
	public void setLastName(String lastName);
	public String getLastName();
	
	public void setShortDescription(String shortDescription);
	public String getShortDescription();
	
	public void setLongDescription(String longDescription);
	public String getLongDescription();
	
	public EntityStats getStats();
}

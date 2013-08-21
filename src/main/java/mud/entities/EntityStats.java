package mud.entities;

/**
 * Struct like data type for Stats; could be used for Player and NPC stats.
 * @author Andrew
 */
public class EntityStats {
	int health;
	int energy; // Energy is a universal action stat, it's true name will change on class.
	int experience;
	int experienceNextLevel;
	
	int strength, dexterity, constitution; // Base Stats for Physical Combat
	int intelligence, wisdom, spirit; // Base Stats Magic Combat
	int charisma, instinct; // These are special stats for non-combat actions
}

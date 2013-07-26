package Mud.Entities.Player;

/**
 * Struct like data type for Player Stats; could also be used for NPC stats.
 * @author Andrew
 */
public class PlayerStats {
	int Health;
	int Energy; // Energy is a universal action stat, it's true name will change on class.
	int Experience;
	int ExperienceNextLevel;
	
	int Strength, Dexterity, Constitution; // Base Stats for Physical Combat
	int Intelligence, Wisdom, Spirit; // Base Stats Magic Combat
	int Charisma, Instinct; // These are special stats for non-combat actions
}

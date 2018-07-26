package trirow;

import java.awt.Color;

/*************
 * A class to describe a player
 * @author Andy
 *
 */
public class Player {

	private final Color gColor;
	private final Agent gAgent;
	private final String gName;
	
	/**********
	 * The constructor
	 * @param mName the player's name
	 * @param mAgent the agent that handles the player's moves
	 * @param mColor the player's color
	 */
	public Player(String mName, Agent mAgent, Color mColor) {
		gName = mName;
		gAgent = mAgent;
		gColor = mColor;
	}
	
	public boolean equals(Object o) {
		if(o == null || !(o instanceof Player)) { return false; }
		else {
			Player p = (Player) o;
			return gColor.equals(p.gColor) && gAgent.equals(p.gAgent) && gName.equals(p.gName);
		}
	}
	
	/**********
	 * Getter for the player's color
	 * @return the player's color
	 */
	public Color getColor() { return gColor; }
	
	/**********
	 * Getter for the player's name
	 * @return the player's name
	 */
	public String getName() { return gName; }
	
	/**********
	 * Getter for the player's agent
	 * @return the player's agent
	 */
	public Agent getAgent() { return gAgent; }
	
	public String toString() { return gName; }
}

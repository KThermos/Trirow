package trirow;

/*************
 * An implementation of agent for Human players
 * @author Andy
 *
 */
public class HumanAgent implements Agent {

	public GameState move(GameState state) {
		/* punt and let GUI handle movement */ 
		return null;
	}

	public String toString() { return "Human"; }
	
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof HumanAgent)) { return false; }
		return true;
	}
}

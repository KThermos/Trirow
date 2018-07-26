package trirow;

import java.util.List;

/*********
 * Class that represents an AI agent that makes random moves.
 * @author Andy
 *
 */
public class RandomAIAgent implements Agent {
	
	public GameState move(GameState state) {
		List<GameState> tNextStates = state.getSuccessorStates();
		int tMove = (int)(Math.random() * tNextStates.size());
		return tNextStates.get(tMove);
	}

	public String toString() { return "Computer (Random)"; }

	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof RandomAIAgent)) { return false; }
		return true;
	}
}

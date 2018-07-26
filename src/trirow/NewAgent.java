package trirow;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class NewAgent implements Agent {
/*******
 * class that represents an improved form of the RandomAIAgent
 */
	public GameState move(GameState gameState){
		/* first, check to see if there are any moves it can make that will win it the game
		 * if there is, return that one.
		 * */
		List<GameState> tNextStates = gameState.getSuccessorStates();
		for(int i = 0; i<tNextStates.size();i++){
			if(tNextStates.get(i).getWinner()!=null){
				return tNextStates.get(i);	
			}
		}
		/* then, go through each potential move and, from those, go onto their potential moves and count
		 * how many of them will result in a winner. each time ther is one, increment the count
		 */
		for(GameState gameS:tNextStates){
			for(GameState r : gameS.getSuccessorStates()){
				if(r.getWinner()!=gameState.getPrevPlayer()){
					gameS.incCounter();
				}
			}
		}
		/* sort by the number of ways for the opposition to win and return the one with  the smallest number*/
		Collections.sort(tNextStates,new CounterComp());
		return tNextStates.get(0);
		}

	


	public String toString() { return "Computer (Little Better)"; }
	
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof NewAgent)) { return false; }
		return true;
	}
	
	
}

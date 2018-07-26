package trirow;

import java.util.Comparator;

public class CounterComp implements Comparator<GameState>{

	/**
	 * class for a comparator to compare two counts in separate GameState objects
	 */
	public int compare(GameState one, GameState two){
		if(one.getCounter()<two.getCounter()){
			return -1;
		}
		if(one.getCounter()>two.getCounter()){
			return 1;
			
		}
		else{
			return 0;
		}
	}
}

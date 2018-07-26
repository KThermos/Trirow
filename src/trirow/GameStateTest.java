package trirow;

import java.awt.Color;
import java.util.LinkedList;

import junit.framework.TestCase;
/**************
 * Test class for GameState methods
 * @author Andy
 *
 */
public class GameStateTest extends TestCase {

	private Player gPlayer1;
	private Player gPlayer2;
	
	public void setUp() {
        gPlayer1 = new Player("p1", new HumanAgent(), Color.RED);
        gPlayer2 = new Player("p2", new HumanAgent(), Color.BLUE);
}
	
	/*********
	 * Tests that a beginning board has no winner
	 */
	public void testGetWinnerNull() {
		GameState tState = new GameState(gPlayer1, gPlayer2);
		assertTrue(null == tState.getWinner());
	}
	
	/*********
	 * A single very simple test of getWinner. More tests may be required.
	 */
	public void testGetWinner() {
		LinkedList<Piece> tPList = new LinkedList<Piece>();
		tPList.add(new Piece(Color.RED, 0, 0, 0));
		tPList.add(new Piece(Color.RED, 1, 0, 1));
		tPList.add(new Piece(Color.RED, 2, 0, 2));
		tPList.add(new Piece(Color.RED, 3, 0, 0));
		tPList.add(new Piece(Color.RED, 4, 0, 1));
		tPList.add(new Piece(Color.RED, 3, 2, 2));
		
		tPList.add(new Piece(Color.BLUE, 0, 5, 0));
		tPList.add(new Piece(Color.BLUE, 1, 5, 1));
		tPList.add(new Piece(Color.BLUE, 3, 3, 2));
		tPList.add(new Piece(Color.BLUE, 3, 5, 0));
		tPList.add(new Piece(Color.BLUE, 4, 5, 1));
		tPList.add(new Piece(Color.BLUE, 3, 4, 2));
		
		GameBoard tBoard = new GameBoard(tPList, gPlayer1, gPlayer2);
		GameState tState = new GameState(tBoard, gPlayer1, gPlayer2, gPlayer1);
		assertEquals(gPlayer2, tState.getWinner());
	}
	
}

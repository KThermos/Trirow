package trirow;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

/*****************
 * Class to represent a TriRow game
 * @author Andy
 *
 */
public class TriRowGame {
	public Stack <GameState> dBoards = new Stack<GameState>();
	private GameState gState;

	/***********
	 * Constructor that takes the players
	 * @param mPlayer1 player 1
	 * @param mPlayer2 player 2
	 */
	public TriRowGame(Player mPlayer1, Player mPlayer2) {
		gState = new GameState(mPlayer1, mPlayer2);
	}

	/************
	 * Method that begins the game
	 */
	public void start() {
		
		move();	
	}

	/************
	 * Method that asks the current player object to move
	 */
	public void move() {
		if(!isGameOver()) {

			GameState tMove = gState.getCurrentPlayer().getAgent().move(gState);
			if(tMove != null) { 
				gState.makeSafeMove(tMove);
				move();
			}
			/*copy every element of the state of the board before adding it to the top of the stack*/
			else{
				List<Piece> tList = new LinkedList<Piece>();
				for(Piece p : this.gState.getGameBoard().getPieces()) {
					tList.add(p.clone());
				}
				GameBoard tBoard = new GameBoard(tList, gState.getPlayer1(), gState.getPlayer2());
				GameState tState = new GameState(tBoard, gState.getCurrentPlayer(), gState.getPlayer1(), gState.getPlayer2());
				dBoards.push(tState);
			}

		}
	}
	
	/************
	 * Method to test whether the current game is over
	 * @return true if the game is over (either there is a winner, or there are no more moves.)
	 */
	public boolean isGameOver() { 
		return gState.getWinner() != null || gState.getSuccessorStates().isEmpty();
	}
	
	/************
	 * Getter for the current player
	 * @return the current player
	 */
	public Player getCurrentPlayer() { return gState.getCurrentPlayer(); }

	/************
	 * Getter for the current GameState
	 * @return the current game state
	 */
	public GameState getGameState() { return gState; }
	
	/************
	 * Setter for player 1
	 * @param mPlayer the new player 1
	 */
	public void setPlayer1(Player mPlayer) {
		boolean myturn = (gState.getPlayer1() == gState.getCurrentPlayer());
		gState.setPlayer1(mPlayer);
		if(myturn) { 
			move(); }
	}
	/**
	 * method to revert to the previous game board
	 */
	public void undo(){
		if(dBoards.size()>0){
			gState = dBoards.pop();
			gState= dBoards.peek();	
			/* in addition to returning the previous state, this also adds the new (old) state to the top of the stack*/
			dBoards.pop();
			List<Piece> tList = new LinkedList<Piece>();
			for(Piece p : this.gState.getGameBoard().getPieces()) {
				tList.add(p.clone());
			}
			GameBoard tBoard = new GameBoard(tList, gState.getPlayer1(), gState.getPlayer2());
			GameState tState = new GameState(tBoard, gState.getCurrentPlayer(), gState.getPlayer1(), gState.getPlayer2());
			dBoards.push(tState);
		}
	}
	/************
	 * Setter for player 2
	 * @param mPlayer the new player 2
	 */
	public void setPlayer2(Player mPlayer) {
		boolean myturn = (gState.getPlayer2() == gState.getCurrentPlayer());
		gState.setPlayer2(mPlayer);
		if(myturn) {
			move(); }
	}
	
	/*************
	 * Main method that current runs the game 1,000 times with two random agents.
	 * Feel free to change this method to test how good other agents are.
	 * This main method does not use the gui at all, therefore it is quite a bit faster.
	 * @param args
	 */
	public static void main(String[] args) {
		int[] winners = new int[3];
		for(int i = 0; i < 1000; i++) {
			TriRowGame tGame = new TriRowGame(new Player("blue", new RandomAIAgent(), Color.BLUE), 
					new Player("red", new NewAgent(), Color.RED));
			tGame.start();
			if(tGame.getGameState().getWinner() == null) {
				winners[0]++;
			} else if(tGame.getGameState().getWinner().getName().equals("blue")) {
				winners[1]++;
			} else {
				winners[2]++;
			}
		}
		System.out.printf("Blue: %d, Red: %d, Draw: %d\n", winners[1], winners[2], winners[0]);
	}
}

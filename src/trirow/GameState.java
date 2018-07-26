package trirow;

//import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/************
 * Represents a particular TriRow game state
 * @author Andy
 *
 */
public class GameState {

	/* private instance variables in a game state */
	private Player gCurrentPlayer;
	private Player gPlayer1;
	private Player gPlayer2;
	private GameBoard gBoard;
	private LinkedList<GameState> gSuccessorStates;
	private int counter;

	/**********
	 * Constructor for a game state
	 * @param mPlayer1 player 1
	 * @param mPlayer2 player 2
	 */
	public GameState(Player mPlayer1, Player mPlayer2) {
		gPlayer1 = gCurrentPlayer = mPlayer1;
		gPlayer2 = mPlayer2;
		gBoard = new GameBoard(3, gPlayer1, gPlayer2);
	}

	/**********
	 * Package-private constructor for testing
	 * @param mBoard the GameBoard to use
	 * @param mCurrentPlayer the current player
	 * @param mPlayer1 player 1
	 * @param mPlayer2 player 2
	 */
	GameState(GameBoard mBoard, Player mCurrentPlayer, Player mPlayer1, Player mPlayer2) {
		gBoard = mBoard;
		gCurrentPlayer = mCurrentPlayer;
		gPlayer1 = mPlayer1;
		gPlayer2 = mPlayer2;
	}
	
	/***********
	 * Makes a move if mState is a legal move
	 * @param mState the move to make
	 */
	public void makeSafeMove(GameState mState) {
		if(isLegalMove(mState)) {
			makeMove(mState);
		}
	}
	/**
	 * getter for the counter
	 * @return the counter of a game state
	 */
	public int getCounter(){
		return counter;
	}
	/**
	 * method to increment the counter of a game state
	 */
	public void incCounter(){
		counter++;
	}
	/***********
	 * Makes a move if moving mPiece to mPosition is legal
	 * @param mPiece the piece to move
	 * @param mPosition the position to move it to
	 */
	public void makeSafeMove(Piece mPiece, Position mPosition) {
		if(gBoard.isLegalMove(mPiece, mPosition)) {
			GameState tState = new GameState(gPlayer1, gPlayer2);
			tState.gBoard = gBoard.movePiece(mPiece.getPosition(), mPosition);
			makeMove(tState);
		}
	}
	/**
	 * sets the game state to mState
	 * @param mState
	 */
	public void makeMove(GameState mState) {
		if(gCurrentPlayer == gPlayer1) { gCurrentPlayer = gPlayer2; }
		else { gCurrentPlayer = gPlayer1; }
		gSuccessorStates = null;
		gBoard = mState.getGameBoard();
	}

	/************
	 * Gets the current player
	 * @return the current player
	 */
	public Player getCurrentPlayer() { return gCurrentPlayer; }
	public Player getPrevPlayer(){
		if (getCurrentPlayer().equals(gPlayer1)){
			return gPlayer2;
		}
		else{
			return gPlayer1;
		}
	}
	/************
	 * Gets a list of all possible successor states
	 * @return the list
	 */
	public List<GameState> getSuccessorStates() {
		if(gSuccessorStates == null) {
			gSuccessorStates = new LinkedList<GameState>();
			for(Piece p: gBoard.getPieces()) {
				if(p.getColor().equals(gCurrentPlayer.getColor())) {
					for(Position tPos: gBoard.getLegalMoves(p)) {
						GameState tState = new GameState(gPlayer1, gPlayer2);
						tState.gBoard = gBoard.movePiece(p.getPosition(), tPos);
						gSuccessorStates.add(tState);
					}
				}
			}
		}
		return gSuccessorStates;
	}
	
	/************
	 * Returns true if mState is legal move from this state
	 * @param mState the state to test
	 * @return whether mState is a legal move
	 */
	public boolean isLegalMove(GameState mState) {
		return getSuccessorStates().contains(mState);
	}

	/************
	 * Gets the winner, if there is one at the current state
	 * @return the winner, or null if there is not a winner
	 */
	public Player getWinner() {
		/*Goes through each piece, creating a list of neighboring pieces (mrRogers)*/
		
		for(Piece tPiece: this.getGameBoard().getPieces()){
			List<Piece> mrRogers = new LinkedList<Piece>();
			List<Piece> family = new LinkedList<Piece>();
			for(Piece gPiece:this.getGameBoard().getPieces()){
				if(tPiece.isNeighbor(gPiece)){
					mrRogers.add(gPiece);
				}
			}
			/* checks each element in mrRogers to see if it's of the same type.  if it is,
			 * add it to the family list*/
			for(Piece n:mrRogers){
				if(tPiece.getType().equals(n.getType())){
					family.add(n);
				}
			}
			/*if the size of the list of pieces of the same type is 2 or more, it should return the player that last moved*/
			if(family.size()>=2){
				if(this.gCurrentPlayer.equals(gPlayer1)){
					return gPlayer2;
				}
				else{
					return gPlayer1;
				}
			}
		}
		return null;
	}
	
	
	/*************
	 * Gets the game board of the current state
	 * @return the game board
	 */
	public GameBoard getGameBoard() { return gBoard; }

	/*************
	 * Implementation of equals for GameStates
	 * @param obj the object to compare to this
	 * @return true if obj equals this
	 */
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof GameState)) { return false; }
		GameState tmp = (GameState) obj;
		return tmp.gBoard.equals(this.gBoard) && tmp.gPlayer1.equals(this.gPlayer1) && tmp.gPlayer2.equals(this.gPlayer2) &&
			tmp.gCurrentPlayer.equals(this.gCurrentPlayer);
	}

	/*************
	 * Getter for player 1
	 * @return player 1
	 */
	public Player getPlayer1() { return gPlayer1; }
	
	/*************
	 * Setter for player 1
	 * @param mPlayer the new player 1
	 */
	public void setPlayer1(Player mPlayer) { 
		if(gPlayer1 == gCurrentPlayer) { gCurrentPlayer = mPlayer; }
		gPlayer1 = mPlayer;
		for(GameState g: getSuccessorStates()) {
			g.gPlayer1 = mPlayer;
		}
	}

	/*************
	 * Getter for player 2
	 * @return player 2
	 */
	public Player getPlayer2() { return gPlayer2; }
	
	/*************
	 * Setter for player 2
	 * @param mPlayer the new player 2
	 */
	public void setPlayer2(Player mPlayer) { 
		if(gPlayer2 == gCurrentPlayer) { gCurrentPlayer = mPlayer; }
		gPlayer2 = mPlayer;
		for(GameState g: getSuccessorStates()) {
			g.gPlayer2 = mPlayer;
		}
	}
}

package trirow;

import java.util.LinkedList;
import java.util.List;

/***************
 * Represents a TriRow GameBoard
 * 
 * @author Andy
 *
 */
public class GameBoard {

	/* Instance variables to hold board size, players and pieces */
	private final int gSize;
	private Player gPlayer1;
	private Player gPlayer2;
	private List<Piece> gPieceList;

	/*************
	 * GameBoard constructor
	 * @param mUniquePieces the number of unique pieces per player
	 * @param mPlayer1 the first player
	 * @param mPlayer2 the second player
	 */
	public GameBoard(int mUniquePieces, Player mPlayer1, Player mPlayer2) {
		gSize = mUniquePieces * 2;
		gPlayer1 = mPlayer1;
		gPlayer2 = mPlayer2;
		gPieceList = new LinkedList<Piece>();
		initPieces();
	}
	
	/**************
	 * Package-private constructor for use in unit tests
	 * @param mPieces the list of pieces on the board
	 */
	GameBoard(List<Piece> mPieces, Player mPlayer1, Player mPlayer2) {
		gSize = 6;
		gPlayer1 = mPlayer1;
		gPlayer2 = mPlayer2;
		gPieceList = mPieces;
	}

	private void initPieces() {
		gPieceList.clear();
		for(int i = 0; i < (gSize/2); i++) {
			gPieceList.add(new Piece(gPlayer1.getColor(), i, 0, i));
			gPieceList.add(new Piece(gPlayer1.getColor(), i + gSize/2, 0, i));
			gPieceList.add(new Piece(gPlayer2.getColor(), i, gSize-1, i));
			gPieceList.add(new Piece(gPlayer2.getColor(), i + gSize/2, gSize-1, i));
		}
	}

	/************
	 * Gets a collection of all pieces on the board
	 * @return the pieces, as an Iterable
	 */
	public Iterable<Piece> getPieces() { return gPieceList; }

	/************
	 * Gets the size of the board
	 * @return the size
	 */
	public int getSize() { return gSize; }
	
	/***********
	 * Gets the piece at (mColumn, mRow)
	 * @param mColumn the column to look at
	 * @param mRow the row to look at
	 * @return the piece that is there, or null if there is no piece there.
	 */
	public Piece getPiece(int mColumn, int mRow) {
		for(Piece tPiece: gPieceList) {
			if(tPiece.getPosition().column == mColumn && tPiece.getPosition().row == mRow) {
				return tPiece;
			}
		}
		return null;
	}

	/************
	 * Gets the piece at position mPos
	 * @param mPos the position
	 * @return the piece that is at mPos, or null if there is no piece there
	 */
	public Piece getPiece(Position mPos) {
		for(Piece tPiece: gPieceList) {
			if(tPiece.getPosition().equals(mPos)) {
				return tPiece;
			}
		}
		return null;
	}

	/*************
	 * Move a piece from position mPos1 to mPos2, if possible, and returns a new GameBoard after moving
	 * @param mPos1 the position to move from
	 * @param mPos2 the position to move to
	 * @return the new GameBoard, if the move was legal, otherwise a copy of the original GameBoard
	 */
	public GameBoard movePiece(Position mPos1, Position mPos2) {
		GameBoard tNewBoard = new GameBoard(3, gPlayer1, gPlayer2);
		Piece tOldPiece = getPiece(mPos1);
		if(tOldPiece != null && isLegalMove(tOldPiece, mPos2)) {
			tNewBoard.gPieceList = new LinkedList<Piece>();
			for(Piece tPiece : getPieces()) {
				if(tPiece != tOldPiece) {
					tNewBoard.gPieceList.add(tPiece);
				} else {
					Piece tNewPiece = tOldPiece.clone();
					tNewPiece.setPosition(mPos2);
					tNewBoard.gPieceList.add(tNewPiece);
				}
			}
		}
		return tNewBoard;
	}

	/*************
	 * Returns whether moving mPiece to position mPos is legal or not
	 * @param mPiece the piece to try to move
	 * @param mPos the position to try to move it to
	 * @return whether the move is legal or not
	 */
	public boolean isLegalMove(Piece mPiece, Position mPos) {
		if(getPiece(mPos) != null) { return false; }
		if(Math.abs(mPos.column - mPiece.getPosition().column) <= 1) {
			if(mPiece.getColor().equals(gPlayer1.getColor()) &&
					(mPos.row == mPiece.getPosition().row || mPos.row == (mPiece.getPosition().row + 1)) &&
					getPiece(mPos) == null) {
				return true;
			} else if(mPiece.getColor().equals(gPlayer2.getColor()) &&
					(mPos.row == mPiece.getPosition().row || mPos.row == (mPiece.getPosition().row - 1)) &&
					getPiece(mPos) == null) {
				return true;
			}
		}
		return false;
	}

	/**************
	 * Returns a collection of all legal moves for mPiece
	 * @param mPiece the piece
	 * @return the collection of legal moves
	 */
	public Iterable<Position> getLegalMoves(Piece mPiece) {
		LinkedList<Position> tMoves = new LinkedList<Position>();
		if(gPieceList.contains(mPiece)) {
			Position tPos = mPiece.getPosition();
			int tColMin = tPos.column - 1;
			int tColMax = tPos.column + 1;
			int tRowMax = tPos.row;
			if(tColMin < 0) { tColMin = 0; }
			else if (tColMax > gSize - 1) { tColMax = gSize - 1; }
			if(mPiece.getColor().equals(gPlayer1.getColor())) {
				// piece moves down
				if(tPos.row < gSize - 1) { tRowMax++; }
			} else {
				// piece moves up
				if(tPos.row > 0) { tRowMax--; }
			}
			int[] rows = {tPos.row, tRowMax};
			for(int i = tColMin; i <= tColMax; i++) {
				for(int j : rows) {
					if(getPiece(i, j) == null) {
						tMoves.add(new Position(i, j));
					}
				}
			}
		}
		return tMoves;
	}
	
	/*********
	 * Implementation of equals for GameBoards
	 * @param obj the object to compare to this
	 * @return true if obj is equal to this
	 */
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof GameBoard)) { return false; }
		GameBoard tmp = (GameBoard) obj;
		return tmp.gSize == this.gSize && tmp.gPlayer1.equals(this.gPlayer1) && tmp.gPlayer2.equals(this.gPlayer2) &&
			tmp.gPieceList.containsAll(this.gPieceList) && this.gPieceList.containsAll(tmp.gPieceList);
	}
}

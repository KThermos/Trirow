package trirow;

import java.awt.Color;
//import java.awt.List;
//import java.util.ArrayList;

/*************
 * Class to represent a TriRow piece
 * @author Andy
 *
 */
public class Piece {

	/* private instance variables */
	private Position gPosition;
	private final Color gColor;
	private final Integer gType;
	private int numShapeNeighbors;

	/*********
	 * Getter for this piece's position
	 * @return the position
	 */
	public Position getPosition() { return gPosition; }
	/**
	 * method to increment the number of same-shape neighbors
	 */
	public void incrementOpp(){
		this.numShapeNeighbors++;
	}
/**
 * method to reset the number of same-shape neighbors to zero
 */
	public void resetNumShapeNeighbors(){
		numShapeNeighbors= 0;
	}
	/**
	 * getter for numShapeNeighbors
	 * @return number of neighbors of the same shape
	 */
	public int getNumShapeNeighbors(){return numShapeNeighbors;}
	/*********
	 * Getter for this piece's color
	 * @return the color
	 */
	public Color getColor() { return gColor; }

	/*********
	 * Getter for this piece's type (i.e. shape)
	 * @return the type
	 */
	public Integer getType() { return gType; }

	/*********
	 * Constructor
	 * @param mColor piece's color
	 * @param mXPos piece's x position
	 * @param mYPos piece's y position
	 * @param mType piece's type
	 */
	public Piece(Color mColor, int mXPos, int mYPos, int mType) {
		gColor = mColor;
		gType = mType;
		gPosition = new Position(mXPos, mYPos);
	}

	/*********
	 * Setter for a piece's position
	 * @param mXPos x position
	 * @param mYPos y position
	 */
	public void setPosition(int mXPos, int mYPos) {
		gPosition = new Position(mXPos, mYPos);
	}

	public Piece clone() {
		return new Piece(gColor, gPosition.column, gPosition.row, gType);
	}
	/**
	 * 
	 * @param w the piece that may or may not be a neighbor to this
	 * @return true if it is a neighbor
	 */
	public boolean isNeighbor(Piece w){
		int xDist = Math.abs(w.getPosition().getX()-this.getPosition().getX());
		int yDist = Math.abs(w.getPosition().getY()-this.getPosition().getY());
		/* first make sure that it is not the same piece*/
		if(Math.sqrt((xDist*xDist)+(yDist*yDist))!=0){
			if(Math.abs(w.getPosition().getX()-this.getPosition().getX())<2 && Math.abs(w.getPosition().getY()-this.getPosition().getY())<2){
				return true;
			}
		}
		return false;
	}
	/*********
	 * Setter for a piece's position
	 * @param mPos the new position
	 */
	public void setPosition(Position mPos) { gPosition = mPos; }

	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof Piece)) { return false; }
		Piece tmp = (Piece) obj;
		return tmp.gPosition.equals(this.gPosition) && tmp.gColor.equals(this.gColor) && tmp.gType.equals(this.gType);
	}

}

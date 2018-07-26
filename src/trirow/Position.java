package trirow;

/*****************
 * A simple position class to hold game board x-y coordinates
 * @author Andy
 *
 */
public class Position {
	/*******
	 * The column
	 */
	public final int column;
	
	/*******
	 * The row
	 */
	public final int row;
	
	/*******
	 * Constructor for a position object
	 * @param mXPos 
	 * @param mYPos
	 */

	public Position(int mXPos, int mYPos) {
		if(mXPos < 0) { throw new IndexOutOfBoundsException(); }
		if(mYPos < 0) { throw new IndexOutOfBoundsException(); } 
		row = mYPos;
		column = mXPos;
	}
	public boolean isNeighbor(Position g){
		if(Math.abs(this.row-g.row) <2 && Math.abs(this.column-g.column)<2){
			return true;
		}
		else{
			return false;
		}
		
	}
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof Position)) { return false; }
		Position p = (Position) obj;
		return p.column == this.column && p.row == this.row;
	}
	public int getX(){
		return row;
		
	}
	public int getY(){
		return column;
		
	}
}
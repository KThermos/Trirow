package trirow;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JComponent;

/********************
 * A class to handle the graphics of a Trirow game
 * @author Andy
 *
 */
public class GameComponent extends JComponent implements MouseListener, MouseMotionListener {

	/* constants for drawing sizes */
	private static final int SQUARE_SIZE = 95;
	private static final int LEFT_MARGIN = 12;
	private static final int TOP_MARGIN = 30;
	private static final int PIECE_MARGIN = 10;

	/* serialization constant */
	private static final long serialVersionUID = 1L;

	/* instance variables for game or drawing */
	private TriRowGame gGame;
	private ArrayList<Rectangle2D.Double> gLegalMoveList;
	private Rectangle2D.Double gCurrentMove;
	private Piece gCurrentPiece;
	private Point gDragPoint;
	private Point gDragStartPoint;

	/*********
	 * Simple constructor
	 */
	public GameComponent() {
		// add mouse listeners
		this.addMouseListener(this);
		this.addMouseMotionListener(this);

		// set size and do the java gui dance
		this.setMinimumSize(new Dimension(600, 620));
		this.setPreferredSize(new Dimension(600, 620));
		this.setVisible(true);

		gLegalMoveList = new ArrayList<Rectangle2D.Double>();
	}

	/**********
	 * Begins the game
	 * @param mPlayer1 player 1
	 * @param mPlayer2 player 2
	 */
	public void start(Player mPlayer1, Player mPlayer2) {
		gGame = new TriRowGame(mPlayer1, mPlayer2);
		gGame.start();
		repaint();
	}

	/***********
	 * Returns the TriRow game object shown in this GameComponent
	 * @return the TriRow game
	 */
	public TriRowGame getGame() {
		return gGame;
	}

	/*********
	 * Overriding paintComponent method
	 * @param mGraphics the graphics object to draw with
	 */
	public void paintComponent(Graphics mGraphics) {
		Graphics2D g2 = (Graphics2D) mGraphics;

		if(gGame != null) {
			// draw turn indicator / winner / draw
			if(gGame.isGameOver()) {
				if(gGame.getGameState().getWinner() != null) {
					g2.drawString("Winner:", 260, 15);
					g2.setColor(gGame.getGameState().getWinner().getColor());
					g2.fillRect(315, 2, 18, 18);
				} else {
					g2.drawString("Draw!!", 270, 15);
				}
			} else {
				g2.drawString("Current Player:", 220, 15);
				g2.setColor(gGame.getCurrentPlayer().getColor());
				g2.fillRect(315, 2, 18, 18);
			}

			// draw grid
			for(int i=0; i<6; i++) {
				for(int j=0; j<6; j++) {
					g2.setColor(Color.BLACK);
					g2.drawRect(LEFT_MARGIN + i*SQUARE_SIZE, TOP_MARGIN + j*SQUARE_SIZE,
							SQUARE_SIZE, SQUARE_SIZE);
				}
			}

			// draw legal moves - based on currently held piece
			for(Rectangle2D.Double tRect : gLegalMoveList) {
				g2.setColor(Color.GREEN.brighter());
				if(tRect == gCurrentMove) { g2.setColor(Color.GREEN.darker()); }
				g2.fill(tRect);
			}

			// draw pieces
			for(Piece tPiece: gGame.getGameState().getGameBoard().getPieces()) {
				g2.setColor(tPiece.getColor());
				Polygon p = getPolyFromPiece(tPiece);
				if(tPiece == gCurrentPiece) {
					p.translate(gDragPoint.x - gDragStartPoint.x, gDragPoint.y - gDragStartPoint.y);
				}
				g2.fill(p);
			}
		}
	}

	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1 && !gGame.isGameOver() && 
				gGame.getCurrentPlayer().getAgent() instanceof HumanAgent) {
			for(Piece tPiece : gGame.getGameState().getGameBoard().getPieces()) {
				if(getPolyFromPiece(tPiece).contains(e.getX(), e.getY()) && 
						tPiece.getColor().equals(gGame.getCurrentPlayer().getColor())) {
					gDragPoint = e.getPoint();
					gDragStartPoint = e.getPoint();
					gCurrentPiece = tPiece;
					setLegalMoves(tPiece);
					repaint();
				}
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		if(gCurrentPiece != null) {
			if(gCurrentMove != null) {
				// move the piece, yay!
				int nrow = gCurrentPiece.getPosition().row;
				int ncolumn = gCurrentPiece.getPosition().column;
				if(gCurrentMove.x + SQUARE_SIZE <= gDragStartPoint.x) {
					ncolumn--;
				} else if (gCurrentMove.x >= gDragStartPoint.x) {
					ncolumn++;
				}
				if(gCurrentMove.y + SQUARE_SIZE <= gDragStartPoint.y) {
					nrow--;
				} else if(gCurrentMove.y >= gDragStartPoint.y) {
					nrow++;
				}
				if(gGame.getGameState().getGameBoard().isLegalMove(gCurrentPiece, new Position(ncolumn, nrow))) {
					/* make human's move */
					gGame.getGameState().makeSafeMove(gCurrentPiece, new Position(ncolumn, nrow));
					/* ask next agent to move */
					gGame.move();
				}
			}
		}
		gCurrentPiece = null;
		gCurrentMove = null;
		gDragPoint = null;
		gLegalMoveList.clear();
		repaint();

	}

	public void mouseDragged(MouseEvent e) {
		if(gCurrentPiece != null && gDragPoint != null) {
			gDragPoint = e.getPoint();
			boolean found = false;
			for(Rectangle2D.Double tRect : gLegalMoveList) {
				if(tRect.contains(gDragPoint)) {
					gCurrentMove = tRect;
					found = true;
				}
			}
			if(!found) { gCurrentMove = null; }
			repaint();
		}
	}

	public void mouseClicked(MouseEvent e) { /* do nothing */ }
	public void mouseExited(MouseEvent e) { /* do nothing */ }
	public void mouseMoved(MouseEvent arg0) { /* do nothing */ }
	public void mouseEntered(MouseEvent e) { /* do nothing */ }

	private Polygon getPolyFromPiece(Piece mPiece) {
		Polygon rPoly = new Polygon();
		int pixelx = LEFT_MARGIN + SQUARE_SIZE * mPiece.getPosition().column;
		int pixely = TOP_MARGIN + SQUARE_SIZE * mPiece.getPosition().row;
		int centerx = pixelx + SQUARE_SIZE/2;
		int centery = pixely + SQUARE_SIZE/2;
		int diameter = (centerx - pixelx) - PIECE_MARGIN;
		for(double i = Math.PI; i < 3 * Math.PI; i += (2.0 * Math.PI / (double)(mPiece.getType()+4))) {
			rPoly.addPoint((int)(centerx + diameter * Math.sin(i)), (int)(centery + diameter*Math.cos(i)));
		}
		return rPoly;
	}

	private void setLegalMoves(Piece mPiece) {
		for(Position p : gGame.getGameState().getGameBoard().getLegalMoves(gCurrentPiece)) {
			gLegalMoveList.add(new Rectangle2D.Double(LEFT_MARGIN + p.column*SQUARE_SIZE + 1, TOP_MARGIN + p.row*SQUARE_SIZE + 1,
					SQUARE_SIZE-1 , SQUARE_SIZE-1));			
		}
	}
}

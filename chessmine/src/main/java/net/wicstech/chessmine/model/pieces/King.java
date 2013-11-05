package net.wicstech.chessmine.model.pieces;

import java.awt.Point;
import java.util.List;

/**
 * Rei.
 * 
 * @author Sergio
 * 
 */
public class King extends Piece {
	private static final long serialVersionUID = -7098370625157522187L;

	public boolean whoIsThreatening(Piece behaveAs) {
		return false;
	}

	@Override
	public boolean acceptMove(Point newPosition) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List<Point> possibleMoves(Point givenPoint) {
		// TODO Auto-generated method stub
		return null;
	}

}

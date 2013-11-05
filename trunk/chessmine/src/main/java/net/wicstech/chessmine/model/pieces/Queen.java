package net.wicstech.chessmine.model.pieces;

import java.awt.Point;
import java.util.List;

/**
 * Rainha.
 * 
 * @author Sergio
 * 
 */
public class Queen extends Piece {
	private static final long serialVersionUID = 4817619455656435077L;

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

package net.wicstech.chessmine.model.pieces;

import java.awt.Point;
import java.util.List;

/**
 * Cavalo.<br>
 * Possible Moves: (x-1, y-2) (x-2, y-1) (x-2, y+1) (x-1, y+2) (x+1, y+2) (x+2,
 * y+1) (x+2, y-1) (x+1, y-2)
 */
public class Knight extends Piece {
	private static final long serialVersionUID = 8949299390635327820L;

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

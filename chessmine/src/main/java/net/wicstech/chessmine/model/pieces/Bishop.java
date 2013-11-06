package net.wicstech.chessmine.model.pieces;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import net.wicstech.chessmine.model.Direction;
import net.wicstech.chessmine.model.Orientation;

/**
 * Bispo.
 * 
 * @author Sergio
 * 
 */
public class Bishop extends Piece {
	private static final long serialVersionUID = 5443101730149654318L;

	@Override
	public boolean acceptMove(Point newPosition) {
		List<Point> possibleMoves = new ArrayList<>();
		possibleMoves.addAll(moveBias(Orientation.BACK, Direction.LEFT, Integer.MAX_VALUE));
		possibleMoves.addAll(moveBias(Orientation.BACK, Direction.RIGHT, Integer.MAX_VALUE));
		possibleMoves.addAll(moveBias(Orientation.FORTH, Direction.LEFT, Integer.MAX_VALUE));
		possibleMoves.addAll(moveBias(Orientation.FORTH, Direction.RIGHT, Integer.MAX_VALUE));
		return possibleMoves.contains(newPosition);
	}

	@Override
	public List<Point> possibleMoves(Point givenPoint) {
		// TODO Auto-generated method stub
		return null;
	}

}

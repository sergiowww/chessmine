package net.wicstech.chessmine.model.pieces;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import net.wicstech.chessmine.model.Direction;
import net.wicstech.chessmine.model.Orientation;

/**
 * Torre.
 * 
 * @author Sergio
 * 
 */
public class Rook extends Piece {
	private static final long serialVersionUID = -251879153705382657L;

	@Override
	public boolean acceptMove(Point newPosition) {
		List<Point> points = new ArrayList<>();
		points.addAll(moveHorizontally(Direction.LEFT, Integer.MAX_VALUE));
		points.addAll(moveHorizontally(Direction.RIGHT, Integer.MAX_VALUE));
		points.addAll(moveVertically(Orientation.BACK, Integer.MAX_VALUE));
		points.addAll(moveVertically(Orientation.FORTH, Integer.MAX_VALUE));
		return points.contains(newPosition);
	}

	@Override
	public List<Point> possibleMoves(Point givenPoint) {
		// TODO Auto-generated method stub
		return null;
	}

}

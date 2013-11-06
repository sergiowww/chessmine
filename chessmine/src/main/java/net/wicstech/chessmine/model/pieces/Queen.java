package net.wicstech.chessmine.model.pieces;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import net.wicstech.chessmine.model.Direction;
import net.wicstech.chessmine.model.Orientation;

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
		List<Point> points = new ArrayList<>();
		points.addAll(moveBias(Orientation.BACK, Direction.LEFT, Integer.MAX_VALUE));
		points.addAll(moveBias(Orientation.BACK, Direction.RIGHT, Integer.MAX_VALUE));
		points.addAll(moveBias(Orientation.FORTH, Direction.LEFT, Integer.MAX_VALUE));
		points.addAll(moveBias(Orientation.FORTH, Direction.RIGHT, Integer.MAX_VALUE));
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

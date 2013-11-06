package net.wicstech.chessmine.model.pieces;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import net.wicstech.chessmine.model.BoardSide;
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
		return possibleMoves(getCurrentPosition(), getBoardSide()).contains(newPosition);
	}

	@Override
	public List<Point> possibleMoves(Point givenPoint, BoardSide boardSide) {
		List<Point> points = new ArrayList<>();
		points.addAll(moveBias(givenPoint, boardSide, Orientation.BACK, Direction.LEFT, Integer.MAX_VALUE));
		points.addAll(moveBias(givenPoint, boardSide, Orientation.BACK, Direction.RIGHT, Integer.MAX_VALUE));
		points.addAll(moveBias(givenPoint, boardSide, Orientation.FORTH, Direction.LEFT, Integer.MAX_VALUE));
		points.addAll(moveBias(givenPoint, boardSide, Orientation.FORTH, Direction.RIGHT, Integer.MAX_VALUE));
		points.addAll(moveHorizontally(givenPoint, boardSide, Direction.LEFT, Integer.MAX_VALUE));
		points.addAll(moveHorizontally(givenPoint, boardSide, Direction.RIGHT, Integer.MAX_VALUE));
		points.addAll(moveVertically(givenPoint, boardSide, Orientation.BACK, Integer.MAX_VALUE));
		points.addAll(moveVertically(givenPoint, boardSide, Orientation.FORTH, Integer.MAX_VALUE));
		return points;
	}

}

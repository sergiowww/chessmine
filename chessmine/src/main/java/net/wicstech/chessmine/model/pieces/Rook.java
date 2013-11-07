package net.wicstech.chessmine.model.pieces;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import net.wicstech.chessmine.model.BoardSide;
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

	/**
	 * Construtor de visibilidade padrão.
	 */
	Rook() {
		super();
	}

	@Override
	public boolean acceptMove(Point newPosition) {
		return possibleMoves(getCurrentPosition(), getBoardSide()).contains(newPosition);
	}

	@Override
	public List<Point> possibleMoves(Point givenPoint, BoardSide boardSide) {
		List<Point> points = new ArrayList<>();
		points.addAll(moveHorizontally(givenPoint, boardSide, Direction.LEFT, Integer.MAX_VALUE));
		points.addAll(moveHorizontally(givenPoint, boardSide, Direction.RIGHT, Integer.MAX_VALUE));
		points.addAll(moveVertically(givenPoint, boardSide, Orientation.BACK, Integer.MAX_VALUE));
		points.addAll(moveVertically(givenPoint, boardSide, Orientation.FORTH, Integer.MAX_VALUE));
		return points;
	}

}

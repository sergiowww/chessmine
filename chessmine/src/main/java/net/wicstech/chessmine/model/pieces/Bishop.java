package net.wicstech.chessmine.model.pieces;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import net.wicstech.chessmine.model.BoardSide;
import net.wicstech.chessmine.model.Direction;
import net.wicstech.chessmine.model.Orientation;

/**
 * Bispo.
 * 
 * @author Sergio
 * 
 */
public class Bishop extends AbstractPiece {
	private static final long serialVersionUID = 5443101730149654318L;

	/**
	 * Construtor de visibilidade padrão.
	 */
	Bishop() {
		super();
	}

	@Override
	public boolean acceptMove(Point newPosition) {
		return possibleMoves(getCurrentPosition(), getBoardSide()).contains(newPosition);
	}

	@Override
	public List<Point> possibleMoves(Point givenPoint, BoardSide boardSide) {
		List<Point> possibleMoves = new ArrayList<>();
		possibleMoves.addAll(moveBias(givenPoint, boardSide, Orientation.BACK, Direction.LEFT, Integer.MAX_VALUE));
		possibleMoves.addAll(moveBias(givenPoint, boardSide, Orientation.BACK, Direction.RIGHT, Integer.MAX_VALUE));
		possibleMoves.addAll(moveBias(givenPoint, boardSide, Orientation.FORTH, Direction.LEFT, Integer.MAX_VALUE));
		possibleMoves.addAll(moveBias(givenPoint, boardSide, Orientation.FORTH, Direction.RIGHT, Integer.MAX_VALUE));
		return possibleMoves;
	}

}

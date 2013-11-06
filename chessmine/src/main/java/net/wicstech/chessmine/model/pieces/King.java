package net.wicstech.chessmine.model.pieces;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.wicstech.chessmine.model.BoardSide;
import net.wicstech.chessmine.model.Direction;
import net.wicstech.chessmine.model.Orientation;

import org.apache.commons.lang.math.NumberUtils;

/**
 * Rei.
 * 
 * @author Sergio
 * 
 */
public class King extends Piece {
	private static final long serialVersionUID = -7098370625157522187L;

	@Override
	public boolean acceptMove(Point newPosition) {
		List<Point> points = possibleMoves(getCurrentPosition(), getBoardSide());
		return points.contains(newPosition);
	}

	@Override
	public List<Point> possibleMoves(Point givenPoint, BoardSide boardSide) {
		List<Point> points = new ArrayList<>();
		points.addAll(moveBias(givenPoint, boardSide, Orientation.BACK, Direction.LEFT, NumberUtils.INTEGER_ONE));
		points.addAll(moveBias(givenPoint, boardSide, Orientation.BACK, Direction.RIGHT, NumberUtils.INTEGER_ONE));
		points.addAll(moveBias(givenPoint, boardSide, Orientation.FORTH, Direction.LEFT, NumberUtils.INTEGER_ONE));
		points.addAll(moveBias(givenPoint, boardSide, Orientation.FORTH, Direction.RIGHT, NumberUtils.INTEGER_ONE));
		points.addAll(moveHorizontally(givenPoint, boardSide, Direction.LEFT, NumberUtils.INTEGER_ONE));
		points.addAll(moveHorizontally(givenPoint, boardSide, Direction.RIGHT, NumberUtils.INTEGER_ONE));
		points.addAll(moveVertically(givenPoint, boardSide, Orientation.BACK, NumberUtils.INTEGER_ONE));
		points.addAll(moveVertically(givenPoint, boardSide, Orientation.FORTH, NumberUtils.INTEGER_ONE));
		return points;
	}

	/**
	 * Verificar se o rei está em cheque, fazendo o mesmo se comportar como a
	 * peça informada.
	 * 
	 * @param piece
	 * @return
	 */
	public boolean isInCheck(Piece piece) {
		List<Point> possibleMoves = piece.possibleMoves(getCurrentPosition(), getBoardSide());
		for (Point point : possibleMoves) {
			Map<Point, Piece> piecesOnBoard = getBoard().getPiecesOnBoard();
			// Existe uma peça nesta posição
			if (piecesOnBoard.containsKey(point)) {
				Piece pieceAttacking = piecesOnBoard.get(point);
				// Se a peça que estou testando, está de fato na posição de
				// ataque e ela pertence ao outro lado
				if (pieceAttacking.getClass() == piece.getClass() && !pieceAttacking.getBoardSide().equals(getBoardSide())) {
					// Este rei está em cheque
					return true;
				}

			}
		}
		return false;
	}
}

package net.wicstech.chessmine.model.pieces;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import net.wicstech.chessmine.model.BoardSide;
import net.wicstech.chessmine.model.Direction;
import net.wicstech.chessmine.model.IPromotable;
import net.wicstech.chessmine.model.Orientation;

import org.apache.commons.lang.math.NumberUtils;

/**
 * Pião.
 * 
 * @author Sergio
 * 
 */
public class Pawn extends Piece implements IPromotable<Queen> {
	private static final long serialVersionUID = 6214561026158689018L;

	@Override
	public Queen promoteTo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean acceptMove(Point newPosition) {
		List<Point> points = new ArrayList<>();
		orientedAttack(points, getBoardSide());
		points.addAll(moveVertically(getBoardSide().orientation(), NumberUtils.INTEGER_ONE));
		return points.contains(newPosition);
	}

	/**
	 * Ataque baseado na orientação da peça, porque o pião só ataca para frente,
	 * ou seja, se for do lado preto, tem que se mover descendo, se for do lado
	 * branco tem que se mover subindo.
	 * 
	 * @param points
	 * @param boardSide
	 */
	private void orientedAttack(List<Point> points, BoardSide boardSide) {

		Point leftAttack = getAttackMove(boardSide.orientation(), Direction.LEFT);
		if (leftAttack != null) {
			points.add(leftAttack);
		}
		Point rightAttack = getAttackMove(boardSide.orientation(), Direction.RIGHT);
		if (rightAttack != null) {
			points.add(rightAttack);
		}
	}

	private Point getAttackMove(Orientation back, Direction left) {
		Point point = moveBias(back, left);
		if (point != null) {
			Piece attackedPiece = getBoard().getPiecesOnBoard().get(point);
			if (attackedPiece != null && attackedPiece.getBoardSide().equals(getBoardSide().negate())) {
				return point;
			}
		}
		return null;
	}

	private Point moveBias(Orientation back, Direction left) {
		List<Point> moves = moveBias(back, left, NumberUtils.INTEGER_ONE);
		if (moves.isEmpty()) {
			return null;
		}
		return moves.get(NumberUtils.INTEGER_ZERO);
	}

	@Override
	public List<Point> possibleMoves(Point givenPoint) {
		// TODO Auto-generated method stub
		return null;
	}

}

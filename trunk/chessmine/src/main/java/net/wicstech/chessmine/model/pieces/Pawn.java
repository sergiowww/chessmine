package net.wicstech.chessmine.model.pieces;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import net.wicstech.chessmine.model.BoardSide;
import net.wicstech.chessmine.model.Direction;
import net.wicstech.chessmine.model.IPromotable;
import net.wicstech.chessmine.model.IUpdateTimesMoved;
import net.wicstech.chessmine.model.Orientation;

import org.apache.commons.lang.math.NumberUtils;

/**
 * Pião.
 * 
 * @author Sergio
 * 
 */
public class Pawn extends Piece implements IPromotable<Queen>, IUpdateTimesMoved {
	private static final int QUANTIDADE_CASAS = 2;
	private static final long serialVersionUID = 6214561026158689018L;
	private boolean firstMove = true;

	@Override
	public Queen promoteTo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean acceptMove(Point newPosition) {
		List<Point> points = new ArrayList<>();
		orientedAttack(points, getBoardSide());
		if (firstMove) {
			points.addAll(moveVertically(getBoardSide().orientation(), QUANTIDADE_CASAS));
		} else {
			points.addAll(moveVertically(getBoardSide().orientation(), NumberUtils.INTEGER_ONE));
		}
		return points.contains(newPosition);
	}

	/**
	 * Ataque baseado na orientação da peça, porque o pião só ataca para frente,
	 * ou seja, se for do lado preto, tem que se mover subindo, se for do lado
	 * branco tem que se mover descendo.
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

	/**
	 * Tenta atacar outra peça na primeira casa diagonal esquerda ou direita e
	 * retorna a peça que sofrerá o ataque.
	 * 
	 * @param orientation
	 * @param direction
	 * @return
	 */
	private Point getAttackMove(Orientation orientation, Direction direction) {
		Point point = moveBias(orientation, direction);
		if (point != null) {
			Piece attackedPiece = getBoard().getPiecesOnBoard().get(point);
			if (attackedPiece != null && attackedPiece.getBoardSide().equals(getBoardSide().negate())) {
				return point;
			}
		}
		return null;
	}

	/**
	 * Resume o retorno para um único ponto.
	 * 
	 * @param back
	 * @param left
	 * @return
	 */
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

	@Override
	public void moved() {
		firstMove = false;
	}

}

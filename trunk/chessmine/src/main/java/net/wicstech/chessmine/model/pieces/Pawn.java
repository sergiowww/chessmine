package net.wicstech.chessmine.model.pieces;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import net.wicstech.chessmine.model.BoardSide;
import net.wicstech.chessmine.model.Direction;
import net.wicstech.chessmine.model.IPromotable;
import net.wicstech.chessmine.model.IUpdateTimesMoved;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

/**
 * Pião.
 * 
 * @author Sergio
 * 
 */
@SuppressWarnings("PMD.ShortClassName")
public class Pawn extends AbstractPiece implements IPromotable<Queen>, IUpdateTimesMoved {
	private static final int MOVIMENTO_MAX_INICIAL = 2;
	private static final long serialVersionUID = 6214561026158689018L;
	private boolean firstMove = true;

	@Autowired
	private PieceFactory pieceFactory;

	/**
	 * Construtor de visibilidade padrão.
	 */
	Pawn() {
		super();
	}

	@Override
	public Queen promoteTo() {
		if (getCurrentPosition().y == getBoardSide().ultimaCasa()) {
			Queen queen = pieceFactory.newInstance(Queen.class);
			queen.setCurrentPosition(getCurrentPosition());
			queen.setBoardSide(getBoardSide());
			return queen;
		}
		return null;
	}

	@Override
	public boolean acceptMove(Point newPosition) {
		return possibleMoves(getCurrentPosition(), getBoardSide(), firstMove).contains(newPosition);
	}

	/**
	 * Ataque baseado na orientação da peça, porque o pião só ataca para frente,
	 * ou seja, se for do lado preto, tem que se mover subindo, se for do lado
	 * branco tem que se mover descendo.
	 * 
	 * @param points
	 * @param boardSide
	 * @param pos
	 */
	private void orientedAttack(List<Point> points, BoardSide boardSide, Point pos) {

		Point leftAttack = getAttackMove(boardSide, Direction.LEFT, pos);
		if (leftAttack != null) {
			points.add(leftAttack);
		}
		Point rightAttack = getAttackMove(boardSide, Direction.RIGHT, pos);
		if (rightAttack != null) {
			points.add(rightAttack);
		}
	}

	/**
	 * Tenta atacar outra peça na primeira casa diagonal esquerda ou direita e
	 * retorna a peça que sofrerá o ataque.
	 * 
	 * @param boardSide
	 * @param direction
	 * @param pos
	 * @return
	 */
	private Point getAttackMove(BoardSide boardSide, Direction direction, Point pos) {
		Point point = moveBias(boardSide, direction, pos);
		if (point != null) {
			AbstractPiece attackedPiece = getBoard().getPiecesOnBoard().get(point);
			if (attackedPiece != null && attackedPiece.getBoardSide().equals(boardSide.negate())) {
				return point;
			}
		}
		return null;
	}

	/**
	 * Resume o retorno para um único ponto.
	 * 
	 * @param boardSide
	 * @param direction
	 * @param pos
	 * @return
	 */
	private Point moveBias(BoardSide boardSide, Direction direction, Point pos) {
		List<Point> moves = moveBias(pos, boardSide, boardSide.orientation(), direction, NumberUtils.INTEGER_ONE);
		if (moves.isEmpty()) {
			return null;
		}
		return moves.get(NumberUtils.INTEGER_ZERO);
	}

	@Override
	public List<Point> possibleMoves(Point givenPoint, BoardSide boardSide) {
		return possibleMoves(givenPoint, boardSide, false);
	}

	private List<Point> possibleMoves(Point givenPoint, BoardSide boardSide, boolean first) {
		List<Point> points = new ArrayList<>();
		orientedAttack(points, boardSide, givenPoint);
		if (first) {
			points.addAll(justMoveNoAttack(givenPoint, boardSide, MOVIMENTO_MAX_INICIAL));
		} else {
			points.addAll(justMoveNoAttack(givenPoint, boardSide, NumberUtils.INTEGER_ONE));
		}
		return points;
	}

	private List<Point> justMoveNoAttack(Point givenPoint, BoardSide boardSide, Integer mov) {
		List<Point> pointsCollected = moveVertically(givenPoint, boardSide, boardSide.orientation(), mov);
		if (!CollectionUtils.isEmpty(pointsCollected)) {
			Point lastPoint = pointsCollected.get(pointsCollected.size() - NumberUtils.INTEGER_ONE);
			AbstractPiece point = getBoard().getPiecesOnBoard().get(lastPoint);
			if (point != null) {
				pointsCollected.remove(lastPoint);
			}
		}
		return pointsCollected;
	}

	@Override
	public void moved() {
		firstMove = false;
	}

}

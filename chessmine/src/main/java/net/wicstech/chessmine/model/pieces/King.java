package net.wicstech.chessmine.model.pieces;

import java.awt.Point;
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
@SuppressWarnings("PMD.ShortClassName")
public class King extends AbstractCollectBehaviorPiece {
	private static final long serialVersionUID = -7098370625157522187L;

	/**
	 * Construtor de visibilidade padr�o.
	 */
	King() {
		super();
	}

	@Override
	protected ICollector[] getCollectors(final Point givenPoint, final BoardSide boardSide) {
		ICollector[] collectors = {new ICollector() {
			@Override
			public void collect(List<Point> points) {
				points.addAll(moveVertically(givenPoint, boardSide, Orientation.FORTH, NumberUtils.INTEGER_ONE));
			}
		}, new ICollector() {

			@Override
			public void collect(List<Point> points) {
				points.addAll(moveVertically(givenPoint, boardSide, Orientation.BACK, NumberUtils.INTEGER_ONE));
			}
		}, new ICollector() {

			@Override
			public void collect(List<Point> points) {
				points.addAll(moveHorizontally(givenPoint, boardSide, Direction.RIGHT, NumberUtils.INTEGER_ONE));
			}
		}, new ICollector() {

			@Override
			public void collect(List<Point> points) {
				points.addAll(moveHorizontally(givenPoint, boardSide, Direction.LEFT, NumberUtils.INTEGER_ONE));
			}
		}, new ICollector() {

			@Override
			public void collect(List<Point> points) {
				points.addAll(moveBias(givenPoint, boardSide, Orientation.FORTH, Direction.RIGHT, NumberUtils.INTEGER_ONE));
			}
		}, new ICollector() {

			@Override
			public void collect(List<Point> points) {
				points.addAll(moveBias(givenPoint, boardSide, Orientation.FORTH, Direction.LEFT, NumberUtils.INTEGER_ONE));
			}
		}, new ICollector() {

			@Override
			public void collect(List<Point> points) {
				points.addAll(moveBias(givenPoint, boardSide, Orientation.BACK, Direction.RIGHT, NumberUtils.INTEGER_ONE));
			}
		}, new ICollector() {

			@Override
			public void collect(List<Point> points) {
				points.addAll(moveBias(givenPoint, boardSide, Orientation.BACK, Direction.LEFT, NumberUtils.INTEGER_ONE));
			}
		}};
		return collectors;
	}

	/**
	 * Verificar se o rei est� em cheque, fazendo o mesmo se comportar como a
	 * pe�a informada.
	 * 
	 * @param piece
	 * @return
	 */
	public boolean isInCheck(AbstractPiece piece) {
		List<Point> possibleMoves = piece.possibleMoves(getCurrentPosition(), getBoardSide());
		for (Point point : possibleMoves) {
			Map<Point, AbstractPiece> piecesOnBoard = getBoard().getPiecesOnBoard();
			// Existe uma pe�a nesta posi��o
			if (piecesOnBoard.containsKey(point)) {
				AbstractPiece attackingPiece = piecesOnBoard.get(point);
				// Se a pe�a que estou testando, est� de fato na posi��o de
				// ataque e ela pertence ao outro lado
				if (attackingPiece.getClass() == piece.getClass() && !attackingPiece.getBoardSide().equals(getBoardSide())) {
					// Este rei est� em cheque
					return true;
				}

			}
		}
		return false;
	}
}

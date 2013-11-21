package net.wicstech.chessmine.model.pieces;

import java.awt.Point;
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
@SuppressWarnings("ucd")
public class Bishop extends AbstractCollectBehaviorPiece {
	private static final long serialVersionUID = 5443101730149654318L;

	/**
	 * Construtor de visibilidade padrão.
	 */
	Bishop() {
		super();
	}

	@Override
	protected ICollector[] getCollectors(final Point givenPoint, final BoardSide boardSide) {
		return new ICollector[] {new ICollector() {

			@Override
			public void collect(List<Point> points) {
				points.addAll(moveBias(givenPoint, boardSide, Orientation.FORTH, Direction.RIGHT, Integer.MAX_VALUE));

			}
		}, new ICollector() {

			@Override
			public void collect(List<Point> points) {

				points.addAll(moveBias(givenPoint, boardSide, Orientation.FORTH, Direction.LEFT, Integer.MAX_VALUE));
			}
		}, new ICollector() {

			@Override
			public void collect(List<Point> points) {

				points.addAll(moveBias(givenPoint, boardSide, Orientation.BACK, Direction.RIGHT, Integer.MAX_VALUE));
			}
		}, new ICollector() {

			@Override
			public void collect(List<Point> points) {

				points.addAll(moveBias(givenPoint, boardSide, Orientation.BACK, Direction.LEFT, Integer.MAX_VALUE));
			}
		}};
	}

}

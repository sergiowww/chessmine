package net.wicstech.chessmine.model.pieces;

import java.awt.Point;
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
public class Queen extends AbstractCollectBehaviorPiece {
	private static final long serialVersionUID = 4817619455656435077L;

	/**
	 * Construtor de visibilidade padrão.
	 */
	Queen() {
		super();
	}

	@Override
	protected ICollector[] getCollectors(final Point givenPoint, final BoardSide boardSide) {
		return new ICollector[] {new ICollector() {

			@Override
			public void collect(List<Point> points) {
				points.addAll(moveVertically(givenPoint, boardSide, Orientation.FORTH, Integer.MAX_VALUE));
			}
		}, new ICollector() {

			@Override
			public void collect(List<Point> points) {
				points.addAll(moveVertically(givenPoint, boardSide, Orientation.BACK, Integer.MAX_VALUE));
			}
		}, new ICollector() {

			@Override
			public void collect(List<Point> points) {
				points.addAll(moveHorizontally(givenPoint, boardSide, Direction.RIGHT, Integer.MAX_VALUE));
			}
		}, new ICollector() {

			@Override
			public void collect(List<Point> points) {
				points.addAll(moveHorizontally(givenPoint, boardSide, Direction.LEFT, Integer.MAX_VALUE));
			}
		}, new ICollector() {

			@Override
			public void collect(List<Point> points) {
				points.addAll(moveBias(givenPoint, boardSide, Orientation.FORTH, Direction.RIGHT, Integer.MAX_VALUE));
			}
		}, new ICollector() {

			@Override
			public void collect(List<Point> points) {
				points.addAll(moveBias(givenPoint, boardSide, Orientation.BACK, Direction.LEFT, Integer.MAX_VALUE));
			}
		}, new ICollector() {

			@Override
			public void collect(List<Point> points) {
				points.addAll(moveBias(givenPoint, boardSide, Orientation.BACK, Direction.RIGHT, Integer.MAX_VALUE));
			}
		}, new ICollector() {

			@Override
			public void collect(List<Point> points) {
				points.addAll(moveBias(givenPoint, boardSide, Orientation.FORTH, Direction.LEFT, Integer.MAX_VALUE));
			}
		}};
	}

}

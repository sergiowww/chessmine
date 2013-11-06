package net.wicstech.chessmine.model.pieces;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

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
		List<Point> points = new ArrayList<>();
		points.addAll(moveBias(Orientation.BACK, Direction.LEFT, NumberUtils.INTEGER_ONE));
		points.addAll(moveBias(Orientation.BACK, Direction.RIGHT, NumberUtils.INTEGER_ONE));
		points.addAll(moveBias(Orientation.FORTH, Direction.LEFT, NumberUtils.INTEGER_ONE));
		points.addAll(moveBias(Orientation.FORTH, Direction.RIGHT, NumberUtils.INTEGER_ONE));
		points.addAll(moveHorizontally(Direction.LEFT, NumberUtils.INTEGER_ONE));
		points.addAll(moveHorizontally(Direction.RIGHT, NumberUtils.INTEGER_ONE));
		points.addAll(moveVertically(Orientation.BACK, NumberUtils.INTEGER_ONE));
		points.addAll(moveVertically(Orientation.FORTH, NumberUtils.INTEGER_ONE));
		return points.contains(newPosition);
	}

	@Override
	public List<Point> possibleMoves(Point givenPoint) {
		// TODO Auto-generated method stub
		return null;
	}

}

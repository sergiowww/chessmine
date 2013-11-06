package net.wicstech.chessmine.model.pieces;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import net.wicstech.chessmine.model.MoveAction;

/**
 * Cavalo.<br>
 * Possible Moves: (x-1, y-2) (x-2, y-1) (x-2, y+1) (x-1, y+2) (x+1, y+2) (x+2,
 * y+1) (x+2, y-1) (x+1, y-2)
 */
public class Knight extends Piece {
	private static final long serialVersionUID = 8949299390635327820L;

	@Override
	public boolean acceptMove(Point newPosition) {
		List<Point> points = new ArrayList<>();
		Point c = getCurrentPosition();
		points.add(new Point(c.x - 2, c.y - 1));
		points.add(new Point(c.x - 2, c.y + 1));
		points.add(new Point(c.x + 2, c.y + 1));
		points.add(new Point(c.x + 2, c.y - 1));
		points.add(new Point(c.x + 1, c.y + 2));
		points.add(new Point(c.x + 1, c.y - 2));
		points.add(new Point(c.x - 1, c.y - 2));
		points.add(new Point(c.x - 1, c.y + 2));
		for (int index = points.size() - 1; index >= 0; index--) {
			Point point = points.get(index);
			if (MoveAction.STOP.equals(getBoard().canItMoveTo(point, getBoardSide()))) {
				points.remove(index);
			}
		}
		return points.contains(newPosition);
	}

	@Override
	public List<Point> possibleMoves(Point givenPoint) {
		// TODO Auto-generated method stub
		return null;
	}

}

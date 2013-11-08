package net.wicstech.chessmine.model.pieces;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import net.wicstech.chessmine.model.BoardSide;
import net.wicstech.chessmine.model.MoveAction;

/**
 * Cavalo.<br>
 * Possible Moves: (x-1, y-2) (x-2, y-1) (x-2, y+1) (x-1, y+2) (x+1, y+2) (x+2,
 * y+1) (x+2, y-1) (x+1, y-2)
 */
public class Knight extends AbstractPiece {
	private static final long serialVersionUID = 8949299390635327820L;

	/**
	 * Construtor de visibilidade padrão.
	 */
	Knight() {
		super();
	}

	@Override
	public boolean acceptMove(Point newPosition) {
		List<Point> points = possibleMoves(getCurrentPosition(), getBoardSide());
		return points.contains(newPosition);
	}

	@Override
	public List<Point> possibleMoves(Point position, BoardSide boardSide) {
		List<Point> points = new ArrayList<>();
		points.add(new Point(position.x - 2, position.y - 1));
		points.add(new Point(position.x - 2, position.y + 1));
		points.add(new Point(position.x + 2, position.y + 1));
		points.add(new Point(position.x + 2, position.y - 1));
		points.add(new Point(position.x + 1, position.y + 2));
		points.add(new Point(position.x + 1, position.y - 2));
		points.add(new Point(position.x - 1, position.y - 2));
		points.add(new Point(position.x - 1, position.y + 2));
		for (int index = points.size() - 1; index >= 0; index--) {
			Point point = points.get(index);
			if (MoveAction.STOP.equals(getBoard().canItMoveTo(point, boardSide))) {
				points.remove(index);
			}
		}
		return points;
	}

}

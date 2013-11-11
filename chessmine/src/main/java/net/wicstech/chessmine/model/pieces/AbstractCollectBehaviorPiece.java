package net.wicstech.chessmine.model.pieces;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import net.wicstech.chessmine.model.BoardSide;

/**
 * Pe�a com otimiza��o da coleta de pe�as.
 * 
 * @author Sergio
 * 
 */
abstract class AbstractCollectBehaviorPiece extends AbstractPiece {
	private static final long serialVersionUID = -8984134437768976060L;

	/**
	 * Retornar os coletores.
	 * 
	 * @param givenPoint
	 * @param boardSide
	 * @return
	 */
	public abstract ICollector[] getCollectors(Point givenPoint, BoardSide boardSide);

	/**
	 * Coletar pontos at� encontrar a posi��o correta.
	 * 
	 * @param searchFor
	 * @return <code>true</code> se encontrou <code>false</code> se n�o.
	 */
	@Override
	public final boolean acceptMove(Point searchFor) {
		ICollector[] collectors = ((AbstractCollectBehaviorPiece) this).getCollectors(getCurrentPosition(), getBoardSide());
		List<Point> points = new ArrayList<>();
		for (ICollector iCollector : collectors) {
			iCollector.collect(points);
			if (points.contains(searchFor)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Recuperar lista de pontos.
	 * 
	 * @param givenPoint
	 * @param boardSide
	 * @return
	 */
	@Override
	public final List<Point> possibleMoves(Point givenPoint, BoardSide boardSide) {
		ICollector[] collectors = ((AbstractCollectBehaviorPiece) this).getCollectors(givenPoint, boardSide);
		List<Point> points = new ArrayList<>();
		for (ICollector iCollector : collectors) {
			iCollector.collect(points);
		}
		return points;
	}
}

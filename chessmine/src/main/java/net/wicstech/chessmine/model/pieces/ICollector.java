package net.wicstech.chessmine.model.pieces;

import java.awt.Point;
import java.util.List;

/**
 * Coletor de pontos.
 * 
 * @author Sergio
 * 
 */
interface ICollector {

	/**
	 * Adicionar os pontos coletados na lista.
	 * 
	 * @param points
	 */
	void collect(List<Point> points);
}

package net.wicstech.chessmine.model;

import java.awt.Point;

/**
 * Tradutor de coordenadas x e y para {@link Point}.
 * 
 * @author Sergio
 * 
 */
public class PointFactory {

	/**
	 * Traduz um new point.
	 * 
	 * @param col
	 * @param line
	 * @return
	 */
	public static Point newPoint(int col, int line) {
		return new Point(col, line);
	}

}

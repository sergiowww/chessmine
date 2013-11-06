package net.wicstech.chessmine.model;

import net.wicstech.chessmine.model.pieces.Piece;

/**
 * Peça promovível a algo.
 * 
 * @author Sergio
 * 
 * @param <T>
 *            peça de destino da promoção.
 */
public interface IPromotable<T extends Piece> {

	T promoteTo();

}

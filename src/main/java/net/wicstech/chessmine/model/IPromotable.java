package net.wicstech.chessmine.model;

import net.wicstech.chessmine.model.pieces.AbstractPiece;

/**
 * Peça promovível a algo.
 * 
 * @author Sergio
 * 
 * @param <T>
 *            peça de destino da promoção.
 */
public interface IPromotable<T extends AbstractPiece> {

	T promoteTo();

}

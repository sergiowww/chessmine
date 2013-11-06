package net.wicstech.chessmine.model;

import net.wicstech.chessmine.model.pieces.Piece;

/**
 * Pe�a promov�vel a algo.
 * 
 * @author Sergio
 * 
 * @param <T>
 *            pe�a de destino da promo��o.
 */
public interface IPromotable<T extends Piece> {

	T promoteTo();

}

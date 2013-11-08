package net.wicstech.chessmine.model;

import net.wicstech.chessmine.model.pieces.AbstractPiece;

/**
 * Pe�a promov�vel a algo.
 * 
 * @author Sergio
 * 
 * @param <T>
 *            pe�a de destino da promo��o.
 */
public interface IPromotable<T extends AbstractPiece> {

	T promoteTo();

}

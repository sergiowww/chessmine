package net.wicstech.chessmine.model.pieces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * F�brica de pe�as - constroi pe�as e injeta depend�ncia nelas.
 * 
 * @author Sergio
 * 
 */
@Service
public class PieceFactory {

	@Autowired
	private ApplicationContext context;

	/**
	 * Cria uma nova pe�a.
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("ucd")
	public <T extends AbstractPiece> T newInstance(Class<T> clazz) {
		for (PieceType pieceType : PieceType.values()) {
			if (clazz.equals(pieceType.getPieceClass())) {
				T piece = pieceType.newInstance();
				piece.setType(pieceType.ordinal());
				autowirePiece(piece);
				return piece;
			}
		}
		return null;
	}

	private <T extends AbstractPiece> void autowirePiece(T piece) {
		context.getAutowireCapableBeanFactory().autowireBean(piece);
	}

	/**
	 * Cria uma inst�ncia pelo tipo.
	 * 
	 * @param type
	 * @return
	 */
	public <T extends AbstractPiece> T newInstance(int type) {
		PieceType pieceType = PieceType.values()[type];
		T piece = pieceType.newInstance();
		piece.setType(pieceType.ordinal());
		autowirePiece(piece);
		return piece;
	}
}

package net.wicstech.chessmine.model.pieces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Fábrica de peças - constroi peças e injeta dependência nelas.
 * 
 * @author Sergio
 * 
 */
@Service
public class PieceFactory {

	@Autowired
	private ApplicationContext context;

	/**
	 * Cria uma nova peça.
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
	 * Cria uma instância pelo tipo.
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

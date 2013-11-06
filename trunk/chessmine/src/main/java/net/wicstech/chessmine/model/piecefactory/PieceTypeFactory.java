package net.wicstech.chessmine.model.piecefactory;

import net.wicstech.chessmine.model.pieces.Bishop;
import net.wicstech.chessmine.model.pieces.King;
import net.wicstech.chessmine.model.pieces.Knight;
import net.wicstech.chessmine.model.pieces.Pawn;
import net.wicstech.chessmine.model.pieces.Piece;
import net.wicstech.chessmine.model.pieces.Queen;
import net.wicstech.chessmine.model.pieces.Rook;

import org.springframework.cglib.core.ReflectUtils;

/**
 * Tipos de peça.
 * 
 * @author Sergio
 * 
 */
public enum PieceTypeFactory {
	EMPTY(null),

	PAWN(Pawn.class),

	KNIGHT(Knight.class),

	BISHOP(Bishop.class),

	ROOK(Rook.class),

	KING(King.class),

	QUEEN(Queen.class);

	private Class<?> pieceClass;

	/**
	 * Construtor padrão.
	 * 
	 * @param pieceClass
	 */
	private <T extends Piece> PieceTypeFactory(Class<T> pieceClass) {
		this.pieceClass = pieceClass;
	}

	/**
	 * Busca uma instância pelo tipo.
	 * 
	 * @param type
	 * @return
	 */
	public static <T extends Piece> T newInstance(int type) {
		PieceTypeFactory pieceType = values()[type];
		return pieceType.newInstance();
	}

	/**
	 * Cria uma nova peça.
	 * 
	 * @param clazz
	 * @return
	 */
	public static <T extends Piece> T newInstance(Class<T> clazz) {
		for (PieceTypeFactory pieceType : values()) {
			if (clazz.equals(pieceType.pieceClass)) {
				return pieceType.newInstance();
			}
		}
		return null;
	}

	/**
	 * Retorna uma nova instância do tipo.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends Piece> T newInstance() {
		return (T) ReflectUtils.newInstance(pieceClass);
	}
}

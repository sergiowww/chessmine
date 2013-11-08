package net.wicstech.chessmine.model.pieces;

import org.springframework.cglib.core.ReflectUtils;

/**
 * Tipos de peça.
 * 
 * @author Sergio
 * 
 */
enum PieceType {
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
	private <T extends AbstractPiece> PieceType(Class<T> pieceClass) {
		this.pieceClass = pieceClass;
	}

	/**
	 * @return the pieceClass
	 */
	public Class<?> getPieceClass() {
		return pieceClass;
	}

	/**
	 * Retorna uma nova instância do tipo.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends AbstractPiece> T newInstance() {
		return (T) ReflectUtils.newInstance(pieceClass);
	}
}

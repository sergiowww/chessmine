package net.wicstech.chessmine.model;

import java.awt.Point;

import net.wicstech.chessmine.model.pieces.AbstractPiece;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Gerenciador do tabuleiro, faz movimentos desfaz movimentos.
 * 
 * @author Sergio
 * 
 */
@Service
@SuppressWarnings({"PMD.ShortVariable"})
class PieceBoardMover {

	@Autowired
	private BoardCurrentGameData boardData;

	/**
	 * Realizar o movimento pedido.
	 * 
	 * @param from
	 * @param to
	 * @param piece
	 * @return
	 */
	AbstractPiece move(Point from, Point to, AbstractPiece piece) {
		AbstractPiece pieceCaptured = boardData.getPiecesOnBoard().remove(to);
		boardData.getPiecesOnBoard().remove(from);
		boardData.getPiecesOnBoard().put(to, piece);
		piece.setCurrentPosition(to);
		return pieceCaptured;
	}

	/**
	 * Desfazer o movimento realizado.
	 * 
	 * @param from
	 * @param to
	 * @param piece
	 * @param capturado
	 */
	void undomove(Point from, Point to, AbstractPiece piece, AbstractPiece capturado) {
		// restaurar a posição.
		boardData.getPiecesOnBoard().remove(to);
		boardData.getPiecesOnBoard().put(from, piece);
		if (capturado != null) {
			boardData.getPiecesOnBoard().put(to, capturado);
		}
		piece.setCurrentPosition(from);
	}
}

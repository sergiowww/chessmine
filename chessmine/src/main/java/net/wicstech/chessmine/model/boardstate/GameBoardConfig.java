package net.wicstech.chessmine.model.boardstate;

import java.util.List;

import net.wicstech.chessmine.model.BoardSide;
import net.wicstech.chessmine.model.pieces.AbstractPiece;

/**
 * Configuração carregada do tabuleiro.
 * 
 * @author Sergio
 * 
 */
public class GameBoardConfig {
	/**
	 * Peças do tabuleiro.
	 */
	private final List<AbstractPiece> pieces;

	/**
	 * Jogador que tem a vez.
	 */
	private final BoardSide boardSide;

	/**
	 * Construtor do dto.
	 * 
	 * @param pieces
	 * @param boardSide
	 */
	GameBoardConfig(List<AbstractPiece> pieces, BoardSide boardSide) {
		super();
		this.pieces = pieces;
		this.boardSide = boardSide;
	}

	/**
	 * @return the pieces
	 */
	public List<AbstractPiece> getPieces() {
		return pieces;
	}

	/**
	 * @return the boardSide
	 */
	public BoardSide getBoardSide() {
		return boardSide;
	}

}

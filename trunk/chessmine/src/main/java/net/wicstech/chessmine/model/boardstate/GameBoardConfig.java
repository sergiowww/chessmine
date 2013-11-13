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
	private List<AbstractPiece> pieces;

	/**
	 * Jogador que tem a vez.
	 */
	private BoardSide boardSide;

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
	 * @param pieces
	 *            the pieces to set
	 */
	public void setPieces(List<AbstractPiece> pieces) {
		this.pieces = pieces;
	}

	/**
	 * @return the boardSide
	 */
	public BoardSide getBoardSide() {
		return boardSide;
	}

	/**
	 * @param boardSide
	 *            the boardSide to set
	 */
	public void setBoardSide(BoardSide boardSide) {
		this.boardSide = boardSide;
	}
}

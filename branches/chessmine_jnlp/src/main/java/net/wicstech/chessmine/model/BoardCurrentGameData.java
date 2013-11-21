package net.wicstech.chessmine.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.wicstech.chessmine.model.pieces.AbstractPiece;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Service;

/**
 * Dados do jogo corrente.
 * 
 * @author Sergio
 * 
 */
@Service
public class BoardCurrentGameData {
	/**
	 * Tabuleiro de peças indexado pela coordenada.
	 */
	private final Map<Point, AbstractPiece> piecesOnBoard = new HashMap<>();

	/**
	 * Peças capturadas.
	 */
	private final List<AbstractPiece> capturedPieces = new ArrayList<>();

	/**
	 * Lado que tem a vez de jogar.
	 */
	private BoardSide boardSidePlaying = RandomUtils.nextBoolean() ? BoardSide.BLACK : BoardSide.WHITE;

	/**
	 * @return the boardSidePlaying
	 */
	public BoardSide getBoardSidePlaying() {
		return boardSidePlaying;
	}

	/**
	 * @param boardSidePlaying
	 *            the boardSidePlaying to set
	 */
	public void setBoardSidePlaying(BoardSide boardSidePlaying) {
		this.boardSidePlaying = boardSidePlaying;
	}

	/**
	 * @return the piecesOnBoard
	 */
	public Map<Point, AbstractPiece> getPiecesOnBoard() {
		return piecesOnBoard;
	}

	/**
	 * Buscar peças no tabuleiro do tipo informado.
	 * 
	 * @param clazz
	 * @param boardSide
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends AbstractPiece> T getPiece(Class<T> clazz, BoardSide boardSide) {
		Collection<? extends AbstractPiece> values = getPiecesOnBoard().values();
		for (AbstractPiece piece : values) {
			if (clazz.isInstance(piece) && boardSide.equals(piece.getBoardSide())) {
				return (T) piece;
			}
		}
		return null;
	}

	/**
	 * Retorna uma peça de cada tipo do boardSide informado constantes no
	 * tabuleiro.
	 * 
	 * @param boardSide
	 * @return
	 */
	public Collection<AbstractPiece> getDistinctPiecesOnBoard(BoardSide boardSide) {
		Map<Class<?>, AbstractPiece> distinctPieces = new HashMap<>();
		for (AbstractPiece piece : getPiecesOnBoard().values()) {
			if (piece.getBoardSide().equals(boardSide) && !distinctPieces.containsKey(piece.getClass())) {
				distinctPieces.put(piece.getClass(), piece);
			}
		}
		return distinctPieces.values();
	}

	/**
	 * @return the capturedPieces
	 */
	public List<AbstractPiece> getCapturedPieces() {
		return capturedPieces;
	}
}

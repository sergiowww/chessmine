package net.wicstech.chessmine.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import net.wicstech.chessmine.model.piecefactory.PieceLoader;
import net.wicstech.chessmine.model.pieces.Piece;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Tabuleiro.
 * 
 * @author Sergio
 * 
 */
@Service
public class Board {

	@Autowired
	private PieceLoader pieceLoader;

	private Map<Point, Piece> piecesOnBoard = new HashMap<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	private BoardSide boardSidePlaying = BoardSide.BLACK;

	/**
	 * Indica se houve a configuração inicial.
	 */
	private boolean configured;

	/**
	 * Carrega a posição inicial do tabuleiro.
	 */
	@PostConstruct
	public void initialSetup() {
		List<Piece> pieces = pieceLoader.getPiecesInitial();
		for (Piece piece : pieces) {
			piece.setBoard(this);
			piecesOnBoard.put(piece.getCurrentPosition(), piece);
		}
	}

	/**
	 * Tenta mover uma peça e retorna o sucesso do movimento.
	 * 
	 * @param from
	 * @param to
	 * @return <code>true</code> se o movimento foi concluído com sucesso
	 *         <code>false</code> se não - houver alguma violação às regras do
	 *         jogo.
	 */
	public boolean tryMoving(Point from, Point to) {
		Piece piece = piecesOnBoard.get(from);
		if (!piece.getBoardSide().equals(boardSidePlaying)) {
			return false;
		}
		if (piece.acceptMove(to)) {
			Piece pieceCaptured = piecesOnBoard.get(to);
			piecesOnBoard.remove(to);
			piecesOnBoard.remove(from);
			piecesOnBoard.put(to, piece);
			piece.setCurrentPosition(to);
			if (piece instanceof IUpdateTimesMoved) {
				((IUpdateTimesMoved) piece).moved();
			}
			capturedPieces.add(pieceCaptured);
			boardSidePlaying = boardSidePlaying.negate();
			return true;
		}
		return false;
	}

	/**
	 * Promover o pião se estiver na última casa, null se não puder ser
	 * promovido
	 */
	public Piece promote(Point lastPosition) {
		return null;
	}

	/**
	 * @return the configured
	 */
	public boolean isConfigured() {
		return configured;
	}

	/**
	 * @param configured
	 *            the configured to set
	 */
	public void setConfigured(boolean configured) {
		this.configured = configured;
	}

	/**
	 * @return the piecesOnBoard
	 */
	public Map<Point, Piece> getPiecesOnBoard() {
		return piecesOnBoard;
	}

	public void reiniciar() {
		piecesOnBoard.clear();
		capturedPieces.clear();
		setConfigured(false);
		initialSetup();
	}

	/**
	 * Checar se uma peça do lado <code>boarSide</code> pode ser deslocada para
	 * o <code>point</code> informado.<br>
	 * Ela poderá ser
	 * 
	 * @param point
	 * @param boardSide
	 * @return
	 */
	public MoveAction canItMoveTo(Point point, BoardSide boardSide) {
		if (!isValidPosition(point)) {
			return MoveAction.STOP;
		}
		boolean pontoOcupado = piecesOnBoard.containsKey(point);
		if (!pontoOcupado) {
			return MoveAction.MOVE;
		}
		BoardSide checkSide = boardSide.negate();
		if (piecesOnBoard.get(point).getBoardSide().equals(checkSide)) {
			return MoveAction.MOVE_AND_ATTACK;
		}
		return MoveAction.STOP;
	}

	/**
	 * Checa se a posição é válida no tabuleiro.
	 * 
	 * @param point
	 * @return
	 */
	private boolean isValidPosition(Point point) {
		boolean xMaiorIgualZero = point.x >= NumberUtils.INTEGER_ZERO;
		boolean yMaiorIgualZero = point.y >= NumberUtils.INTEGER_ZERO;
		boolean xMenorQueOito = point.x < Constants.TOTAL_COLUNAS;
		boolean yMenorQueOito = point.y < Constants.TOTAL_COLUNAS;
		return xMaiorIgualZero && xMenorQueOito && yMenorQueOito && yMaiorIgualZero;
	}

	/**
	 * @return the boardSidePlaying
	 */
	public BoardSide getBoardSidePlaying() {
		return boardSidePlaying;
	}
}

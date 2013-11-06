package net.wicstech.chessmine.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import net.wicstech.chessmine.model.piecefactory.PieceLoader;
import net.wicstech.chessmine.model.pieces.King;
import net.wicstech.chessmine.model.pieces.Piece;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
	private static final Log LOG = LogFactory.getLog(Board.class);

	@Autowired
	private PieceLoader pieceLoader;

	private Map<Point, Piece> piecesOnBoard = new HashMap<>();

	/**
	 * Peças capturadas.
	 */
	private List<Piece> capturedPieces = new ArrayList<>();

	/**
	 * Lado que tem a vez de jogar.
	 */
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
		LOG.info("from: " + from + " to: " + to);
		Piece piece = piecesOnBoard.get(from);
		if (!piece.getBoardSide().equals(boardSidePlaying)) {
			return false;
		}
		if (piece.acceptMove(to) && !kingsPlayerIsInCheck(from, to, piece)) {
			Piece pieceCaptured = move(from, to, piece, piecesOnBoard);
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
	 * Realizar o movimento pedido.
	 * 
	 * @param from
	 * @param to
	 * @param piece
	 * @param boardMap
	 * @return
	 */
	private Piece move(Point from, Point to, Piece piece, Map<Point, Piece> boardMap) {
		Piece pieceCaptured = boardMap.remove(to);
		boardMap.remove(from);
		boardMap.put(to, piece);
		piece.setCurrentPosition(to);
		return pieceCaptured;
	}

	/**
	 * Verifica se o rei do jogador que terminou a jogada está em cheque.
	 * 
	 * @param from
	 * @param to
	 * @param piece
	 * @return
	 */
	boolean kingsPlayerIsInCheck(Point from, Point to, Piece piece) {
		Piece capturado = move(from, to, piece, piecesOnBoard);
		boolean check = kingsPlayerIsInCheck(piece);
		undomove(from, to, piece, capturado);

		return check;
	}

	/**
	 * Desfazer o movimento realizado.
	 * 
	 * @param from
	 * @param to
	 * @param piece
	 * @param capturado
	 */
	private void undomove(Point from, Point to, Piece piece, Piece capturado) {
		// restaurar a posição.
		piecesOnBoard.remove(to);
		piecesOnBoard.put(from, piece);
		if (capturado != null) {
			piecesOnBoard.put(to, capturado);
		}
		piece.setCurrentPosition(from);
	}

	/**
	 * Checa se o após o movimento, o rei do jogador corrente ficou em cheque,
	 * se ficou, o movimento é inválido.
	 * 
	 * @param piece
	 * @return
	 */
	private boolean kingsPlayerIsInCheck(Piece piece) {
		List<King> kings = getPieces(King.class, piece.getBoardSide());
		King king = kings.get(NumberUtils.INTEGER_ZERO);
		Collection<Piece> distinctPieces = getDistinctPiecesOnBoard(piece.getBoardSide().negate());
		for (Piece pieceToBeTested : distinctPieces) {
			if (king.isInCheck(pieceToBeTested)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Retorna uma peça de cada tipo do boardSide informado constantes no
	 * tabuleiro.
	 * 
	 * @param boardSide
	 * @return
	 */
	private Collection<Piece> getDistinctPiecesOnBoard(BoardSide boardSide) {
		Map<Class<?>, Piece> distinctPieces = new HashMap<>();
		for (Piece piece : piecesOnBoard.values()) {
			if (piece.getBoardSide().equals(boardSide) && !distinctPieces.containsKey(piece.getClass())) {
				distinctPieces.put(piece.getClass(), piece);
			}
		}
		return distinctPieces.values();
	}

	/**
	 * Buscar peças no tabuleiro do tipo informado.
	 * 
	 * @param clazz
	 * @param boardSide
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T extends Piece> List<T> getPieces(Class<T> clazz, BoardSide boardSide) {
		List<T> encontrados = new ArrayList<>();
		Collection<? extends Piece> values = piecesOnBoard.values();
		for (Piece piece : values) {
			if (clazz.isInstance(piece) && boardSide.equals(piece.getBoardSide())) {
				encontrados.add((T) piece);
			}
		}
		return encontrados;
	}

	/**
	 * Promover o pião se estiver na última casa, null se não puder ser
	 * promovido TODO falta implementar
	 * 
	 * @param lastPosition
	 * @return
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

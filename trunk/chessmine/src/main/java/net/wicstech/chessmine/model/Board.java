package net.wicstech.chessmine.model;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBException;

import net.wicstech.chessmine.model.boardstate.BoardResultXML;
import net.wicstech.chessmine.model.boardstate.BoardStateXML;
import net.wicstech.chessmine.model.pieces.King;
import net.wicstech.chessmine.model.pieces.AbstractPiece;

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
@SuppressWarnings("PMD.ShortVariable")
public class Board {
	private static final Log LOG = LogFactory.getLog(Board.class);

	@Autowired
	private BoardStateXML boardConfigXML;

	@Autowired
	private BoardResultXML boardResultXML;

	/**
	 * Tabuleiro de pe�as indexado pela coordenada.
	 */
	private final Map<Point, AbstractPiece> piecesOnBoard = new HashMap<>();

	/**
	 * Pe�as capturadas.
	 */
	private final List<AbstractPiece> capturedPieces = new ArrayList<>();

	/**
	 * Lado que tem a vez de jogar.
	 */
	private BoardSide boardSidePlaying = BoardSide.BLACK;

	/**
	 * Indica se houve a configura��o inicial.
	 */
	private boolean configured;

	/**
	 * Carrega a posi��o inicial do tabuleiro.
	 */
	@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
	@PostConstruct
	public void initialSetup() {
		List<AbstractPiece> pieces = boardConfigXML.getPiecesInitial();
		for (AbstractPiece piece : pieces) {
			piecesOnBoard.put(piece.getCurrentPosition(), piece);
		}
	}

	/**
	 * Tenta mover uma pe�a e retorna o sucesso do movimento.
	 * 
	 * @param from
	 * @param to
	 * @return <code>true</code> se o movimento foi conclu�do com sucesso
	 *         <code>false</code> se n�o - houver alguma viola��o �s regras do
	 *         jogo.
	 */
	public boolean tryMoving(Point from, Point to) {
		AbstractPiece piece = piecesOnBoard.get(from);
		if (!piece.getBoardSide().equals(boardSidePlaying)) {
			return false;
		}
		if (piece.acceptMove(to) && !kingsPlayerIsInCheck(from, to, piece)) {
			AbstractPiece pieceCaptured = move(from, to, piece);
			if (piece instanceof IUpdateTimesMoved) {
				((IUpdateTimesMoved) piece).moved();
			}
			if (pieceCaptured != null) {
				capturedPieces.add(pieceCaptured);
			}
			boardSidePlaying = boardSidePlaying.negate();
			LOG.info("from: " + from + " to: " + to);
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
	 * @return
	 */
	private AbstractPiece move(Point from, Point to, AbstractPiece piece) {
		AbstractPiece pieceCaptured = piecesOnBoard.remove(to);
		piecesOnBoard.remove(from);
		piecesOnBoard.put(to, piece);
		piece.setCurrentPosition(to);
		return pieceCaptured;
	}

	/**
	 * Verifica se o rei do jogador que terminou a jogada est� em cheque.
	 * 
	 * @param from
	 * @param to
	 * @param piece
	 * @return
	 */
	boolean kingsPlayerIsInCheck(Point from, Point to, AbstractPiece piece) {
		AbstractPiece capturado = move(from, to, piece);
		boolean check = kingsPlayerIsInCheck(piece);
		undomove(from, to, piece, capturado);
		if (check) {
			LOG.info("Rei em cheque");
		}
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
	private void undomove(Point from, Point to, AbstractPiece piece, AbstractPiece capturado) {
		// restaurar a posi��o.
		piecesOnBoard.remove(to);
		piecesOnBoard.put(from, piece);
		if (capturado != null) {
			piecesOnBoard.put(to, capturado);
		}
		piece.setCurrentPosition(from);
	}

	/**
	 * Checa se o ap�s o movimento, o rei do jogador corrente ficou em cheque,
	 * se ficou, o movimento � inv�lido.
	 * 
	 * @param piece
	 * @return
	 */
	@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
	private boolean kingsPlayerIsInCheck(AbstractPiece piece) {
		List<King> kings = getPieces(King.class, piece.getBoardSide());
		King king = kings.get(NumberUtils.INTEGER_ZERO);
		Collection<AbstractPiece> distinctPieces = getDistinctPiecesOnBoard(piece.getBoardSide().negate());
		for (AbstractPiece pieceToBeTested : distinctPieces) {
			if (king.isInCheck(pieceToBeTested)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Retorna uma pe�a de cada tipo do boardSide informado constantes no
	 * tabuleiro.
	 * 
	 * @param boardSide
	 * @return
	 */
	private Collection<AbstractPiece> getDistinctPiecesOnBoard(BoardSide boardSide) {
		Map<Class<?>, AbstractPiece> distinctPieces = new HashMap<>();
		for (AbstractPiece piece : piecesOnBoard.values()) {
			if (piece.getBoardSide().equals(boardSide) && !distinctPieces.containsKey(piece.getClass())) {
				distinctPieces.put(piece.getClass(), piece);
			}
		}
		return distinctPieces.values();
	}

	/**
	 * Buscar pe�as no tabuleiro do tipo informado.
	 * 
	 * @param clazz
	 * @param boardSide
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T extends AbstractPiece> List<T> getPieces(Class<T> clazz, BoardSide boardSide) {
		List<T> encontrados = new ArrayList<>();
		Collection<? extends AbstractPiece> values = piecesOnBoard.values();
		for (AbstractPiece piece : values) {
			if (clazz.isInstance(piece) && boardSide.equals(piece.getBoardSide())) {
				encontrados.add((T) piece);
			}
		}
		return encontrados;
	}

	/**
	 * Promover o pi�o se estiver na �ltima casa, null se n�o puder ser
	 * promovido
	 * 
	 * @param lastPosition
	 * @return
	 */
	public AbstractPiece promote(Point lastPosition) {
		AbstractPiece piece = piecesOnBoard.get(lastPosition);
		if (piece instanceof IPromotable<?>) {
			AbstractPiece promoted = ((IPromotable<?>) piece).promoteTo();
			if (promoted != null) {
				piecesOnBoard.remove(lastPosition);
				piecesOnBoard.put(lastPosition, promoted);
			}
			return promoted;
		}
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
	public Map<Point, AbstractPiece> getPiecesOnBoard() {
		return piecesOnBoard;
	}

	/**
	 * Reiniciar o jogo.
	 */
	public void reiniciar() {
		piecesOnBoard.clear();
		capturedPieces.clear();
		setConfigured(false);
		initialSetup();
	}

	/**
	 * Checar se uma pe�a do lado <code>boarSide</code> pode ser deslocada para
	 * o <code>point</code> informado.<br>
	 * Ela poder� ser
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
	 * Checa se a posi��o � v�lida no tabuleiro.
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

	/**
	 * Salvar pe�as do tabuleiro.
	 * 
	 * @param arquivoDestino
	 * 
	 * @return
	 * 
	 * @throws IOException
	 * @throws JAXBException
	 */
	public String salvar(File arquivoDestino) throws JAXBException, IOException {
		return boardResultXML.savePieces(piecesOnBoard.values(), arquivoDestino);
	}
}

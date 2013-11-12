package net.wicstech.chessmine.model;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBException;

import net.wicstech.chessmine.model.boardstate.BoardState;
import net.wicstech.chessmine.model.boardstate.GameBoardConfig;
import net.wicstech.chessmine.model.pieces.AbstractPiece;
import net.wicstech.chessmine.model.pieces.King;

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
@SuppressWarnings({"PMD.ShortVariable", "PMD.GodClass"})
public class Board {
	private static final Log LOG = LogFactory.getLog(Board.class);

	@Autowired
	private BoardState boardConfigXML;

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
	private BoardSide boardSidePlaying = new Random().nextBoolean() ? BoardSide.BLACK : BoardSide.WHITE;

	/**
	 * Indica se houve a configuração inicial.
	 */
	private boolean configured;

	/**
	 * Carrega a posição inicial do tabuleiro.
	 */
	@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
	@PostConstruct
	public void initialSetup() {
		piecesOnInitialPosition();
	}

	/**
	 * Alocar peças na posição inicial.
	 */
	private void piecesOnInitialPosition() {
		setPiecesOnBoard(boardConfigXML.getPiecesInitial());
	}

	/**
	 * Alocar peças no tabuleiro.
	 * 
	 * @param pieces
	 */
	private void setPiecesOnBoard(List<AbstractPiece> pieces) {
		for (AbstractPiece piece : pieces) {
			piecesOnBoard.put(piece.getCurrentPosition(), piece);
		}
	}

	/**
	 * Tenta mover uma peça e retorna o sucesso do movimento.
	 * 
	 * @param from
	 * @param to
	 * @return codigos de retorno do {@link MoveResult}.
	 */
	public MoveResult tryMoving(Point from, Point to) {
		AbstractPiece piece = piecesOnBoard.get(from);
		if (!piece.getBoardSide().equals(boardSidePlaying)) {
			return MoveResult.ILEGAL_PLAYER;
		}
		boolean moveAccepted = piece.acceptMove(to);
		if (moveAccepted && !kingsPlayerIsInCheck(from, to, piece)) {
			AbstractPiece pieceCaptured = move(from, to, piece);
			if (piece instanceof IUpdateTimesMoved) {
				((IUpdateTimesMoved) piece).moved();
			}
			if (pieceCaptured != null) {
				capturedPieces.add(pieceCaptured);
			}
			boardSidePlaying = boardSidePlaying.negate();
			LOG.info("from: " + from + " to: " + to);
			if (isCheckMate()) {
				return MoveResult.CHECK_MATE;
			}
			return MoveResult.LEGAL;
		}
		if (!moveAccepted) {
			return MoveResult.ILEGAL;
		}

		return MoveResult.KING_IN_CHECK;
	}

	/**
	 * Verificar se a jogada anterior, finalizou o jogo.
	 * 
	 * @return
	 */
	private boolean isCheckMate() {
		King king = getPiece(King.class, boardSidePlaying);
		List<Point> possibleMoves = king.possibleMoves(king.getCurrentPosition(), king.getBoardSide());
		if (possibleMoves.isEmpty()) {
			return false;
		}
		for (Point point : possibleMoves) {
			if (!kingsPlayerIsInCheck(king.getCurrentPosition(), point, king)) {
				return false;
			}
		}
		return true;
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
	 * Verifica se o rei do jogador que terminou a jogada está em cheque.
	 * 
	 * @param from
	 *            quadrado origem
	 * @param to
	 *            quadrado destino
	 * @param piece
	 *            peça que será movida
	 * @return
	 */
	private boolean kingsPlayerIsInCheck(Point from, Point to, AbstractPiece piece) {
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
	@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
	private boolean kingsPlayerIsInCheck(AbstractPiece piece) {
		King king = getPiece(King.class, piece.getBoardSide());
		Collection<AbstractPiece> distinctPieces = getDistinctPiecesOnBoard(piece.getBoardSide().negate());
		for (AbstractPiece pieceToBeTested : distinctPieces) {
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
	 * Buscar peças no tabuleiro do tipo informado.
	 * 
	 * @param clazz
	 * @param boardSide
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T extends AbstractPiece> T getPiece(Class<T> clazz, BoardSide boardSide) {
		Collection<? extends AbstractPiece> values = piecesOnBoard.values();
		for (AbstractPiece piece : values) {
			if (clazz.isInstance(piece) && boardSide.equals(piece.getBoardSide())) {
				return (T) piece;
			}
		}
		return null;
	}

	/**
	 * Promover o pião se estiver na última casa, null se não puder ser
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
	 * 
	 * @param xmlFile
	 */
	public void reiniciar(InputStream xmlFile) {
		piecesOnBoard.clear();
		capturedPieces.clear();
		setConfigured(false);
		if (xmlFile == null) {
			piecesOnInitialPosition();
		} else {
			GameBoardConfig gameBoardConfig = boardConfigXML.loadPieces(xmlFile);
			setPiecesOnBoard(gameBoardConfig.getPieces());
			boardSidePlaying = gameBoardConfig.getBoardSide();
		}
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

	/**
	 * Salvar peças do tabuleiro.
	 * 
	 * @param arquivoDestino
	 * 
	 * @return
	 * 
	 * @throws IOException
	 * @throws JAXBException
	 */
	public String salvar(File arquivoDestino) throws JAXBException, IOException {
		return boardConfigXML.savePieces(piecesOnBoard.values(), boardSidePlaying, arquivoDestino);
	}

}

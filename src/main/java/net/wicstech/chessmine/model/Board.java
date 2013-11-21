package net.wicstech.chessmine.model;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBException;

import net.wicstech.chessmine.model.boardstate.BoardState;
import net.wicstech.chessmine.model.boardstate.GameBoardConfig;
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
public class Board {
	private static final Log LOG = LogFactory.getLog(Board.class);

	@Autowired
	private BoardState boardConfigXML;

	@Autowired
	private CheckDetector checkDetector;

	@Autowired
	private PieceBoardMover boardManager;

	@Autowired
	private BoardCurrentGameData boardData;

	/**
	 * Indica se houve a configuração inicial.
	 */
	private boolean configured;

	/**
	 * Carrega a posição inicial do tabuleiro.
	 */
	@SuppressWarnings({"PMD.DataflowAnomalyAnalysis", "ucd"})
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
			getPiecesOnBoard().put(piece.getCurrentPosition(), piece);
		}
	}

	/**
	 * Tenta mover uma peça e retorna o sucesso do movimento.
	 * 
	 * @param from
	 * @param to
	 * @return codigos de retorno do {@link MoveResult}.
	 */
	@SuppressWarnings({"PMD.ShortVariable"})
	public MoveResult tryMoving(Point from, Point to) {
		AbstractPiece piece = getPiecesOnBoard().get(from);
		if (!piece.getBoardSide().equals(getBoardSidePlaying())) {
			return MoveResult.ILEGAL_PLAYER;
		}
		boolean moveAccepted = piece.acceptMove(to);
		if (moveAccepted && !checkDetector.kingsPlayerIsInCheck(from, to, piece)) {
			AbstractPiece pieceCaptured = boardManager.move(from, to, piece);
			if (piece instanceof IUpdateTimesMoved) {
				((IUpdateTimesMoved) piece).moved();
			}
			if (pieceCaptured != null) {
				boardData.getCapturedPieces().add(pieceCaptured);
			}
			boardData.setBoardSidePlaying(getBoardSidePlaying().negate());
			LOG.info("from: " + from + " to: " + to);
			if (checkDetector.isCheckMate()) {
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
	 * Promover o pião se estiver na última casa, null se não puder ser
	 * promovido
	 * 
	 * @param lastPosition
	 * @return
	 */
	public AbstractPiece promote(Point lastPosition) {
		AbstractPiece piece = getPiecesOnBoard().get(lastPosition);
		if (piece instanceof IPromotable<?>) {
			AbstractPiece promoted = ((IPromotable<?>) piece).promoteTo();
			if (promoted != null) {
				getPiecesOnBoard().remove(lastPosition);
				getPiecesOnBoard().put(lastPosition, promoted);
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
	 * Reiniciar o jogo.
	 * 
	 * @param xmlFile
	 */
	public void reiniciar(InputStream xmlFile) {
		getPiecesOnBoard().clear();
		boardData.getCapturedPieces().clear();
		setConfigured(false);
		if (xmlFile == null) {
			piecesOnInitialPosition();
		} else {
			GameBoardConfig gameBoardConfig = boardConfigXML.loadPieces(xmlFile);
			setPiecesOnBoard(gameBoardConfig.getPieces());
			boardData.setBoardSidePlaying(gameBoardConfig.getBoardSide());
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
		boolean pontoOcupado = getPiecesOnBoard().containsKey(point);
		if (!pontoOcupado) {
			return MoveAction.MOVE;
		}
		BoardSide checkSide = boardSide.negate();
		if (getPiecesOnBoard().get(point).getBoardSide().equals(checkSide)) {
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
		return boardConfigXML.savePieces(arquivoDestino);
	}

	public BoardSide getBoardSidePlaying() {
		return boardData.getBoardSidePlaying();
	}

	public Map<Point, AbstractPiece> getPiecesOnBoard() {
		return boardData.getPiecesOnBoard();
	}

}

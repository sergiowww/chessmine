package net.wicstech.chessmine.model;

import java.awt.Point;
import java.util.Collection;
import java.util.List;

import net.wicstech.chessmine.model.pieces.AbstractPiece;
import net.wicstech.chessmine.model.pieces.King;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Funcionalidades para detectar o check ou check-mate.
 * 
 * @author Sergio
 * 
 */
@Service
@SuppressWarnings({"PMD.ShortVariable"})
class CheckDetector {
	private static final Log LOG = LogFactory.getLog(CheckDetector.class);

	@Autowired
	private PieceBoardMover boardManager;

	@Autowired
	private BoardCurrentGameData boardData;

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
	boolean kingsPlayerIsInCheck(Point from, Point to, AbstractPiece piece) {
		AbstractPiece capturado = boardManager.move(from, to, piece);
		boolean check = kingsPlayerIsInCheck(piece);
		boardManager.undomove(from, to, piece, capturado);
		if (check) {
			LOG.info("Rei em cheque");
		}
		return check;
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
		King king = boardData.getPiece(King.class, piece.getBoardSide());
		Collection<AbstractPiece> distinctPieces = boardData.getDistinctPiecesOnBoard(piece.getBoardSide().negate());
		for (AbstractPiece pieceToBeTested : distinctPieces) {
			if (king.isInCheck(pieceToBeTested)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Verificar se a jogada anterior, finalizou o jogo.
	 * 
	 * @return
	 */
	public boolean isCheckMate() {
		King king = boardData.getPiece(King.class, boardData.getBoardSidePlaying());
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

}

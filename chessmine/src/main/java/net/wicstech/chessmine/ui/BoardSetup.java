package net.wicstech.chessmine.ui;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import net.wicstech.chessmine.model.Board;
import net.wicstech.chessmine.model.Constants;
import net.wicstech.chessmine.model.PointFactory;
import net.wicstech.chessmine.model.pieces.AbstractPiece;

/**
 * Construir os quadrados de cores alternadas, onde as peças serão alocadas.
 * 
 * @author Sergio
 * 
 */
public class BoardSetup {
	private final GridPane gameBoard;
	private final Board board;

	/**
	 * Construtor.
	 * 
	 * @param gameBoard
	 * @param board
	 */
	public BoardSetup(GridPane gameBoard, Board board) {
		super();
		this.gameBoard = gameBoard;
		this.board = board;
	}

	/**
	 * Preencher quadrados com cores alternadas.
	 * 
	 * @param painelMensagem
	 * @return
	 */
	@SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
	public Map<Point, SquarePane> setupSquares(Label painelMensagem) {
		Map<Point, SquarePane> indicePaineis = new HashMap<>();
		int totalColunas = Constants.TOTAL_COLUNAS;
		boolean alternarCorFundo = true;
		for (int i = 0; i < totalColunas; i++) {
			alternarCorFundo = !alternarCorFundo;
			for (int j = 0; j < totalColunas; j++) {
				Point point = PointFactory.newPoint(i, j);
				SquarePane pane = new SquarePane(alternarCorFundo, indicePaineis, board, point);
				pane.setPainelMensagem(painelMensagem);
				indicePaineis.put(point, pane);
				gameBoard.add(pane, i, j);
				alternarCorFundo = !alternarCorFundo;
			}
		}
		return indicePaineis;
	}

	/**
	 * Alocar as peças do xadrês no tabuleiro.
	 * 
	 * @param piecesOnBoard
	 * @param squarePositions
	 */
	@SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
	public void allocatePiecesOnBoard(Map<Point, AbstractPiece> piecesOnBoard, Map<Point, SquarePane> squarePositions) {
		for (AbstractPiece piece : piecesOnBoard.values()) {
			PieceView pieceView = new PieceView(piece);
			SquarePane pane = squarePositions.get(piece.getCurrentPosition());
			pane.setPieceView(pieceView);
		}
	}

}

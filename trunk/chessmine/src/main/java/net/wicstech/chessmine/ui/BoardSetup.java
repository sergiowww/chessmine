package net.wicstech.chessmine.ui;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.layout.GridPane;
import net.wicstech.chessmine.model.Board;
import net.wicstech.chessmine.model.Constants;
import net.wicstech.chessmine.model.PointFactory;
import net.wicstech.chessmine.model.pieces.Piece;

/**
 * Construir os quadrados de cores alternadas, onde as peças serão alocadas.
 * 
 * @author Sergio
 * 
 */
public class BoardSetup {
	private GridPane gameBoard;
	private Board board;

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
	 * @param gameBoard
	 * @return
	 */
	public Map<Point, SquarePane> setupSquares() {
		Map<Point, SquarePane> indicePaineis = new HashMap<>();
		int totalColunas = Constants.TOTAL_COLUNAS;
		boolean alternarCorFundo = true;
		for (int i = 0; i < totalColunas; i++) {
			alternarCorFundo = !alternarCorFundo;
			for (int j = 0; j < totalColunas; j++) {
				SquarePane pane = new SquarePane(alternarCorFundo, indicePaineis, board);
				Point point = PointFactory.newPoint(i, j);
				pane.setSquarePosition(point);
				indicePaineis.put(point, pane);
				gameBoard.add(pane, i, j);
				alternarCorFundo = !alternarCorFundo;
			}
		}
		return indicePaineis;
	}

	public void allocatePiecesOnBoard(Map<Point, Piece> piecesOnBoard, Map<Point, SquarePane> squarePositions) {
		for (Piece piece : piecesOnBoard.values()) {
			PieceView pieceView = new PieceView(piece);
			SquarePane pane = squarePositions.get(piece.getCurrentPosition());
			pane.setPieceView(pieceView);
		}
	}

	/**
	 * @param gameBoard
	 *            the gameBoard to set
	 */
	public void setGameBoard(GridPane gameBoard) {
		this.gameBoard = gameBoard;
	}

}

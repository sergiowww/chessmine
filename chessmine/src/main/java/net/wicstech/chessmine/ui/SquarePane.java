package net.wicstech.chessmine.ui;

import java.awt.Point;
import java.util.Map;

import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.TilePane;
import net.wicstech.chessmine.model.Board;
import net.wicstech.chessmine.model.BoardSide;
import net.wicstech.chessmine.model.pieces.Piece;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * Quadrado.
 * 
 * @author Sergio
 * 
 */
public class SquarePane extends TilePane {

	private Point squarePosition;

	public SquarePane(boolean alternarCorFundo, final Map<Point, SquarePane> indicePaineis, final Board board) {
		setStyle(BooleanUtils.toString(alternarCorFundo, BoardSide.WHITE.color(), BoardSide.BLACK.color()));
		setOrientation(Orientation.HORIZONTAL);
		setAlignment(Pos.CENTER);

		setOnDragOver(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				if (db.hasContent(UIConstants.CHESS_PIECE_VIEW)) {
					event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
				}
			}
		});
		setOnDragDropped(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				Dragboard dragboard = event.getDragboard();
				Piece piece = (Piece) dragboard.getContent(UIConstants.CHESS_PIECE_VIEW);
				if (board.tryMoving(piece.getCurrentPosition(), squarePosition)) {
					SquarePane quadradoOrigem = indicePaineis.get(piece.getCurrentPosition());
					PieceView pieceViewOrigem = quadradoOrigem.getPieceView();
					quadradoOrigem.getChildren().remove(pieceViewOrigem);
					setPieceView(pieceViewOrigem);
				}
			}
		});
	}

	/**
	 * Adicionar peça ao quadrado.
	 * 
	 * @param pieceView
	 */
	public void setPieceView(PieceView pieceView) {
		getChildren().clear();
		getChildren().add(pieceView);
	}

	/**
	 * Peça associada com o quadrado.
	 * 
	 * @return
	 */
	public PieceView getPieceView() {
		return (PieceView) getChildren().get(NumberUtils.INTEGER_ZERO);
	}

	/**
	 * @param squarePosition
	 *            the squarePosition to set
	 */
	public void setSquarePosition(Point squarePosition) {
		this.squarePosition = squarePosition;
	}
}

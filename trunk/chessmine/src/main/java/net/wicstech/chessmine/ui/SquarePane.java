package net.wicstech.chessmine.ui;

import java.awt.Point;
import java.util.Map;

import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
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
	private Board board;
	private Map<Point, SquarePane> indicePaineis;
	private Label painelMensagem;

	public SquarePane(boolean alternarCorFundo, Map<Point, SquarePane> indicePaineis, Board board) {
		this.indicePaineis = indicePaineis;
		this.board = board;
		setStyle(BooleanUtils.toString(alternarCorFundo, BoardSide.WHITE.color(), BoardSide.BLACK.color()));
		setOrientation(Orientation.HORIZONTAL);
		setAlignment(Pos.CENTER);

		setOnDragOver(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				if (db.hasContent(UIConstants.POINT_CURRENT_POSITION)) {
					event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
				}
			}
		});
		setOnDragDropped(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				droppedPiece(event);
			}

		});
	}

	private void droppedPiece(DragEvent event) {
		Dragboard dragboard = event.getDragboard();
		Point currentPosition = (Point) dragboard.getContent(UIConstants.POINT_CURRENT_POSITION);
		boolean procceedMove = board.tryMoving(currentPosition, squarePosition);
		if (procceedMove) {
			SquarePane quadradoOrigem = indicePaineis.get(currentPosition);
			PieceView pieceViewOrigem = quadradoOrigem.getPieceView();
			quadradoOrigem.getChildren().remove(pieceViewOrigem);
			painelMensagem.setText(getMensagemAguardando());
			setOrPromotePieceView(pieceViewOrigem);
		} else {
			painelMensagem.setText("Movimento inválido!");
		}
	}

	private void setOrPromotePieceView(PieceView pieceViewOrigem) {
		Piece newPiece = board.promote(squarePosition);
		if (newPiece != null) {
			setPieceView(new PieceView(newPiece));
		} else {
			setPieceView(pieceViewOrigem);
		}
	}

	private String getMensagemAguardando() {
		return "Aguardando jogador " + board.getBoardSidePlaying().name() + " executar um movimento.";
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

	/**
	 * @param painelMensagem
	 *            the painelMensagem to set
	 */
	public void setPainelMensagem(Label painelMensagem) {
		this.painelMensagem = painelMensagem;
		this.painelMensagem.setText(getMensagemAguardando());
	}
}

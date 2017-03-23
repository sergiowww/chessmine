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
import net.wicstech.chessmine.model.MoveResult;
import net.wicstech.chessmine.model.pieces.AbstractPiece;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * Quadrado.
 * 
 * @author Sergio
 * 
 */
public class SquarePane extends TilePane {
	private final Point squarePosition;
	private final Board board;
	private final Map<Point, SquarePane> indicePaineis;
	private Label painelMensagem;

	public SquarePane(boolean alternarCorFundo, Map<Point, SquarePane> indicePaineis, Board board, Point squarePosition) {
		super();
		this.indicePaineis = indicePaineis;
		this.board = board;
		this.squarePosition = squarePosition;
		setStyle("-fx-background-color: " + BooleanUtils.toString(alternarCorFundo, BoardSide.WHITE.color(), BoardSide.BLACK.color()));
		setOrientation(Orientation.HORIZONTAL);
		setAlignment(Pos.CENTER);

		setOnDragOver(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				Dragboard dragBoard = event.getDragboard();
				if (dragBoard.hasContent(UIConstants.POINT_CURRENT_POSITION)) {
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
	
	@SuppressWarnings({"PMD.SwitchStmtsShouldHaveDefault"})
	private void droppedPiece(DragEvent event) {
		Dragboard dragboard = event.getDragboard();
		Point currentPosition = (Point) dragboard.getContent(UIConstants.POINT_CURRENT_POSITION);
		MoveResult procceedMove = board.tryMoving(currentPosition, squarePosition);

		switch (procceedMove) {
		case CHECK_MATE:
			updateBoard(currentPosition);
			painelMensagem.setText("O jogador " + board.getBoardSidePlaying().negate() + " ganhou a partida, CHECK MATE!");
			break;

		case LEGAL:
			updateBoard(currentPosition);
			painelMensagem.setText(getMensagemAguardando());
			break;

		case ILEGAL:
			painelMensagem.setText("Movimento inválido!");
			break;

		case KING_IN_CHECK:
			painelMensagem.setText("O seu movimento deixou seu rei em cheque!");
			break;

		case ILEGAL_PLAYER:
			painelMensagem.setText("Não é sua vez de jogar!");
			break;

		}
	}

	private void updateBoard(Point currentPosition) {
		SquarePane quadradoOrigem = indicePaineis.get(currentPosition);
		PieceView pieceViewOrigem = quadradoOrigem.getPieceView();
		quadradoOrigem.getChildren().remove(pieceViewOrigem);
		setOrPromotePieceView(pieceViewOrigem);
	}

	private void setOrPromotePieceView(PieceView pieceViewOrigem) {
		AbstractPiece newPiece = board.promote(squarePosition);
		if (newPiece == null) {
			setPieceView(pieceViewOrigem);
		} else {
			setPieceView(new PieceView(newPiece));
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
	 * @param painelMensagem
	 *            the painelMensagem to set
	 */
	public void setPainelMensagem(Label painelMensagem) {
		this.painelMensagem = painelMensagem;
		this.painelMensagem.setText(getMensagemAguardando());
	}
}

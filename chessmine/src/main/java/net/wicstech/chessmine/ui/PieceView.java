package net.wicstech.chessmine.ui;

import java.io.InputStream;

import javafx.event.EventHandler;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import net.wicstech.chessmine.model.pieces.Piece;

/**
 * Visualização da peça.
 * 
 * @author Sergio
 * 
 */
public class PieceView extends ImageView {

	public PieceView(final Piece piece) {
		String icone = "/images/" + piece.getIconName() + ".png";
		InputStream input = getClass().getResourceAsStream(icone);
		Image image = new Image(input, 0, 50, true, true);
		setImage(image);
		setPreserveRatio(true);
		setEffect(new DropShadow(4, Color.BLACK));

		setOnDragDetected(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				Dragboard db = startDragAndDrop(TransferMode.COPY_OR_MOVE);
				ClipboardContent clipboardContent = new ClipboardContent();
				clipboardContent.put(UIConstants.CHESS_PIECE_VIEW, piece);
				db.setContent(clipboardContent);
				event.consume();
			}
		});
	}

}

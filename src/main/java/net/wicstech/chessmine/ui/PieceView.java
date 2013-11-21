package net.wicstech.chessmine.ui;

import java.io.InputStream;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import net.wicstech.chessmine.model.pieces.AbstractPiece;

/**
 * Visualização da peça.
 * 
 * @author Sergio
 * 
 */
public class PieceView extends ImageView {

	public PieceView(final AbstractPiece piece) {
		super();
		String icone = "/images/" + piece.getIconName() + ".png";
		InputStream input = getClass().getResourceAsStream(icone);
		Image image = new Image(input, 0, 50, true, true);
		setImage(image);
		setPreserveRatio(true);

		setOnDragDetected(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				Dragboard dragBoard = startDragAndDrop(TransferMode.COPY_OR_MOVE);
				ClipboardContent clipboardContent = new ClipboardContent();
				clipboardContent.put(UIConstants.POINT_CURRENT_POSITION, piece.getCurrentPosition());
				dragBoard.setContent(clipboardContent);
				event.consume();
			}
		});
	}

}

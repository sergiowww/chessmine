package net.wicstech.chessmine.ui;

import java.awt.Point;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import net.wicstech.chessmine.model.Board;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

/**
 * 
 * Tela principal.
 * 
 * @author Sergio
 * 
 */
@Controller
public class MainController implements Initializable {
	private static final Log LOG = LogFactory.getLog(MainController.class);
	@FXML
	private GridPane gameBoard;

	@Autowired
	private Board board;

	@Autowired
	private ClassPathXmlApplicationContext context;

	private Map<Point, SquarePane> squarePositions;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		inicializar();
	}

	private void inicializar() {
		synchronized (board) {
			if (!board.isConfigured()) {
				BoardSetup boardSetup = new BoardSetup(gameBoard, board);
				squarePositions = boardSetup.setupSquares();
				boardSetup.allocatePiecesOnBoard(board.getPiecesOnBoard(), squarePositions);
				board.setConfigured(true);
			}
		}
	}

	/**
	 * Sair do jogo.
	 */
	public void sair() {
		context.stop();
		context.destroy();
		System.exit(NumberUtils.INTEGER_ZERO);
	}

	/**
	 * Reiniciar o jogo.
	 */
	public void novoJogo() {
		LOG.info("Reiniciando tabuleiro");
		gameBoard.getChildren().clear();
		board.reiniciar();
		inicializar();
	}
}

package net.wicstech.chessmine.ui;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.Duration;

import javax.xml.bind.JAXBException;

import net.wicstech.chessmine.model.Board;

import org.apache.commons.lang.SystemUtils;
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

	@FXML
	private Label painelMensagem;

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
				squarePositions = boardSetup.setupSquares(painelMensagem);
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
		Platform.exit();
	}

	/**
	 * Carregar jogo salvo.
	 */
	@SuppressWarnings("PMD.AvoidCatchingGenericException")
	public void carregarJogo() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Abrir um jogo salvo...");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML File", "*.xml"));
		fileChooser.setInitialDirectory(SystemUtils.getUserHome());
		File file = fileChooser.showOpenDialog(getWindow());
		try {
			abrirJogo(file);
		} catch (Exception e) {
			painelMensagem.setText("Não foi possível abrir este jogo: " + e.getMessage());
		}

	}

	/**
	 * Reiniciar o jogo.
	 * 
	 * @throws FileNotFoundException
	 */
	public void novoJogo() throws FileNotFoundException {
		LOG.info("Reiniciando tabuleiro");

		abrirJogo(null);
	}

	/**
	 * Abrir jogo ou carregar um novo.
	 * 
	 * @param xmlFile
	 * @throws FileNotFoundException
	 */
	private void abrirJogo(File xmlFile) throws FileNotFoundException {
		FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000));
		fadeTransition.setFromValue(1.0f);
		fadeTransition.setToValue(0.0);
		fadeTransition.setNode(gameBoard);
		fadeTransition.play();

		gameBoard.getChildren().clear();
		FileInputStream fis = null;
		if (xmlFile != null && xmlFile.exists()) {
			fis = new FileInputStream(xmlFile);
		}
		board.reiniciar(fis);
		inicializar();

		fadeTransition = new FadeTransition(Duration.millis(1000));
		fadeTransition.setFromValue(0);
		fadeTransition.setNode(gameBoard);
		fadeTransition.setToValue(1.0);
		fadeTransition.play();
	}

	/**
	 * Salvar posição das peças.
	 * 
	 * @throws IOException
	 * @throws JAXBException
	 */
	public void salvarJogo() throws JAXBException, IOException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Escolha onde salvar o arquivo");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML File", "*.xml"));
		fileChooser.setInitialFileName("board-result.xml");
		fileChooser.setInitialDirectory(SystemUtils.getUserHome());
		File file = fileChooser.showSaveDialog(getWindow());
		if (file != null) {
			String fileName = board.salvar(file);
			painelMensagem.setText("Configuração das peças gravada em " + fileName);
		}
	}

	/**
	 * Instância da janela.
	 * 
	 * @return
	 */
	private Window getWindow() {
		return gameBoard.getScene().getWindow();
	}

	/**
	 * Informações sobre o jogo.
	 */
	public void sobre() {
		painelMensagem.setText("ChessMine 2013 wicstech.net - Sergio Eduardo D Oliveira");
	}

}

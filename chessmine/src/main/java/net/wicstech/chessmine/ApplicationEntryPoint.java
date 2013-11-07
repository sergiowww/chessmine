package net.wicstech.chessmine;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Ponto de entrada da aplicação.
 * 
 * @author Sergio
 * 
 */
public class ApplicationEntryPoint extends Application {
	private static final Log LOG = LogFactory.getLog(ApplicationEntryPoint.class);

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		LOG.info("Iniciando aplicação...");
		Scene scene = new Scene(ApplicationControl.load("MainLayout.fxml"));
		primaryStage.setTitle("Chess Mine");
		primaryStage.setMinHeight(500);
		primaryStage.setHeight(500);
		primaryStage.setWidth(500);
		primaryStage.setMinWidth(500);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent arg0) {
				ApplicationControl.stop();
			}
		});
	}

}

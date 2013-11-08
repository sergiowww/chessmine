package net.wicstech.chessmine;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Utilit�rios de tela.
 * 
 * @author Sergio
 * 
 */
public final class ApplicationControl {

	private static ClassPathXmlApplicationContext context;

	/**
	 * N�o pode ser instanciado.
	 */
	private ApplicationControl() {
		super();
	}

	/**
	 * Carregar fxml.
	 * 
	 * @param fxmlFile
	 * @param primaryStage
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("PMD.LawOfDemeter")
	public static Parent load(String fxmlFile) throws IOException {
		synchronized (ApplicationControl.class) {
			if (context == null) {
				context = new ClassPathXmlApplicationContext("applicationContext.xml");
				context.start();
			}
		}
		URL resource = ApplicationControl.class.getResource("/fxml/" + fxmlFile);
		FXMLLoader loader = new FXMLLoader(resource);
		loader.setControllerFactory(new SpringControllerFactory(context));
		return (Parent) loader.load();
	}

	/**
	 * Parar contexto spring.
	 */
	public static void stop() {
		context.stop();
		context.destroy();
	}

}

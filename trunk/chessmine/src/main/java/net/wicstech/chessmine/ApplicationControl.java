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
public abstract class ApplicationControl {

	private static ClassPathXmlApplicationContext context;

	/**
	 * Carregar fxml.
	 * 
	 * @param fxmlFile
	 * @return
	 */
	public static Parent load(String fxmlFile) {
		if (context == null) {
			context = new ClassPathXmlApplicationContext("applicationContext.xml");
			context.start();
		}
		URL resource = ApplicationControl.class.getResource("/fxml/" + fxmlFile);
		try {
			FXMLLoader loader = new FXMLLoader(resource);
			loader.setControllerFactory(new SpringControllerFactory(context));
			return (Parent) loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
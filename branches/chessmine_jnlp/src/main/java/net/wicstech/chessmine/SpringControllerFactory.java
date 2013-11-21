package net.wicstech.chessmine;

import javafx.util.Callback;

import org.springframework.context.ApplicationContext;

/**
 * Spring Controller Factory.
 * 
 * @author Sergio
 * 
 */
final class SpringControllerFactory implements Callback<Class<?>, Object> {
	private final ApplicationContext context;

	public SpringControllerFactory(ApplicationContext context) {
		this.context = context;
	}

	@Override
	public Object call(Class<?> param) {
		return context.getBean(param);
	}
}
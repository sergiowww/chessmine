package net.wicstech.chessmine.model.boardstate;

/**
 * Exceção de leitura do XML.
 * 
 * @author Sergio
 * 
 */
public class BoardStateReadException extends RuntimeException {
	private static final long serialVersionUID = 1843696539705600291L;

	public BoardStateReadException(String message, Throwable cause) {
		super(message, cause);
	}

	public BoardStateReadException(Throwable cause) {
		super(cause);
	}

}

package net.wicstech.chessmine.model;

/**
 * Lado do jogador.
 * 
 * @author Sergio
 * 
 */
public enum BoardSide {
	BLACK("-fx-background-color: brown;", Orientation.FORTH),

	WHITE("-fx-background-color: white;", Orientation.BACK);

	private String backgroundColor;
	private Orientation orientation;

	private BoardSide(String backgroundColor, Orientation orientation) {
		this.backgroundColor = backgroundColor;
		this.orientation = orientation;
	}

	/**
	 * @return the backgroundColor
	 */
	public String color() {
		return backgroundColor;
	}

	/**
	 * Retorna o lado oposto deste.
	 * 
	 * @return
	 */
	public BoardSide negate() {
		if (BLACK.equals(this)) {
			return WHITE;
		}
		return BLACK;
	}

	/**
	 * Direção do ataque.
	 * 
	 * @return
	 */
	public Orientation orientation() {
		return orientation;
	}
}

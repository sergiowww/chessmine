package net.wicstech.chessmine.model;

import org.apache.commons.lang.math.NumberUtils;

/**
 * Lado do jogador.
 * 
 * @author Sergio
 * 
 */
public enum BoardSide {
	BLACK(Orientation.FORTH, 7, "brown"),

	WHITE(Orientation.BACK, NumberUtils.INTEGER_ZERO, "white");

	/**
	 * Direção do ataque das peças que estão deste lado.
	 */
	private Orientation orientation;

	/**
	 * Última casa que as peças deste lado tendem a ir.
	 */
	private int ultimaCasa;
	private String color;

	private BoardSide(Orientation orientation, int ultimaCasa, String color) {
		this.orientation = orientation;
		this.ultimaCasa = ultimaCasa;
		this.color = color;
	}

	/**
	 * @return the backgroundColor
	 */
	public String color() {
		return color;
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

	/**
	 * @return the ultimaCasa
	 */
	public int ultimaCasa() {
		return ultimaCasa;
	}
}

package net.wicstech.chessmine.model;

import org.apache.commons.lang.math.NumberUtils;

/**
 * Lado do jogador.
 * 
 * @author Sergio
 * 
 */
public enum BoardSide {
	BLACK(Orientation.FORTH, 7),

	WHITE(Orientation.BACK, NumberUtils.INTEGER_ZERO);

	/**
	 * Dire��o do ataque das pe�as que est�o deste lado.
	 */
	private Orientation orientation;

	/**
	 * �ltima casa que as pe�as deste lado tendem a ir.
	 */
	private int ultimaCasa;

	private BoardSide(Orientation orientation, int ultimaCasa) {
		this.orientation = orientation;
		this.ultimaCasa = ultimaCasa;
	}

	/**
	 * @return the backgroundColor
	 */
	public String color() {
		return name().toLowerCase();
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
	 * Dire��o do ataque.
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

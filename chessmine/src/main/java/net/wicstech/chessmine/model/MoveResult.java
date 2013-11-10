package net.wicstech.chessmine.model;

/**
 * Informar o resultado do movimento, se resultou em um movimento legal, ilegal,
 * cheque ou cheque-mate.
 * 
 * @author Sergio
 * 
 */
public enum MoveResult {
	/**
	 * Movimento legal.
	 */
	LEGAL,

	/**
	 * Movimento ilegal
	 */
	ILEGAL,

	/**
	 * Movimento deixou o rei em cheque, portante ilegal.
	 */
	KING_IN_CHECK,

	/**
	 * Não é a vez do jogador que fez a jogada.
	 */
	ILEGAL_PLAYER

}

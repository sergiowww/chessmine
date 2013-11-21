package net.wicstech.chessmine.model;

/**
 * Indicação do movimento da peça a cada quadrado.
 * 
 * @author Sergio
 * 
 */
public enum MoveAction {
	/**
	 * Pode mover e continue andando.
	 */
	MOVE,

	/**
	 * Pode mover, vai atacar mas para onde atacou.
	 */
	MOVE_AND_ATTACK,

	/**
	 * Não pode ir para esta posição.
	 */
	STOP
}

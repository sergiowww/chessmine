package net.wicstech.chessmine.model;

/**
 * Indica��o do movimento da pe�a a cada quadrado.
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
	 * N�o pode ir para esta posi��o.
	 */
	STOP
}

package net.wicstech.chessmine.model.pieces;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.wicstech.chessmine.model.Board;
import net.wicstech.chessmine.model.BoardSide;
import net.wicstech.chessmine.model.Direction;
import net.wicstech.chessmine.model.MoveAction;
import net.wicstech.chessmine.model.Orientation;

/**
 * Peça genérica.
 * 
 * @author Sergio
 * 
 */
public abstract class Piece implements Serializable {
	private static final long serialVersionUID = 6058030896941443816L;
	private static final List<MoveAction> TIPOS_VALIDOS = Arrays.asList(MoveAction.MOVE, MoveAction.MOVE_AND_STOP);

	private Point currentPosition;
	private BoardSide boardSide;
	private Board board;

	/**
	 * Aceita ou não o movimento realizado pelo usuário.
	 */
	public abstract boolean acceptMove(Point newPosition);

	/**
	 * Retorna a coordenada dos possíveis movimentos a partir do ponto atual
	 */
	public abstract List<Point> possibleMoves(Point givenPoint);

	/**
	 * Retorna o nome do ícone.
	 * 
	 * @return
	 */
	public final String getIconName() {
		String pieceName = getClass().getSimpleName().toLowerCase();
		String colorName = boardSide.name().toLowerCase();
		return colorName + "-" + pieceName;
	}

	/**
	 * Mover horizontalmente
	 */
	protected List<Point> moveHorizontally(Direction side, int squares) {
		return null;
	}

	/**
	 * Mover verticalmente
	 */
	protected List<Point> moveVertically(Orientation direction, int squares) {
		return Collections.emptyList();
	}

	/**
	 * Mover diagonalmente.
	 * 
	 * @param orientation
	 * @param direction
	 * @param squares
	 * @return
	 */
	protected List<Point> moveBias(Orientation orientation, Direction direction, int squares) {
		Point point = currentPosition.getLocation();
		List<Point> points = new ArrayList<>();
		MoveAction legalMove = MoveAction.STOP;
		do {
			if (Direction.LEFT.equals(direction)) {
				point.x--;
			}
			if (Direction.RIGHT.equals(direction)) {
				point.x++;
			}
			if (Orientation.BACK.equals(orientation)) {
				point.y--;
			}
			if (Orientation.FORTH.equals(orientation)) {
				point.y++;
			}
			legalMove = board.canItMoveTo(point, boardSide);
			if (TIPOS_VALIDOS.contains(legalMove)) {
				points.add(point);
			}
			point = point.getLocation();
		} while (legalMove.equals(MoveAction.MOVE) && points.size() < squares);

		return points;
	}

	/**
	 * @return the currentPosition
	 */
	public Point getCurrentPosition() {
		return currentPosition;
	}

	/**
	 * @param currentPosition
	 *            the currentPosition to set
	 */
	public void setCurrentPosition(Point currentPosition) {
		this.currentPosition = currentPosition;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currentPosition == null) ? 0 : currentPosition.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Piece)) {
			return false;
		}
		Piece other = (Piece) obj;
		if (currentPosition == null) {
			if (other.currentPosition != null) {
				return false;
			}
		} else if (!currentPosition.equals(other.currentPosition)) {
			return false;
		}
		return true;
	}

	/**
	 * @return the boardSide
	 */
	public BoardSide getBoardSide() {
		return boardSide;
	}

	/**
	 * @param boardSide
	 *            the boardSide to set
	 */
	public void setBoardSide(BoardSide boardSide) {
		this.boardSide = boardSide;
	}

	/**
	 * @param board
	 *            the board to set
	 */
	public void setBoard(Board board) {
		this.board = board;
	}

	/**
	 * @return the board
	 */
	protected Board getBoard() {
		return board;
	}

}

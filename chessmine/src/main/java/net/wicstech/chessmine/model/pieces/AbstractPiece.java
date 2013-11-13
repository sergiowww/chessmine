package net.wicstech.chessmine.model.pieces;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.wicstech.chessmine.model.Board;
import net.wicstech.chessmine.model.BoardSide;
import net.wicstech.chessmine.model.Direction;
import net.wicstech.chessmine.model.MoveAction;
import net.wicstech.chessmine.model.Orientation;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Peça genérica.
 * 
 * @author Sergio
 * 
 */
public abstract class AbstractPiece implements Serializable {
	private static final long serialVersionUID = 6058030896941443816L;
	private static final List<MoveAction> TIPOS_VALIDOS = Arrays.asList(MoveAction.MOVE, MoveAction.MOVE_AND_ATTACK);

	private Point currentPosition;
	private BoardSide boardSide;
	private int type;

	@Autowired
	private Board board;

	/**
	 * Construtor de visibilidade padrão.
	 */
	AbstractPiece() {
		super();
	}

	/**
	 * Aceita ou não o movimento realizado pelo usuário.
	 * 
	 * @param newPosition
	 * @return
	 */
	public abstract boolean acceptMove(Point newPosition);

	/**
	 * Retorna a coordenada dos possíveis movimentos a partir do ponto atual
	 * 
	 * @param givenPoint
	 * @param boardSide
	 * @return
	 */
	public abstract List<Point> possibleMoves(Point givenPoint, BoardSide boardSide);

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
	 * Mover-se na horizontal, ou para os lados.
	 * 
	 * @param givenPoint
	 * @param givenBoardSide
	 * @param direction
	 * @param squares
	 * @return
	 */
	protected List<Point> moveHorizontally(Point givenPoint, BoardSide givenBoardSide, Direction direction, int squares) {
		WalkDirection walkDirection = new WalkDirection(direction);
		return executeStrategy(givenPoint, givenBoardSide, squares, walkDirection);
	}

	/**
	 * Mover verticalmente - para baixo ou para cima.
	 * 
	 * @param givenPosition
	 * @param givenBoardSide
	 * @param orientation
	 * @param squares
	 * @return
	 */
	protected List<Point> moveVertically(Point givenPosition, BoardSide givenBoardSide, Orientation orientation, int squares) {
		WalkOrientation walkOrientation = new WalkOrientation(orientation);
		return executeStrategy(givenPosition, givenBoardSide, squares, walkOrientation);
	}

	/**
	 * Executar a estratégia para andar as casas no tabuleiro.
	 * 
	 * @param givenPosition
	 * @param givenBoardSide
	 * @param squares
	 * @param walks
	 * @return
	 */
	private List<Point> executeStrategy(Point givenPosition, BoardSide givenBoardSide, int squares, IWalk... walks) {
		Point point = givenPosition.getLocation();
		List<Point> points = new ArrayList<>();
		MoveAction legalMove = MoveAction.STOP;
		do {
			for (IWalk iWalk : walks) {
				iWalk.walk(point);
			}
			legalMove = board.canItMoveTo(point, givenBoardSide);
			if (TIPOS_VALIDOS.contains(legalMove)) {
				points.add(point);
			}
			point = point.getLocation();
		} while (legalMove.equals(MoveAction.MOVE) && points.size() < squares);

		return points;
	}

	/**
	 * Mover diagonalmente.
	 * 
	 * @param givenPosition
	 * @param givenBoardSide
	 * @param orientation
	 * @param direction
	 * @param squares
	 * @return
	 */
	protected List<Point> moveBias(Point givenPosition, BoardSide givenBoardSide, Orientation orientation, Direction direction, int squares) {
		WalkDirection walkDirection = new WalkDirection(direction);
		WalkOrientation walkOrientation = new WalkOrientation(orientation);
		return executeStrategy(givenPosition, givenBoardSide, squares, walkDirection, walkOrientation);
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
		int prime = 31;
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
		if (!(obj instanceof AbstractPiece)) {
			return false;
		}
		AbstractPiece other = (AbstractPiece) obj;
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
	 * @return the board
	 */
	protected Board getBoard() {
		return board;
	}

	/**
	 * Strategy - Walk direction behavior.
	 * 
	 * @author Sergio
	 * 
	 */
	private interface IWalk {
		void walk(Point point);
	}

	/**
	 * Caminhar na orientação frente ou trás.
	 * 
	 * @author Sergio
	 * 
	 */
	private class WalkOrientation implements IWalk {
		private final Orientation orientation;

		public WalkOrientation(Orientation orientation) {
			this.orientation = orientation;
		}

		@Override
		public void walk(Point point) {
			if (Orientation.BACK.equals(orientation)) {
				point.y--;
			}
			if (Orientation.FORTH.equals(orientation)) {
				point.y++;
			}
		}
	}

	/**
	 * Caminhar na direção esquerda ou direita.
	 * 
	 * @author Sergio
	 * 
	 */
	private class WalkDirection implements IWalk {
		private final Direction direction;

		public WalkDirection(Direction direction) {
			this.direction = direction;
		}

		@Override
		public void walk(Point point) {
			if (Direction.LEFT.equals(direction)) {
				point.x--;
			}
			if (Direction.RIGHT.equals(direction)) {
				point.x++;
			}
		}
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
}

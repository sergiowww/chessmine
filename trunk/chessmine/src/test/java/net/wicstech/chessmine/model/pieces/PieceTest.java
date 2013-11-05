package net.wicstech.chessmine.model.pieces;

import java.awt.Point;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;
import net.wicstech.chessmine.model.Board;
import net.wicstech.chessmine.model.BoardSide;
import net.wicstech.chessmine.model.Direction;
import net.wicstech.chessmine.model.Orientation;

import org.apache.commons.lang.math.NumberUtils;
import org.junit.Test;

public class PieceTest {

	@Test
	public void testMoveBiasForthLeftMax() {
		Board board = new Board();
		Map<Point, Piece> pieces = board.getPiecesOnBoard();
		Bishop bishop = new Bishop();
		bishop.setBoardSide(BoardSide.BLACK);
		Point beingAttacked = new Point(2, 4);
		bishop.setCurrentPosition(beingAttacked);
		pieces.put(beingAttacked, bishop);

		Bishop attacker = new Bishop();
		attacker.setCurrentPosition(new Point(4, 2));
		attacker.setBoard(board);
		attacker.setBoardSide(BoardSide.WHITE);
		List<Point> pointsAllowed = attacker.moveBias(Orientation.FORTH, Direction.LEFT, Integer.MAX_VALUE);
		Assert.assertEquals(2, pointsAllowed.size());
		Assert.assertEquals(new Point(3, 3), pointsAllowed.get(0));
		Assert.assertEquals(new Point(2, 4), pointsAllowed.get(1));
	}

	@Test
	public void testMoveBiasForthRightMax() {
		Board board = new Board();
		Map<Point, Piece> pieces = board.getPiecesOnBoard();
		Bishop bishop = new Bishop();
		bishop.setBoardSide(BoardSide.BLACK);
		Point beingAttacked = new Point(5, 3);
		bishop.setCurrentPosition(beingAttacked);
		pieces.put(beingAttacked, bishop);

		Bishop attacker = new Bishop();
		attacker.setCurrentPosition(new Point(3, 1));
		attacker.setBoard(board);
		attacker.setBoardSide(BoardSide.WHITE);
		List<Point> pointsAllowed = attacker.moveBias(Orientation.FORTH, Direction.RIGHT, Integer.MAX_VALUE);
		Assert.assertEquals(2, pointsAllowed.size());
		Assert.assertEquals(new Point(4, 2), pointsAllowed.get(0));
		Assert.assertEquals(new Point(5, 3), pointsAllowed.get(1));
	}

	@Test
	public void testMoveBiasBackLeftMax() {
		Board board = new Board();
		Map<Point, Piece> pieces = board.getPiecesOnBoard();
		Bishop bishop = new Bishop();
		bishop.setBoardSide(BoardSide.BLACK);
		Point beingAttacked = new Point(1, 3);
		bishop.setCurrentPosition(beingAttacked);
		pieces.put(beingAttacked, bishop);

		Bishop attacker = new Bishop();
		attacker.setCurrentPosition(new Point(5, 7));
		attacker.setBoard(board);
		attacker.setBoardSide(BoardSide.WHITE);
		List<Point> pointsAllowed = attacker.moveBias(Orientation.BACK, Direction.LEFT, Integer.MAX_VALUE);
		Assert.assertEquals(4, pointsAllowed.size());
		Assert.assertEquals(new Point(4, 6), pointsAllowed.get(0));
		Assert.assertEquals(new Point(3, 5), pointsAllowed.get(1));
		Assert.assertEquals(new Point(2, 4), pointsAllowed.get(2));
		Assert.assertEquals(new Point(1, 3), pointsAllowed.get(3));
	}

	@Test
	public void testMoveBiasBackLeftMaxSameOpponent() {
		Board board = new Board();
		Map<Point, Piece> pieces = board.getPiecesOnBoard();
		Bishop bishop = new Bishop();
		bishop.setBoardSide(BoardSide.BLACK);
		Point beingAttacked = new Point(1, 3);
		bishop.setCurrentPosition(beingAttacked);
		pieces.put(beingAttacked, bishop);

		Bishop attacker = new Bishop();
		attacker.setCurrentPosition(new Point(5, 7));
		attacker.setBoard(board);
		attacker.setBoardSide(BoardSide.BLACK);
		List<Point> pointsAllowed = attacker.moveBias(Orientation.BACK, Direction.LEFT, Integer.MAX_VALUE);
		Assert.assertEquals(3, pointsAllowed.size());
		Assert.assertEquals(new Point(4, 6), pointsAllowed.get(0));
		Assert.assertEquals(new Point(3, 5), pointsAllowed.get(1));
		Assert.assertEquals(new Point(2, 4), pointsAllowed.get(2));
	}

	@Test
	public void testMoveBiasBackRightMax() {
		Board board = new Board();
		Map<Point, Piece> pieces = board.getPiecesOnBoard();
		Bishop bishop = new Bishop();
		bishop.setBoardSide(BoardSide.BLACK);
		Point beingAttacked = new Point(6, 4);
		bishop.setCurrentPosition(beingAttacked);
		pieces.put(beingAttacked, bishop);

		Bishop attacker = new Bishop();
		attacker.setCurrentPosition(new Point(3, 7));
		attacker.setBoard(board);
		attacker.setBoardSide(BoardSide.WHITE);
		List<Point> pointsAllowed = attacker.moveBias(Orientation.BACK, Direction.RIGHT, Integer.MAX_VALUE);
		Assert.assertEquals(3, pointsAllowed.size());
		Assert.assertEquals(new Point(4, 6), pointsAllowed.get(0));
		Assert.assertEquals(new Point(5, 5), pointsAllowed.get(1));
		Assert.assertEquals(new Point(6, 4), pointsAllowed.get(2));
	}

	@Test
	public void testMoveBiasNoAttackForthLeftMax() {
		Board board = new Board();

		Bishop walker = new Bishop();
		walker.setCurrentPosition(new Point(7, 1));
		walker.setBoard(board);
		walker.setBoardSide(BoardSide.WHITE);
		List<Point> pointsAllowed = walker.moveBias(Orientation.FORTH, Direction.LEFT, Integer.MAX_VALUE);
		Assert.assertEquals(6, pointsAllowed.size());
		int indice = 0;
		Assert.assertEquals(new Point(6, 2), pointsAllowed.get(indice++));
		Assert.assertEquals(new Point(5, 3), pointsAllowed.get(indice++));
		Assert.assertEquals(new Point(4, 4), pointsAllowed.get(indice++));
		Assert.assertEquals(new Point(3, 5), pointsAllowed.get(indice++));
		Assert.assertEquals(new Point(2, 6), pointsAllowed.get(indice++));
		Assert.assertEquals(new Point(1, 7), pointsAllowed.get(indice++));
	}

	@Test
	public void testMoveBiasNoAttackForthLeftLimited() {
		Board board = new Board();

		Bishop walker = new Bishop();
		walker.setCurrentPosition(new Point(7, 1));
		walker.setBoard(board);
		walker.setBoardSide(BoardSide.WHITE);
		List<Point> pointsAllowed = walker.moveBias(Orientation.FORTH, Direction.LEFT, NumberUtils.INTEGER_ONE);
		Assert.assertEquals(1, pointsAllowed.size());
		int indice = 0;
		Assert.assertEquals(new Point(6, 2), pointsAllowed.get(indice++));
	}

	@Test
	public void testMoveBiasNoAttackForthLeftLimited2() {
		Board board = new Board();

		Bishop walker = new Bishop();
		walker.setCurrentPosition(new Point(7, 1));
		walker.setBoard(board);
		walker.setBoardSide(BoardSide.WHITE);
		List<Point> pointsAllowed = walker.moveBias(Orientation.FORTH, Direction.LEFT, 3);
		Assert.assertEquals(3, pointsAllowed.size());
		int indice = 0;
		Assert.assertEquals(new Point(6, 2), pointsAllowed.get(indice++));
		Assert.assertEquals(new Point(5, 3), pointsAllowed.get(indice++));
		Assert.assertEquals(new Point(4, 4), pointsAllowed.get(indice++));

	}

}

package net.wicstech.chessmine.model.pieces;

import java.awt.Point;

import junit.framework.Assert;
import net.wicstech.chessmine.model.Board;
import net.wicstech.chessmine.model.BoardSide;

import org.junit.Test;

public class PawnTest {

	@Test
	public void testAcceptMove() {
		Pawn pawn = new Pawn();
		pawn.setBoardSide(BoardSide.WHITE);
		Board board = new Board();
		pawn.setBoard(board);
		pawn.setCurrentPosition(new Point(1, 6));

		Assert.assertEquals(true, pawn.acceptMove(new Point(1, 5)));
		Assert.assertEquals(true, pawn.acceptMove(new Point(1, 4)));
		pawn.moved();
		Assert.assertEquals(false, pawn.acceptMove(new Point(1, 3)));

	}

	@Test
	public void testAcceptMove1() {
		Pawn pawn = new Pawn();
		pawn.setBoardSide(BoardSide.WHITE);
		Board board = new Board();
		pawn.setBoard(board);
		pawn.setCurrentPosition(new Point(3, 6));
		pawn.moved();

		Assert.assertEquals(true, pawn.acceptMove(new Point(3, 5)));
		Assert.assertEquals(false, pawn.acceptMove(new Point(3, 4)));
		Assert.assertEquals(false, pawn.acceptMove(new Point(2, 5)));

	}

	@Test
	public void testAcceptMove2() {
		Pawn pawn = new Pawn();
		pawn.setBoardSide(BoardSide.WHITE);
		Board board = new Board();
		Point pointKnight = new Point(4, 5);
		Knight knight = new Knight();
		knight.setBoardSide(BoardSide.BLACK);
		knight.setCurrentPosition(pointKnight);
		board.getPiecesOnBoard().put(pointKnight, knight);
		pawn.setBoard(board);
		pawn.setCurrentPosition(new Point(5, 6));
		pawn.moved();

		Assert.assertEquals(true, pawn.acceptMove(new Point(4, 5)));
		Assert.assertEquals(true, pawn.acceptMove(new Point(5, 5)));
		Assert.assertEquals(false, pawn.acceptMove(new Point(2, 5)));

	}

	@Test
	public void testAcceptMove3() {
		Pawn pawn = new Pawn();
		pawn.setBoardSide(BoardSide.BLACK);
		Board board = new Board();
		Point pointKnight = new Point(1, 2);
		Knight knight = new Knight();
		knight.setBoardSide(BoardSide.WHITE);
		knight.setCurrentPosition(pointKnight);
		board.getPiecesOnBoard().put(pointKnight, knight);
		pawn.setBoard(board);
		pawn.setCurrentPosition(new Point(2, 1));
		pawn.moved();

		Assert.assertEquals(true, pawn.acceptMove(new Point(1, 2)));
		Assert.assertEquals(true, pawn.acceptMove(new Point(2, 2)));
		Assert.assertEquals(false, pawn.acceptMove(new Point(3, 2)));

	}
}

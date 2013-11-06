package net.wicstech.chessmine.model.pieces;

import java.awt.Point;

import junit.framework.Assert;
import net.wicstech.chessmine.model.Board;
import net.wicstech.chessmine.model.BoardSide;

import org.junit.Test;

public class QueenTest {

	@Test
	public void testAcceptMove() {
		Queen queen = new Queen();
		Board board = new Board();
		queen.setBoard(board);
		queen.setBoardSide(BoardSide.WHITE);
		queen.setCurrentPosition(new Point(6, 6));

		Pawn pawn = new Pawn();
		Point point = new Point(6, 5);
		pawn.setCurrentPosition(point);
		pawn.setBoardSide(BoardSide.WHITE);
		board.getPiecesOnBoard().put(point, pawn);

		Assert.assertEquals(false, queen.acceptMove(point));

		for (int i = 0; i < 6; i++) {
			Assert.assertEquals(true, queen.acceptMove(new Point(i, 6)));
		}

		Assert.assertEquals(true, queen.acceptMove(new Point(6, 7)));
		Assert.assertEquals(true, queen.acceptMove(new Point(7, 6)));
		Assert.assertEquals(true, queen.acceptMove(new Point(7, 7)));
		Assert.assertEquals(false, queen.acceptMove(new Point(4, 7)));

	}

}

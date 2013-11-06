package net.wicstech.chessmine.model.pieces;

import java.awt.Point;

import junit.framework.Assert;
import net.wicstech.chessmine.model.Board;

import org.junit.Test;

public class RookTest {

	@Test
	public void testAcceptMove() {
		Rook rook = new Rook();
		rook.setBoard(new Board());
		rook.setCurrentPosition(new Point(1, 4));
		Assert.assertEquals(true, rook.acceptMove(new Point(1, 5)));
		Assert.assertEquals(true, rook.acceptMove(new Point(1, 6)));
		Assert.assertEquals(true, rook.acceptMove(new Point(1, 7)));

		Assert.assertEquals(true, rook.acceptMove(new Point(1, 3)));
		Assert.assertEquals(true, rook.acceptMove(new Point(1, 2)));
		Assert.assertEquals(true, rook.acceptMove(new Point(1, 1)));

		Assert.assertEquals(true, rook.acceptMove(new Point(0, 4)));
		Assert.assertEquals(true, rook.acceptMove(new Point(2, 4)));
		Assert.assertEquals(true, rook.acceptMove(new Point(3, 4)));

		Assert.assertEquals(false, rook.acceptMove(new Point(2, 3)));
		Assert.assertEquals(false, rook.acceptMove(new Point(2, 8)));

	}

}

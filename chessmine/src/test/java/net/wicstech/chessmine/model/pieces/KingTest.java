package net.wicstech.chessmine.model.pieces;

import java.awt.Point;

import junit.framework.Assert;
import net.wicstech.chessmine.model.Board;

import org.junit.Test;

public class KingTest {

	@Test
	public void testAcceptMove() {
		King king = new King();
		king.setBoard(new Board());
		king.setCurrentPosition(new Point(4, 3));
		Assert.assertEquals(true, king.acceptMove(new Point(3, 3)));
		Assert.assertEquals(true, king.acceptMove(new Point(3, 2)));
		Assert.assertEquals(true, king.acceptMove(new Point(4, 2)));
		Assert.assertEquals(true, king.acceptMove(new Point(5, 2)));
		Assert.assertEquals(true, king.acceptMove(new Point(5, 3)));
		Assert.assertEquals(true, king.acceptMove(new Point(5, 4)));
		Assert.assertEquals(true, king.acceptMove(new Point(4, 4)));
		Assert.assertEquals(true, king.acceptMove(new Point(3, 4)));
		Assert.assertEquals(false, king.acceptMove(new Point(2, 3)));
	}

	@Test
	public void testAcceptMove2() {
		King king = new King();
		king.setBoard(new Board());
		king.setCurrentPosition(new Point(5, 7));
		Assert.assertEquals(true, king.acceptMove(new Point(4, 7)));
		Assert.assertEquals(true, king.acceptMove(new Point(4, 7)));
		Assert.assertEquals(true, king.acceptMove(new Point(5, 6)));
		Assert.assertEquals(true, king.acceptMove(new Point(6, 6)));
		Assert.assertEquals(true, king.acceptMove(new Point(6, 7)));
		Assert.assertEquals(false, king.acceptMove(new Point(2, 3)));
	}

}

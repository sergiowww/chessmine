package net.wicstech.chessmine.model.pieces;

import java.awt.Point;

import junit.framework.Assert;
import net.wicstech.chessmine.model.Board;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/RookTest.xml", "/common-tests.xml"})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class RookTest {

	@Autowired
	private Rook rook;

	@Autowired
	private Board board;

	@Before
	public void setup() {
		board.getPiecesOnBoard().clear();
	}

	@Test
	public void testAcceptMove() {
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

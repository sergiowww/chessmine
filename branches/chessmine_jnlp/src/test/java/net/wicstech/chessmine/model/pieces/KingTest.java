package net.wicstech.chessmine.model.pieces;

import java.awt.Point;

import junit.framework.Assert;
import net.wicstech.chessmine.model.Board;
import net.wicstech.chessmine.model.BoardSide;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/KingTest.xml", "/common-tests.xml"})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class KingTest {

	@Autowired
	private Board board;

	@Autowired
	private King king;

	@Autowired
	private Knight knight;

	@Before
	public void setup() {
		board.getPiecesOnBoard().clear();
	}

	@Test
	public void testIsInCheck() {

		king.setBoardSide(BoardSide.BLACK);
		king.setCurrentPosition(new Point(5, 6));

		knight.setBoardSide(BoardSide.WHITE);

		board.getPiecesOnBoard().put(new Point(4, 4), knight);

		Assert.assertEquals(true, king.isInCheck(knight));
	}

	@Test
	public void testAcceptMove() {
		king.setCurrentPosition(new Point(4, 3));
		king.setBoardSide(BoardSide.BLACK);
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
		king.setCurrentPosition(new Point(5, 7));
		king.setBoardSide(BoardSide.BLACK);
		Assert.assertEquals(true, king.acceptMove(new Point(4, 7)));
		Assert.assertEquals(true, king.acceptMove(new Point(4, 7)));
		Assert.assertEquals(true, king.acceptMove(new Point(5, 6)));
		Assert.assertEquals(true, king.acceptMove(new Point(6, 6)));
		Assert.assertEquals(true, king.acceptMove(new Point(6, 7)));
		Assert.assertEquals(false, king.acceptMove(new Point(2, 3)));
	}

}

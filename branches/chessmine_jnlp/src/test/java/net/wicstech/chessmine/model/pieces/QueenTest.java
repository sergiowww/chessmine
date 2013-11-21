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
@ContextConfiguration({"/QueenTest.xml", "/common-tests.xml"})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class QueenTest {

	@Autowired
	private Board board;

	@Autowired
	private Queen queen;

	@Autowired
	private Pawn pawn;

	@Before
	public void setup() {
		board.getPiecesOnBoard().clear();
	}

	@Test
	public void testAcceptMove() {
		queen.setBoardSide(BoardSide.WHITE);
		queen.setCurrentPosition(new Point(6, 6));

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

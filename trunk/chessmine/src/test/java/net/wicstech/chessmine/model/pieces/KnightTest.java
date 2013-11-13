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
@ContextConfiguration({"/KnightTest.xml", "/common-tests.xml"})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class KnightTest {

	@Autowired
	private Knight knight;

	@Autowired
	private Queen blackQueen;

	@Autowired
	private Board board;

	@Before
	public void setup() {
		board.getPiecesOnBoard().clear();
	}

	@Test
	public void testAcceptMove() {
		knight.setBoardSide(BoardSide.WHITE);
		knight.setCurrentPosition(new Point(3, 4));

		Assert.assertEquals(true, knight.acceptMove(new Point(1, 3)));
		Assert.assertEquals(true, knight.acceptMove(new Point(1, 5)));
		Assert.assertEquals(true, knight.acceptMove(new Point(2, 6)));
		Assert.assertEquals(true, knight.acceptMove(new Point(4, 6)));
		Assert.assertEquals(true, knight.acceptMove(new Point(2, 6)));
		Assert.assertEquals(true, knight.acceptMove(new Point(5, 3)));
		Assert.assertEquals(true, knight.acceptMove(new Point(5, 5)));
		Assert.assertEquals(true, knight.acceptMove(new Point(4, 2)));
		Assert.assertEquals(true, knight.acceptMove(new Point(4, 2)));
		Assert.assertEquals(true, knight.acceptMove(new Point(2, 2)));

		Assert.assertEquals(false, knight.acceptMove(new Point(2, 3)));

	}

	@Test
	public void testAcceptMove1() {
		knight.setBoardSide(BoardSide.WHITE);
		blackQueen.setBoardSide(BoardSide.BLACK);
		Point pointBlackQueen = new Point(5, 5);
		blackQueen.setCurrentPosition(pointBlackQueen);
		board.getPiecesOnBoard().put(pointBlackQueen, blackQueen);

		putQueenOnSameSide(board, new Point(4, 6));

		knight.setCurrentPosition(new Point(3, 4));

		Assert.assertEquals(true, knight.acceptMove(new Point(1, 3)));
		Assert.assertEquals(true, knight.acceptMove(new Point(1, 5)));
		Assert.assertEquals(true, knight.acceptMove(new Point(2, 6)));
		Assert.assertEquals(false, knight.acceptMove(new Point(4, 6)));
		Assert.assertEquals(true, knight.acceptMove(new Point(5, 3)));
		Assert.assertEquals(true, knight.acceptMove(new Point(5, 5)));
		Assert.assertEquals(true, knight.acceptMove(new Point(4, 2)));
		Assert.assertEquals(true, knight.acceptMove(new Point(2, 2)));

	}

	@Test
	public void testAcceptMove2() {
		knight.setBoardSide(BoardSide.WHITE);
		blackQueen.setBoardSide(BoardSide.BLACK);
		Point pos6 = new Point(5, 5);
		Point pointBlackQueen = pos6;
		blackQueen.setCurrentPosition(pointBlackQueen);
		board.getPiecesOnBoard().put(pointBlackQueen, blackQueen);

		Point pos1 = new Point(1, 3);
		Point pos7 = new Point(4, 2);
		Point pos2 = new Point(1, 5);
		Point pos8 = new Point(5, 5);
		Point pos3 = new Point(2, 6);
		Point pos4 = new Point(4, 6);
		Point pos5 = new Point(5, 3);
		Point pos9 = new Point(2, 2);

		putQueenOnSameSide(board, pos1);
		putQueenOnSameSide(board, pos2);
		putQueenOnSameSide(board, pos3);
		putQueenOnSameSide(board, pos4);
		putQueenOnSameSide(board, pos5);
		putQueenOnSameSide(board, pos6);
		putQueenOnSameSide(board, pos7);
		putQueenOnSameSide(board, pos8);
		putQueenOnSameSide(board, pos9);

		knight.setCurrentPosition(new Point(3, 4));

		Assert.assertEquals(false, knight.acceptMove(pos1));
		Assert.assertEquals(false, knight.acceptMove(pos2));
		Assert.assertEquals(false, knight.acceptMove(pos3));
		Assert.assertEquals(false, knight.acceptMove(pos4));
		Assert.assertEquals(false, knight.acceptMove(pos5));
		Assert.assertEquals(false, knight.acceptMove(pos6));
		Assert.assertEquals(false, knight.acceptMove(pos7));
		Assert.assertEquals(false, knight.acceptMove(pos8));
		Assert.assertEquals(false, knight.acceptMove(pos9));

	}

	private void putQueenOnSameSide(Board board, Point pointWhiteQueen) {
		Queen whiteQueen = new Queen();
		whiteQueen.setCurrentPosition(pointWhiteQueen);
		whiteQueen.setBoardSide(BoardSide.WHITE);
		board.getPiecesOnBoard().put(pointWhiteQueen, whiteQueen);
	}

}

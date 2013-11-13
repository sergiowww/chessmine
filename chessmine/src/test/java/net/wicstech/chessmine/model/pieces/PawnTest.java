package net.wicstech.chessmine.model.pieces;

import java.awt.Point;

import junit.framework.Assert;
import net.wicstech.chessmine.model.Board;
import net.wicstech.chessmine.model.BoardSide;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/PawnTest.xml", "/common-tests.xml"})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class PawnTest {

	@Autowired
	@Qualifier("pawn")
	private Pawn pawn;

	@Autowired
	private Board board;

	@Autowired
	@Qualifier("anotherPawn")
	private Pawn anotherPawn;

	@Autowired
	private Knight knight;

	@Before
	public void setup() {
		board.getPiecesOnBoard().clear();
	}

	@Test
	public void testAcceptMove() {
		pawn.setBoardSide(BoardSide.WHITE);
		pawn.setCurrentPosition(new Point(1, 6));

		Assert.assertEquals(true, pawn.acceptMove(new Point(1, 5)));
		Assert.assertEquals(true, pawn.acceptMove(new Point(1, 4)));
		pawn.moved();
		Assert.assertEquals(false, pawn.acceptMove(new Point(1, 3)));

	}

	@Test
	public void testAcceptMove1() {
		pawn.setBoardSide(BoardSide.WHITE);
		pawn.setCurrentPosition(new Point(3, 6));
		pawn.moved();

		Assert.assertEquals(true, pawn.acceptMove(new Point(3, 5)));
		Assert.assertEquals(false, pawn.acceptMove(new Point(3, 4)));
		Assert.assertEquals(false, pawn.acceptMove(new Point(2, 5)));

	}

	@Test
	public void testAcceptMove2() {
		pawn.setBoardSide(BoardSide.WHITE);
		Point pointKnight = new Point(4, 5);
		Knight knight = new Knight();
		knight.setBoardSide(BoardSide.BLACK);
		knight.setCurrentPosition(pointKnight);
		board.getPiecesOnBoard().put(pointKnight, knight);
		pawn.setCurrentPosition(new Point(5, 6));
		pawn.moved();

		Assert.assertEquals(true, pawn.acceptMove(new Point(4, 5)));
		Assert.assertEquals(true, pawn.acceptMove(new Point(5, 5)));
		Assert.assertEquals(false, pawn.acceptMove(new Point(2, 5)));

	}

	@Test
	public void testAcceptMove3() {
		pawn.setBoardSide(BoardSide.BLACK);
		Point pointAnotherPawn = new Point(1, 4);
		anotherPawn.setBoardSide(BoardSide.WHITE);
		anotherPawn.setCurrentPosition(pointAnotherPawn);
		board.getPiecesOnBoard().put(pointAnotherPawn, anotherPawn);
		Point pointFirstPawn = new Point(1, 3);
		pawn.setCurrentPosition(pointFirstPawn);
		board.getPiecesOnBoard().put(pointFirstPawn, pawn);

		Assert.assertEquals(false, pawn.acceptMove(pointAnotherPawn));
		Assert.assertEquals(false, anotherPawn.acceptMove(pointFirstPawn));

	}

	@Test
	public void testAcceptMove4() {
		pawn.setBoardSide(BoardSide.BLACK);
		Point pointKnight = new Point(1, 2);
		knight.setBoardSide(BoardSide.WHITE);
		knight.setCurrentPosition(pointKnight);
		board.getPiecesOnBoard().put(pointKnight, knight);
		pawn.setCurrentPosition(new Point(2, 1));
		pawn.moved();

		Assert.assertEquals(true, pawn.acceptMove(new Point(1, 2)));
		Assert.assertEquals(true, pawn.acceptMove(new Point(2, 2)));
		Assert.assertEquals(false, pawn.acceptMove(new Point(3, 2)));

	}
}

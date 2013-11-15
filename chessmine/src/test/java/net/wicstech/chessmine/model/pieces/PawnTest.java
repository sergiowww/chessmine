package net.wicstech.chessmine.model.pieces;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.Point;

import net.wicstech.chessmine.model.Board;
import net.wicstech.chessmine.model.BoardCurrentGameData;
import net.wicstech.chessmine.model.BoardSide;
import net.wicstech.chessmine.model.MoveResult;

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

	@Autowired
	private BoardCurrentGameData gameData;

	@Before
	public void setup() {
		board.getPiecesOnBoard().clear();
	}

	@Test
	public void testAcceptMove() {
		pawn.setBoardSide(BoardSide.WHITE);
		pawn.setCurrentPosition(new Point(1, 6));

		assertEquals(true, pawn.acceptMove(new Point(1, 5)));
		assertEquals(true, pawn.acceptMove(new Point(1, 4)));
		pawn.moved();
		assertEquals(false, pawn.acceptMove(new Point(1, 3)));

	}

	@Test
	public void testAcceptMove1() {
		pawn.setBoardSide(BoardSide.WHITE);
		pawn.setCurrentPosition(new Point(3, 6));
		pawn.moved();

		assertEquals(true, pawn.acceptMove(new Point(3, 5)));
		assertEquals(false, pawn.acceptMove(new Point(3, 4)));
		assertEquals(false, pawn.acceptMove(new Point(2, 5)));

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

		assertEquals(true, pawn.acceptMove(new Point(4, 5)));
		assertEquals(true, pawn.acceptMove(new Point(5, 5)));
		assertEquals(false, pawn.acceptMove(new Point(2, 5)));

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

		assertEquals(false, pawn.acceptMove(pointAnotherPawn));
		assertEquals(false, anotherPawn.acceptMove(pointFirstPawn));

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

		assertEquals(true, pawn.acceptMove(new Point(1, 2)));
		assertEquals(true, pawn.acceptMove(new Point(2, 2)));
		assertEquals(false, pawn.acceptMove(new Point(3, 2)));

	}

	/**
	 * Converter log para teste:<br>
	 * <code>\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}\.\d+\s*Board\s*\[INFO\]\s*from\:\s*java.awt.Point\[x=(\d),y=(\d)\]\s*to\:\s*java.awt.Point\[x=(\d),y=(\d)\]</code>
	 * <br>
	 * <code>assertEquals(MoveResult.LEGAL,board.tryMoving(new Point(\1, \2), new Point(\3, \4)));</code>
	 */
	@Test
	public void testPromotion() {
		board.reiniciar(null);
		gameData.setBoardSidePlaying(BoardSide.WHITE);

		assertEquals(MoveResult.LEGAL, board.tryMoving(new Point(0, 6), new Point(0, 4)));
		assertEquals(MoveResult.LEGAL, board.tryMoving(new Point(1, 1), new Point(1, 3)));
		assertEquals(MoveResult.LEGAL, board.tryMoving(new Point(0, 4), new Point(1, 3)));
		assertEquals(MoveResult.LEGAL, board.tryMoving(new Point(1, 0), new Point(2, 2)));
		assertEquals(MoveResult.LEGAL, board.tryMoving(new Point(1, 3), new Point(1, 2)));
		assertEquals(MoveResult.LEGAL, board.tryMoving(new Point(2, 2), new Point(1, 4)));
		assertNull(board.promote(new Point(1, 4)));
		assertEquals(MoveResult.LEGAL, board.tryMoving(new Point(1, 2), new Point(1, 1)));
		assertNull(board.promote(new Point(1, 1)));
		assertEquals(MoveResult.LEGAL, board.tryMoving(new Point(2, 1), new Point(2, 2)));
		assertEquals(MoveResult.LEGAL, board.tryMoving(new Point(1, 1), new Point(1, 0)));
		assertTrue(board.promote(new Point(1, 0)) instanceof Queen);
	}
}

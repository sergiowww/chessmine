package net.wicstech.chessmine.model.pieces;

import java.awt.Point;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;
import net.wicstech.chessmine.model.Board;
import net.wicstech.chessmine.model.BoardSide;
import net.wicstech.chessmine.model.Direction;
import net.wicstech.chessmine.model.Orientation;

import org.apache.commons.lang.math.NumberUtils;
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
@ContextConfiguration({"/PieceTest.xml", "/common-tests.xml"})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class PieceTest {

	@Autowired
	private Board board;

	@Autowired
	@Qualifier("bishop")
	private Bishop bishop;

	@Autowired
	@Qualifier("attacker")
	private Bishop attacker;

	@Autowired
	@Qualifier("walker")
	private Bishop walker;

	@Before
	public void setup() {
		board.getPiecesOnBoard().clear();
	}

	@Test
	public void testMoveBiasForthLeftMax() {
		Map<Point, AbstractPiece> pieces = board.getPiecesOnBoard();
		bishop.setBoardSide(BoardSide.BLACK);
		Point beingAttacked = new Point(2, 4);
		bishop.setCurrentPosition(beingAttacked);
		pieces.put(beingAttacked, bishop);

		attacker.setCurrentPosition(new Point(4, 2));
		attacker.setBoardSide(BoardSide.WHITE);
		List<Point> pointsAllowed = attacker.moveBias(new Point(4, 2), BoardSide.WHITE, Orientation.FORTH, Direction.LEFT, Integer.MAX_VALUE);
		Assert.assertEquals(2, pointsAllowed.size());
		Assert.assertEquals(new Point(3, 3), pointsAllowed.get(0));
		Assert.assertEquals(new Point(2, 4), pointsAllowed.get(1));
	}

	@Test
	public void testMoveBiasForthRightMax() {
		Map<Point, AbstractPiece> pieces = board.getPiecesOnBoard();
		bishop.setBoardSide(BoardSide.WHITE);
		Point beingAttacked = new Point(5, 3);
		bishop.setCurrentPosition(beingAttacked);
		pieces.put(beingAttacked, bishop);

		attacker.setCurrentPosition(new Point(3, 1));
		attacker.setBoardSide(BoardSide.BLACK);
		List<Point> pointsAllowed = attacker.moveBias(new Point(3, 1), BoardSide.BLACK, Orientation.FORTH, Direction.RIGHT, Integer.MAX_VALUE);
		Assert.assertEquals(2, pointsAllowed.size());
		Assert.assertEquals(new Point(4, 2), pointsAllowed.get(0));
		Assert.assertEquals(new Point(5, 3), pointsAllowed.get(1));
	}

	@Test
	public void testMoveBiasBackLeftMax() {
		Map<Point, AbstractPiece> pieces = board.getPiecesOnBoard();
		bishop.setBoardSide(BoardSide.BLACK);
		Point beingAttacked = new Point(1, 3);
		bishop.setCurrentPosition(beingAttacked);
		pieces.put(beingAttacked, bishop);

		attacker.setCurrentPosition(new Point(5, 7));
		attacker.setBoardSide(BoardSide.WHITE);
		List<Point> pointsAllowed = attacker.moveBias(new Point(5, 7), BoardSide.WHITE, Orientation.BACK, Direction.LEFT, Integer.MAX_VALUE);
		Assert.assertEquals(4, pointsAllowed.size());
		Assert.assertEquals(new Point(4, 6), pointsAllowed.get(0));
		Assert.assertEquals(new Point(3, 5), pointsAllowed.get(1));
		Assert.assertEquals(new Point(2, 4), pointsAllowed.get(2));
		Assert.assertEquals(new Point(1, 3), pointsAllowed.get(3));
	}

	@Test
	public void testMoveBiasBackLeftMaxSameOpponent() {
		Map<Point, AbstractPiece> pieces = board.getPiecesOnBoard();
		bishop.setBoardSide(BoardSide.BLACK);
		Point beingAttacked = new Point(1, 3);
		bishop.setCurrentPosition(beingAttacked);
		pieces.put(beingAttacked, bishop);

		attacker.setCurrentPosition(new Point(5, 7));
		attacker.setBoardSide(BoardSide.BLACK);
		List<Point> pointsAllowed = attacker.moveBias(new Point(5, 7), BoardSide.BLACK, Orientation.BACK, Direction.LEFT, Integer.MAX_VALUE);
		Assert.assertEquals(3, pointsAllowed.size());
		Assert.assertEquals(new Point(4, 6), pointsAllowed.get(0));
		Assert.assertEquals(new Point(3, 5), pointsAllowed.get(1));
		Assert.assertEquals(new Point(2, 4), pointsAllowed.get(2));
	}

	@Test
	public void testMoveBiasBackRightMax() {
		Map<Point, AbstractPiece> pieces = board.getPiecesOnBoard();
		bishop.setBoardSide(BoardSide.BLACK);
		Point beingAttacked = new Point(6, 4);
		bishop.setCurrentPosition(beingAttacked);
		pieces.put(beingAttacked, bishop);

		attacker.setCurrentPosition(new Point(3, 7));
		attacker.setBoardSide(BoardSide.WHITE);
		List<Point> pointsAllowed = attacker.moveBias(new Point(3, 7), BoardSide.WHITE, Orientation.BACK, Direction.RIGHT, Integer.MAX_VALUE);
		Assert.assertEquals(3, pointsAllowed.size());
		Assert.assertEquals(new Point(4, 6), pointsAllowed.get(0));
		Assert.assertEquals(new Point(5, 5), pointsAllowed.get(1));
		Assert.assertEquals(new Point(6, 4), pointsAllowed.get(2));
	}

	@Test
	public void testMoveVerticallyBackMax() {
		Map<Point, AbstractPiece> pieces = board.getPiecesOnBoard();
		bishop.setBoardSide(BoardSide.BLACK);
		Point beingAttacked = new Point(4, 2);
		bishop.setCurrentPosition(beingAttacked);
		pieces.put(beingAttacked, bishop);

		attacker.setCurrentPosition(new Point(4, 6));
		attacker.setBoardSide(BoardSide.WHITE);
		List<Point> pointsAllowed = attacker.moveVertically(new Point(4, 6), BoardSide.WHITE, Orientation.BACK, Integer.MAX_VALUE);
		Assert.assertEquals(4, pointsAllowed.size());
		int indice = 0;
		Assert.assertEquals(new Point(4, 5), pointsAllowed.get(indice++));
		Assert.assertEquals(new Point(4, 4), pointsAllowed.get(indice++));
		Assert.assertEquals(new Point(4, 3), pointsAllowed.get(indice++));
		Assert.assertEquals(new Point(4, 2), pointsAllowed.get(indice++));
	}

	@Test
	public void testMoveHorizontallyLeft() {
		Map<Point, AbstractPiece> pieces = board.getPiecesOnBoard();
		bishop.setBoardSide(BoardSide.WHITE);
		Point beingAttacked = new Point(4, 7);
		bishop.setCurrentPosition(beingAttacked);
		pieces.put(beingAttacked, bishop);

		attacker.setCurrentPosition(new Point(7, 7));
		attacker.setBoardSide(BoardSide.BLACK);
		List<Point> pointsAllowed = attacker.moveHorizontally(new Point(7, 7), BoardSide.BLACK, Direction.LEFT, Integer.MAX_VALUE);
		Assert.assertEquals(3, pointsAllowed.size());
		int indice = 0;
		Assert.assertEquals(new Point(6, 7), pointsAllowed.get(indice++));
		Assert.assertEquals(new Point(5, 7), pointsAllowed.get(indice++));
		Assert.assertEquals(new Point(4, 7), pointsAllowed.get(indice++));
	}

	@Test
	public void testMoveHorizontallyRight() {
		Map<Point, AbstractPiece> pieces = board.getPiecesOnBoard();
		bishop.setBoardSide(BoardSide.WHITE);
		Point beingAttacked = new Point(4, 5);
		bishop.setCurrentPosition(beingAttacked);
		pieces.put(beingAttacked, bishop);

		attacker.setCurrentPosition(new Point(3, 5));
		attacker.setBoardSide(BoardSide.BLACK);
		List<Point> pointsAllowed = attacker.moveHorizontally(new Point(3, 5), BoardSide.BLACK, Direction.RIGHT, Integer.MAX_VALUE);
		Assert.assertEquals(1, pointsAllowed.size());
		int indice = 0;
		Assert.assertEquals(new Point(4, 5), pointsAllowed.get(indice++));
	}

	@Test
	public void testMoveVerticallyForthMax() {
		Map<Point, AbstractPiece> pieces = board.getPiecesOnBoard();
		bishop.setBoardSide(BoardSide.WHITE);
		Point beingAttacked = new Point(1, 4);
		bishop.setCurrentPosition(beingAttacked);
		pieces.put(beingAttacked, bishop);

		attacker.setCurrentPosition(new Point(1, 1));
		attacker.setBoardSide(BoardSide.BLACK);
		List<Point> pointsAllowed = attacker.moveVertically(new Point(1, 1), BoardSide.BLACK, Orientation.FORTH, Integer.MAX_VALUE);
		Assert.assertEquals(3, pointsAllowed.size());
		int indice = 0;
		Assert.assertEquals(new Point(1, 2), pointsAllowed.get(indice++));
		Assert.assertEquals(new Point(1, 3), pointsAllowed.get(indice++));
		Assert.assertEquals(new Point(1, 4), pointsAllowed.get(indice++));
	}

	@Test
	public void testMoveBiasNoAttackForthLeftMax() {

		walker.setCurrentPosition(new Point(7, 1));
		walker.setBoardSide(BoardSide.BLACK);
		List<Point> pointsAllowed = walker.moveBias(new Point(7, 1), BoardSide.BLACK, Orientation.FORTH, Direction.LEFT, Integer.MAX_VALUE);
		Assert.assertEquals(6, pointsAllowed.size());
		int indice = 0;
		Assert.assertEquals(new Point(6, 2), pointsAllowed.get(indice++));
		Assert.assertEquals(new Point(5, 3), pointsAllowed.get(indice++));
		Assert.assertEquals(new Point(4, 4), pointsAllowed.get(indice++));
		Assert.assertEquals(new Point(3, 5), pointsAllowed.get(indice++));
		Assert.assertEquals(new Point(2, 6), pointsAllowed.get(indice++));
		Assert.assertEquals(new Point(1, 7), pointsAllowed.get(indice++));
	}

	@Test
	public void testMoveBiasNoAttackForthLeftLimited() {

		walker.setCurrentPosition(new Point(7, 1));
		walker.setBoardSide(BoardSide.WHITE);
		List<Point> pointsAllowed = walker.moveBias(new Point(7, 1), BoardSide.WHITE, Orientation.FORTH, Direction.LEFT, NumberUtils.INTEGER_ONE);
		Assert.assertEquals(1, pointsAllowed.size());
		int indice = 0;
		Assert.assertEquals(new Point(6, 2), pointsAllowed.get(indice++));
	}

	@Test
	public void testMoveBiasNoAttackInvalid() {

		walker.setCurrentPosition(new Point(7, 1));
		walker.setBoardSide(BoardSide.BLACK);
		List<Point> pointsAllowed = walker.moveBias(new Point(7, 1), BoardSide.BLACK, Orientation.FORTH, Direction.RIGHT, NumberUtils.INTEGER_ONE);
		Assert.assertEquals(0, pointsAllowed.size());
	}

	@Test
	public void testMoveBiasNoAttackInvalid2() {

		walker.setCurrentPosition(new Point(0, 1));
		walker.setBoardSide(BoardSide.BLACK);
		List<Point> pointsAllowed = walker.moveBias(new Point(0, 1), BoardSide.BLACK, Orientation.FORTH, Direction.LEFT, NumberUtils.INTEGER_ONE);
		Assert.assertEquals(0, pointsAllowed.size());
	}

	@Test
	public void testMoveBiasNoAttackInvalid3() {

		walker.setCurrentPosition(new Point(7, 0));
		walker.setBoardSide(BoardSide.WHITE);
		List<Point> pointsAllowed = walker.moveBias(new Point(7, 0), BoardSide.WHITE, Orientation.BACK, Direction.LEFT, NumberUtils.INTEGER_ONE);
		Assert.assertEquals(0, pointsAllowed.size());
	}

	@Test
	public void testMoveBiasNoAttackForthLeftLimited2() {

		walker.setCurrentPosition(new Point(7, 1));
		walker.setBoardSide(BoardSide.BLACK);
		List<Point> pointsAllowed = walker.moveBias(new Point(7, 1), BoardSide.BLACK, Orientation.FORTH, Direction.LEFT, 3);
		Assert.assertEquals(3, pointsAllowed.size());
		int indice = 0;
		Assert.assertEquals(new Point(6, 2), pointsAllowed.get(indice++));
		Assert.assertEquals(new Point(5, 3), pointsAllowed.get(indice++));
		Assert.assertEquals(new Point(4, 4), pointsAllowed.get(indice++));

	}

}

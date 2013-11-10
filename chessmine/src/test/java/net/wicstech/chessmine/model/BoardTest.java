package net.wicstech.chessmine.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.Point;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/testApplicationContext.xml")
public class BoardTest {

	@Autowired
	private Board board;

	@Test
	public void testKingsPlayerIsInCheck() {
		if (board.getBoardSidePlaying().equals(BoardSide.WHITE)) {
			assertEquals(MoveResult.LEGAL, board.tryMoving(new Point(7, 6), new Point(7, 5)));
		}
		assertNotNull(board);

		// o jogador black moveu o pião
		assertEquals(MoveResult.LEGAL, board.tryMoving(new Point(3, 1), new Point(3, 3)));

		// o jogador white também moveu um pião
		assertEquals(MoveResult.LEGAL, board.tryMoving(new Point(2, 6), new Point(2, 5)));

		// o jogador black moveu outro pião e deixou o rei descoberto
		assertEquals(MoveResult.LEGAL, board.tryMoving(new Point(4, 1), new Point(4, 2)));

		// o jogador white moveu sua rainha e deixou o rei em cheque
		assertEquals(MoveResult.LEGAL, board.tryMoving(new Point(3, 7), new Point(0, 4)));

		// tentar mover o cavalo não vai resolver a situação
		assertEquals(MoveResult.KING_IN_CHECK, board.tryMoving(new Point(6, 0), new Point(7, 2)));

		// Mas se colocar o pião na frente da rainha que está atacando, o rei
		// sai do cheque e o movimento é válido.
		assertEquals(MoveResult.LEGAL, board.tryMoving(new Point(2, 1), new Point(2, 2)));
	}

}

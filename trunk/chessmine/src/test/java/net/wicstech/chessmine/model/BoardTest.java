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

		// o jogador black moveu o pi�o
		assertEquals(MoveResult.LEGAL, board.tryMoving(new Point(3, 1), new Point(3, 3)));

		// o jogador white tamb�m moveu um pi�o
		assertEquals(MoveResult.LEGAL, board.tryMoving(new Point(2, 6), new Point(2, 5)));

		// o jogador black moveu outro pi�o e deixou o rei descoberto
		assertEquals(MoveResult.LEGAL, board.tryMoving(new Point(4, 1), new Point(4, 2)));

		// o jogador white moveu sua rainha e deixou o rei em cheque
		assertEquals(MoveResult.LEGAL, board.tryMoving(new Point(3, 7), new Point(0, 4)));

		// tentar mover o cavalo n�o vai resolver a situa��o
		assertEquals(MoveResult.KING_IN_CHECK, board.tryMoving(new Point(6, 0), new Point(7, 2)));

		// Mas se colocar o pi�o na frente da rainha que est� atacando, o rei
		// sai do cheque e o movimento � v�lido.
		assertEquals(MoveResult.LEGAL, board.tryMoving(new Point(2, 1), new Point(2, 2)));
	}

}

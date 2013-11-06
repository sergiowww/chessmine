package net.wicstech.chessmine.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Point;

import net.wicstech.chessmine.model.pieces.Piece;

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
		assertNotNull(board);

		// o jogador black moveu o pi�o
		assertTrue(board.tryMoving(new Point(3, 1), new Point(3, 3)));

		// o jogador white tamb�m moveu um pi�o
		assertTrue(board.tryMoving(new Point(2, 6), new Point(2, 5)));

		// o jogador black moveu outro pi�o e deixou o rei descoberto
		assertTrue(board.tryMoving(new Point(4, 1), new Point(4, 2)));

		// o jogador white moveu sua rainha e deixou o rei em cheque
		assertTrue(board.tryMoving(new Point(3, 7), new Point(0, 4)));

		// tentar mover o cavalo n�o vai resolver a situa��o
		Point pointFrom_6_0 = new Point(6, 0);
		Point pointTo_7_2 = new Point(7, 2);
		Piece ultimaPecaMovida = board.getPiecesOnBoard().get(pointFrom_6_0);
		assertFalse(board.tryMoving(pointFrom_6_0, pointTo_7_2));
		assertTrue(board.kingsPlayerIsInCheck(pointFrom_6_0, pointTo_7_2, ultimaPecaMovida));

		// Mas se colocar o pi�o na frente da rainha que est� atacando, o rei
		// sai do cheque e o movimento � v�lido.
		assertTrue(board.tryMoving(new Point(2, 1), new Point(2, 2)));
	}

}

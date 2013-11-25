package net.wicstech.chessmine.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import net.wicstech.chessmine.model.pieces.AbstractPiece;
import net.wicstech.chessmine.model.pieces.Pawn;

import org.apache.commons.lang.SystemUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/testApplicationContext.xml")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class BoardTest {

	@Autowired
	private Board board;

	@Autowired
	private BoardCurrentGameData gameData;

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

	@Test
	public void testCheckMate1() {
		if (board.getBoardSidePlaying().equals(BoardSide.BLACK)) {
			assertEquals(MoveResult.LEGAL, board.tryMoving(new Point(0, 1), new Point(0, 2)));
		}
		assertEquals(MoveResult.LEGAL, board.tryMoving(new Point(5, 6), new Point(5, 5)));
		assertEquals(MoveResult.LEGAL, board.tryMoving(new Point(4, 1), new Point(4, 3)));
		assertEquals(MoveResult.LEGAL, board.tryMoving(new Point(6, 6), new Point(6, 4)));
		assertEquals(MoveResult.CHECK_MATE, board.tryMoving(new Point(3, 0), new Point(7, 4)));
	}

	@Test
	public void testCheckMate2() throws IOException {
		board.reiniciar(BoardTest.class.getResourceAsStream("/check-mate2.xml"));
		assertEquals(3, board.getPiecesOnBoard().values().size());
		assertEquals(MoveResult.CHECK_MATE, board.tryMoving(new Point(6, 5), new Point(6, 0)));

	}

	@Test
	public void testIlegalMoves() {

		if (board.getBoardSidePlaying().equals(BoardSide.BLACK)) {
			assertEquals(MoveResult.ILEGAL_PLAYER, board.tryMoving(new Point(5, 6), new Point(5, 5)));
			assertEquals(MoveResult.ILEGAL, board.tryMoving(new Point(0, 1), new Point(1, 2)));
		} else {
			assertEquals(MoveResult.ILEGAL_PLAYER, board.tryMoving(new Point(0, 1), new Point(0, 2)));
			assertEquals(MoveResult.ILEGAL, board.tryMoving(new Point(5, 6), new Point(6, 5)));
		}
	}

	@Test
	public void testAttack() {
		if (board.getBoardSidePlaying().equals(BoardSide.WHITE)) {
			assertEquals(MoveResult.LEGAL, board.tryMoving(new Point(6, 6), new Point(6, 5)));
		}
		assertEquals(MoveResult.LEGAL, board.tryMoving(new Point(1, 1), new Point(1, 3)));
		Point pointCapturada = new Point(2, 4);
		assertEquals(MoveResult.LEGAL, board.tryMoving(new Point(2, 6), pointCapturada));
		assertTrue(gameData.getPiecesOnBoard().containsKey(pointCapturada));
		assertFalse(gameData.getCapturedPieces().contains(pointCapturada));
		AbstractPiece capturada = gameData.getPiecesOnBoard().get(pointCapturada);
		assertEquals(MoveResult.LEGAL, board.tryMoving(new Point(1, 3), pointCapturada));
		assertFalse(gameData.getPiecesOnBoard().containsValue(capturada));
		assertTrue(gameData.getCapturedPieces().contains(capturada));
	}

	@Test
	public void testSalvarJogo() throws IOException {
		File arquivoDestino = new File(SystemUtils.getJavaIoTmpDir(), "board-state.xml");
		try {
			Point point_0_2 = new Point(0, 2);
			Point point_5_5 = new Point(5, 5);

			if (board.getBoardSidePlaying().equals(BoardSide.BLACK)) {
				board.tryMoving(new Point(0, 1), point_0_2);
			} else {
				board.tryMoving(new Point(5, 6), point_5_5);
			}
			BoardSide boardSidePlaying = board.getBoardSidePlaying();
			board.salvar(arquivoDestino);
			board.reiniciar(null);
			board.reiniciar(new FileInputStream(arquivoDestino));
			board.setConfigured(true);
			assertTrue(board.isConfigured());

			assertEquals(boardSidePlaying, board.getBoardSidePlaying());
			if (boardSidePlaying.equals(BoardSide.BLACK)) {
				assertPoint(point_5_5);
			} else {
				assertPoint(point_0_2);
			}
		} finally {
			arquivoDestino.delete();
		}

	}

	private void assertPoint(Point point) {
		AbstractPiece abstractPiece = board.getPiecesOnBoard().get(point);
		assertNotNull(abstractPiece);
		assertTrue(abstractPiece instanceof Pawn);
	}
}

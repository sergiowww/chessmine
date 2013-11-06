package net.wicstech.chessmine.model.piecefactory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import net.wicstech.chessmine.model.BoardSide;
import net.wicstech.chessmine.model.PointFactory;
import net.wicstech.chessmine.model.pieces.Piece;

/**
 * Nó de informação do XML de estado.
 * 
 * @author Sergio
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
class PieceNode {
	// gambi por causa do formato do XML que sou obrigado a utilizar.
	@XmlRootElement(name = "black")
	public static class Black {
		// dummy-class
	}

	// gambi por causa do formato do XML que sou obrigado a utilizar.
	@XmlRootElement(name = "white")
	public static class White {
		// dummy-class
	}

	private int type;
	private int line;
	private int col;
	private Black black;
	private White white;

	public PieceNode() {
		super();
	}

	public PieceNode(Piece piece) {
		super();
		this.type = PieceTypeFactory.getType(piece.getClass()).ordinal();
		this.line = piece.getCurrentPosition().y + 1;
		this.col = piece.getCurrentPosition().x + 1;
		if (piece.getBoardSide().equals(BoardSide.BLACK)) {
			black = new Black();
		} else {
			white = new White();
		}

	}

	/**
	 * Instancia a peça com os dados.
	 * 
	 * @return
	 */
	public Piece getPiece() {
		Piece piece = PieceTypeFactory.newInstance(type);
		piece.setCurrentPosition(PointFactory.newPoint(col - 1, line - 1));
		if (black != null) {
			piece.setBoardSide(BoardSide.BLACK);
		}
		if (white != null) {
			piece.setBoardSide(BoardSide.WHITE);
		}
		return piece;
	}
}

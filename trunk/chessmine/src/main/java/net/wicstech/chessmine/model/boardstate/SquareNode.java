package net.wicstech.chessmine.model.boardstate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import net.wicstech.chessmine.model.BoardSide;
import net.wicstech.chessmine.model.pieces.Piece;

@XmlAccessorType(XmlAccessType.FIELD)
class SquareNode {
	@XmlElement(name = "pieceId")
	private String id;

	@XmlElement(name = "line")
	private int line;

	@XmlElement(name = "col")
	private int col;

	@XmlElement(name = "black")
	private Black black;

	@XmlElement(name = "white")
	private White white;

	public SquareNode(Piece piece) {
		super();
		this.id = piece.getPieceIdXML();
		this.line = piece.getCurrentPosition().y + 1;
		this.col = piece.getCurrentPosition().x + 1;
		if (piece.getBoardSide().equals(BoardSide.BLACK)) {
			black = new Black();
		} else {
			white = new White();
		}

	}
}

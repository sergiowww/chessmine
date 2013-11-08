package net.wicstech.chessmine.model.boardstate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import net.wicstech.chessmine.model.BoardSide;
import net.wicstech.chessmine.model.pieces.AbstractPiece;

import org.apache.commons.lang.StringUtils;

@SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
@XmlAccessorType(XmlAccessType.FIELD)
class SquareNode {
	@XmlElement(name = "pieceId")
	private final String pieceId;

	@XmlElement(name = "line")
	private final int line;

	@XmlElement(name = "col")
	private final int col;

	@XmlElement(name = "black")
	private String black;

	@XmlElement(name = "white")
	private String white;

	public SquareNode(AbstractPiece piece) {
		super();
		this.pieceId = piece.getPieceIdXML();
		this.line = piece.getCurrentPosition().y + 1;
		this.col = piece.getCurrentPosition().x + 1;
		if (piece.getBoardSide().equals(BoardSide.BLACK)) {
			black = StringUtils.EMPTY;
		} else {
			white = StringUtils.EMPTY;
		}

	}
}

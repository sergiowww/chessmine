package net.wicstech.chessmine.model.boardstate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import net.wicstech.chessmine.model.BoardSide;
import net.wicstech.chessmine.model.PointFactory;
import net.wicstech.chessmine.model.pieces.AbstractPiece;
import net.wicstech.chessmine.model.pieces.PieceFactory;

/**
 * Nó de informação do XML de estado.
 * 
 * @author Sergio
 * 
 */
@SuppressWarnings("PMD.ShortVariable")
@XmlAccessorType(XmlAccessType.FIELD)
class PieceNode {

	@XmlElement(name = "type")
	private int type;

	@XmlElement(name = "color")
	private String color;

	@XmlElement(name = "x")
	private int x;

	@XmlElement(name = "y")
	private int y;

	/**
	 * Construtor wrapper.
	 * 
	 * @param abstractPiece
	 */
	public PieceNode(AbstractPiece abstractPiece) {
		super();
		this.x = abstractPiece.getCurrentPosition().x;
		this.y = abstractPiece.getCurrentPosition().y;
		this.color = abstractPiece.getBoardSide().name();
		this.type = abstractPiece.getType();
	}

	/**
	 * Construtor padrão.
	 */
	public PieceNode() {
		super();
	}

	/**
	 * Instancia a peça com os dados.
	 * 
	 * @param pieceFactory
	 * 
	 * @return
	 */
	public AbstractPiece getPiece(PieceFactory pieceFactory) {
		AbstractPiece piece = pieceFactory.newInstance(type);
		piece.setCurrentPosition(PointFactory.newPoint(x, y));
		piece.setBoardSide(BoardSide.valueOf(color));
		return piece;
	}
}

package net.wicstech.chessmine.model.boardstate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import net.wicstech.chessmine.model.BoardSide;
import net.wicstech.chessmine.model.PointFactory;
import net.wicstech.chessmine.model.pieces.Piece;
import net.wicstech.chessmine.model.pieces.PieceFactory;

/**
 * Nó de informação do XML de estado.
 * 
 * @author Sergio
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
class PieceNode {
	@XmlAttribute(name = "id")
	private String id;

	@XmlElement(name = "type")
	private int type;

	@XmlElement(name = "line")
	private int line;

	@XmlElement(name = "col")
	private int col;

	@XmlElement(name = "black")
	private Black black;

	@XmlElement(name = "white")
	private White white;

	/**
	 * Instancia a peça com os dados.
	 * 
	 * @param pieceFactory
	 * 
	 * @return
	 */
	public Piece getPiece(PieceFactory pieceFactory) {
		Piece piece = pieceFactory.newInstance(type);
		piece.setPieceIdXML(id);
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

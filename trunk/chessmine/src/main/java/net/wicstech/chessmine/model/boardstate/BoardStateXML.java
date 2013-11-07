package net.wicstech.chessmine.model.boardstate;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.wicstech.chessmine.model.pieces.Piece;
import net.wicstech.chessmine.model.pieces.PieceFactory;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Carregador das peças.
 * 
 * @author Sergio
 * 
 */
@XmlRootElement(name = "board_setup")
public class BoardStateXML {

	@XmlElement(name = "piece")
	private List<PieceNode> pieceNodes;

	@Autowired
	private PieceFactory pieceFactory;

	/**
	 * Retorna as peças inicialmente posicionadas.
	 * 
	 * @return
	 */
	public List<Piece> getPiecesInitial() {
		List<Piece> pieces = new ArrayList<>();
		for (PieceNode pieceNode : pieceNodes) {
			pieces.add(pieceNode.getPiece(pieceFactory));
		}
		return pieces;
	}

}

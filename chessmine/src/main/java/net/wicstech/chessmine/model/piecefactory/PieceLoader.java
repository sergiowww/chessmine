package net.wicstech.chessmine.model.piecefactory;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.wicstech.chessmine.model.pieces.Piece;

/**
 * Carregador das peças.
 * 
 * @author Sergio
 * 
 */
@XmlRootElement(name = "board_setup")
public class PieceLoader {

	@XmlElement(name = "piece")
	private List<PieceNode> pieceNodes;

	/**
	 * Retorna as peças inicialmente posicionadas.
	 * 
	 * @return
	 */
	public List<Piece> getPiecesInitial() {
		List<Piece> pieces = new ArrayList<>();
		for (PieceNode pieceNode : pieceNodes) {
			pieces.add(pieceNode.getPiece());
		}
		return pieces;
	}

}

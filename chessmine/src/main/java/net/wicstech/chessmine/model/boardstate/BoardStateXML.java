package net.wicstech.chessmine.model.boardstate;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "board-state")
@XmlAccessorType(XmlAccessType.FIELD)
class BoardStateXML {

	@XmlElement(name = "piece")
	private List<PieceNode> pieceNodes;

	/**
	 * @return the pieceNodes
	 */
	public List<PieceNode> getPieceNodes() {
		return pieceNodes;
	}

	/**
	 * @param pieceNodes
	 *            the pieceNodes to set
	 */
	public void setPieceNodes(List<PieceNode> pieceNodes) {
		this.pieceNodes = pieceNodes;
	}
}

package net.wicstech.chessmine.model.boardstate;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.wicstech.chessmine.model.BoardSide;

import org.apache.commons.lang.StringUtils;

@XmlRootElement(name = "board-state")
@XmlAccessorType(XmlAccessType.FIELD)
class BoardStateXML {

	@XmlElement(name = "piece")
	private List<PieceNode> pieceNodes;

	@XmlElement(name = "boardSidePlaying")
	private String boardSidePlaying;

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

	/**
	 * Retorna o lado do tabuleiro que está jogando.
	 * 
	 * @return
	 */
	public BoardSide getBoardSidePlaying() {
		if (StringUtils.isNotBlank(boardSidePlaying)) {
			return BoardSide.valueOf(boardSidePlaying);
		}
		return null;
	}

	/**
	 * Atribuir o board side playing.
	 * 
	 * @param boardSide
	 */
	public void setBoardSidePlaying(BoardSide boardSide) {
		boardSidePlaying = boardSide.name();
	}
}

package net.wicstech.chessmine.model.piecefactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.wicstech.chessmine.model.pieces.Piece;

import org.apache.commons.lang.SystemUtils;

/**
 * Carregador das peças.
 * 
 * @author Sergio
 * 
 */
@XmlRootElement(name = "board_setup")
public class BoardConfigXML {

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

	/**
	 * Salvar as peças e posições em um xml.
	 * 
	 * @param values
	 * @return nome do arquivo e do diretório onde foi salvo.
	 * @throws JAXBException
	 * @throws IOException
	 */
	public String savePieces(Collection<Piece> values) throws JAXBException, IOException {
		this.pieceNodes = new ArrayList<>();
		for (Piece piece : values) {
			this.pieceNodes.add(new PieceNode(piece));
		}
		JAXBContext jaxb = JAXBContext.newInstance(BoardConfigXML.class);
		Marshaller marshaller = jaxb.createMarshaller();
		File destino = new File(SystemUtils.getUserHome(), "board_state" + System.currentTimeMillis() + ".xml");
		if (!destino.exists()) {
			destino.createNewFile();
		}
		marshaller.marshal(this, destino);
		return destino.getPath();
	}
}

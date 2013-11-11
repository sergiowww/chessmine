package net.wicstech.chessmine.model.boardstate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import net.wicstech.chessmine.model.pieces.AbstractPiece;
import net.wicstech.chessmine.model.pieces.PieceFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Carregador das peças.
 * 
 * @author Sergio
 * 
 */
@Service
public class BoardState {

	@Autowired
	private PieceFactory pieceFactory;

	/**
	 * Retorna as peças inicialmente posicionadas.
	 * 
	 * @return
	 */
	public List<AbstractPiece> getPiecesInitial() {
		return loadPieces(BoardState.class.getResourceAsStream("/board-state.xml"));
	}

	/**
	 * Carregar objetos de um XML.
	 * 
	 * @param initialFile
	 * @return
	 */
	private List<AbstractPiece> loadPieces(InputStream initialFile) {
		List<PieceNode> pieceNodes = loadPieceNodes(initialFile);
		List<AbstractPiece> pieces = new ArrayList<>();
		for (PieceNode pieceNode : pieceNodes) {
			pieces.add(pieceNode.getPiece(pieceFactory));
		}
		return pieces;
	}

	/**
	 * Carregar posição do xml.
	 * 
	 * @param xmlFile
	 * @return
	 * @throws FileNotFoundException
	 */
	public List<AbstractPiece> loadFromFile(File xmlFile) throws FileNotFoundException {
		return loadPieces(new FileInputStream(xmlFile));
	}

	/**
	 * Ler objetos do XML.
	 * 
	 * @param inputStream
	 * @return
	 */
	private List<PieceNode> loadPieceNodes(InputStream inputStream) {
		try (InputStream xmlFile = inputStream) {
			JAXBContext context = JAXBContext.newInstance(BoardStateXML.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			BoardStateXML boardStateXML = (BoardStateXML) unmarshaller.unmarshal(xmlFile);
			List<PieceNode> pieceNodes = boardStateXML.getPieceNodes();
			return pieceNodes;
		} catch (JAXBException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Salvar peças em um arquivo.
	 * 
	 * @param values
	 * @param arquivoDestino
	 * @return
	 * @throws JAXBException
	 * @throws IOException
	 */
	public String savePieces(Collection<AbstractPiece> values, File arquivoDestino) throws JAXBException, IOException {
		JAXBContext jaxb = JAXBContext.newInstance(BoardStateXML.class);
		Marshaller marshaller = jaxb.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		if (arquivoDestino.exists()) {
			arquivoDestino.delete();
		}
		arquivoDestino.createNewFile();
		BoardStateXML boardStateXML = new BoardStateXML();
		boardStateXML.setPieceNodes(new ArrayList<PieceNode>());
		for (AbstractPiece abstractPiece : values) {
			boardStateXML.getPieceNodes().add(new PieceNode(abstractPiece));
		}
		marshaller.marshal(boardStateXML, arquivoDestino);
		return arquivoDestino.getPath();
	}

}

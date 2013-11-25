package net.wicstech.chessmine.model.boardstate;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import net.wicstech.chessmine.model.BoardCurrentGameData;
import net.wicstech.chessmine.model.pieces.AbstractPiece;
import net.wicstech.chessmine.model.pieces.PieceFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

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

	@Autowired
	private BoardCurrentGameData boardData;

	/**
	 * Retorna as peças inicialmente posicionadas.
	 * 
	 * @return
	 * @throws IOException
	 */
	public List<AbstractPiece> getPiecesInitial() throws IOException {
		try (InputStream boardStateXML = BoardState.class.getResourceAsStream("/board-state.xml")) {
			return loadPieces(boardStateXML).getPieces();
		}
	}

	/**
	 * Carregar objetos de um XML.
	 * 
	 * @param initialFile
	 * @return
	 */
	public GameBoardConfig loadPieces(InputStream initialFile) {
		BoardStateXML pieceNodes = loadPieceNodes(initialFile);
		List<AbstractPiece> pieces = new ArrayList<>();
		for (PieceNode pieceNode : pieceNodes.getPieceNodes()) {
			pieces.add(pieceNode.getPiece(pieceFactory));
		}
		return new GameBoardConfig(pieces, pieceNodes.getBoardSidePlaying());
	}

	/**
	 * Ler objetos do XML.
	 * 
	 * @param inputStream
	 * @return
	 */
	@SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
	private BoardStateXML loadPieceNodes(InputStream inputStream) {
		try (InputStream xmlFile = inputStream) {
			JAXBContext context = JAXBContext.newInstance(BoardStateXML.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			return (BoardStateXML) unmarshaller.unmarshal(xmlFile);
		} catch (JAXBException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Salvar peças em um arquivo.
	 * 
	 * @param arquivoDestino
	 * @return
	 */
	@SuppressWarnings({"PMD.AvoidInstantiatingObjectsInLoops", "PMD.AvoidThrowingRawExceptionTypes"})
	public String savePieces(File arquivoDestino) {
		try (InputStream boardStateXsd = getClass().getResourceAsStream("/board-state.xsd")) {
			JAXBContext jaxb = JAXBContext.newInstance(BoardStateXML.class);
			Marshaller marshaller = jaxb.createMarshaller();

			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(new StreamSource(boardStateXsd));

			marshaller.setSchema(schema);
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			if (arquivoDestino.exists()) {
				arquivoDestino.delete();
			}
			arquivoDestino.createNewFile();
			BoardStateXML boardStateXML = new BoardStateXML();
			boardStateXML.setPieceNodes(new ArrayList<PieceNode>());
			boardStateXML.setBoardSidePlaying(boardData.getBoardSidePlaying());
			for (AbstractPiece abstractPiece : boardData.getPiecesOnBoard().values()) {
				boardStateXML.getPieceNodes().add(new PieceNode(abstractPiece));
			}
			marshaller.marshal(boardStateXML, arquivoDestino);
			return arquivoDestino.getPath();
		} catch (JAXBException | IOException | SAXException e) {
			throw new RuntimeException(e);
		}
	}

}

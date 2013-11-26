package net.wicstech.chessmine.model.boardstate;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;

import net.wicstech.chessmine.model.BoardCurrentGameData;
import net.wicstech.chessmine.model.pieces.AbstractPiece;
import net.wicstech.chessmine.model.pieces.PieceFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

	private static final Log LOG = LogFactory.getLog(BoardState.class);

	@Autowired
	private PieceFactory pieceFactory;

	@Autowired
	private BoardCurrentGameData boardData;

	@Autowired
	@Qualifier("schema")
	private Schema schema;

	@Autowired
	@Qualifier("validator")
	private Validator validator;

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
	private BoardStateXML loadPieceNodes(InputStream inputStream) {
		try (InputStream xmlFile = inputStream) {
			JAXBContext context = JAXBContext.newInstance(BoardStateXML.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			return (BoardStateXML) unmarshaller.unmarshal(xmlFile);
		} catch (JAXBException | IOException e) {
			throw new BoardStateReadException(e);
		}
	}

	/**
	 * Verifica se o XML é válido.
	 * 
	 * @param xmlFile
	 * @return
	 */
	public boolean isValidXMLConfig(File xmlFile) {
		if (xmlFile == null || !xmlFile.exists()) {
			return false;
		}
		try {
			validator.validate(new StreamSource(xmlFile));
			return true;
		} catch (SAXException | IOException e) {
			LOG.error(e, e);
			throw new BoardStateReadException("XML Inválido", e);
		}
	}

	/**
	 * Salvar peças em um arquivo.
	 * 
	 * @param arquivoDestino
	 * @return
	 */
	@SuppressWarnings({"PMD.AvoidInstantiatingObjectsInLoops"})
	public String savePieces(File arquivoDestino) {
		try {
			JAXBContext jaxb = JAXBContext.newInstance(BoardStateXML.class);
			Marshaller marshaller = jaxb.createMarshaller();

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
		} catch (JAXBException | IOException e) {
			throw new BoardStateReadException(e);
		}
	}

}

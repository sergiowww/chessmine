package net.wicstech.chessmine.model.boardstate;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import net.wicstech.chessmine.model.BoardSide;
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
		return loadPieces(BoardState.class.getResourceAsStream("/board-state.xml")).getPieces();
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
			BoardStateXML boardStateXML = (BoardStateXML) unmarshaller.unmarshal(xmlFile);
			return boardStateXML;
		} catch (JAXBException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Salvar peças em um arquivo.
	 * 
	 * @param values
	 * @param boardSidePlaying
	 * @param arquivoDestino
	 * @return
	 * @throws JAXBException
	 * @throws IOException
	 */
	public String savePieces(Collection<AbstractPiece> values, BoardSide boardSidePlaying, File arquivoDestino) throws JAXBException, IOException {
		JAXBContext jaxb = JAXBContext.newInstance(BoardStateXML.class);
		Marshaller marshaller = jaxb.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		if (arquivoDestino.exists()) {
			arquivoDestino.delete();
		}
		arquivoDestino.createNewFile();
		BoardStateXML boardStateXML = new BoardStateXML();
		boardStateXML.setPieceNodes(new ArrayList<PieceNode>());
		boardStateXML.setBoardSidePlaying(boardSidePlaying);
		for (AbstractPiece abstractPiece : values) {
			boardStateXML.getPieceNodes().add(new PieceNode(abstractPiece));
		}
		marshaller.marshal(boardStateXML, arquivoDestino);
		return arquivoDestino.getPath();
	}

}

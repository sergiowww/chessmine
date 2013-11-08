package net.wicstech.chessmine.model.boardstate;

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

import net.wicstech.chessmine.model.pieces.AbstractPiece;

import org.springframework.stereotype.Service;

@Service
@XmlRootElement(name = "board_result")
@SuppressWarnings("PMD.SingularField")
public class BoardResultXML {

	@XmlElement(name = "square")
	private List<SquareNode> squareNodes;

	/**
	 * Salvar as peças e posições em um xml.
	 * 
	 * @param values
	 * @param arquivoDestino
	 * @return nome do arquivo e do diretório onde foi salvo.
	 * @throws JAXBException
	 * @throws IOException
	 */
	@SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
	public String savePieces(Collection<AbstractPiece> values, File arquivoDestino) throws JAXBException, IOException {
		this.squareNodes = new ArrayList<>();
		for (AbstractPiece piece : values) {
			this.squareNodes.add(new SquareNode(piece));
		}
		JAXBContext jaxb = JAXBContext.newInstance(BoardResultXML.class);
		Marshaller marshaller = jaxb.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		if (arquivoDestino.exists()) {
			arquivoDestino.delete();
		}
		arquivoDestino.createNewFile();
		marshaller.marshal(this, arquivoDestino);
		return arquivoDestino.getPath();
	}
}

package net.wicstech.chessmine.model.piecefactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Service;

/**
 * Factory bean da fábrica de peças.
 * 
 * @author Sergio
 * 
 */
@Service
public class PieceLoaderFactoryBean implements FactoryBean<PieceLoader> {
	@Override
	public PieceLoader getObject() throws Exception {
		JAXBContext context = JAXBContext.newInstance(PieceLoader.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();

		XMLInputFactory xif = XMLInputFactory.newFactory();
		xif.setProperty(XMLInputFactory.SUPPORT_DTD, false);
		XMLStreamReader xsr = xif.createXMLStreamReader(new StreamSource(PieceLoader.class.getResourceAsStream("/board_state.xml")));
		return (PieceLoader) unmarshaller.unmarshal(xsr);
	}

	@Override
	public Class<?> getObjectType() {
		return PieceLoader.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}

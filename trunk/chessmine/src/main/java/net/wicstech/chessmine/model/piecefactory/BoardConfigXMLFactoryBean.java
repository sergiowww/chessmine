package net.wicstech.chessmine.model.piecefactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * Factory bean da fábrica de peças.
 * 
 * @author Sergio
 * 
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "prototype")
public class BoardConfigXMLFactoryBean implements FactoryBean<BoardConfigXML> {
	@Override
	public BoardConfigXML getObject() throws Exception {
		JAXBContext context = JAXBContext.newInstance(BoardConfigXML.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();

		XMLInputFactory xif = XMLInputFactory.newFactory();
		xif.setProperty(XMLInputFactory.SUPPORT_DTD, false);
		XMLStreamReader xsr = xif.createXMLStreamReader(new StreamSource(BoardConfigXML.class.getResourceAsStream("/board_state.xml")));
		return (BoardConfigXML) unmarshaller.unmarshal(xsr);
	}

	@Override
	public Class<?> getObjectType() {
		return BoardConfigXML.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

}

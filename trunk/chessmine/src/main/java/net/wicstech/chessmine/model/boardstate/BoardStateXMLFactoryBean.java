package net.wicstech.chessmine.model.boardstate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
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
public class BoardStateXMLFactoryBean implements FactoryBean<BoardStateXML>, ApplicationContextAware {
	private ApplicationContext applicationContext;

	@Override
	public BoardStateXML getObject() throws JAXBException, XMLStreamException {
		JAXBContext context = JAXBContext.newInstance(BoardStateXML.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();

		XMLInputFactory xif = XMLInputFactory.newFactory();
		xif.setProperty(XMLInputFactory.SUPPORT_DTD, false);
		XMLStreamReader xsr = xif.createXMLStreamReader(new StreamSource(BoardStateXML.class.getResourceAsStream("/board_state.xml")));
		BoardStateXML boardStateXML = (BoardStateXML) unmarshaller.unmarshal(xsr);
		applicationContext.getAutowireCapableBeanFactory().autowireBean(boardStateXML);
		return boardStateXML;
	}

	@Override
	public Class<?> getObjectType() {
		return BoardStateXML.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}

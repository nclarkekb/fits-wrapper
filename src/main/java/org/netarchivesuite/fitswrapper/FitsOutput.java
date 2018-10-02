package org.netarchivesuite.fitswrapper;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class FitsOutput {

	public static final XMLInputFactory inputFactory;

	public static final JAXBContext jaxbContext;

	static {
		//inputFactory = XMLInputFactory.newFactory();
		inputFactory = XMLInputFactory.newInstance();
		try {
			jaxbContext = JAXBContext.newInstance( org.netarchivesuite.fitswrapper.jaxb.Fits.class );
		}
		catch (JAXBException e) {
			e.printStackTrace();
			throw new RuntimeException( e );
		}
	}

	protected FitsOutput() {
	}

	public static void marshall(org.netarchivesuite.fitswrapper.jaxb.Fits model, OutputStream out) throws JAXBException {
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, true );
		jaxbMarshaller.marshal( model, out );
	}

	public static org.netarchivesuite.fitswrapper.jaxb.Fits unmarshall(InputStream in) throws JAXBException, XMLStreamException {
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		XMLStreamReader reader = inputFactory.createXMLStreamReader( in );
		return jaxbUnmarshaller.unmarshal( reader, org.netarchivesuite.fitswrapper.jaxb.Fits.class ).getValue();
	}

}

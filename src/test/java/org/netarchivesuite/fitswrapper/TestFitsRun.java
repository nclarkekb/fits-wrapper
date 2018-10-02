package org.netarchivesuite.fitswrapper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.netarchivesuite.fitswrapper.FitsRun;
import org.netarchivesuite.fitswrapper.jaxb.Fits;
import org.netarchivesuite.fitswrapper.jaxb.IdentificationType;
import org.netarchivesuite.fitswrapper.jaxb.StatusType;
import org.netarchivesuite.fitswrapper.jaxb.IdentificationType.Identity;

import com.antiaction.bootstrap.classloader.Classloader;
import com.antiaction.bootstrap.classloader.ClassloaderFileLogger;
import com.antiaction.bootstrap.classloader.DefaultResourceLocator;
import com.antiaction.bootstrap.classloader.SimpleSystemClassFilter;

public class TestFitsRun {

	public static void main(String[] args) {
		TestFitsRun p = new TestFitsRun();
		p.Main(args);
	}

	protected static String[] postFixMatchList = {
		"java.",
		"javax.",
		"sun.",
		"com.sun.",
		"org.xml.",
		"org.w3c.",
		"org.netarchivesuite.fitswrapper.",
		"dk.netarkivet.dab."
    };

	protected static String[] classMatchList = {
	};

	public void Main(String[] args) {
		//System.setProperty("jaxp.debug", "1");

		//String fits_home = "/home/nicl/fits-0.8.10/";
		String fits_home = "/home/nicl/fits-0.8.10-nicl/";
		//String fits_home = "/home/nicl/fits-0.9.0/";

		File fits_home_file = new File(fits_home);
		ClassloaderFileLogger logger = new ClassloaderFileLogger( new File( "/home/nicl/classloader.log") );
		Classloader classloader = new Classloader( this.getClass().getClassLoader() );
		classloader.setClassloaderLogger( logger );
		//classloader.setSystemClassFilter( new SimpleSystemClassFilter( classMatchList, SimpleSystemClassFilter.concat( SystemClassFilter.defaultPostFixMatchList, postFixMatchList ) ) );
		classloader.setSystemClassFilter( new SimpleSystemClassFilter( classMatchList, postFixMatchList ) );
		classloader.setResourceLocator( new DefaultResourceLocator( new File( fits_home_file, "xml/nlnz/" ), new File( fits_home_file, "lib/" ), logger) );
		logger.info( classloader.getClass().getName() + " classloader initialized." );

		FitsRun fitsRun = FitsRun.getInstance(fits_home, classloader);
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		byte[] fitsOutputXml;

		bOut.reset();
		fitsRun.examine(new File("/home/nicl/Dropbox/kb/Team building program.pdf"), bOut);
		fitsOutputXml = bOut.toByteArray();
		// debug
		System.out.println(new String(fitsOutputXml));
		try {
			Fits fitsOutput = FitsOutput.unmarshall( new ByteArrayInputStream( fitsOutputXml ) );
			IdentificationType identificationType = fitsOutput.getIdentification();
			List<Identity> identityList = identificationType.getIdentity();
			StatusType statusType = identificationType.getStatus();
			if ( statusType == null || statusType.equals(StatusType.SINGLE_RESULT)) {
				System.out.println("Yes!");
			}
			for ( int i=0; i<identityList.size();++i ) {
				System.out.println(identityList.get( i ).getMimetype() );
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}

		bOut.reset();
		fitsRun.examine(new File("/home/nicl/fits-0.8.10.zip"), bOut);
		fitsOutputXml = bOut.toByteArray();
		// debug
		System.out.println(new String(fitsOutputXml));
		try {
			Fits fitsOutput = FitsOutput.unmarshall( new ByteArrayInputStream( fitsOutputXml ) );
			IdentificationType identificationType = fitsOutput.getIdentification();
			List<Identity> identityList = identificationType.getIdentity();
			StatusType statusType = identificationType.getStatus();
			if ( statusType == null || statusType.equals(StatusType.SINGLE_RESULT)) {
				System.out.println("Yes!");
			}
			for ( int i=0; i<identityList.size();++i ) {
				System.out.println(identityList.get( i ).getMimetype() );
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}

		bOut.reset();
		fitsRun.examine(new File("/home/nicl/fits-0.8.10/lib/fits.jar"), bOut);
		fitsOutputXml = bOut.toByteArray();
		// debug
		System.out.println(new String(fitsOutputXml));
		try {
			Fits fitsOutput = FitsOutput.unmarshall( new ByteArrayInputStream( fitsOutputXml ) );
			IdentificationType identificationType = fitsOutput.getIdentification();
			List<Identity> identityList = identificationType.getIdentity();
			StatusType statusType = identificationType.getStatus();
			if ( statusType == null || statusType.equals(StatusType.SINGLE_RESULT)) {
				System.out.println("Yes!");
			}
			for ( int i=0; i<identityList.size();++i ) {
				System.out.println(identityList.get( i ).getMimetype() );
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}

}

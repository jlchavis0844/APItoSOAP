package testing;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class SOAPFrame {


	private SOAPFrame(){};

	public static SOAPMessage buildFrame(){
		MessageFactory factory = null;
		SOAPMessage message = null;
		SOAPPart soapPart = null;
		SOAPEnvelope envelope = null;
		SOAPHeader header = null;
		SOAPBody body = null;

		try {
			factory = MessageFactory.newInstance();
			message = factory.createMessage();
			soapPart = message.getSOAPPart();
			envelope = soapPart.getEnvelope();

			//envelope.addNamespaceDeclaration("soap", "http://schemas.xmlsoap.org/soap/envelope/");
			//envelope.addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance")
			//.setAttribute("schemaLocation", "http://schemas.xmlsoap.org/soap/envelope/SoapEnvelope.xsd");
			envelope.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:schemaLocation", 
					"http://schemas.xmlsoap.org/soap/envelope/ SoapEnvelope.xsd");
			header = envelope.getHeader();
			//header.addNamespaceDeclaration("cuns", "http://calpers.ca.gov/PSR/CommonUtilitiesV1");
			Name cuns = envelope.createName("HeaderInfo", "cuns", "http://calpers.ca.gov/PSR/CommonUtilitiesV1");
			SOAPHeaderElement sHead = (SOAPHeaderElement) header.addChildElement(cuns);
			sHead.addChildElement("InterfaceTypeId", "cuns").addTextNode("50031");
			sHead.addChildElement("BusinessPartnerId", "cuns").addTextNode("7111270643");
			sHead.addChildElement("SchemaVersion", "cuns").addTextNode("1.0");
			sHead.addChildElement("DateTime", "cuns").addTextNode(LocalDateTime.now().toString());

			body = envelope.getBody();


			//			message.writeTo(System.out);
			//			System.out.println();
			envelope.setPrefix("soap");
			envelope.removeNamespaceDeclaration("SOAP-ENV");
			header.setPrefix("soap");
			header.removeNamespaceDeclaration("SOAP-ENV");
			body.setPrefix("soap");
			body.removeNamespaceDeclaration("SOAP-ENV");
			
			//message.writeTo(System.out);
			SOAPFrame.printEnvelope(message.getSOAPPart().getEnvelope());
			System.out.println();

		} catch (SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		return message;
	}

	public static void printEnvelope(SOAPEnvelope envelope){

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;

		try {
			transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT,"yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			DOMSource source = new DOMSource(envelope);

			StreamResult result = new StreamResult(new File("Test.xml"));
			transformer.transform(source, result);

			StreamResult consoleResult = new StreamResult(System.out);
			transformer.transform(source,  consoleResult);
		
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

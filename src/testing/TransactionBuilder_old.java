package testing;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.util.Vector;

public class TransactionBuilder_old {
	private final static String employerID = "7111270643";
	private static DocumentBuilder dBuilder;
	private static DocumentBuilderFactory dbFactory;
	private static Document doc;

	public static Element makeTransaction(Vector<Transaction> transactions, SOAPBody body){
			
			DOMSource source = null;
			Element superRoot = null;
			//Make the document
			
			try {
				dbFactory = DocumentBuilderFactory.newInstance();
				dbFactory.setNamespaceAware(false);
				dBuilder = dbFactory.newDocumentBuilder();
				doc = dBuilder.newDocument();
				//superRoot = doc.createElement("RetirementHealthEnrollment");
//				body.setAttributeNS("RetirementHealthEnrollment", "n1", 
//						"http://calers.ca.gov/PSR/RetirementHealthTransactionsV1");
//				body.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:schemaLocation", 
//						"http://schemas.xmlsoap.org/soap/envelope/ SoapEnvelope.xsd");
//				
				//body.addDocument(doc);
			} catch (ParserConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			

		for(Transaction t: transactions){
			Person emp = t.getEmployee();
			Element rootElement;
			try {


				//build the root
				rootElement = doc.createElement("Transaction");
				//rootElement.setIdAttributeNS(namespaceURI, localName, isId);


				doc.appendChild(rootElement);

				//transaction type
				Element tType = doc.createElement("TransactionType");
				tType.appendChild(doc.createTextNode(t.getTransactionType()));
				rootElement.appendChild(tType);

				//transaction id
				Element tID = doc.createElement("UniqueTransactionId");
				tID.appendChild(doc.createTextNode(t.getUniqueTransactionId()));
				rootElement.appendChild(tID);

				//Demo graphics area
				Element demo = doc.createElement("Demographics");//holds PersonalInfo and others
				Element pInfo = doc.createElement("PersonalInfo");//holds all personal id including personid
				pInfo.setPrefix("n1");
				Element pID = doc.createElement("PersonId"); // holds calPERSId

				//only calpersid or SSN should be in the thing
				//write the calPERSid to the personal info. Exist it personidtype = PID
				if(emp.getCalPERSid() != null){
					Element cpID = doc.createElement("CalPERSId");
					cpID.appendChild(doc.createTextNode(emp.getCalPERSid()));
					pID.appendChild(cpID);
					pInfo.appendChild(pID);
				}

				//write ssn, should be here if calpersid isn't and peronidtype != PID
				if(emp.getSSN() != null){
					Element cpID = doc.createElement("SSN");
					cpID.setPrefix("cuns");
					cpID.appendChild(doc.createTextNode(emp.getSSN()));
					pID.appendChild(cpID);
					pInfo.appendChild(pID);
				}


				if(emp.getPersonIdType() != null){
					Element pIDtype = doc.createElement("PersonIdType");
					pIDtype.appendChild(doc.createTextNode((emp.getPersonIdType())));
					pInfo.appendChild(pIDtype);
				}


				if(emp.getLastName() != null){
					Element lName = doc.createElement("LastName");
					lName.appendChild(doc.createTextNode(emp.getLastName()));
					pInfo.appendChild(lName);
				}

				if(emp.getFirstName() != null){
					Element fName = doc.createElement("FirstName");
					fName.appendChild(doc.createTextNode(emp.getFirstName()));
					pInfo.appendChild(fName);
				}


				if(emp.getBirthDate() != null){
					Element bDay = doc.createElement("BirthDate");
					pInfo.appendChild(bDay);
					bDay.appendChild(doc.createTextNode(emp.getBirthDate()));
				}

				if(emp.getGender() != null){
					Element gender = doc.createElement("Gender");
					pInfo.appendChild(gender);
					gender.appendChild(doc.createTextNode((emp.getGender())));
				}

				//add address to Demographics
				//Does any address info exist?
				if(emp.addressExists()){
					Element aInfo = doc.createElement("AddressInfo");
					aInfo.setPrefix("n1");
					//read in up to 3 address lines
					String[] empAdds = emp.getAddresses();

					for(int i = 0, len = empAdds.length; i < len; i++){
						if(empAdds[i] != null && !empAdds[i].equals("") && !empAdds[i].equals(" ")){
							Element aLine = doc.createElement("AddressLine");
							aLine.setPrefix("cuns");
							aLine.appendChild(doc.createTextNode(empAdds[i]));
							aInfo.appendChild(aLine);
						}
					}

					if(emp.getAddressType() != null){
						Element aType = doc.createElement("AddressType");
						aType.setPrefix("cuns");
						aType.appendChild(doc.createTextNode(emp.getAddressType()));
						aInfo.appendChild(aType);
					}

					//load city, state, zip
					if(emp.getZip5() != null){
						Element zip5 = doc.createElement("ZipCode5");
						zip5.setPrefix("cuns");
						zip5.appendChild(doc.createTextNode(emp.getZip5()));
						aInfo.appendChild(zip5);
					}

					if(emp.getCity() != null){
						Element city = doc.createElement("City");
						city.setPrefix("cuns");
						city.appendChild(doc.createTextNode(emp.getCity()));
						aInfo.appendChild(city);
					}

					if(emp.getState() != null){
						Element state = doc.createElement("State");
						state.setPrefix("cuns");
						state.appendChild(doc.createTextNode(emp.getState()));
						aInfo.appendChild(state);
					}

					Element country = doc.createElement("Country");
					country.setPrefix("cuns");
					country.appendChild(doc.createTextNode("US"));
					aInfo.appendChild(country);
					//write the address info to the demo element
					demo.appendChild(aInfo);

				}//end address exists

				Element app = doc.createElement("Appointment");
				Element erInfo = doc.createElement("EmployerInfo");
				Element erID = doc.createElement("EmployerCalPERSId");
				//TODO: change employerID variable implementation from hard coded
				erID.appendChild(doc.createTextNode(employerID));
				erInfo.appendChild(erID);
				app.appendChild(erInfo);

				//start the health enrollment sub group
				Element hEnroll = doc.createElement("HealthEnrollment");

				Element heReason = doc.createElement("HealthEventReason");
				heReason.appendChild(doc.createTextNode(t.getHealthEventReason()));
				hEnroll.appendChild(heReason);

				Element eDate = doc.createElement("EventDate");
				eDate.appendChild(doc.createTextNode(t.getEventDate()));
				hEnroll.appendChild(eDate);

				Element rDate = doc.createElement("ReceivedDate");
				rDate.appendChild(doc.createTextNode(t.getReceivedDate()));
				hEnroll.appendChild(rDate);

				//TODO: change hard coding of medical group
				Element mGrp = doc.createElement("MedicalGroup");
				mGrp.appendChild(doc.createTextNode("045"));
				hEnroll.appendChild(mGrp);

				//Subscriber info in healthenrollment element
				Element subInfo = doc.createElement("SubscriberInfo");

				Element chngMed = doc.createElement("ApplyChangeToMedicalPlan");
				chngMed.appendChild(doc.createTextNode(
						(t.getApplyChangeToMedicalPlan())));
				subInfo.appendChild(chngMed);

				Element chngDen = doc.createElement("ApplyChangeToDentalPlan");
				chngDen.appendChild(doc.createTextNode(
						(t.getApplyChangeToDentalPlan())));
				subInfo.appendChild(chngDen);

				Element chngVis = doc.createElement("ApplyChangeToVisionPlan");
				chngVis.appendChild(doc.createTextNode(
						(t.getApplyChangeToVisionPlan())));
				subInfo.appendChild(chngVis);

				Element heZipCode = doc.createElement("HealthEligibilityZipCode");
				heZipCode.appendChild(doc.createTextNode(emp.getHealthEligibilityZip()));
				subInfo.appendChild(heZipCode);

				//TODO: remove type hardcoding for zip type
				Element heZipCodeType = doc.createElement("HealthEligibilityZipCodeType");
				heZipCodeType.appendChild(doc.createTextNode("Personal"));
				subInfo.appendChild(heZipCodeType);

				Element heCounty = doc.createElement("HealthEligibilityCounty");
				heCounty.appendChild(doc.createTextNode(emp.getHealthEligibilityCounty()));
				subInfo.appendChild(heCounty);


				//Add subscriber info to health enrollment
				//end subscriber add
				hEnroll.appendChild(subInfo);

				Vector<Person> deps = t.getDependents();


				for(Person depend: deps){
					Element depInfo = doc.createElement("DependentInfo");
					Element depPersonInfo = doc.createElement("DependentPersonInfo");

					if(depend.getSSN() != null){
						Element depPID = doc.createElement("PersonId");
						Element depSSN = doc.createElement("SSN");
						depSSN.setPrefix("cuns");
						depSSN.appendChild(doc.createTextNode(depend.getSSN()));
						depPID.appendChild(depSSN);
						depPersonInfo.appendChild(depPID);
					}

					if(depend.getPersonIdType() != null){
						Element depPtype = doc.createElement("PersonIdType");
						depPtype.appendChild(doc.createTextNode(depend.getPersonIdType()));
						depPersonInfo.appendChild(depPtype);
					}

					if(depend.getLastName() != null){
						Element depLast = doc.createElement("LastName");
						depLast.appendChild(doc.createTextNode(depend.getLastName()));
						depPersonInfo.appendChild(depLast);
					}

					if(depend.getFirstName() != null){
						Element depFirst = doc.createElement("FirstName");
						depFirst.appendChild(doc.createTextNode(depend.getFirstName()));
						depPersonInfo.appendChild(depFirst);
					}

					if(depend.getBirthDate() != null){
						Element depBday = doc.createElement("BirthDate");
						depBday.appendChild(doc.createTextNode(depend.getBirthDate()));
						depPersonInfo.appendChild(depBday);
					}

					if(depend.getGender() != null){
						Element depGen = doc.createElement("Gender");
						depGen.appendChild(doc.createTextNode(depend.getGender()));
						depPersonInfo.appendChild(depGen);
					}

					//write DependentPersonInfo to DependentInfo
					depInfo.appendChild(depPersonInfo);

					if(depend.getRelationship() != null){
						Element depRel = doc.createElement("Relationship");
						depRel.appendChild(doc.createTextNode(depend.getRelationship()));
						depInfo.appendChild(depRel);
					}

					Element aSame = doc.createElement("AddressSameAsPrimarySubscriber");
					aSame.appendChild(doc.createTextNode(
							Boolean.toString(depend.isAddressSameAsPrimarySubscriber())));
					depInfo.appendChild(aSame);

					if(depend.getDependentType() != null){
						Element depType = doc.createElement("DependentType");
						depType.appendChild(doc.createTextNode(depend.getDependentType()));
						depInfo.appendChild(depType);
					}

					Element appMed = doc.createElement("ApplyToMedical");
					appMed.appendChild(doc.createTextNode(
							Boolean.toString(depend.isApplyToMedical())));
					depInfo.appendChild(appMed);

					Element depEcon = doc.createElement("EconomicallyConfirmIndicator");
					depEcon.appendChild(doc.createTextNode(
							Boolean.toString(depend.isEconomicallyConfirmIndicator())));
					depInfo.appendChild(depEcon);

					if(depend.getDependentAcquiredDate() != null){
						Element depAdate = doc.createElement("DependentAcquiredDate");
						depAdate.appendChild(doc.createTextNode(depend.getDependentAcquiredDate()));
						depInfo.appendChild(depAdate);
					}

					//write DependentInfo to Transaction
					hEnroll.appendChild(depInfo);
				}

				demo.appendChild(pInfo);
				rootElement.appendChild(demo);
				rootElement.appendChild(app);
				rootElement.appendChild(hEnroll);

				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT,"yes");
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
				source = new DOMSource(doc);

				StreamResult result = new StreamResult(new File("Test.xml"));
				transformer.transform(source, result);

				StreamResult consoleResult = new StreamResult(System.out);
				transformer.transform(source,  consoleResult);

			} catch (TransformerConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return superRoot;
	}

}

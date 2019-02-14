package testing;

import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.util.Vector;

public class TransactionBuilder {
	private final static String employerID = "7111270643";

	public static void makeTransaction(Vector<Transaction> transactions, SOAPMessage message){

		SOAPBodyElement bodyRoot = null;
		SOAPEnvelope envelope = null;
		SOAPBody body = null;

		try {
			//Make the document
			envelope = message.getSOAPPart().getEnvelope();
			body = envelope.getBody();

			Name n1 = envelope.createName("RetirementHealthEnrollment", "n1", 
					"http://calpers.ca.gov/PSR/RetirementHealthTransactionsV1");
			bodyRoot = body.addBodyElement(n1);

			bodyRoot.addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");
			bodyRoot.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:schemaLocation", 
					"http://schemas.xmlsoap.org/soap/envelope/SoapEnvelope.xsd");
			bodyRoot.addNamespaceDeclaration("cuns", "http://calpers.ca.gov/PSR/CommonUtilitiesV1");
	
		} catch (SOAPException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.exit(1);
		}
//		bodyRoot.setAttribute("schemaLocation", 
//				"http://calpers.ca.gov/PSR/RetirementHealthTransactionsV1 RetirementHealthTransactionsV1");

		for(Transaction t: transactions){
			Person emp = t.getEmployee();
			try {
				//build the root
				SOAPElement rootElement = bodyRoot.addChildElement("Transaction");
				//				rootElement = doc.createElement("Transaction");
				//				doc.appendChild(rootElement);

				//transaction type
				rootElement.addChildElement("TransactionType").addTextNode(t.getTransactionType());
				//				Element tType = doc.createElement("TransactionType");
				//				tType.appendChild(doc.createTextNode(t.getTransactionType()));
				//				rootElement.appendChild(tType);

				//transaction id
				rootElement.addChildElement("UniqueTransactionId").addTextNode(t.getUniqueTransactionId());
				//				Element tID = doc.createElement("UniqueTransactionId");
				//				tID.appendChild(doc.createTextNode(t.getUniqueTransactionId()));
				//				rootElement.appendChild(tID);

				//Demo graphics area
				SOAPElement demo = rootElement.addChildElement("Demographics");
				SOAPElement pInfo = demo.addChildElement("PersonInfo", "n1");
				pInfo.addChildElement("SSN", "cuns").addTextNode(emp.getSSN());


				if(emp.getPersonIdType() != null){
					pInfo.addChildElement("PersonIdType").addTextNode(emp.getPersonIdType());
					//					Element pIDtype = doc.createElement("PersonIdType");
					//					pIDtype.appendChild(doc.createTextNode((emp.getPersonIdType())));
					//					pInfo.appendChild(pIDtype);
				}


				if(emp.getLastName() != null){
					pInfo.addChildElement("LastName").addTextNode(emp.getLastName());
					//					Element lName = doc.createElement("LastName");
					//					lName.appendChild(doc.createTextNode(emp.getLastName()));
					//					pInfo.appendChild(lName);
				}

				if(emp.getFirstName() != null){
					pInfo.addChildElement("FirstName").addTextNode(emp.getFirstName());
					//					Element fName = doc.createElement("FirstName");
					//					fName.appendChild(doc.createTextNode(emp.getFirstName()));
					//					pInfo.appendChild(fName);
				}


				if(emp.getBirthDate() != null){
					pInfo.addChildElement("BirthDate").addTextNode(emp.getBirthDate());
					//					Element bDay = doc.createElement("BirthDate");
					//					pInfo.appendChild(bDay);
					//					bDay.appendChild(doc.createTextNode(emp.getBirthDate()));
				}

				if(emp.getGender() != null){
					pInfo.addChildElement("Gender").addTextNode(emp.getGender());
					//					Element gender = doc.createElement("Gender");
					//					pInfo.appendChild(gender);
					//					gender.appendChild(doc.createTextNode((emp.getGender())));
				}

				demo.addChildElement("UseAddressForHealth").addTextNode("true");

				//add address to Demographics
				//Does any address info exist?
				if(emp.addressExists()){
					SOAPElement aInfo = demo.addChildElement("AddressInfo", "n1");
					//					Element aInfo = doc.createElement("AddressInfo");
					//					aInfo.setPrefix("n1");
					//read in up to 3 address lines
					String[] empAdds = emp.getAddresses();

					for(int i = 0, len = empAdds.length; i < len; i++){
						if(empAdds[i] != null && !empAdds[i].equals("") && !empAdds[i].equals(" ")){
							aInfo.addChildElement("AddressLine", "cuns").addTextNode(empAdds[i]);
							//							Element aLine = doc.createElement("AddressLine");
							//							aLine.setPrefix("cuns");
							//							aLine.appendChild(doc.createTextNode(empAdds[i]));
							//							aInfo.appendChild(aLine);
						}
					}

					if(emp.getAddressType() != null){
						aInfo.addChildElement("AddressType", "cuns").addTextNode(emp.getAddressType());
						//						Element aType = doc.createElement("AddressType");
						//						aType.setPrefix("cuns");
						//						aType.appendChild(doc.createTextNode(emp.getAddressType()));
						//						aInfo.appendChild(aType);
					}

					//load city, state, zip
					if(emp.getZip5() != null){
						aInfo.addChildElement("ZipCode5", "cuns").addTextNode(emp.getZip5());
						//						Element zip5 = doc.createElement("ZipCode5");
						//						zip5.setPrefix("cuns");
						//						zip5.appendChild(doc.createTextNode(emp.getZip5()));
						//						aInfo.appendChild(zip5);
					}

					if(emp.getCity() != null){
						aInfo.addChildElement("City", "cuns").addTextNode(emp.getCity());
						//						Element city = doc.createElement("City");
						//						city.setPrefix("cuns");
						//						city.appendChild(doc.createTextNode(emp.getCity()));
						//						aInfo.appendChild(city);
					}

					if(emp.getState() != null){
						aInfo.addChildElement("State", "cuns").addTextNode(emp.getState());
						//						Element state = doc.createElement("State");
						//						state.setPrefix("cuns");
						//						state.appendChild(doc.createTextNode(emp.getState()));
						//						aInfo.appendChild(state);
					}

					aInfo.addChildElement("Country", "cuns").addTextNode("US");
					//					Element country = doc.createElement("Country");
					//					country.setPrefix("cuns");
					//					country.appendChild(doc.createTextNode("US"));
					//					aInfo.appendChild(country);
					//					//write the address info to the demo element
					//					demo.appendChild(aInfo);

				}//end address exists

				rootElement.addChildElement("Appointment")//<Appointment>
						.addChildElement("EmployerInfo")						//<EmployerInfo>
						.addChildElement("EmployerCalPERSId")						//<EmployerCalPERSId>
						.addTextNode(employerID);

				//				Element app = doc.createElement("Appointment");
				//				Element erInfo = doc.createElement("EmployerInfo");
				//				Element erID = doc.createElement("EmployerCalPERSId");
				//				//TODO: change employerID variable implementation from hard coded
				//				erID.appendChild(doc.createTextNode(employerID));
				//				erInfo.appendChild(erID);
				//				app.appendChild(erInfo);

				//start the health enrollment sub group
				SOAPElement hEnroll = rootElement.addChildElement("HealthEnrollment");
				hEnroll.addChildElement("HealthEventReason").addTextNode(t.getHealthEventReason());
				hEnroll.addChildElement("EventDate").addTextNode(t.getEventDate());
				hEnroll.addChildElement("ReceivedDate").addTextNode(t.getReceivedDate());
				hEnroll.addChildElement("MedicalGroup").addTextNode("045");

				//				Element hEnroll = doc.createElement("HealthEnrollment");

				//				Element heReason = doc.createElement("HealthEventReason");
				//				heReason.appendChild(doc.createTextNode(t.getHealthEventReason()));
				//				hEnroll.appendChild(heReason);

				//				Element eDate = doc.createElement("EventDate");
				//				eDate.appendChild(doc.createTextNode(t.getEventDate()));
				//				hEnroll.appendChild(eDate);
				//
				//				Element rDate = doc.createElement("ReceivedDate");
				//				rDate.appendChild(doc.createTextNode(t.getReceivedDate()));
				//				hEnroll.appendChild(rDate);
				//
				//				//TODO: change hard coding of medical group
				//				Element mGrp = doc.createElement("MedicalGroup");
				//				mGrp.appendChild(doc.createTextNode("045"));
				//				hEnroll.appendChild(mGrp);

				//Subscriber info in healthenrollment element
				SOAPElement subInfo = hEnroll.addChildElement("SubscriberInfo");

				//				Element subInfo = doc.createElement("SubscriberInfo");

				subInfo.addChildElement("ApplyChangeToMedicalPlan").addTextNode(t.getApplyChangeToMedicalPlan());
				//				Element chngMed = doc.createElement("ApplyChangeToMedicalPlan");
				//				chngMed.appendChild(doc.createTextNode(
				//						Boolean.toString(t.getApplyChangeToMedicalPlan())));
				//				subInfo.appendChild(chngMed);

				subInfo.addChildElement("ApplyChangeToDentalPlan").addTextNode(t.getApplyChangeToDentalPlan());
				//				Element chngDen = doc.createElement("ApplyChangeToDentalPlan");
				//				chngDen.appendChild(doc.createTextNode(
				//						Boolean.toString(t.getApplyChangeToDentalPlan())));
				//				subInfo.appendChild(chngDen);

				subInfo.addChildElement("ApplyChangeToVisionPlan").addTextNode(t.getApplyChangeToVisionPlan());
				//				Element chngVis = doc.createElement("ApplyChangeToVisionPlan");
				//				chngVis.appendChild(doc.createTextNode(
				//						Boolean.toString(t.getApplyChangeToVisionPlan())));
				//				subInfo.appendChild(chngVis);

				subInfo.addChildElement("HealthEligibilityZipCode").addTextNode(emp.getHealthEligibilityZip());
				//				Element heZipCode = doc.createElement("HealthEligibilityZipCode");
				//				heZipCode.appendChild(doc.createTextNode(emp.getHealthEligibilityZip()));
				//				subInfo.appendChild(heZipCode);

				//TODO: remove type hardcoding for zip type
				subInfo.addChildElement("HealthEligibilityZipCodeType").addTextNode("Personal");
				//				Element heZipCodeType = doc.createElement("HealthEligibilityZipCodeType");
				//				heZipCodeType.appendChild(doc.createTextNode("Personal"));
				//				subInfo.appendChild(heZipCodeType);

				subInfo.addChildElement("HealthEligibilityCounty").addTextNode(emp.getHealthEligibilityCounty());
				//				Element heCounty = doc.createElement("HealthEligibilityCounty");
				//				heCounty.appendChild(doc.createTextNode(emp.getHealthEligibilityCounty()));
				//				subInfo.appendChild(heCounty);

				//Add subscriber info to health enrollment
				//end subscriber add
				//				hEnroll.appendChild(subInfo);

				//start dependent info stuff
				Vector<Person> deps = t.getDependents();
				if(deps != null){
					for(Person depend: deps){
						SOAPElement depInfo = hEnroll.addChildElement("DependentInfo");
						SOAPElement depPersonInfo = depInfo.addChildElement("DependentPersonInfo", "n1");
						SOAPElement dpID = depPersonInfo.addChildElement("PersonId");
						dpID.addChildElement("SSN", "cuns").addTextNode(depend.getSSN());

						//					Element depInfo = doc.createElement("DependentInfo");
						//					Element depPersonInfo = doc.createElement("DependentPersonInfo");


						//if(depend.getSSN() != null){
						//Element depPID = doc.createElement("PersonId");
						//Element depSSN = doc.createElement("SSN");
						//						depSSN.setPrefix("cuns");
						//						depSSN.appendChild(doc.createTextNode(depend.getSSN()));
						//						depPID.appendChild(depSSN);
						//						depPersonInfo.appendChild(depPID);
						//}

						if(depend.getPersonIdType() != null){
							depPersonInfo.addChildElement("PersonIdType").addTextNode(depend.getPersonIdType());
							//						Element depPtype = doc.createElement("PersonIdType");
							//						depPtype.appendChild(doc.createTextNode(depend.getPersonIdType()));
							//						depPersonInfo.appendChild(depPtype);
						}

						if(depend.getLastName() != null){
							depPersonInfo.addChildElement("LastName").addTextNode(depend.getLastName());
							//						Element depLast = doc.createElement("LastName");
							//						depLast.appendChild(doc.createTextNode(depend.getLastName()));
							//						depPersonInfo.appendChild(depLast);
						}

						if(depend.getFirstName() != null){
							depPersonInfo.addChildElement("FirstName").addTextNode(depend.getFirstName());
							//						Element depFirst = doc.createElement("FirstName");
							//						depFirst.appendChild(doc.createTextNode(depend.getFirstName()));
							//						depPersonInfo.appendChild(depFirst);
						}

						if(depend.getBirthDate() != null){
							depPersonInfo.addChildElement("BirthDate").addTextNode(depend.getBirthDate());
							//						Element depBday = doc.createElement("BirthDate");
							//						depBday.appendChild(doc.createTextNode(depend.getBirthDate()));
							//						depPersonInfo.appendChild(depBday);
						}

						if(depend.getGender() != null){
							depPersonInfo.addChildElement("Gender").addTextNode(depend.getGender());
							//						Element depGen = doc.createElement("Gender");
							//						depGen.appendChild(doc.createTextNode(depend.getGender()));
							//						depPersonInfo.appendChild(depGen);
						}

						//write DependentPersonInfo to DependentInfo
						//					depInfo.appendChild(depPersonInfo);

						if(depend.getRelationship() != null){
							depInfo.addChildElement("Relationship").addTextNode(depend.getRelationship());
							//						Element depRel = doc.createElement("Relationship");
							//						depRel.appendChild(doc.createTextNode(depend.getRelationship()));
							//						depInfo.appendChild(depRel);
						}

						depInfo.addChildElement("AddressSameAsPrimarySubscriber")
						.addTextNode(Boolean.toString(depend.isAddressSameAsPrimarySubscriber()));
						//					Element aSame = doc.createElement("AddressSameAsPrimarySubscriber");
						//					aSame.appendChild(doc.createTextNode(
						//							Boolean.toString(depend.isAddressSameAsPrimarySubscriber())));
						//					depInfo.appendChild(aSame);

						if(depend.getDependentType() != null){
							depInfo.addChildElement("DependentType").addTextNode(depend.getDependentType());
							//						Element depType = doc.createElement("DependentType");
							//						depType.appendChild(doc.createTextNode(depend.getDependentType()));
							//						depInfo.appendChild(depType);
						}

						//The various ApplyToFields are set to false in the constructor and this cannot be null.
						//Skip the null check. Hey what could possible go wrong?
						depInfo.addChildElement("ApplyToMedical").addTextNode(t.getApplyChangeToMedicalPlan());
						depInfo.addChildElement("ApplyToDental").addTextNode(t.getApplyChangeToDentalPlan());
						depInfo.addChildElement("ApplyToVision").addTextNode(t.getApplyChangeToVisionPlan());
						//					Element appMed = doc.createElement("ApplyToMedical");
						//					appMed.appendChild(doc.createTextNode(
						//							Boolean.toString(depend.isApplyToMedical())));
						//					depInfo.appendChild(appMed);

						//depInfo.addChildElement("EconomicallyConfirmIndicator").addTextNode("true");
						//					Element depEcon = doc.createElement("EconomicallyConfirmIndicator");
						//					depEcon.appendChild(doc.createTextNode(
						//							Boolean.toString(depend.isEconomicallyConfirmIndicator())));
						//					depInfo.appendChild(depEcon);

						//					if(depend.getDependentAcquiredDate() != null){
						//						Element depAdate = doc.createElement("DependentAcquiredDate");
						//						depAdate.appendChild(doc.createTextNode(depend.getDependentAcquiredDate()));
						//						depInfo.appendChild(depAdate);
						//					}

						//write DependentInfo to Transaction
						//hEnroll.appendChild(depInfo);
					}
				}
				//				demo.appendChild(pInfo);
				//				rootElement.appendChild(demo);
				//				rootElement.appendChild(app);
				//				rootElement.appendChild(hEnroll);


			} catch (SOAPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			SOAPFrame.printEnvelope(message.getSOAPPart().getEnvelope());
		} catch (SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

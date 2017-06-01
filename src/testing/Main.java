package testing;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.xml.soap.*;

import org.apache.commons.lang3.RandomStringUtils;
import org.w3c.dom.Element;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class Main {

	public static String SMMUSDtoken = "f4c6oegvnsme9uly19o9tvnjoql4mqgq";
	public static String CPEMPID = "1234567890";
	public static String STARTDATE = "07/01/2017";
	public static String MEDGRP = "010";
	public static SOAPMessage message;

	public static void main(String[] args) {

//		User smmAdmn = new User("testAdmin", "Password1!", "SMMUSD");
//		APIClass smmusd = new APIClass("SMMUSD", SMMUSDtoken, smmAdmn);
//		//smmusd.setToken();
//		System.out.println(smmusd.getToken());
//		String reportText = smmusd.getExportData("84000065");
		Vector<Person> emps = new Vector<>();
		Map<String, Vector<Person>> deps = new HashMap<>();
		String recvDate = LocalDateTime.now().toString().substring(0,10);
		Vector<Transaction> transactions = new Vector<Transaction>();
		message = SOAPFrame.buildFrame();
		
		try {

			SOAPBody body = message.getSOAPPart().getEnvelope().getBody();
//			PrintWriter writer = new PrintWriter("report.csv", "UTF-8");
//			writer.println(reportText);
//			writer.close();

			String headers = "";
//			Reader strReader = new StringReader(reportText.trim());
			Reader in = new FileReader("report.csv");
			Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(in);


			int cnt = 0;
			for(CSVRecord rec: records){
				System.out.println("Starting with record " + cnt++);
				//easier to always use ssn
				String ssn = rec.get("Member SSN");
				String empSSN = rec.get("Employee SSN");

				String fName = rec.get("First Name of Member");
				String lName = rec.get("Last Name of Member");
				String mName = rec.get("Middle Initial of Member");

				String planCode = rec.get("Plan Code");
				String bDay = rec.get("Members Date of Birth");
				String gender = rec.get("Members Gender");
				String hireDate = rec.get("Hire Date"); 
				String hZip = rec.get("Member Zip");
				String appID = rec.get("AppointmentID");

				String addresses[] = new String[3];
				addresses[0] = rec.get("Member Address 1");
				addresses[1] = rec.get("Member Address 2");
				addresses[2] = rec.get("Member Address 3");

				String city = rec.get("Member City");
				String state = rec.get("Member State");
				String zip5 = rec.get("Member Zip");

				//load in phone numbers
				Phone phones[] = new Phone[3];
				if(rec.isSet("Member Home Phone")){
					phones[0] = new Phone(rec.get("Member Home Phone"), "Home");
				}

				if(rec.isSet("Member Phone Mobile")){
					phones[1] = new Phone(rec.get("Member Phone Mobile"), "Cell");
				}

				if(rec.isSet("Member Home Phone")){
					phones[2] = new Phone(rec.get("Member Phone Work"), "Work");
				}

				String email = rec.get("Member Email");

				//start building person
				Person p = null;
				String relText = rec.get("Relationship Code");
				String relCode = relText;

				if(!relText.equalsIgnoreCase("Self")){//for dependents
					relCode = Person.fetchRelCode(relCode);
					p = new Person.Builder(fName, lName, "SSN", ssn)
							.addresses(addresses)
							.addressSame(true)
							.addressType("PHY")
							.middleName(mName)
							.birthDate(bDay)
							.gender(gender.charAt(0))
							.relationship(relCode)
							.addressSame(true)
							.applyToMedical(true)
							.economicallyConfirmIndicator(true)
							.dependentType(Person.fetchDepType(relText))
							.healthEligibilityZip(zip5)
							.zip5(zip5)
							//.zip4(zip4)
							.city(city)
							.state(state)
							.phoneNumber1(phones[0])
							.phoneNumber2(phones[1])
							.phoneNumber3(phones[2])
							.healthEligibilityCounty("US")
							.build();

					//if a vector does not already exist for the employee, then add a new, empty vector
					if(!deps.containsKey(empSSN)){
						deps.put(empSSN,new Vector<Person>());
					}
					//add the person to the map's object vector
					deps.get(empSSN).add(p);
				} else { //for employees
					p = new Person.Builder(fName, lName, "SSN", ssn)
							.addresses(addresses)
							.addressSame(true)
							.addressType("PHY")
							.middleName(mName)
							.birthDate(bDay)
							.gender(gender.charAt(0))
							.relationship(relCode)//not needed
							.applyToMedical(true)
							.healthEligibilityZip(zip5)
							.zip5(zip5)
							//.zip4(zip4)
							.city(city)
							.state(state)
							.phoneNumber1(phones[0])
							.phoneNumber2(phones[1])
							.phoneNumber3(phones[2])
							.healthEligibilityCounty("US")
							.hiredDate(hireDate)
							.build();

					//add to the list of employees
					emps.add(p);
				}
			}
			System.out.println("Loaded " + cnt + " records.");

			for(Person ePerson: emps){
				String tID = RandomStringUtils.random(36, false, true);
				Transaction t = new Transaction("OEN", tID, ePerson);
				t.setAppointmentID(ePerson.getAppointmentID());
				t.setEmployerCalPERSId(CPEMPID);
				t.setHealthEventReason("400");
				t.setEventDate(STARTDATE);
				t.setReceivedDate(recvDate);
				t.setApplyChangeToMedicalPlan(true);
				t.setMedicalGroup(MEDGRP);
				t.setMedicalPlanSelection(ePerson.getPlanCode());
				transactions.add(new Transaction(t));
			}
			
			TransactionBuilder.makeTransaction(transactions, message);
		} catch (IOException | SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("exiting...");
		System.exit(0);
	}
}

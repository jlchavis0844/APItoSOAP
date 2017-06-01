package testing;

public class Person {
	private String SSN;
	private String personIdType;
	private String firstName;
	private String middleName;
	private String lastName;
	private String birthDate;
	private String gender;
	private String relationship;
	private boolean addressSameAsPrimarySubscriber;
	private boolean applyToMedical;
	private boolean economicallyConfirmIndicator;
	private String dependentAcquiredDate;
	private String receivedDate;
	private String dependentType;
	private String calPERSid;
	private String hireDate;
	private String retirementSystem;
	private String addressType;
	private String healthEligibilityZip;
	private String country = "US";
	private String[] addresses;
	private String city;
	private String	state;
	private String zip5;
	private String zip4;
	private Phone[] pNumbers;
	private String email;
	private String planCode;
	private String permanentSparationDate;
	private String retirementDate;
	private String healthEligibilityCounty;
	private String appointmentID;

	private Person(Builder b){
		this.SSN = b.SSN;
		this.personIdType = b.personIdType;
		this.firstName = b.firstName;
		this.lastName = b.lastName;
		this.birthDate = b.birthDate;
		this.gender = b.gender;
		this.relationship = b.relationship;
		this.addressSameAsPrimarySubscriber = b.addressSameAsPrimarySubscriber;
		this.dependentType = b.dependentType;
		this.applyToMedical = b.applyToMedical;
		this.economicallyConfirmIndicator = b.economicallyConfirmIndicator;
		this.dependentAcquiredDate = b.dependentAcquiredDate;
		this.receivedDate = b.receivedDate;
		this.calPERSid = b.calPERSid;
		this.hireDate = b.hireDate;
		this.retirementSystem = b.retirementSystem;
		this.middleName = b.middleName;
		this.addressType = b.addressType;
		this.healthEligibilityZip = b.healthEligibilityZip;
		this.addresses = b.addresses;
		this.city = b.city;
		this.state = b.state;
		this.zip5 = b.zip5;
		this.zip4 = b.zip4;
		this.pNumbers = b.pNumbers;
		this.email = b.email;
		this.permanentSparationDate = b.permanentSparationDate;
		this.retirementDate = b.retirementDate;
		this.healthEligibilityCounty = b.healthEligibilityCounty;
		this.appointmentID = b.appointmentID;
		this.planCode = b.planCode;
	}

	public static class Builder{
		private String SSN;
		private String personIdType;
		private String firstName;
		private String middleName;
		private String lastName;
		private String birthDate;
		private String gender;
		private String relationship;
		private boolean addressSameAsPrimarySubscriber;
		private String dependentType;
		private boolean applyToMedical;
		private boolean economicallyConfirmIndicator;
		private String dependentAcquiredDate;
		private String receivedDate;
		private String calPERSid;
		private String hireDate;
		private String retirementSystem;
		private String addressType;
		private String healthEligibilityZip;
		private String[] addresses;
		private String city;
		private String	state;
		private String zip5;
		private String zip4;
		private Phone[] pNumbers;
		private String email;
		private String permanentSparationDate;
		private String retirementDate;
		private String healthEligibilityCounty;
		private String appointmentID;
		private String planCode;
		
		/**
		 * Builds the minimum person, see builder options for additional fields
		 * @param first First name
		 * @param last Last Name
		 * @param personIdType PID (calPERS) or SSN
		 * @param pID the ID String
		 */
		public Builder(String first, String last, String personIdType, String pID){
			this.firstName = first;
			this.lastName = last;
			this.personIdType = personIdType;
			this.pNumbers = new Phone[3];

			if(personIdType.equalsIgnoreCase("PID")){
				calPERSid = pID;
			} else {
				SSN = pID;
			}
		}

		public Person build(){
			return new Person(this);
		}

		//		public Builder calPERSid(String id){
		//			this.calPERSid = id;
		//			return this;
		//		}

		public Builder planCode(String planCode){
			this.planCode = planCode;
			return this;
		}
		
		public Builder appointmentID(String appointmentID){
			this.appointmentID = appointmentID;
			return this;
		}
		
		public Builder healthEligibilityCounty(String county){
			this.healthEligibilityCounty = county;
			return this;
		}
		
		public Builder healthEligibilityZip(String zip){
			this.healthEligibilityZip = zip;
			return this;
		}
		
		public Builder retirementDate(String date){
			this.retirementDate = date;
			return this;
		}		
		
		public Builder permanentSparationDate(String date){
			this.permanentSparationDate = date;
			return this;
		}
		
		public Builder email(String email){
			this.email = email;
			return this;
		}
		
		public Builder phoneNumber1(Phone phone){
			pNumbers[0] = phone;
			return this;
		}
		
		public Builder phoneNumber2(Phone phone){
			pNumbers[1] = phone;
			return this;
		}
		
		public Builder phoneNumber3(Phone phone){
			pNumbers[2] = phone;
			return this;
		}
		
		public Builder city(String city){
			this.city = city;
			return this;
		}
		
		
		public Builder state(String state){
			this.state = state;
			return this;
		}
		
		
		public Builder zip5(String zip5){
			this.zip5 = zip5;
			return this;
		}
		
		public Builder zip4(String zip4){
			this.zip4 = zip4;
			return this;
		}
		
		public Builder addresses(String addresses[]){
			this.addresses = addresses;
			return this;
		}
		
		public Builder retirementSystem(String retirementSystem) {
			if(!"STR MRS OTH".contains(retirementSystem)){
				System.err.println("Retirement System must user these codes: STR, MRS, or OTH");
				return this;
			} else {
				this.retirementSystem = retirementSystem;
				return this;
			}
		}
		
		public Builder addressType(String type){
			if("MAI PHY".contains(type)){
				this.addressType = type;
				return this;
			} else {
				System.err.println(type + " is an invalid address type, must be MAI or PHY");
				return this;
			}
		}
		
		public Builder middleName(String middleName){
			this.middleName = middleName;
			return this;
		}
		
		public Builder hiredDate(String hireDate){
			this.hireDate = hireDate;
			return this;
		}
		
		public Builder dependentAcquiredDate(String dependentAcquiredDate){
			this.dependentAcquiredDate = dependentAcquiredDate;
			return this;
		}

		public Builder economicallyConfirmIndicator(boolean eci){
			this.economicallyConfirmIndicator = eci;
			return this;
		}

		public Builder applyToMedical(boolean applyToMedical){
			this.applyToMedical = applyToMedical;
			return this;
		}


		public Builder dependentType(String dependentType){
			this.dependentType = dependentType;
			return this;
		}

		public Builder birthDate(String birthDate){
			this.birthDate = birthDate;
			return this;
		}

		public Builder gender(char gender){
			this.gender = String.valueOf(gender);
			if(!"F M U".contains(this.gender))
				System.err.println("WARNING: This is not a valid gender: " + this.gender);
			return this;
		}

		public Builder relationship(String relationship){
			this.relationship = relationship;
			return this;
		}

		public Builder addressSame(boolean addressSame){
			this.addressSameAsPrimarySubscriber = addressSame;
			return this;
		}

	}

	/**
	 * Returns whether some address data exists for this Person
	 * @return boolean
	 */
	public boolean addressExists(){
		return (addresses[0] != null || city != null ||
				state != null || zip5 != null);
	}
	
	
	/**
	 * @return the pNumbers
	 */
	public Phone[] getpNumbers() {
		return pNumbers;
	}


	/**
	 * @param pNumbers the pNumbers to set
	 */
	public void setpNumbers(Phone[] pNumbers) {
		this.pNumbers = pNumbers;
	}


	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}


	/**
	 * @return the permanentSparationDate
	 */
	public String getPermanentSparationDate() {
		return permanentSparationDate;
	}


	/**
	 * @param permanentSparationDate the permanentSparationDate to set
	 */
	public void setPermanentSparationDate(String permanentSparationDate) {
		this.permanentSparationDate = permanentSparationDate;
	}


	/**
	 * @return the retirementDate
	 */
	public String getRetirementDate() {
		return retirementDate;
	}


	/**
	 * @param retirementDate the retirementDate to set
	 */
	public void setRetirementDate(String retirementDate) {
		this.retirementDate = retirementDate;
	}


	/**
	 * @return the healthEligibilityCounty
	 */
	public String getHealthEligibilityCounty() {
		return healthEligibilityCounty;
	}


	/**
	 * @param healthEligibilityCounty the healthEligibilityCounty to set
	 */
	public void setHealthEligibilityCounty(String healthEligibilityCounty) {
		this.healthEligibilityCounty = healthEligibilityCounty;
	}


	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * @return the hireDate
	 */
	public String getHireDate() {
		return hireDate;
	}

	/**
	 * @param hireDate the hireDate to set
	 */
	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}

	/**
	 * @return the retirementSystem
	 */
	public String getRetirementSystem() {
		return retirementSystem;
	}

	/**
	 * @param retirementSystem the retirementSystem to set
	 */
	public void setRetirementSystem(String retirementSystem) {
		this.retirementSystem = retirementSystem;
	}

	/**
	 * @return the addressType
	 */
	public String getAddressType() {
		return addressType;
	}

	/**
	 * @param addressType the addressType to set
	 */
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	/**
	 * @return the healthEligibilityZip
	 */
	public String getHealthEligibilityZip() {
		return healthEligibilityZip;
	}

	/**
	 * @param healthEligibilityZip the healthEligibilityZip to set
	 */
	public void setHealthEligibilityZip(String healthEligibilityZip) {
		this.healthEligibilityZip = healthEligibilityZip;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the addresses
	 */
	public String[] getAddresses() {
		return addresses;
	}

	/**
	 * @param addresses the addresses to set
	 */
	public void setAddresses(String[] addresses) {
		this.addresses = addresses;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the zip5
	 */
	public String getZip5() {
		return zip5;
	}

	/**
	 * @param zip5 the zip5 to set
	 */
	public void setZip5(String zip5) {
		this.zip5 = zip5;
	}

	/**
	 * @return the zip4
	 */
	public String getZip4() {
		return zip4;
	}

	/**
	 * @param zip4 the zip4 to set
	 */
	public void setZip4(String zip4) {
		this.zip4 = zip4;
	}
	
	/**
	 * @return the calPERSid
	 */
	public String getCalPERSid() {
		return calPERSid;
	}

	/**
	 * @param calPERSid the calPERSid to set
	 */
	public void setCalPERSid(String calPERSid) {
		this.calPERSid = calPERSid;
	}

	public String getSSN() {
		return SSN;
	}

	public void setSSN(String ssn) {
		SSN = ssn;
	}

	public String getPersonIdType() {
		return personIdType;
	}

	public void setPersonIdType(String personIdType) {
		this.personIdType = personIdType;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		if(!"F M U".contains(gender)){
			System.err.println("WARNING: This is not a valid gender");
		}
		this.gender = gender;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public boolean isAddressSameAsPrimarySubscriber() {
		return addressSameAsPrimarySubscriber;
	}

	public void setAddressSameAsPrimarySubscriber(boolean addressSameAsPrimarySubscriber) {
		this.addressSameAsPrimarySubscriber = addressSameAsPrimarySubscriber;
	}

	public String getDependentType() {
		return dependentType;
	}

	public void setDependentType(String dependentType) {
		this.dependentType = dependentType;
	}

	public boolean isApplyToMedical() {
		return applyToMedical;
	}

	public void setApplyToMedical(boolean applyToMedical) {
		this.applyToMedical = applyToMedical;
	}

	public boolean isEconomicallyConfirmIndicator() {
		return economicallyConfirmIndicator;
	}

	public void setEconomicallyConfirmIndicator(boolean economicallyConfirmIndicator) {
		this.economicallyConfirmIndicator = economicallyConfirmIndicator;
	}

	public String getDependentAcquiredDate() {
		return dependentAcquiredDate;
	}

	public void setDependentAcquiredDate(String dependentAcquiredDate) {
		this.dependentAcquiredDate = dependentAcquiredDate;
	}

	/**
	 * @return the receivedDate
	 */
	public String getReceivedDate() {
		return receivedDate;
	}

	/**
	 * @param receivedDate the receivedDate to set
	 */
	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
	}
	
	/**
	 * @return the planCode
	 */
	public String getPlanCode() {
		return planCode;
	}


	/**
	 * @param planCode the planCode to set
	 */
	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	/**
	 * @return the appointmentID
	 */
	public String getAppointmentID() {
		return appointmentID;
	}


	/**
	 * @param appointmentID the appointmentID to set
	 */
	public void setAppointmentID(String appointmentID) {
		this.appointmentID = appointmentID;
	}
	

	public String toString(){
		String temp = "First:\t" + firstName + "\n";
		temp += "Last:\t" + lastName + "\n";
		temp += "SSN:\t" + SSN + "\n";
		temp += "ID:\t" + personIdType + "\n";
		temp += "birthDate:\t" + birthDate + "\n";
		temp += "gender:\t" + gender + "\n";
		temp += "relationship:\t" + relationship + "\n";
		temp += "addressSameAsPrimarySubscriber:\t" + addressSameAsPrimarySubscriber + "\n";
		temp += "dependentType:\t" + dependentType + "\n";
		temp += "applyToMedical:\t" + applyToMedical + "\n";
		temp += "economicallyConfirmIndicator:\t" + economicallyConfirmIndicator + "\n";
		temp += "dependentAcquiredDate:\t" + dependentAcquiredDate + "\n";
		return temp;
	}

	/**
	 * Static method to return the correct dependent relationship code given plain text
	 * @param oldCode Kronos code
	 * @return 2 or 3 char relationship code
	 */
	public static String fetchRelCode(String oldCode){
		String returnTxt = "";

		switch(oldCode){
		case "Spouse":
			returnTxt = "SPO";
			break;
		
		case "Domestic Partner":
			returnTxt = "DP";
			break;
		case "Brother":
			returnTxt = "BRO";
			break;
		case "Sister":
			returnTxt = "SIS";
			break;
		case "Niece":
			returnTxt = "NIE";
			break;
		case "Nephew":
			returnTxt = "NEP";
			break;
		case "Grandchild":
			returnTxt = "GC";
			break;
		case "Child":
			returnTxt = "CHI";
			break;
		case "Child - Adopted":
			returnTxt = "CHI";
			break;
		case "Step Child":
			returnTxt = "SC";
			break;
		case "Domestic Partner Child":
			returnTxt = "DPC";
			break;
		case "Step Grandchild":
			returnTxt = "SG";
			break;
		case "Great Grandchild":
			returnTxt = "GG";
			break;
		case "Cousin":
			returnTxt = "COU";
			break;
		case "Other Person":
			returnTxt = "OP";
			break;
		case "Adopted Child":
			returnTxt = "ADC";
			break;
		default:
			break;
		}
		return returnTxt;
	}
	
	public static String fetchDepType(String name){
		String returnTxt = "";

		switch(name){
		case "Child":
			returnTxt = "DBC";
			break;
		case "Child Adopted":
			returnTxt = "DAC";
			break;
		case "Economically Dependent Child":
			returnTxt = "EDC";
			break;
		case "Spouse":
			returnTxt = "SPO";
			break;
		case "Step Child":
			returnTxt = "STC";
			break;
		case "Domestic Partner":
			returnTxt = "DP";
			break;
		case "Domestic Partner Child":
			returnTxt = "DPC";
			break;
		case "Sibling":
			returnTxt = "SIB";
			break;
		default:
			break;
		}
		return returnTxt;
	}
}

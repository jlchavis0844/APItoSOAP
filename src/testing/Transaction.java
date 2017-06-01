package testing;

import java.util.Vector;

public class Transaction {
	private String transactionType;
	private String uniqueTransactionId;
	private String employerCalPERSId;
	private String healthEventReason;
	private String eventDate; //not needed for OE
	private String receivedDate;
	private boolean applyChangeToMedicalPlan;//Indicates that the change/enrollment applies to the Medical benefit
	private boolean applyChangeToDentalPlan;
	private boolean applyChangeToVisionPlan;
	private String medicalPlanSelection;
	private String medicalGroup;
	private Person employee;
	private Vector<Person> dependents;

	public Transaction(Transaction rhs){
		this.transactionType = rhs.transactionType;
		this.uniqueTransactionId = rhs.uniqueTransactionId;
		this.employerCalPERSId = rhs.employerCalPERSId;
		this.healthEventReason = rhs.healthEventReason;
		this.eventDate = rhs.eventDate;
		this.receivedDate = rhs.receivedDate;
		this.applyChangeToMedicalPlan = rhs.applyChangeToMedicalPlan;
		this.applyChangeToDentalPlan = rhs.applyChangeToDentalPlan;
		this.applyChangeToVisionPlan = rhs.applyChangeToVisionPlan;
		this.medicalPlanSelection = rhs.medicalPlanSelection;
		this.medicalGroup = rhs.medicalGroup;
		this.employee = rhs.employee;
	}
	
	/*
	 *  OE Cancel Coverage	530
		OE Enroll < half time Emp New Enrollment	170
		Open Enrollment Add Dep	206
		Open Enrollment Change Health Plan	400
		Open Enrollment Delete Dependent	320
		Open Enrollment Employees New Enrollment	104
	 */

	/**
	 * @return the medicalPlanSelection
	 */
	public String getMedicalPlanSelection() {
		return medicalPlanSelection;
	}

	/**
	 * @return the medicalGroup
	 */
	public String getMedicalGroup() {
		return medicalGroup;
	}

	/**
	 * @param medicalGroup the medicalGroup to set
	 */
	public void setMedicalGroup(String medicalGroup) {
		this.medicalGroup = medicalGroup;
	}

	/**
	 * @param medicalPlanSelection the medicalPlanSelection to set
	 */
	public void setMedicalPlanSelection(String medicalPlanSelection) {
		this.medicalPlanSelection = medicalPlanSelection;
	}

	/**
	 * @return the rescindIndicator
	 */
	public boolean isRescindIndicator() {
		return rescindIndicator;
	}

	/**
	 * @param rescindIndicator the rescindIndicator to set
	 */
	public void setRescindIndicator(boolean rescindIndicator) {
		this.rescindIndicator = rescindIndicator;
	}

	/**
	 * @return the rescindReason
	 */
	public boolean isRescindReason() {
		return rescindReason;
	}

	/**
	 * @param rescindReason the rescindReason to set
	 */
	public void setRescindReason(boolean rescindReason) {
		this.rescindReason = rescindReason;
	}

	/**
	 * @return the rescindNotes
	 */
	public String getRescindNotes() {
		return rescindNotes;
	}

	/**
	 * @param rescindNotes the rescindNotes to set
	 */
	public void setRescindNotes(String rescindNotes) {
		this.rescindNotes = rescindNotes;
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

	//Indicates whether a health enrollment transaction, with a future date, should be rescinded
	private boolean rescindIndicator;
	//Provides the reason why a health enrollment transaction is rescinded
	private boolean rescindReason;
	
	private String rescindNotes;
	
	//Prior to system implementation, CalPERS will provide employers with a 
	//list of Appointment IDs for their employees. After system implementation, 
	//employers can run a report online to generate a list of Appointment IDs.
	
	private String appointmentID;
	
	
	/**
	 * @return the applyChangeToVisionPlan
	 */
	public String getApplyChangeToVisionPlan() {
		return Boolean.toString(applyChangeToVisionPlan);
	}

	/**
	 * @param applyChangeToVisionPlan the applyChangeToVisionPlan to set
	 */
	public void setApplyChangeToVisionPlan(boolean applyChangeToVisionPlan) {
		this.applyChangeToVisionPlan = applyChangeToVisionPlan;
	}

	public Transaction(String type, String id, Person emp){
		dependents = new Vector<>();
		transactionType = type;
		uniqueTransactionId = id;
		employee = emp;
	}
	
	public boolean addDependent(Person dep){
		return dependents.add(dep);
	}

	/**
	 * @return the employee
	 */
	public Person getEmployee() {
		return employee;
	}

	/**
	 * @param employee the employee to set
	 */
	public void setEmployee(Person employee) {
		this.employee = employee;
	}

	/**
	 * @return the dependents
	 */
	public Vector<Person> getDependents() {
		return dependents;
	}

	/**
	 * @param dependents the dependents to set
	 */
	public void setDependents(Vector<Person> dependents) {
		this.dependents = dependents;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getUniqueTransactionId() {
		return uniqueTransactionId;
	}

	public void setUniqueTransactionId(String uniqueTransactionId) {
		this.uniqueTransactionId = uniqueTransactionId;
	}

	public String getEmployerCalPERSId() {
		return employerCalPERSId;
	}

	public void setEmployerCalPERSId(String employerCalPERSId) {
		this.employerCalPERSId = employerCalPERSId;
	}

	public String getHealthEventReason() {
		return healthEventReason;
	}

	public void setHealthEventReason(String healthEventReason) {
		this.healthEventReason = healthEventReason;
	}

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	public String getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
	}

	public String getApplyChangeToMedicalPlan() {
		return Boolean.toString(applyChangeToMedicalPlan);
	}

	public void setApplyChangeToMedicalPlan(boolean applyChangeToMedicalPlan) {
		this.applyChangeToMedicalPlan = applyChangeToMedicalPlan;
	}

	public String getApplyChangeToDentalPlan() {
		return Boolean.toString(applyChangeToDentalPlan);
	}

	public void setApplyChangeToDentalPlan(boolean applyChangeToDentalPlan) {
		this.applyChangeToDentalPlan = applyChangeToDentalPlan;
	}
	
}

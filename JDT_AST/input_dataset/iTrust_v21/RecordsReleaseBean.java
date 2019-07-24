package edu.ncsu.csc.itrust.beans;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * A bean for storing information about releasing medical records.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters 
 * to create these easily)
 */
public class RecordsReleaseBean {
	
	private long releaseID = 0;
	private long pid;
	private String releaseHospitalID;
	private String recHospitalName;
	private String recHospitalAddress;
	private String docFirstName;
	private String docLastName;
	private String docPhone;
	private String docEmail;
	private String justification;
	private int status;
	private Timestamp dateRequested;
	
	/**
	 * Constructor for RecordsRelease bean. Creates a RecordsReleaseBean with null fields
	 */
	public RecordsReleaseBean() {	
	}
	
	/**
	 * Sets the releaseID of the release request
	 * @param releaseID the id of the release request as a long
	 */
	public void setReleaseID(long releaseID) {
		this.releaseID = releaseID;
	}
	
	/**
	 * Gets the release request id as a long
	 * @return the release request id
	 */ 
	public long getReleaseID() {
		return releaseID;
	}
	
	/**
	 * Sets the patient mid
	 * @param pid the patient's mid as a long
	 */
	public void setPid(long pid) {
		this.pid = pid;
	}
	
	/**
	 * Gets the patient's mid
	 * @return the patient's mid
	 */
	public long getPid() {
		return pid;
	}
	
	/**
	 * Sets the name of the release hospital for the records release
	 * @param hospital the hospital's name as a string
	 */
	public void setReleaseHospitalID(String hospitalID) {
		releaseHospitalID = hospitalID;
	}
	
	/**
	 * Gets the id of the hospital for which to release the patient's medical records
	 * @return the name of the hospital
	 */
	public String getReleaseHospitalID() {
		return releaseHospitalID;
	}
	
	/**
	 * Set the receiving hospital's name
	 * @param name the receiving hospital's name as a string
	 */
	public void setRecHospitalName(String name) {
		recHospitalName = name;
	}
	
	/**
	 * Get the receiving hospital's name
	 * @return the receiving hospital's name
	 */
	public String getRecHospitalName() {
		return recHospitalName;
	}
	
	/**
	 * Set the receiving hospital's address.
	 * @param address the receiving hospital's address
	 */
	public void setRecHospitalAddress(String address) {
		recHospitalAddress = address;
	}
	
	/**
	 * Get the receiving hospital's address
	 * @return the receiving hospital's address
	 */
	public String getRecHospitalAddress() {
		return recHospitalAddress;
	}
	
	/**
	 * Set the first name of the receiving doctor
	 * @param firstName the first name of the receiving doctor as a string
	 */
	public void setDocFirstName(String firstName) {
		docFirstName = firstName;
	}
	
	/**
	 * Gets the first name of the receiving doctor
	 * @return the receiving doctor's first name
	 */
	public String getDocFirstName() {
		return docFirstName;
	}
	
	/**
	 * Set the last name of the receiving doctor
	 * @param lastName the first name of the receiving doctor as a string
	 */
	public void setDocLastName(String lastName) {
		docLastName = lastName;
	}
	
	/**
	 * Get the receiving doctor's last name
	 * @return the doctor's last name
	 */
	public String getDocLastName() {
		return docLastName;
	}
	
	/**
	 * Set the receiving doctor's phone number
	 * @param phoneNumber the doctor's phone number as a string
	 */
	public void setDocPhone(String phoneNumber) {
		docPhone = phoneNumber;
	}
	
	/**
	 * Get the receiving doctor's phone number 
	 * @return the receiving doctor's phone number
	 */
	public String getDocPhone() {
		return docPhone;
	}
	
	/**
	 * Set the receiving doctor's email address
	 * @param email the receiving doctor's email address as a string
	 */
	public void setDocEmail(String email) {
		docEmail = email;
	}
	
	/**
	 * Get the receiving doctor's email address
	 * @return the receiving doctor's email address
	 */
	public String getDocEmail() {
		return docEmail;
	}
	
	/**
	 * Set the justification for the medical records release
	 * @param justification the justification as a string
	 */
	public void setJustification(String justification) {
		this.justification = justification;
	}
	
	/**
	 * Get the justification for the medical records release
	 * @return the justification for the medical records release
	 */
	public String getJustification() {
		return justification;
	}
	
	/**
	 * Sets the status of the medical records release request.
	 * 0 for pending. 1 for approved. 2 for denied.
	 * @param status the status as an integer. 0 for pending. 1 for approved. 2 for denied.
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	
	/**
	 * Gets the status of the medical records release request.
	 * 0 for pending. 1 for approved. 2 for denied.
	 * @return the status of the release request. 0 for pending. 1 for approved. 2 for denied.
	 */
	public int getStatus() {
		return status;
	}
	
	/**
	 * Gets the status description of the release request.
	 * @return the status description of the release request
	 */
	public String getStatusStr() {
		switch (status) {
		case 0:
			return "Pending";
		case 1:
			return "Approved";
		case 2:
			return "Denied";
		default:
			return "";
		}
	}
	
	/**
	 * Sets the date that the medical records release is requested 
	 * @param requestDate the date of the medical records release as a Date object
	 */
	public void setDateRequested(Timestamp requestDate) {
		this.dateRequested = (Timestamp) requestDate.clone();
	}
	
	/**
	 * Gets the date that the medical records release was requested
	 * @return the date that the medical records release was requested
	 */
	public Timestamp getDateRequested() {
		return (Timestamp) dateRequested.clone();
	}
	
	/**
	 * Gets the date of the request as a string in mm/dd/yyyy format
	 * @return the date of the request as a string 
	 */
	public String getDateRequestedStr() {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String dateString = df.format(dateRequested);
		return dateString;
	}
}

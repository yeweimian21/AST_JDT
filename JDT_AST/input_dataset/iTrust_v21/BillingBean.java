package edu.ncsu.csc.itrust.beans;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Stores information about a bill
 */
public class BillingBean {
	public static final String SUBMITTED = "Submitted";
	public static final String UNSUBMITTED = "Unsubmitted";
	public static final String PENDING = "Pending";
	public static final String APPROVED = "Approved";
	public static final String DENIED = "Denied";
	public static final int MAX_SUBMISSIONS = 2;
	
	private int billID;
	private int apptID;
	private long patient;
	private long hcp;
	private int amt;
	private String status;
	private String ccHolderName = null;
	private String billingAddress = null;
	private String ccType = null;
	private String ccNumber = null;
	private String cvv = null;
	private String insHolderName = null;
	private String insID = null;
	private String insProviderName = null;
	private String insAddress1 = null;
	private String insAddress2 = null;
	private String insCity = null;
	private String insState = null;
	private String insZip = null;
	private String insPhone = null;
	private int submissions = 0;
	private boolean isInsurance = false;
	private Date billTime = null;
	private Timestamp subTime = null;
	
	/**
	 * @return the isInsurance
	 */
	public boolean isInsurance() {
		return isInsurance;
	}
	/**
	 * @param isInsurance the isInsurance to set
	 */
	public void setInsurance(boolean isInsurance) {
		this.isInsurance = isInsurance;
	}

	/**
	 * @return the ccHolderName
	 */
	public String getCcHolderName() {
		return ccHolderName;
	}
	/**
	 * @param ccHolderName the ccHolderName to set
	 */
	public void setCcHolderName(String ccHolderName) {
		this.ccHolderName = ccHolderName;
	}
	/**
	 * @return the billingAddress
	 */
	public String getBillingAddress() {
		return billingAddress;
	}
	/**
	 * @param billingAddress the billingAddress to set
	 */
	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}
	/**
	 * @return the ccType
	 */
	public String getCcType() {
		return ccType;
	}
	/**
	 * @param ccType the ccType to set
	 */
	public void setCcType(String ccType) {
		this.ccType = ccType;
	}
	/**
	 * @return the ccNumber
	 */
	public String getCcNumber() {
		return ccNumber;
	}
	/**
	 * @param ccNumber the ccNumber to set
	 */
	public void setCcNumber(String ccNumber) {
		this.ccNumber = ccNumber;
	}
	/**
	 * @return the cvv
	 */
	public String getCvv() {
		return cvv;
	}
	/**
	 * @param cvv the cvv to set
	 */
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	/**
	 * @return the insHolderName
	 */
	public String getInsHolderName() {
		return insHolderName;
	}
	/**
	 * @param insHolderName the insHolderName to set
	 */
	public void setInsHolderName(String insHolderName) {
		this.insHolderName = insHolderName;
	}
	/**
	 * @return the insID
	 */
	public String getInsID() {
		return insID;
	}
	/**
	 * @param insID the insID to set
	 */
	public void setInsID(String insID) {
		this.insID = insID;
	}
	/**
	 * @return the insProviderName
	 */
	public String getInsProviderName() {
		return insProviderName;
	}
	/**
	 * @param insProviderName the insProviderName to set
	 */
	public void setInsProviderName(String insProviderName) {
		this.insProviderName = insProviderName;
	}
	/**
	 * @return the insAddress1
	 */
	public String getInsAddress1() {
		return insAddress1;
	}
	/**
	 * @param insAddress1 the insAddress1 to set
	 */
	public void setInsAddress1(String insAddress1) {
		this.insAddress1 = insAddress1;
	}
	/**
	 * @return the insAddress2
	 */
	public String getInsAddress2() {
		return insAddress2;
	}
	/**
	 * @param insAddress2 the insAddress2 to set
	 */
	public void setInsAddress2(String insAddress2) {
		this.insAddress2 = insAddress2;
	}
	/**
	 * @return the insCity
	 */
	public String getInsCity() {
		return insCity;
	}
	/**
	 * @param insCity the insCity to set
	 */
	public void setInsCity(String insCity) {
		this.insCity = insCity;
	}
	/**
	 * @return the insState
	 */
	public String getInsState() {
		return insState;
	}
	/**
	 * @param insState the insState to set
	 */
	public void setInsState(String insState) {
		this.insState = insState;
	}
	/**
	 * @return the insZip
	 */
	public String getInsZip() {
		return insZip;
	}
	/**
	 * @param insZip the insZip to set
	 */
	public void setInsZip(String insZip) {
		this.insZip = insZip;
	}
	/**
	 * @return the insPhone
	 */
	public String getInsPhone() {
		return insPhone;
	}
	/**
	 * @param insPhone the insPhone to set
	 */
	public void setInsPhone(String insPhone) {
		this.insPhone = insPhone;
	}

	/**
	 * @return the billID
	 */
	public int getBillID() {
		return billID;
	}
	/**
	 * @param billID the billID to set
	 */
	public void setBillID(int billID) {
		this.billID = billID;
	}
	
	/**
	 * @return the apptID
	 */
	public int getApptID() {
		return apptID;
	}
	/**
	 * @param apptID the apptID to set
	 */
	public void setApptID(int apptID) {
		this.apptID = apptID;
	}
	/**
	 * @return the patient
	 */
	public long getPatient() {
		return patient;
	}
	/**
	 * @param patient the patient to set
	 */
	public void setPatient(long patient) {
		this.patient = patient;
	}
	/**
	 * @return the hcp
	 */
	public long getHcp() {
		return hcp;
	}
	/**
	 * @param hcp the hcp to set
	 */
	public void setHcp(long hcp) {
		this.hcp = hcp;
	}
	/**
	 * @return the amt
	 */
	public int getAmt() {
		return amt;
	}
	/**
	 * @param amt the amt to set
	 */
	public void setAmt(int amt) {
		this.amt = amt;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the submissions
	 */
	public int getSubmissions() {
		return submissions;
	}
	/**
	 * @param submissions the submissions to set
	 */
	public void setSubmissions(int submissions) {
		this.submissions = submissions;
	}
	/**
	 * @return the billTime
	 */
	public Date getBillTime() {
		return new Date(billTime.getTime());
	}
	/**
	 * @param billTime the billTime to set
	 */
	public void setBillTime(Date billTime) {
		if(billTime != null)
			this.billTime = new Date(billTime.getTime());
		else
			this.billTime = null;
	}
	/**
	 * @return the subTime
	 */
	public Timestamp getSubTime() {
		return subTime != null ? new Timestamp(subTime.getTime()) : null;
	}
	/**
	 * @return the subDate
	 */
	public Date getSubDate(){
		return subTime != null ? new Date(subTime.getTime()) : null;
	}
	/**
	 * @param subTime the subTime to set
	 */
	public void setSubTime(Timestamp subTime) {
		if(subTime != null)
			this.subTime = new Timestamp(subTime.getTime());
		else
			this.subTime = null;
	}
}

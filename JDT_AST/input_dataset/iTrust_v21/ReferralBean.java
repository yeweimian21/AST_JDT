package edu.ncsu.csc.itrust.beans;

/**
 * A bean for storing data about a medical referral.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters.
 * to create these easily)
 */
public class ReferralBean {
	private long id = 0L;
	private long patientID = 0L;
	private long senderID = 0L;
	private long receiverID = 0L;
	private String referralDetails = "";
	private long ovid = 0L;
	private boolean viewedByPatient = false;
	private boolean viewedByHCP = false;
	private String timeStamp = "";
	private int priority = 3;
	

	public ReferralBean() {
	}

	@Override
	public boolean equals(Object other) {
		return (other != null) && this.getClass().equals(other.getClass())
				&& this.equals((ReferralBean) other);
	}

	private boolean equals(ReferralBean other) {
		return (id == other.id
				&& senderID == other.senderID
				&& receiverID == other.receiverID
				&& ovid == other.ovid
				&& viewedByPatient == other.viewedByPatient
				&& viewedByHCP == other.viewedByHCP
				&& timeStamp.equals(other.timeStamp)
				&& referralDetails.equals(other.referralDetails));
	}

	@Override
	public int hashCode() {
		return 42; // any arbitrary constant will do
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSenderID() {
		return senderID;
	}

	public void setSenderID(long senderID) {
		this.senderID = senderID;
	}

	public long getReceiverID() {
		return receiverID;
	}

	public void setReceiverID(long receiverID) {
		this.receiverID = receiverID;
	}

	public String getReferralDetails() {
		return referralDetails;
	}

	public void setReferralDetails(String referralDetails) {
		this.referralDetails = referralDetails;
	}

	public long getPatientID() {
		return patientID;
	}

	public void setPatientID(long patientID) {
		this.patientID = patientID;
	}

	public void setOvid(long ovid) {
		this.ovid = ovid;
	}

	public long getOvid() {
		return ovid;
	}

	public void setViewedByPatient(boolean viewedByPatient) {
		this.viewedByPatient = viewedByPatient;
	}

	public boolean isViewedByPatient() {
		return viewedByPatient;
	}

	public void setViewedByHCP(boolean viewedByHCP) {
		this.viewedByHCP = viewedByHCP;
	}

	public boolean isViewedByHCP() {
		return viewedByHCP;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getPriority() {
		return priority;
	}

	

}

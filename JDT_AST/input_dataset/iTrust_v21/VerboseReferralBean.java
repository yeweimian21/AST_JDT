package edu.ncsu.csc.itrust.beans;

/**
 * A bean for storing data about a medical referral.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters.
 * to create these easily)
 */
public class VerboseReferralBean {
	private long id = 0L;
	private long patientID = 0L;
	private long senderID = 0L;
	private long receiverID = 0L;
	private String patientName = "";
	private String senderName = "";
	private String receiverName = "";
	private String referralDetails = "";
	private long ovid = 0L;
	private String ovDate = "";
	private boolean viewedByPatient = false;
	private boolean viewedByHCP = false;
	private String timeStamp = "";
	private int priority = 3;
	

	public VerboseReferralBean() {
	}
	
	public ReferralBean toReferralBean() {
		ReferralBean bean = new ReferralBean();
		bean.setId(id);
		bean.setPatientID(patientID);
		bean.setSenderID(senderID);
		bean.setReceiverID(receiverID);
		bean.setReferralDetails(referralDetails);
		bean.setOvid(ovid);
		bean.setViewedByPatient(viewedByPatient);
		bean.setViewedByHCP(viewedByHCP);
		bean.setTimeStamp(timeStamp);
		bean.setPriority(priority);
		return bean;
	}

	@Override
	public boolean equals(Object other) {
		return (other != null) && this.getClass().equals(other.getClass())
				&& this.equals((VerboseReferralBean) other);
	}

	private boolean equals(VerboseReferralBean other) {
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

	/**
	 * @return the patientName
	 */
	public String getPatientName() {
		return patientName;
	}

	/**
	 * @param patientName the patientName to set
	 */
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	/**
	 * @return the senderName
	 */
	public String getSenderName() {
		return senderName;
	}

	/**
	 * @param senderName the senderName to set
	 */
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	/**
	 * @return the receiverName
	 */
	public String getReceiverName() {
		return receiverName;
	}

	/**
	 * @param receiverName the receiverName to set
	 */
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	/**
	 * @return the ovDate
	 */
	public String getOvDate() {
		return ovDate;
	}

	/**
	 * @param ovDate the ovDate to set
	 */
	public void setOvDate(String ovDate) {
		this.ovDate = ovDate;
	}

	

}

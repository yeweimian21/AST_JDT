package edu.ncsu.csc.itrust.beans.forms;

/**
 * A form to contain data coming from editing an office visit.
 * 
 * A form is a bean, kinda. You could say that it's a form of a bean :) 
 * Think of a form as a real-life administrative form that you would fill out to get 
 * something done, not necessarily making sense by itself.
 */
public class EditOfficeVisitForm {
	private String ovID;
	private String hcpID;
	private String patientID;
	private String hospitalID;
	private String notes;
	private String visitDate;
	private boolean isBilled;
	private String apptType;

	
	/**
	 * @return the apptType
	 */
	public String getApptType() {
		return apptType;
	}

	/**
	 * @param apptType the apptType to set
	 */
	public void setApptType(String apptType) {
		this.apptType = apptType;
	}

	/**
	 * we need to use a string because there is a bean builder class that
	 * only uses strings as parameters.
	 * @param isBilled the isBilled to set
	 */
	public void setIsBilled(String isBilled) {
		this.isBilled = "true".equals(isBilled);
	}
	
	public boolean getIsBilled() {
		return isBilled;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getOvID() {
		return ovID;
	}

	public void setOvID(String ovID) {
		this.ovID = ovID;
	}
	
	public String getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}

	public String getHcpID() {
		return hcpID;
	}

	public void setHcpID(String hcpID) {
		this.hcpID = hcpID;
	}

	public String getPatientID() {
		return patientID;
	}

	public void setPatientID(String patientID) {
		this.patientID = patientID;
	}

	public String getHospitalID() {
		return hospitalID;
	}

	public void setHospitalID(String hospitalID) {
		this.hospitalID = hospitalID;
	}

}

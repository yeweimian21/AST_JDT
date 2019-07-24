package edu.ncsu.csc.itrust.beans;

public class LabelBean {
	/**
	 * Unique Primary key so entries can be edited and deleted
	 */
	private long entryID;
	
	/**
	 * The MID of the user this Food Entry belongs to
	 */
	private long patientID;

	private String labelName;
	
	private String labelColor;
	/**
	 * Returns the id of this entry so it can be edited/deleted.
	 * @return unique id of the food entry
	 */
	public long getEntryID() {
		return entryID;
	}
	
	/**
	 * Sets the id of this entry
	 * @param id the unique id of a food entry
	 */
	public void setEntryID(long id) {
		entryID = id;
	}
	
	/**
	 * The patient that ate this meal
	 * @return patient ID that ate this meal
	 */
	public long getPatientID() {
		return patientID;
	}
	
	/**
	 * Patient that ate this meal
	 * @param patientID patient id of who ate this meal
	 */
	public void setPatientID(long patientID) {
		this.patientID = patientID;
	}
	
	public String getLabelName() {
		return labelName;
	}
	
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	
	public String getLabelColor() {
		return labelColor;
	}
	
	public void setLabelColor(String labelColor) {
		this.labelColor = labelColor;
	}
}

package edu.ncsu.csc.itrust.beans;

import java.io.Serializable;
import java.sql.Timestamp;


public class ApptBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1965704529780021183L;
	private String apptType;
	private int apptID;
	private long patient;
	private long hcp;
	private Timestamp date;
	private String comment;
	private int price;
	
	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * @return the apptType
	 */
	public String getApptType() {
		return apptType;
	}
	
	/**
	 * @param apptID the apptID to set
	 */
	public void setApptID(int apptID) {
		this.apptID = apptID;
	}
	
	public int getApptID() {
		return apptID;
	}
	
	/**
	 * @param apptType the apptType to set
	 */
	public void setApptType(String apptType) {
		this.apptType = apptType;
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
	 * @return the date
	 */
	public Timestamp getDate() {
		return (Timestamp) date.clone();
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Timestamp date) {
		this.date = (Timestamp) date.clone();
	}
	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@Override
	public int hashCode() {
		return apptID; // any arbitrary constant will do
	}
	
	/**
	 * Returns true if both id's are equal. Probably needs more advance field by field checking.
	 */
	@Override public boolean equals(Object other) {
	   
	    if ( this == other ){
	    	return true;
	    }

	    if ( !(other instanceof ApptBean) ){
	    	return false;
	    }
	    
	    ApptBean otherAppt = (ApptBean)other;
		return otherAppt.getApptID() == getApptID();
	    
	}
}

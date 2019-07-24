package edu.ncsu.csc.itrust.beans;

import java.sql.Timestamp;

/**
 * Bean for Scheduled Ophthalmology Office Visits.
 * Holds all of the data needed for a scheduled Ophthalmology Office Visit.
 */
public class OphthalmologyScheduleOVRecordBean {

	/**The mid of the patient.*/
	private long patientmid;
	/**The mid of the doctor.*/
	private long doctormid;
	/**The scheduled Ophthalmology office visit id.*/
	private long oid;
	/**The time of the scheduled Ophthalmology office visit.*/
	private Timestamp date;
	/**The last name of the optometrist.*/
	private String docLastName;
	/**The first name of the optometrist.*/
	private String docFirstName;
	/**The comment attached to the office visit request.*/
	private String comment;
	/**The status of the requested ophthalmology office visit.*/
	private Boolean status;
	
	/**
	 * Getter for the patientmid value.
	 * @return the patientmid.
	 */
	public long getPatientmid() {
		return patientmid;
	}
	
	/**
	 * Set the patientmid value.
	 * @param patientmid the new value.
	 */
	public void setPatientmid(long patientmid) {
		this.patientmid = patientmid;
	}
	
	/**
	 * Getter for the doctormid value.
	 * @return the doctormid.
	 */
	public long getDoctormid() {
		return doctormid;
	}
	
	/**
	 * Set the doctormid value.
	 * @param doctormid the new value.
	 */
	public void setDoctormid(long doctormid) {
		this.doctormid = doctormid;
	}
	
	/**
	 * Getter for the oid value.
	 * @return the oid.
	 */
	public long getOid() {
		return oid;
	}
	
	/**
	 * Set the oid value.
	 * @param oid the new value.
	 */
	public void setOid(long oid) {
		this.oid = oid;
	}
	
	/**
	 * Get the time of the scheduled appointment.
	 * @return the date.
	 */
	public Timestamp getDate() {
		return date;
	}
	
	/**
	 * Set the time of the scheduled appointment.
	 * @param date the new value.
	 */
	public void setDate(Timestamp date) {
		this.date = date;
	}
	
	/**
	 * Getter for the last name of the optometrist.
	 * @return the optometrist last name.
	 */
	public String getDocLastName() {
		return docLastName;
	}
	
	/**
	 * Set the last name of the optometrist.
	 * @param docLastName the new value.
	 */
	public void setDocLastName(String docLastName) {
		this.docLastName = docLastName;
	}
	
	/**
	 * Getter for the first name of the optometrist.
	 * @return the doctors first name.
	 */
	public String getDocFirstName() {
		return docFirstName;
	}
	
	/**
	 * Set the first name of the optometrist.
	 * @param docFirstName the new value.
	 */
	public void setDocFirstName(String docFirstName) {
		this.docFirstName = docFirstName;
	}
	
	/**
	 * Getter for the comment.
	 * @return the comment.
	 */
	public String getComment() {
		return comment;
	}
	
	/**
	 * Set the comment.
	 * @param comment the new value.
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	/**
	 * Check whether the scheduled appointment is pending.
	 * @return status
	 */
	public boolean isPending() {
		return status == null;
	}
	
	/**
	 * Set if the scheduled appointment is pending.
	 * @param pending the new value.
	 */
	public void setPending(boolean pending) {
		if (pending) {
			status = null;
		} else {
			status = Boolean.valueOf(false);
		}
	}

	/**
	 * Check whether the scheduled appointment is pending.
	 * @return status
	 */
	public boolean isAccepted() {
		return status != null && status.booleanValue();
	}
	
	/**
	 * Set if the scheduled appointment is accepted.
	 * If setPending(false) has not been called before using this method, this method will have no effect.
	 * @param accepted accepted
	 */
	public void setAccepted(boolean accepted) {
		if (status != null) {
			status = Boolean.valueOf(accepted);
		}
	}

	/**
	 * Method used to compute the hashcode for a OphthalmologyOVRecordBean. 
	 * @return true if the OphthalmologyOVRecordBeans are equal, false otherwise.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((docFirstName == null) ? 0 : docFirstName.hashCode());
		result = prime * result
				+ ((docLastName == null) ? 0 : docLastName.hashCode());
		result = prime * result + (int) (doctormid ^ (doctormid >>> 32));
		result = prime * result + (int) (oid ^ (oid >>> 32));
		result = prime * result + (int) (patientmid ^ (patientmid >>> 32));
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	/**
	 * Method used to determine if OphthalmologyOVRecordBeans are equal. 
	 * @return true if the OphthalmologyOVRecordBeans are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OphthalmologyScheduleOVRecordBean other = (OphthalmologyScheduleOVRecordBean) obj;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (docFirstName == null) {
			if (other.docFirstName != null)
				return false;
		} else if (!docFirstName.equals(other.docFirstName))
			return false;
		if (docLastName == null) {
			if (other.docLastName != null)
				return false;
		} else if (!docLastName.equals(other.docLastName))
			return false;
		if (doctormid != other.doctormid)
			return false;
		if (oid != other.oid)
			return false;
		if (patientmid != other.patientmid)
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	/**
	 * Creates a string representation of this object.
	 * @return The string representation.
	 */
	@Override
	public String toString() {
		return "OphthalmologyScheduleOVRecordBean [patientmid=" + patientmid
				+ ", doctormid=" + doctormid + ", oid=" + oid + ", date="
				+ date + ", docLastName=" + docLastName + ", docFirstName="
				+ docFirstName + ", comment=" + comment + ", status=" + status
				+ "]";
	}
	
}

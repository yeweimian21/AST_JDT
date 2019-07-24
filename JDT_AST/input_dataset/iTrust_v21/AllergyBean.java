package edu.ncsu.csc.itrust.beans;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A bean for storing data about Allergies.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to
 * be added to a bean (with the exception of minor formatting such as
 * concatenating phone numbers together). A bean must only have Getters and
 * Setters (Eclipse Hint: Use Source > Generate Getters and Setters.to create
 * these easily)
 */
public class AllergyBean {
	private long id;
	private long patientID;
	private String description;
	private String ndcode;
	private Date firstFound;

	/**
	 * Default constructor.
	 */
	public AllergyBean() {
	}

	/**
	 * Returns the description for the allergy.
	 * 
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description for the allergy.
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the ND code for the allergy.
	 * 
	 * @return
	 */
	public String getNDCode() {
		return ndcode;
	}

	/**
	 * Sets the ND code for the allergy.
	 * 
	 * @param ndcode
	 */
	public void setNDCode(String ndcode) {
		this.ndcode = ndcode;
	}

	/**
	 * Returns the date the allergy was first found.
	 * 
	 * @return
	 */
	public Date getFirstFound() {
		if (firstFound == null) {
			return null;
		}
		return (Date) firstFound.clone();
	}

	/**
	 * Sets the date the allergy was first found.
	 * 
	 * @param firstFound
	 */
	public void setFirstFound(Date firstFound) {
		if (null != firstFound)
			this.firstFound = (Date) firstFound.clone();
		else
			this.firstFound = null;
	}

	/**
	 * Returns the allergy ID.
	 * 
	 * @return
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the allergy ID.
	 * 
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Returns the description of the allergy.
	 */
	@Override
	public String toString() {
		return this.description;
	}

	/**
	 * Returns the patient ID.
	 * 
	 * @return
	 */
	public long getPatientID() {
		return patientID;
	}

	/**
	 * Sets the patient ID.
	 * 
	 * @param patientID
	 */
	public void setPatientID(long patientID) {
		this.patientID = patientID;
	}

	/**
	 * Returns the date first found as a String.
	 * 
	 * @return
	 */
	public String getFirstFoundStr() {
		try {
			return new SimpleDateFormat("MM/dd/yyyy").format(getFirstFound());
		} catch (Exception e) {

			return "";
		}
	}
}

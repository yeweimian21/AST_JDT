/**
 * 
 */
package edu.ncsu.csc.itrust.beans;

import java.io.Serializable;
import java.util.Date;

/**
 * A bean for storing data about patient-specific instructions given during an
 * office visit.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters
 * to create these easily)
 */
public class PatientInstructionsBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5599914802451418319L;
	
	private long id;
	private long visitID;
	private String name;
	private String url;
	private String comment;
	private Date modified;
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the visitID
	 */
	public long getVisitID() {
		return visitID;
	}
	/**
	 * @param visitID the visitID to set
	 */
	public void setVisitID(long visitID) {
		this.visitID = visitID;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
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
	/**
	 * @return the modified
	 */
	public Date getModified() {
		return (Date) modified.clone();
	}
	/**
	 * @param modified the modified to set
	 */
	public void setModified(Date modified) {
		this.modified = (Date) modified.clone();
	}

}

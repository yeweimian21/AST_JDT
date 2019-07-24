package edu.ncsu.csc.itrust.beans;

/**
 * A bean for storing data about Diagnosis.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters� 
 * to create these easily)
 */
public class OphthalmologyDiagnosisBean {
	private long ovDiagnosisID = 0L; // optional
	private String icdCode;
	private String description;
	private String classification;
	private String URL = "";
	private long visitID;

	/**
	 * diagnosisBean
	 */
	public OphthalmologyDiagnosisBean() {
	}

	
	/**
	 * OphthalmologyDiagnosisBean
	 * @param code code
	 * @param description description
	 * @param classification classification
	 * @param URL URL
	 */
	public OphthalmologyDiagnosisBean(String code, String description, String classification, String URL) {
		this.icdCode = code;
		this.description = description;
		this.URL = URL;
		
		if (null != classification && classification.equals("yes")) {
			this.classification = classification;
		}
		else {
			this.classification = "no";
		}
	}


	/**
	 * Gets the ICD Code for this procedure
	 * 
	 * @return The ICD Code for this procedure
	 */
	public String getICDCode() {
		return icdCode;
	}

	/**
	 * setICDCode
	 * @param code code
	 */
	public void setICDCode(String code) {
		icdCode = code;
	}
	
	/**
	 * getClassification
	 * @return classification
	 */
	public String getClassification() {
		return classification;
	}

	/**
	 * Gets the ICD Description for this procedure
	 * 
	 * @return The ICD Description for this procedure
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * setDescription
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * getFormattedDescription
	 * @return description
	 */
	public String getFormattedDescription() {
		return description + "(" + icdCode + ")";
	}

	/**
	 * Optional - for use with editing an office visit
	 * 
	 * @return opDiagnosisID
	 */
	public long getOpDiagnosisID() {
		return ovDiagnosisID;
	}

	/**
	 * setOpDiagnosisID
	 * @param opDiagnosisID
	 */
	public void setOpDiagnosisID(long ovDiagnosisID) {
		this.ovDiagnosisID = ovDiagnosisID;
	}

	/**
	 * setVisitID
	 * @param vid
	 */
	public void setVisitID(long vid) {
		visitID = vid;
	}
	
	/**
	 * getVisitID
	 * @return visitID
	 */
	public long getVisitID() {
		return visitID;
	}
	
	/**
	 * setURL
	 * @param newURL
	 */
	public void setURL(String newURL){
		URL = newURL;
	}
	
	/**
	 * getURL
	 * @return URL
	 */
	public String getURL(){
		return URL;
	}
}

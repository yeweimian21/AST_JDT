package edu.ncsu.csc.itrust.beans;

/**
 * Bean for required procedures.
 * Holds data for all immunizations and procedures that are required
 * in order for patients to enter into public schools, etc.
 */
public class RequiredProceduresBean {

	/** Code for the procedure */
	private String cptCode;
	/** Description of the procedure */
	private String description;
	/** Age group that the procedure affects (kindergarten, sixth grade, or college age) */
	private int ageGroup;
	/** Type of procedure (ie: immunization) */
	private String attribute;
	/** Max age that the patient can be before the procedure is no longer required */
	private int ageMax;
	
	/**
	 * Construct a new, empty bean.
	 */
	public RequiredProceduresBean() {
		
	}
	
	/**
	 * Get the CPT code for the bean.
	 * @return CPT code
	 */
	public String getCptCode() {
		return cptCode;
	}
	
	/**
	 * Set the CPT code for the bean.
	 * @param cptCode CPT code for the procedure
	 */
	public void setCptCode(String cptCode) {
		this.cptCode = cptCode;
	}
	
	/**
	 * Get the description of the procedure for the bean.
	 * @return description of the procedure
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Set the description of the procedure for the bean.
	 * @param description description of the procedure
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Get the age group for the bean.
	 * @return age group (kindergarten, sixth grade, or college age)
	 */
	public int getAgeGroup() {
		return ageGroup;
	}
	
	/**
	 * Set the age group for the bean.
	 * @param ageGroup 0 if kindergarten, 1 if sixth grade, or 2 if college age)
	 */
	public void setAgeGroup(int ageGroup) {
		this.ageGroup = ageGroup;
	}
	
	/**
	 * Get the attribute for the bean.
	 * @return type of procedure
	 */
	public String getAttribute() {
		return attribute;
	}
	
	/**
	 * Set the attribute for the bean.
	 * @param attribute type of procedure
	 */
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	
	/**
	 * Get the max age for the bean.
	 * @return max age before procedure no longer required
	 */
	public int getAgeMax() {
		return ageMax;
	}
	
	/**
	 * Set the max age for the bean.
	 * @param age max age before procedure no longer required
	 */
	public void setAgeMax(int age) {
		this.ageMax = age;
	}
}

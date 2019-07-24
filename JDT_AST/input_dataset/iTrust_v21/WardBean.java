package edu.ncsu.csc.itrust.beans;

/**
 * A bean for storing data about a Ward.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters.
 * to create these easily)
 */
public class WardBean {
	

	long wardID = 0;
	String requiredSpecialty = "";
	long inHospital = 0;
	
	public WardBean(long wardID, String requiredSpecialty, long inHospital){
		this.wardID = wardID;
		this.requiredSpecialty= requiredSpecialty; 
		this.inHospital = inHospital;
	}
	
	public long getWardID() {
		return wardID;
	}

	public void setWardID(long wardID) {
		this.wardID = wardID;
	}

	public String getRequiredSpecialty() {
		return requiredSpecialty;
	}

	public void setRequiredSpecialty(String requiredSpecialty) {
		this.requiredSpecialty = requiredSpecialty;
	}

	public long getInHospital() {
		return inHospital;
	}

	public void setInHospital(long inHospital) {
		this.inHospital = inHospital;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj != null && obj.getClass().equals(this.getClass()) && this.equals((WardBean) obj);
	}

	@Override
	public int hashCode() {
		return 42; // any arbitrary constant will do
	}

	private boolean equals(WardBean other) {
		return wardID == other.wardID && requiredSpecialty.equals(other.requiredSpecialty);
	}
}

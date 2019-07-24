package edu.ncsu.csc.itrust.beans;

/**
 * A bean for storing data about a visit with an HCP.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters.
 * to create these easily)
 */
public class HCPVisitBean {
	private String HCPName;
	private String HCPSpecialty;
	private String HCPAddr;

	private String OVDate;
	private boolean designated;
	private long hcpMID;
	
	public HCPVisitBean() {
		HCPName = "";
		HCPSpecialty = "";
		HCPAddr = "";
		OVDate = "";
		
	}
	
	public void setHCPMID(long mid) {
		hcpMID = mid;
	}
	
	public long getHCPMID() {
		return hcpMID;
	}
	
	public String getHCPName() {
		return HCPName;
	}
	
	public void setHCPName(String name) {
		if (null != name) {
			HCPName = name;
		}
	}
	
	public String getHCPSpecialty() {
		return HCPSpecialty;
	}
	
	public void setHCPSpecialty(String specialty) {
		if (null != specialty) {
			HCPSpecialty = specialty;
		}
		else {
			HCPSpecialty = "none";
		}
	}
	
	public String getHCPAddr() {
		return HCPAddr;
	}
	
	public void setHCPAddr(String addr) {
		if (null != addr) {
			HCPAddr = addr;
		}
	}
	
	public String getOVDate() {
		return OVDate;
	}
	
	public void setOVDate(String date) {
		if (null != date) {
			OVDate = date;
		}
	}
	
	public boolean isDesignated() {
		return designated;
	}
	
	public void setDesignated(boolean val) {
		designated = val;
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((HCPAddr == null) ? 0 : HCPAddr.hashCode());
		result = prime * result + ((HCPName == null) ? 0 : HCPName.hashCode());
		result = prime * result + ((HCPSpecialty == null) ? 0 : HCPSpecialty.hashCode());
		result = prime * result + ((OVDate == null) ? 0 : OVDate.hashCode());
		result = prime * result + (designated ? 1231 : 1237);
		result = prime * result + (int) (hcpMID ^ (hcpMID >>> 32));
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HCPVisitBean other = (HCPVisitBean) obj;
		if (HCPAddr == null) {
			if (other.HCPAddr != null)
				return false;
		} else if (!HCPAddr.equals(other.HCPAddr))
			return false;
		if (HCPName == null) {
			if (other.HCPName != null)
				return false;
		} else if (!HCPName.equals(other.HCPName))
			return false;
		if (HCPSpecialty == null) {
			if (other.HCPSpecialty != null)
				return false;
		} else if (!HCPSpecialty.equals(other.HCPSpecialty))
			return false;
		if (OVDate == null) {
			if (other.OVDate != null)
				return false;
		} else if (!OVDate.equals(other.OVDate))
			return false;
		if (designated != other.designated)
			return false;
		if (hcpMID != other.hcpMID)
			return false;
		return true;
	}
	
}

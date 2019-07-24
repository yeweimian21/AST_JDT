package edu.ncsu.csc.itrust.beans;

/**
 * A reason code is like "Aspirin". A reason code is not associated with an
 * office visit; that's a reason associated with a "prescription". See {@link PrescriptionBean}
 * 
 */
public class OverrideReasonBean {
	private long id;
	private long presID;
	private String reasonCode;
	private String description;

	public OverrideReasonBean() {
		description = null;
		reasonCode = "";
	}
	public OverrideReasonBean(String code) {
		reasonCode = code;
	}

	public OverrideReasonBean(String code, String description) {
		reasonCode = code;
		this.description = description;
	}
	
	public long getPresID() {
		return presID;
	}

	public void setPresID(long id) {
		this.presID = id;
	}
	
	public long getID() {
		return id;
	}

	public void setID(long id) {
		this.id = id;
	}

	/**
	 * Gets the reason Code for this procedure
	 * 
	 * @return The reason Code for this procedure
	 */
	public String getORCode() {
		return reasonCode;
	}

	public void setORCode(String code) {
		reasonCode = code;
	}

	/**
	 * Gets the reason Description for this procedure
	 * 
	 * @return The reason Description for this procedure
	 */
	public String getDescription() {
		return description;
	}
	

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		return 42; // any arbitrary constant will do
	}
	
	@Override
	public boolean equals(Object other) {
		if ((other == null) || !this.getClass().equals(other.getClass()))
			return false;
	
		OverrideReasonBean orb = (OverrideReasonBean)other;
		return (orb.description.equals(description)
				&& orb.reasonCode.equals(reasonCode)
				&& orb.presID == presID
				&& orb.id == id);
	}

}

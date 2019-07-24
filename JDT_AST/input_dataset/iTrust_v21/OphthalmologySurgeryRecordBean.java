package edu.ncsu.csc.itrust.beans;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Bean for Ophthalmology Office Visits.
 * Holds all of the data needed for an Ophthalmology Office Visit.
 */
public class OphthalmologySurgeryRecordBean {
	
	/**The mid of the user.*/
	private long mid;
	/**The Ophthalmology office visit id.*/
	private long oid;
	/**The date of the Ophthalmology office visit.*/
	private String visitDate;
	/**The last name of the optometrist.*/
	private String docLastName;
	/**The first name of the optometrist.*/
	private String docFirstName;
	/**The visual acuity numerator for the OD.*/
	private Integer vaNumOD;
	/**The visual acuity denumerator for the OS.*/
	private Integer vaDenOD;
	/**The visual acuity numerator for the OD.*/
	private Integer vaNumOS;
	/**The visual acuity denumerator for the OS.*/
	private Integer vaDenOS;
	/**The sphere value for the OD.*/
	private Double sphereOD;
	/**The sphere value for the OS.*/
	private Double sphereOS;
	/**The cylinder value for the OD.*/
	private Double cylinderOD;
	/**The cylinder value for the OS.*/
	private Double cylinderOS;
	/**The axis value for the OD.*/
	private Integer axisOD;
	/**The axis value for the OS.*/
	private Integer axisOS;
	/**The add value for the OD.*/
	private Double addOD;
	/**The add value for the OS.*/
	private Double addOS;
	/**Surgery*/
	private String surgery;
	/**Surgery notes*/
	private String surgeryNotes;
	
	/**
	 * Getter for the mid value.
	 * @return the mid.
	 */
	public long getMid() {
		return mid;
	}
	
	/**
	 * Set the mid value.
	 * @param mid the new value.
	 */
	public void setMid(long mid) {
		this.mid = mid;
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
	 * Getter for the visitDate as a Date object.
	 * @return the visit date.
	 */
	public Date getVisitDate(){
		if (visitDate == null)
			return null;
		Date date = null; 
		try {
			date = new SimpleDateFormat("MM/dd/yyyy").parse(visitDate);
		} catch (ParseException e) {
			//If it can't be parsed, return null.
			return null;
		}
		return date;
	}
	
	/**
	 * Getter for the visitDate as a string.
	 * @return the visitDate.
	 */
	public String getVisitDateString() {
		return visitDate;
	}
	
	/**
	 * Set the visitDate value
	 * @param visitDate the new value.
	 */
	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}
	
	/**
	 * Getter for the last name of the optometrist.
	 * @return the optometrist last name.
	 */
	public String getLastName(){
		return this.docLastName;
	}
	
	/**
	 * Set the last name of the optometrist.
	 * @param docLastName the new value.
	 */
	public void setLastName(String docLastName){
		this.docLastName = docLastName;
	}

	/**
	 * Getter for the first name of the optometrist.
	 * @return the doctors first name.
	 */
	public String getFirstName(){
		return this.docFirstName;
	}
	
	/**
	 * Set the first name of the optometrist.
	 * @param docFirstName the new value.
	 */
	public void setFirstName(String docFirstName){
		this.docFirstName = docFirstName;
	}
	
	/**
	 * Getter for the visual acuity numerator value for the OD.
	 * @return the vaNum.
	 */
	public Integer getVaNumOD() {
		return vaNumOD;
	}
	
	/**
	 * Set the visual acuity numerator value for the OD.
	 * @param vaNum the new value.
	 */
	public void setVaNumOD(Integer vaNumOD) {
		this.vaNumOD = vaNumOD;
	}
	
	/**
	 * Getter for the visual acuity denumerator value for the OD.
	 * @return the vaDen.
	 */
	public Integer getVaDenOD() {
		return vaDenOD;
	}
	
	/**
	 * Set the visual acuity denumerator value for the OD.
	 * @param vaDen the new value.
	 */
	public void setVaDenOD(Integer vaDenOD) {
		this.vaDenOD = vaDenOD;
	}
	
	/**
	 * Getter for the visual acuity numerator value for the OS.
	 * @return the vaNum.
	 */
	public Integer getVaNumOS() {
		return vaNumOS;
	}
	
	/**
	 * Set the visual acuity numerator value for the OS.
	 * @param vaNum the new value.
	 */
	public void setVaNumOS(Integer vaNumOS) {
		this.vaNumOS = vaNumOS;
	}
	
	/**
	 * Getter for the visual acuity denumerator value for the OS.
	 * @return the vaDen.
	 */
	public Integer getVaDenOS() {
		return vaDenOS;
	}
	
	/**
	 * Set the visual acuity denumerator value for the OS.
	 * @param vaDen the new value.
	 */
	public void setVaDenOS(Integer vaDenOS) {
		this.vaDenOS = vaDenOS;
	}
	
	/**
	 * Getter for the sphere OD value.
	 * @return the sphereOD.
	 */
	public Double getSphereOD() {
		return sphereOD;
	}
	
	/**
	 * Set the sphere OD value.
	 * @param sphereOD the new value.
	 */
	public void setSphereOD(Double sphereOD) {
		this.sphereOD = sphereOD;
	}
	
	/**
	 * Getter for the sphere OS value.
	 * @return the sphereOS.
	 */
	public Double getSphereOS() {
		return sphereOS;
	}
	
	/**
	 * Set the sphere OS value.
	 * @param sphereOS the new value.
	 */
	public void setSphereOS(Double sphereOS) {
		this.sphereOS = sphereOS;
	}
	
	/**
	 * Getter for the cylinder OD value.
	 * @return the cylinderOD.
	 */
	public Double getCylinderOD() {
		return cylinderOD;
	}
	
	/**
	 * Set the cylinder OD value.
	 * @param cylinderOD the new value.
	 */
	public void setCylinderOD(Double cylinderOD) {
		this.cylinderOD = cylinderOD;
	}
	
	/**
	 * Getter for the cylinder OS value.
	 * @return the cylinderOS.
	 */
	public Double getCylinderOS() {
		return cylinderOS;
	}
	
	/**
	 * Set the cylinder OS value.
	 * @param cylinderOS the new value.
	 */
	public void setCylinderOS(Double cylinderOS) {
		this.cylinderOS = cylinderOS;
	}
	
	/**
	 * Getter for the axis OD value.
	 * @return the axisOD.
	 */
	public Integer getAxisOD() {
		return axisOD;
	}
	
	/**
	 * Set the axis OD value.
	 * @param axisOD the new value.
	 */
	public void setAxisOD(Integer axisOD) {
		this.axisOD = axisOD;
	}
	
	/**
	 * Getter for the axis OS value.
	 * @return the axisOS.
	 */
	public Integer getAxisOS() {
		return axisOS;
	}
	
	/**
	 * Set the axis OS value.
	 * @param axisOS the new value.
	 */
	public void setAxisOS(Integer axisOS) {
		this.axisOS = axisOS;
	}
	
	/**
	 * Getter for the add OD value.
	 * @return the addOD.
	 */
	public Double getAddOD() {
		return addOD;
	}
	
	/**
	 * Set the add OD value.
	 * @param addOD the new value.
	 */
	public void setAddOD(Double addOD) {
		this.addOD = addOD;
	}
	
	/**
	 * Getter for the add OS value.
	 * @return the addOS.
	 */
	public Double getAddOS() {
		return addOS;
	}
	
	/**
	 * Set the add OS value.
	 * @param addOS the new value.
	 */
	public void setAddOS(Double addOS) {
		this.addOS = addOS;
	}
	
	/**
	 * @return the surgery
	 */
	public String getSurgery() {
		return surgery;
	}

	/**
	 * @param surgery the surgery to set
	 */
	public void setSurgery(String surgery) {
		this.surgery = surgery;
	}

	/**
	 * @return the surgeryNotes
	 */
	public String getSurgeryNotes() {
		return surgeryNotes;
	}

	/**
	 * @param surgeryNotes the surgeryNotes to set
	 */
	public void setSurgeryNotes(String surgeryNotes) {
		this.surgeryNotes = surgeryNotes;
	}

	/**
	 * Method used to compute the hashcode for a OphthalmologySurgeryRecordBean. 
	 * @return true if the OphthalmologySurgeryRecordBeans are equal, false otherwise.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addOD == null) ? 0 : addOD.hashCode());
		result = prime * result + ((addOS == null) ? 0 : addOS.hashCode());
		result = prime * result + ((axisOD == null) ? 0 : axisOD.hashCode());
		result = prime * result + ((axisOS == null) ? 0 : axisOS.hashCode());
		result = prime * result
				+ ((cylinderOD == null) ? 0 : cylinderOD.hashCode());
		result = prime * result
				+ ((cylinderOS == null) ? 0 : cylinderOS.hashCode());
		result = prime * result
				+ ((docFirstName == null) ? 0 : docFirstName.hashCode());
		result = prime * result
				+ ((docLastName == null) ? 0 : docLastName.hashCode());
		result = prime * result + (int) (mid ^ (mid >>> 32));
		result = prime * result + (int) (oid ^ (oid >>> 32));
		result = prime * result
				+ ((sphereOD == null) ? 0 : sphereOD.hashCode());
		result = prime * result
				+ ((sphereOS == null) ? 0 : sphereOS.hashCode());
		result = prime * result + ((vaDenOD == null) ? 0 : vaDenOD.hashCode());
		result = prime * result + ((vaDenOS == null) ? 0 : vaDenOS.hashCode());
		result = prime * result + ((vaNumOD == null) ? 0 : vaNumOD.hashCode());
		result = prime * result + ((vaNumOS == null) ? 0 : vaNumOS.hashCode());
		result = prime * result
				+ ((visitDate == null) ? 0 : visitDate.hashCode());
		result = prime * result
				+ ((surgery == null) ? 0 : surgery.hashCode());
		result = prime * result
				+ ((surgeryNotes == null) ? 0 : surgeryNotes.hashCode());
		return result;
	}

	/**
	 * Method used to determine if OphthalmologySurgeryRecordBeans are equal. 
	 * @return true if the OphthalmologySurgeryRecordBeans are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OphthalmologySurgeryRecordBean other = (OphthalmologySurgeryRecordBean) obj;
		if (addOD == null) {
			if (other.addOD != null)
				return false;
		} else if (!addOD.equals(other.addOD))
			return false;
		if (addOS == null) {
			if (other.addOS != null)
				return false;
		} else if (!addOS.equals(other.addOS))
			return false;
		if (axisOD == null) {
			if (other.axisOD != null)
				return false;
		} else if (!axisOD.equals(other.axisOD))
			return false;
		if (axisOS == null) {
			if (other.axisOS != null)
				return false;
		} else if (!axisOS.equals(other.axisOS))
			return false;
		if (cylinderOD == null) {
			if (other.cylinderOD != null)
				return false;
		} else if (!cylinderOD.equals(other.cylinderOD))
			return false;
		if (cylinderOS == null) {
			if (other.cylinderOS != null)
				return false;
		} else if (!cylinderOS.equals(other.cylinderOS))
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
		if (mid != other.mid)
			return false;
		if (oid != other.oid)
			return false;
		if (sphereOD == null) {
			if (other.sphereOD != null)
				return false;
		} else if (!sphereOD.equals(other.sphereOD))
			return false;
		if (sphereOS == null) {
			if (other.sphereOS != null)
				return false;
		} else if (!sphereOS.equals(other.sphereOS))
			return false;
		if (vaDenOD == null) {
			if (other.vaDenOD != null)
				return false;
		} else if (!vaDenOD.equals(other.vaDenOD))
			return false;
		if (vaDenOS == null) {
			if (other.vaDenOS != null)
				return false;
		} else if (!vaDenOS.equals(other.vaDenOS))
			return false;
		if (vaNumOD == null) {
			if (other.vaNumOD != null)
				return false;
		} else if (!vaNumOD.equals(other.vaNumOD))
			return false;
		if (vaNumOS == null) {
			if (other.vaNumOS != null)
				return false;
		} else if (!vaNumOS.equals(other.vaNumOS))
			return false;
		if (visitDate == null) {
			if (other.visitDate != null)
				return false;
		} else if (!visitDate.equals(other.visitDate))
			return false;
		if (surgery == null) {
			if (other.surgery != null)
				return false;
		} else if (!surgery.equals(other.surgery))
			return false;
		if (surgeryNotes == null) {
			if (other.surgeryNotes != null)
				return false;
		} else if (!surgeryNotes.equals(other.surgeryNotes))
			return false;
		return true;
	}

	/**
	 * Creates a string representation of this object.
	 * @return The string representation.
	 */
	@Override
	public String toString() {
		return "OphthalmologySurgeryRecordBean [mid=" + mid + ", oid=" + oid
				+ ", visitDate=" + visitDate + ", docLastName=" + docLastName
				+ ", docFirstName=" + docFirstName + ", vaNumOD=" + vaNumOD
				+ ", vaDenOD=" + vaDenOD + ", vaNumOS=" + vaNumOS
				+ ", vaDenOS=" + vaDenOS + ", sphereOD=" + sphereOD
				+ ", sphereOS=" + sphereOS + ", cylinderOD=" + cylinderOD
				+ ", cylinderOS=" + cylinderOS + ", axisOD=" + axisOD
				+ ", axisOS=" + axisOS + ", addOD=" + addOD + ", addOS="
				+ addOS + ", surgery=" + surgery + ",surgeryNotes=" 
				+ surgeryNotes + "]";
	}
}
package edu.ncsu.csc.itrust.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A bean for storing data about a prescription.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters.
 * to create these easily)
 */
public class PrescriptionBean {
	private long id = 0L;
	private MedicationBean medication = new MedicationBean();
	private long visitID = 0L;
	private String startDateStr = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
	private String endDateStr = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
	private int dosage = 0;
	private String instructions = "";
	private List<OverrideReasonBean> reasons = null;
	private String overrideReasonOther = "";
	public PrescriptionBean() {
	}

	@Override
	public boolean equals(Object other) {
		return (other != null) && this.getClass().equals(other.getClass())
				&& this.equals((PrescriptionBean) other);
	}

	/**
	 * @param other
	 * @return
	 */
	private boolean equals(PrescriptionBean other) {
		return (medication == other.medication || (medication != null && medication.equals(other.medication)))
				&& visitID == other.visitID
				&& startDateStr.equals(other.startDateStr)
				&& endDateStr.equals(other.endDateStr)
				&& dosage == other.dosage
				&& instructions.equals(other.instructions);
	}

	@Override
	public int hashCode() {
		return 42; // any arbitrary constant will do
	}
/**
 * getters and setters for dosage,
 * reason, override reason
 */
	public int getDosage() {
		return dosage;
	}

	public void setDosage(int dosage) {
		this.dosage = dosage;
	}
	
	public List<OverrideReasonBean> getReasons() {
		if (reasons==null){
			reasons = new ArrayList<OverrideReasonBean>();
		}
		return reasons;
	}

	public void setReasons(List<OverrideReasonBean> reasons) {
		this.reasons = reasons;
	}
	
	public void addReason(OverrideReasonBean reason){
		if(reasons == null){
			reasons = new ArrayList<OverrideReasonBean>();
		}
		reasons.add(reason);
	}

	public Date getEndDate() {
		try {
			return new SimpleDateFormat("MM/dd/yyyy").parse(endDateStr);
		} catch (java.text.ParseException e) {
			return null;
		}
	}

	public void setEndDateStr(String endDate) {
		this.endDateStr = endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDateStr = new SimpleDateFormat("MM/dd/yyyy").format(endDate);
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instruction) {
		this.instructions = instruction;
	}

	public MedicationBean getMedication() {
		return medication;
	}

	public void setMedication(MedicationBean medication) {
		this.medication = medication;
	}

	public Date getStartDate() {
		try {
			return new SimpleDateFormat("MM/dd/yyyy").parse(startDateStr);
		} catch (ParseException e) {
			return null;
		}
	}

	public String getStartDateStr() {
		return startDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setStartDateStr(String startDate) {
		this.startDateStr = startDate;
	}

	public long getVisitID() {
		return visitID;
	}

	public void setVisitID(long visitID) {
		this.visitID = visitID;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOverrideReasonOther() {
		return overrideReasonOther;
	}

	public void setOverrideReasonOther(String overrideReasonOther) {
		this.overrideReasonOther = overrideReasonOther;
	}


}
